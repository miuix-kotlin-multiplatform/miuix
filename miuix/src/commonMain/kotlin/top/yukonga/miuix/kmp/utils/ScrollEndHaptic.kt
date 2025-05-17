package top.yukonga.miuix.kmp.utils

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Velocity
import kotlin.math.abs

/**
 * A [NestedScrollConnection] that provides haptic feedback when a scrollable container
 * is flung to its start or end boundaries.
 *
 * @param hapticFeedback The [HapticFeedback] instance to perform feedback.
 * @param hapticFeedbackType The type of haptic feedback to perform.
 */
private class ScrollEndHapticConnection(
    private val hapticFeedback: HapticFeedback,
    private val hapticFeedbackType: HapticFeedbackType
) : NestedScrollConnection {

    private enum class OverscrollState {
        /** No overscroll detected. */
        Idle,

        /** Overscroll detected at the top boundary. */
        TopBoundaryHit,

        /** Overscroll detected at the bottom boundary. */
        BottomBoundaryHit
    }

    private var overscrollState: OverscrollState = OverscrollState.Idle

    private fun Float.filter(tolerance: Float): Boolean = abs(this) < tolerance

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        // Reset state when scrolling from a boundary into content.
        if (overscrollState == OverscrollState.TopBoundaryHit && available.y < -1f) {
            overscrollState = OverscrollState.Idle
        } else if (overscrollState == OverscrollState.BottomBoundaryHit && available.y > 1f) {
            overscrollState = OverscrollState.Idle
        }
        return Offset.Zero
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        println("overScrollState: $overscrollState")
        // Flinging beyond the bottom boundary.
        if (available.y > 1f && !consumed.y.filter(5f)) {
            if (overscrollState != OverscrollState.TopBoundaryHit) {
                hapticFeedback.performHapticFeedback(hapticFeedbackType)
                overscrollState = OverscrollState.TopBoundaryHit
            }
        }
        // Flinging beyond the top boundary.
        else if (available.y < -1f && !consumed.y.filter(5f)) {
            if (overscrollState != OverscrollState.BottomBoundaryHit) {
                hapticFeedback.performHapticFeedback(hapticFeedbackType)
                overscrollState = OverscrollState.BottomBoundaryHit
            }
        }
        return Velocity.Zero
    }
}

/**
 * Applies a haptic feedback effect when a scrollable container is flung to its boundaries.
 *
 * @param hapticFeedbackType The type of haptic feedback to perform.
 */
fun Modifier.scrollEndHaptic(
    hapticFeedbackType: HapticFeedbackType = HapticFeedbackType.TextHandleMove
): Modifier = composed {
    val haptic = LocalHapticFeedback.current

    val connection = remember(haptic, hapticFeedbackType) {
        ScrollEndHapticConnection(
            hapticFeedback = haptic,
            hapticFeedbackType = hapticFeedbackType
        )
    }
    Modifier.nestedScroll(connection)
}