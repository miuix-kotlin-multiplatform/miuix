package top.yukonga.miuix.kmp.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.sign
import kotlin.math.sqrt

// form https://github.com/Cormor/ComposeOverscroll
/**
 * A parabolic rolling easing curve.
 *
 * When rolling in the same direction, the farther away from 0, the greater the "resistance"; the closer to 0, the smaller the "resistance";
 *
 * No drag effect is applied when the scrolling direction is opposite to the currently existing overscroll offset
 *
 * Note: when [p] = 50f, its performance should be consistent with iOS
 * @param currentOffset Offset value currently out of bounds
 * @param newOffset The offset of the new scroll
 * @param p Key parameters for parabolic curve calculation
 * @param density Without this param, the unit of offset is pixels,
 * so we need this variable to have the same expectations on different devices.
 */
@Stable
fun parabolaScrollEasing(currentOffset: Float, newOffset: Float, p: Float = 25f, density: Float = 4f): Float {
    val realP = p * density
    val distance = abs(currentOffset + newOffset / 2)
    val ratio = (realP / (sqrt(realP * distance.coerceAtLeast(Float.MIN_VALUE)))).coerceIn(Float.MIN_VALUE, 1f)
    val nonLinearRatio = ratio * (1 - exp(-distance / realP))
    return if (sign(currentOffset) == sign(newOffset)) {
        currentOffset + newOffset * nonLinearRatio
    } else {
        currentOffset + newOffset
    }
}

internal val DefaultParabolaScrollEasing: (currentOffset: Float, newOffset: Float) -> Float
    @Composable
    get() {
        val density = LocalDensity.current.density
        return { currentOffset, newOffset ->
            parabolaScrollEasing(currentOffset, newOffset, density = density)
        }
    }

internal const val OutBoundSpringStiff = 200f
internal const val OutBoundSpringDamp = 1f

/**
 * @see overScrollOutOfBound
 */
@Stable
fun Modifier.overScrollVertical(
    nestedScrollToParent: Boolean = true,
    scrollEasing: ((currentOffset: Float, newOffset: Float) -> Float)? = null,
    springStiff: Float = OutBoundSpringStiff,
    springDamp: Float = OutBoundSpringDamp,
    onOverscroll: ((Boolean) -> Unit)? = null
): Modifier = overScrollOutOfBound(isVertical = true, nestedScrollToParent, scrollEasing, springStiff, springDamp, onOverscroll)

/**
 * @see overScrollOutOfBound
 */
@Stable
fun Modifier.overScrollHorizontal(
    nestedScrollToParent: Boolean = true,
    scrollEasing: ((currentOffset: Float, newOffset: Float) -> Float)? = null,
    springStiff: Float = OutBoundSpringStiff,
    springDamp: Float = OutBoundSpringDamp,
    onOverscroll: ((Boolean) -> Unit)? = null
): Modifier = overScrollOutOfBound(isVertical = false, nestedScrollToParent, scrollEasing, springStiff, springDamp, onOverscroll)

/**
 * OverScroll effect for scrollable Composable.
 *
 * @Author: cormor
 * @Email: cangtiansuo@gmail.com
 * @param isVertical is vertical, or horizontal ?
 * @param nestedScrollToParent Whether to dispatch nested scroll events to parent
 * @param scrollEasing U can refer to [DefaultParabolaScrollEasing], The incoming values are the currently existing overscroll Offset
 * and the new offset from the gesture.
 * modify it to cooperate with [springStiff] to customize the sliding damping effect.
 * The current default easing comes from iOS, you don't need to modify it!
 * @param springStiff springStiff for overscroll effect，For better user experience, the new value is not recommended to be higher than[StiffnessMediumLow]
 * @param springDamp springDamp for overscroll effect，generally do not need to set
 */
@Stable
@Suppress("NAME_SHADOWING")
fun Modifier.overScrollOutOfBound(
    isVertical: Boolean = true,
    nestedScrollToParent: Boolean = true,
    scrollEasing: ((currentOffset: Float, newOffset: Float) -> Float)?,
    springStiff: Float = OutBoundSpringStiff,
    springDamp: Float = OutBoundSpringDamp,
    onOverscroll: ((Boolean) -> Unit)? = null
): Modifier = composed {
    if (!enableOverscroll()) return@composed this
    val onOverscroll by rememberUpdatedState(onOverscroll)
    val nestedScrollToParent by rememberUpdatedState(nestedScrollToParent)
    val scrollEasing by rememberUpdatedState(scrollEasing ?: DefaultParabolaScrollEasing)
    val springStiff by rememberUpdatedState(springStiff)
    val springDamp by rememberUpdatedState(springDamp)
    val isVertical by rememberUpdatedState(isVertical)
    val dispatcher = remember { NestedScrollDispatcher() }
    var offset by remember { mutableFloatStateOf(0f) }

    val nestedConnection = remember {
        object : NestedScrollConnection {
            /**
             * If the offset is less than this value, we consider the animation to end.
             */
            val visibilityThreshold = 0.5f
            lateinit var lastFlingAnimator: Animatable<Float, AnimationVector1D>

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Found fling behavior in the wrong direction.
                if (source != NestedScrollSource.UserInput) {
                    return dispatcher.dispatchPreScroll(available, source)
                }
                if (::lastFlingAnimator.isInitialized && lastFlingAnimator.isRunning) {
                    dispatcher.coroutineScope.launch {
                        lastFlingAnimator.stop()
                    }
                }
                val realAvailable = when {
                    nestedScrollToParent -> available - dispatcher.dispatchPreScroll(available, source)
                    else -> available
                }
                val realOffset = if (isVertical) realAvailable.y else realAvailable.x

                val isSameDirection = sign(realOffset) == sign(offset)
                if (abs(offset) <= visibilityThreshold || isSameDirection) {
                    return available - realAvailable
                }
                val offsetAtLast = scrollEasing(offset, realOffset)
                // sign changed, coerce to start scrolling and exit
                return if (sign(offset) != sign(offsetAtLast)) {
                    offset = 0f
                    onOverscroll?.invoke(false)
                    if (isVertical) {
                        Offset(x = available.x - realAvailable.x, y = available.y - realAvailable.y + realOffset)
                    } else {
                        Offset(x = available.x - realAvailable.x + realOffset, y = available.y - realAvailable.y)
                    }
                } else {
                    offset = offsetAtLast
                    onOverscroll?.invoke(true)
                    if (isVertical) {
                        Offset(x = available.x - realAvailable.x, y = available.y)
                    } else {
                        Offset(x = available.x, y = available.y - realAvailable.y)
                    }
                }
            }

            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                // Found fling behavior in the wrong direction.
                if (source != NestedScrollSource.UserInput) {
                    return dispatcher.dispatchPreScroll(available, source)
                }
                val realAvailable = when {
                    nestedScrollToParent -> available - dispatcher.dispatchPostScroll(consumed, available, source)
                    else -> available
                }
                offset = scrollEasing(offset, if (isVertical) realAvailable.y else realAvailable.x)
                onOverscroll?.invoke(abs(offset) > visibilityThreshold)
                return if (isVertical) {
                    Offset(x = available.x - realAvailable.x, y = available.y)
                } else {
                    Offset(x = available.x, y = available.y - realAvailable.y)
                }
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                onOverscroll?.invoke(false)
                if (::lastFlingAnimator.isInitialized && lastFlingAnimator.isRunning) {
                    lastFlingAnimator.stop()
                }
                val parentConsumed = when {
                    nestedScrollToParent -> dispatcher.dispatchPreFling(available)
                    else -> Velocity.Zero
                }
                val realAvailable = available - parentConsumed
                var leftVelocity = if (isVertical) realAvailable.y else realAvailable.x

                if (abs(offset) >= visibilityThreshold && sign(leftVelocity) != sign(offset)) {
                    lastFlingAnimator = Animatable(offset).apply {
                        when {
                            leftVelocity < 0 -> updateBounds(lowerBound = 0f)
                            leftVelocity > 0 -> updateBounds(upperBound = 0f)
                        }
                    }
                    leftVelocity = lastFlingAnimator.animateTo(0f, spring(springDamp, springStiff, visibilityThreshold), leftVelocity) {
                        offset = scrollEasing(offset, value - offset)
                    }.endState.velocity
                }
                return if (isVertical) {
                    Velocity(parentConsumed.x, y = available.y - leftVelocity)
                } else {
                    Velocity(available.x - leftVelocity, y = parentConsumed.y)
                }
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                onOverscroll?.invoke(false)
                val realAvailable = when {
                    nestedScrollToParent -> available - dispatcher.dispatchPostFling(consumed, available)
                    else -> available
                }
                lastFlingAnimator = Animatable(offset)
                lastFlingAnimator.animateTo(0f, spring(springDamp, springStiff, visibilityThreshold), if (isVertical) realAvailable.y else realAvailable.x) {
                    offset = scrollEasing(offset, value - offset)
                }
                return if (isVertical) {
                    Velocity(x = available.x - realAvailable.x, y = available.y)
                } else {
                    Velocity(x = available.x, y = available.y - realAvailable.y)
                }
            }
        }
    }

    this
        .clipToBounds()
        .nestedScroll(nestedConnection, dispatcher)
        .graphicsLayer {
            if (isVertical) translationY = offset else translationX = offset
        }
}


/**
 * You should use it with [overScrollVertical]
 * @param decaySpec You can use instead [rememberSplineBasedDecay]
 * @param getScrollState Pass in your [ScrollableState], for [LazyColumn]/[LazyRow] , it's [LazyListState]
 */
@Composable
fun rememberOverscrollFlingBehavior(
    decaySpec: DecayAnimationSpec<Float> = exponentialDecay(),
    getScrollState: () -> ScrollableState,
): FlingBehavior = remember(decaySpec, getScrollState) {
    object : FlingBehavior {
        /**
         * - We should check it every frame of fling
         * - Should stop fling when returning true and return the remaining speed immediately.
         * - Without this detection, scrollBy() will continue to consume velocity,
         * which will cause a velocity error in nestedScroll.
         */
        private val Float.canNotBeConsumed: Boolean // this is Velocity
            get() {
                val state = getScrollState()
                return !(this < 0 && state.canScrollBackward || this > 0 && state.canScrollForward)
            }

        override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
            if (initialVelocity.canNotBeConsumed) {
                return initialVelocity
            }
            return if (abs(initialVelocity) > 1f) {
                var velocityLeft = initialVelocity
                var lastValue = 0f
                AnimationState(
                    initialValue = 0f,
                    initialVelocity = initialVelocity,
                ).animateDecay(decaySpec) {
                    val delta = value - lastValue
                    val consumed = scrollBy(delta)
                    lastValue = value
                    velocityLeft = this.velocity
                    // avoid rounding errors and stop if anything is unconsumed
                    if (abs(delta - consumed) > 0.5f || velocityLeft.canNotBeConsumed) {
                        cancelAnimation()
                    }
                }
                velocityLeft
            } else {
                initialVelocity
            }
        }
    }
}

expect fun enableOverscroll(): Boolean