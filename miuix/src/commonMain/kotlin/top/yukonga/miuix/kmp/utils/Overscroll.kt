// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
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
import top.yukonga.miuix.kmp.basic.LocalPullToRefreshState
import top.yukonga.miuix.kmp.basic.RefreshState
import kotlin.math.abs
import kotlin.math.sign
import kotlin.math.sqrt

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
fun parabolaScrollEasing(currentOffset: Float, newOffset: Float, p: Float = 50f, density: Float): Float {
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
            parabolaScrollEasing(currentOffset, newOffset, 20f, density)
        }
    }

internal const val OutBoundSpringStiff = 180f
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
    isEnabled: () -> Boolean = { platform() == Platform.Android || platform() == Platform.IOS }
): Modifier = overScrollOutOfBound(isVertical = true, nestedScrollToParent, scrollEasing, springStiff, springDamp, isEnabled)

/**
 * @see overScrollOutOfBound
 */
@Stable
fun Modifier.overScrollHorizontal(
    nestedScrollToParent: Boolean = true,
    scrollEasing: ((currentOffset: Float, newOffset: Float) -> Float)? = null,
    springStiff: Float = OutBoundSpringStiff,
    springDamp: Float = OutBoundSpringDamp,
    isEnabled: () -> Boolean = { platform() == Platform.Android || platform() == Platform.IOS }
): Modifier = overScrollOutOfBound(isVertical = false, nestedScrollToParent, scrollEasing, springStiff, springDamp, isEnabled)

/**
 * OverScroll effect for scrollable Composable.
 *
 * form https://github.com/Cormor/ComposeOverscroll
 * @Author: cormor
 * @Email: cangtiansuo@gmail.com
 * @param isVertical is vertical, or horizontal?
 * @param nestedScrollToParent Whether to dispatch nested scroll events to parent.
 * @param scrollEasing u can refer to [DefaultParabolaScrollEasing], The incoming values are the currently existing overscroll Offset
 * and the new offset from the gesture.
 * modify it to cooperate with [springStiff] to customize the sliding damping effect.
 * The current default easing comes from iOS, you don't need to modify it!
 * @param springStiff springStiff for overscroll effect，For better user experience, the new value is not recommended to be higher than [StiffnessMediumLow].
 * @param springDamp springDamp for overscroll effect，generally do not need to set.
 * @param isEnabled Whether to enable the overscroll effect, default is enabled on Android and iOS.
 */
@Stable
@Suppress("NAME_SHADOWING")
fun Modifier.overScrollOutOfBound(
    isVertical: Boolean = true,
    nestedScrollToParent: Boolean = true,
    scrollEasing: ((currentOffset: Float, newOffset: Float) -> Float)?,
    springStiff: Float = OutBoundSpringStiff,
    springDamp: Float = OutBoundSpringDamp,
    isEnabled: () -> Boolean = { platform() == Platform.Android || platform() == Platform.IOS }
): Modifier = composed {
    if (!isEnabled()) return@composed this

    val overScrollState = LocalOverScrollState.current
    val pullToRefreshState = LocalPullToRefreshState.current
    val currentNestedScrollToParent by rememberUpdatedState(nestedScrollToParent)
    val currentScrollEasing by rememberUpdatedState(scrollEasing ?: DefaultParabolaScrollEasing)
    val currentSpringStiff by rememberUpdatedState(springStiff)
    val currentSpringDamp by rememberUpdatedState(springDamp)
    val currentIsVertical by rememberUpdatedState(isVertical)
    val dispatcher = remember { NestedScrollDispatcher() }
    var offset by remember { mutableFloatStateOf(0f) }

    val nestedConnection = remember {
        object : NestedScrollConnection {
            /**
             * If the offset is less than this value, we consider the animation to end.
             */
            val visibilityThreshold = 0.5f
            lateinit var lastFlingAnimator: Animatable<Float, AnimationVector1D>

            private fun shouldBypassForPullToRefresh(availableY: Float): Boolean {
                return pullToRefreshState != null &&
                        pullToRefreshState.refreshState != RefreshState.Idle &&
                        currentIsVertical &&
                        availableY > 0
            }

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Check if overScroll should be disabled for drop-down direction
                overScrollState.isOverScrollActive = abs(offset) > visibilityThreshold
                if (shouldBypassForPullToRefresh(available.y)) {
                    return dispatcher.dispatchPreScroll(available, source)
                }
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
                    currentNestedScrollToParent -> available - dispatcher.dispatchPreScroll(available, source)
                    else -> available
                }
                val realOffset = if (currentIsVertical) realAvailable.y else realAvailable.x

                val isSameDirection = sign(realOffset) == sign(offset)
                if (abs(offset) <= visibilityThreshold || isSameDirection) {
                    return available - realAvailable
                }
                val offsetAtLast = currentScrollEasing(offset, realOffset)
                // sign changed, coerce to start scrolling and exit
                return if (sign(offset) != sign(offsetAtLast)) {
                    offset = 0f
                    if (currentIsVertical) {
                        Offset(x = available.x - realAvailable.x, y = available.y - realAvailable.y + realOffset)
                    } else {
                        Offset(x = available.x - realAvailable.x + realOffset, y = available.y - realAvailable.y)
                    }
                } else {
                    offset = offsetAtLast
                    if (currentIsVertical) {
                        Offset(x = available.x - realAvailable.x, y = available.y)
                    } else {
                        Offset(x = available.x, y = available.y - realAvailable.y)
                    }
                }
            }

            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                // Check if overScroll should be disabled for drop-down direction
                overScrollState.isOverScrollActive = abs(offset) > visibilityThreshold
                if (shouldBypassForPullToRefresh(available.y)) {
                    return dispatcher.dispatchPostScroll(consumed, available, source)
                }
                // Found fling behavior in the wrong direction.
                if (source != NestedScrollSource.UserInput) {
                    return dispatcher.dispatchPostScroll(consumed, available, source)
                }
                val realAvailable = when {
                    currentNestedScrollToParent -> available - dispatcher.dispatchPostScroll(consumed, available, source)
                    else -> available
                }
                offset = currentScrollEasing(offset, if (currentIsVertical) realAvailable.y else realAvailable.x)
                return if (currentIsVertical) {
                    Offset(x = available.x - realAvailable.x, y = available.y)
                } else {
                    Offset(x = available.x, y = available.y - realAvailable.y)
                }
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                // Check if overScroll should be disabled for drop-down direction
                overScrollState.isOverScrollActive = abs(offset) > visibilityThreshold
                if (shouldBypassForPullToRefresh(available.y)) {
                    return dispatcher.dispatchPreFling(available)
                }
                if (::lastFlingAnimator.isInitialized && lastFlingAnimator.isRunning) {
                    lastFlingAnimator.stop()
                }
                val parentConsumed = when {
                    currentNestedScrollToParent -> dispatcher.dispatchPreFling(available)
                    else -> Velocity.Zero
                }
                val realAvailable = available - parentConsumed
                var leftVelocity = if (currentIsVertical) realAvailable.y else realAvailable.x

                if (abs(offset) >= visibilityThreshold && sign(leftVelocity) != sign(offset)) {
                    lastFlingAnimator = Animatable(offset).apply {
                        when {
                            leftVelocity < 0 -> updateBounds(lowerBound = 0f)
                            leftVelocity > 0 -> updateBounds(upperBound = 0f)
                        }
                    }
                    leftVelocity = lastFlingAnimator.animateTo(
                        0f,
                        spring(currentSpringDamp, currentSpringStiff, visibilityThreshold),
                        leftVelocity
                    ) {
                        offset = currentScrollEasing(offset, value - offset)
                    }.endState.velocity
                }
                return if (currentIsVertical) {
                    Velocity(parentConsumed.x, y = available.y - leftVelocity)
                } else {
                    Velocity(available.x - leftVelocity, y = parentConsumed.y)
                }
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                // Check if overScroll should be disabled for drop-down direction
                overScrollState.isOverScrollActive = abs(offset) > visibilityThreshold
                if (shouldBypassForPullToRefresh(available.y)) {
                    return dispatcher.dispatchPostFling(consumed, available)
                }
                val realAvailable = when {
                    currentNestedScrollToParent -> available - dispatcher.dispatchPostFling(consumed, available)
                    else -> available
                }
                lastFlingAnimator = Animatable(offset)
                lastFlingAnimator.animateTo(
                    0f,
                    spring(currentSpringDamp, currentSpringStiff, visibilityThreshold),
                    if (currentIsVertical) realAvailable.y else realAvailable.x
                ) {
                    offset = currentScrollEasing(offset, value - offset)
                }
                return if (currentIsVertical) {
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
            if (currentIsVertical) translationY = offset else translationX = offset
        }
}

/**
 * OverScrollState is used to control the overscroll effect.
 *
 * @param isOverScrollActive Whether the overscroll effect is active.
 */
@Stable
class OverScrollState {
    var isOverScrollActive by mutableStateOf(false)
        internal set
}

/**
 * [LocalOverScrollState] is used to provide the [OverScrollState] instance to the composition.
 *
 * @see OverScrollState
 */
val LocalOverScrollState = compositionLocalOf { OverScrollState() }

