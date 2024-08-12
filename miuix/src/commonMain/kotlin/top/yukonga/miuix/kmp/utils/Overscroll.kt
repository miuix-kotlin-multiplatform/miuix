package top.yukonga.miuix.kmp.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.spring
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
fun parabolaScrollEasing(currentOffset: Float, newOffset: Float, p: Float = 50f, density: Float = 4f): Float {
    val realP = p * density
    val ratio = (realP / (sqrt(realP * abs(currentOffset + newOffset / 2).coerceAtLeast(Float.MIN_VALUE)))).coerceIn(Float.MIN_VALUE, 1f)
    return if (sign(currentOffset) == sign(newOffset)) {
        currentOffset + newOffset * ratio
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

internal const val OutBoundSpringStiff = 150f
internal const val OutBoundSpringDamp = 1f

fun Modifier.overScrollVertical(
    onOverscroll: ((Boolean) -> Unit)? = null
): Modifier = composed {
    @Suppress("NAME_SHADOWING")
    val onOverscroll by rememberUpdatedState(onOverscroll)
    val scrollEasing by rememberUpdatedState(DefaultParabolaScrollEasing)
    val springStiff by rememberUpdatedState(OutBoundSpringStiff)
    val springDamp by rememberUpdatedState(OutBoundSpringDamp)
    val dispatcher = remember { NestedScrollDispatcher() }
    var offset by remember { mutableFloatStateOf(0f) }

    val nestedConnection = remember {
        object : NestedScrollConnection {
            val visibilityThreshold = 0.5f
            lateinit var lastFlingAnimator: Animatable<Float, AnimationVector1D>

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (source != NestedScrollSource.UserInput) {
                    return dispatcher.dispatchPreScroll(available, source)
                }
                if (::lastFlingAnimator.isInitialized && lastFlingAnimator.isRunning) {
                    dispatcher.coroutineScope.launch {
                        lastFlingAnimator.stop()
                    }
                }
                val realAvailable = available - dispatcher.dispatchPreScroll(available, source)
                val realOffset = realAvailable.y

                val isSameDirection = sign(realOffset) == sign(offset)
                if (abs(offset) <= visibilityThreshold || isSameDirection) {
                    return available - realAvailable
                }
                val offsetAtLast = scrollEasing(offset, realOffset)
                if (sign(offset) != sign(offsetAtLast)) {
                    offset = 0f
                    onOverscroll?.invoke(false)
                    return Offset(x = available.x - realAvailable.x, y = available.y - realAvailable.y + realOffset)
                } else {
                    offset = offsetAtLast
                    onOverscroll?.invoke(true)
                    return Offset(x = available.x - realAvailable.x, y = available.y)
                }
            }

            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                if (source != NestedScrollSource.UserInput) {
                    return dispatcher.dispatchPreScroll(available, source)
                }
                val realAvailable = available - dispatcher.dispatchPostScroll(consumed, available, source)
                offset = scrollEasing(offset, realAvailable.y)
                println("offset: $offset")
                onOverscroll?.invoke(abs(offset) > visibilityThreshold)
                return Offset(x = available.x - realAvailable.x, y = available.y)
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                if (::lastFlingAnimator.isInitialized && lastFlingAnimator.isRunning) {
                    lastFlingAnimator.stop()
                }
                val parentConsumed = dispatcher.dispatchPreFling(available)
                val realAvailable = available - parentConsumed
                var leftVelocity = realAvailable.y
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
                    onOverscroll?.invoke(false)
                }
                return Velocity(parentConsumed.x, y = available.y - leftVelocity)
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                val realAvailable = available - dispatcher.dispatchPostFling(consumed, available)
                lastFlingAnimator = Animatable(offset)
                lastFlingAnimator.animateTo(0f, spring(springDamp, springStiff, visibilityThreshold), realAvailable.y) {
                    offset = scrollEasing(offset, value - offset)
                }
                onOverscroll?.invoke(false)
                return Velocity(x = available.x - realAvailable.x, y = available.y)
            }
        }
    }

    this
        .clipToBounds()
        .nestedScroll(nestedConnection, dispatcher)
        .graphicsLayer {
            translationY = offset
        }
}