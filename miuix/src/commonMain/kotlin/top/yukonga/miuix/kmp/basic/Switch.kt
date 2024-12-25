package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitVerticalTouchSlopOrCancellation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import kotlin.math.absoluteValue

/**
 * A [Switch] component with Miuix style.
 *
 * @param checked The checked state of the [Switch].
 * @param onCheckedChange The callback to be called when the state of the [Switch] changes.
 * @param modifier The modifier to be applied to the [Switch].
 * @param colors The [SwitchColors] of the [Switch].
 * @param enabled Whether the [Switch] is enabled.
 */
@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    colors: SwitchColors = SwitchDefaults.switchColors(),
    enabled: Boolean = true
) {
    val isChecked by rememberUpdatedState(checked)
    val hapticFeedback = LocalHapticFeedback.current
    var hasVibrated by remember { mutableStateOf(false) }
    val springSpec = remember {
        spring<Dp>(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    }
    var isPressed by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableStateOf(0f) }
    val thumbOffset by animateDpAsState(
        targetValue = if (isChecked) {
            if (!enabled) 26.dp else if (isPressed) 24.dp else 26.dp
        } else {
            if (!enabled) 4.dp else if (isPressed) 3.dp else 4.dp
        } + dragOffset.dp,
        animationSpec = springSpec
    )

    val thumbSize by animateDpAsState(
        targetValue = if (!enabled) 20.dp else if (isPressed) 23.dp else 20.dp,
        animationSpec = springSpec
    )

    val backgroundColor by animateColorAsState(
        if (isChecked) colors.checkedTrackColor(enabled) else colors.uncheckedTrackColor(enabled),
        animationSpec = tween(durationMillis = 200)
    )

    val thumbColor by animateColorAsState(
        if (isChecked) colors.checkedThumbColor(enabled) else colors.uncheckedThumbColor(enabled)
    )

    val toggleableModifier = remember(onCheckedChange, isChecked, enabled) {
        if (onCheckedChange != null) {
            Modifier.toggleable(
                value = isChecked,
                onValueChange = {
                    onCheckedChange(it)
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                },
                enabled = enabled,
                role = Role.Switch,
                indication = null,
                interactionSource = null
            )
        } else {
            Modifier
        }
    }

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.Center)
            .size(50.dp, 28.5.dp)
            .requiredSize(50.dp, 28.5.dp)
            .clip(RoundedCornerShape(100.dp))
            .drawBehind { drawRect(backgroundColor) }
            .pointerInput(Unit) {
                if (!enabled) return@pointerInput
                detectHorizontalDragGestures(
                    onDragStart = {
                        isPressed = true
                        hasVibrated = false
                    },
                    onDragEnd = {
                        isPressed = false
                        val switchWidth = 21f
                        if (dragOffset.absoluteValue > switchWidth / 2) {
                            onCheckedChange?.invoke(!isChecked)
                        }
                        dragOffset = 0f
                    },
                    onDragCancel = {
                        isPressed = false
                        dragOffset = 0f
                    }
                ) { _, dragAmount ->
                    val newOffset = dragOffset + dragAmount / 2
                    dragOffset = if (isChecked) newOffset.coerceIn(-21f, 0f) else newOffset.coerceIn(0f, 21f)
                    if (dragOffset in -20f..-1f || dragOffset in 1f..20f) {
                        hasVibrated = false
                    } else if ((dragOffset == -21f || dragOffset == 0f || dragOffset == 21f) && !hasVibrated) {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        hasVibrated = true
                    }
                }
            }
            .then(toggleableModifier)
    ) {
        Box(
            modifier = Modifier
                .padding(start = thumbOffset)
                .align(Alignment.CenterStart)
                .size(thumbSize)
                .background(
                    color = thumbColor,
                    shape = RoundedCornerShape(100.dp)
                )
                .pointerInput(Unit) {
                    if (!enabled) return@pointerInput
                    awaitEachGesture {
                        awaitFirstDown().also {
                            it.consume()
                            isPressed = true
                        }
                        waitForUpOrCancellation()?.also {
                            it.consume()
                            isPressed = false
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            onCheckedChange?.invoke(!isChecked)
                        }
                    }
                }
                .pointerInput(Unit) {
                    if (!enabled) return@pointerInput
                    awaitEachGesture {
                        val down = awaitFirstDown(requireUnconsumed = false)
                        awaitVerticalTouchSlopOrCancellation(down.id) { _, _ ->
                            isPressed = false
                        }
                    }
                }

        )
    }
}

object SwitchDefaults {

    /**
     * The default colors for the [Switch].
     */
    @Composable
    fun switchColors(
        checkedThumbColor: Color = MiuixTheme.colorScheme.onPrimary,
        uncheckedThumbColor: Color = MiuixTheme.colorScheme.onSecondary,
        disabledCheckedThumbColor: Color = MiuixTheme.colorScheme.disabledOnPrimary,
        disabledUncheckedThumbColor: Color = MiuixTheme.colorScheme.disabledOnSecondary,
        checkedTrackColor: Color = MiuixTheme.colorScheme.primary,
        uncheckedTrackColor: Color = MiuixTheme.colorScheme.secondary,
        disabledCheckedTrackColor: Color = MiuixTheme.colorScheme.disabledPrimary,
        disabledUncheckedTrackColor: Color = MiuixTheme.colorScheme.disabledSecondary
    ): SwitchColors {
        return SwitchColors(
            checkedThumbColor = checkedThumbColor,
            uncheckedThumbColor = uncheckedThumbColor,
            disabledCheckedThumbColor = disabledCheckedThumbColor,
            disabledUncheckedThumbColor = disabledUncheckedThumbColor,
            checkedTrackColor = checkedTrackColor,
            uncheckedTrackColor = uncheckedTrackColor,
            disabledCheckedTrackColor = disabledCheckedTrackColor,
            disabledUncheckedTrackColor = disabledUncheckedTrackColor
        )
    }
}

@Immutable
class SwitchColors(
    private val checkedThumbColor: Color,
    private val uncheckedThumbColor: Color,
    private val disabledCheckedThumbColor: Color,
    private val disabledUncheckedThumbColor: Color,
    private val checkedTrackColor: Color,
    private val uncheckedTrackColor: Color,
    private val disabledCheckedTrackColor: Color,
    private val disabledUncheckedTrackColor: Color
) {
    @Stable
    internal fun checkedThumbColor(enabled: Boolean): Color =
        if (enabled) checkedThumbColor else disabledCheckedThumbColor

    @Stable
    internal fun uncheckedThumbColor(enabled: Boolean): Color =
        if (enabled) uncheckedThumbColor else disabledUncheckedThumbColor

    @Stable
    internal fun checkedTrackColor(enabled: Boolean): Color =
        if (enabled) checkedTrackColor else disabledCheckedTrackColor

    @Stable
    internal fun uncheckedTrackColor(enabled: Boolean): Color =
        if (enabled) uncheckedTrackColor else disabledUncheckedTrackColor
}
