package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.squircleshape.SquircleShape
import kotlin.math.absoluteValue

/**
 * A switch with Miuix style.
 *
 * @param checked The checked state of the [MiuixSwitch].
 * @param onCheckedChange The callback to be called when the state of the [MiuixSwitch] changes.
 * @param modifier The modifier to be applied to the [MiuixSwitch].
 * @param enabled Whether the [MiuixSwitch] is enabled.
 * @param interactionSource The interaction source to be applied to the [MiuixSwitch].
 */
@Composable
fun MiuixSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
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
            if (isPressed) 26.5.dp else 28.dp
        } else {
            if (isPressed) 2.5.dp else 4.dp
        } + dragOffset.dp,
        animationSpec = springSpec
    )

    val thumbSize by animateDpAsState(
        targetValue = if (isPressed) 23.dp else 20.dp,
        animationSpec = springSpec
    )

    val backgroundColor by animateColorAsState(
        if (isChecked) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.secondary,
        animationSpec = tween(durationMillis = 200)
    )
    val disabledBackgroundColor by rememberUpdatedState(
        if (isChecked) MiuixTheme.colorScheme.submitDisabledBg else MiuixTheme.colorScheme.disabledBg
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
                interactionSource = interactionSource,
                indication = null
            )
        } else {
            Modifier
        }
    }

    MiuixBox(
        modifier = modifier
            .then(toggleableModifier)
            .wrapContentSize(Alignment.Center)
            .size(52.dp, 28.5.dp)
            .requiredSize(52.dp, 28.5.dp)
            .clip(SquircleShape(100.dp))
            .background(if (enabled) backgroundColor else disabledBackgroundColor)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                    },
                    onTap = {
                        isPressed = false
                        if (enabled) {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            onCheckedChange?.invoke(!isChecked)
                        }
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isPressed = true
                        hasVibrated = false
                    },
                    onDragEnd = {
                        isPressed = false
                        val switchWidth = 24f
                        if (dragOffset.absoluteValue > switchWidth / 2) {
                            if (enabled) onCheckedChange?.invoke(!isChecked)
                        }
                        dragOffset = 0f
                    },
                    onDragCancel = {
                        isPressed = false
                        dragOffset = 0f
                    },
                    onDrag = { change, dragAmount ->
                        if (!enabled) return@detectDragGestures
                        val newOffset = dragOffset + dragAmount.x / 2
                        dragOffset = if (isChecked) newOffset.coerceIn(-24f, 0f) else newOffset.coerceIn(0f, 24f)
                        if (isChecked) {
                            if (dragOffset in -23f..-1f) {
                                hasVibrated = false
                            } else if ((dragOffset == -24f || dragOffset == 0f) && !hasVibrated) {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                hasVibrated = true
                            }
                        } else {
                            if (dragOffset in 1f..23f) {
                                hasVibrated = false
                            } else if ((dragOffset == 0f || dragOffset == 24f) && !hasVibrated) {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                hasVibrated = true
                            }
                        }
                        change.consume()
                    }
                )
            }
    ) {
        MiuixBox(
            modifier = Modifier
                .padding(start = thumbOffset)
                .align(Alignment.CenterStart)
                .size(thumbSize)
                .background(Color.White, shape = SquircleShape(100.dp))
        )
    }
}