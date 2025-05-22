// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * A [Checkbox] component with Miuix style.
 *
 * @param checked The current state of the [Checkbox].
 * @param onCheckedChange The callback to be called when the state of the [Checkbox] changes.
 * @param modifier The modifier to be applied to the [Checkbox].
 * @param colors The [CheckboxColors] of the [Checkbox].
 * @param enabled Whether the [Checkbox] is enabled.
 */
@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    colors: CheckboxColors = CheckboxDefaults.checkboxColors(),
    enabled: Boolean = true,
) {
    val isChecked by rememberUpdatedState(checked)
    val currentOnCheckedChange by rememberUpdatedState(onCheckedChange)
    var onCheck by remember { mutableStateOf(false) }
    val hapticFeedback = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }

    val isInteracted = interactionSource.let { source ->
        val isPressed by source.collectIsPressedAsState()
        val isDragged by source.collectIsDraggedAsState()
        val isHovered by source.collectIsHoveredAsState()
        isPressed || isDragged || isHovered
    }

    LaunchedEffect(onCheck == true) {
        delay(80)
        onCheck = false
    }

    val springSpec = remember(onCheck) {
        spring<Float>(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = if (onCheck) Spring.StiffnessHigh else Spring.StiffnessMedium
        )
    }

    val scale by animateFloatAsState(
        targetValue = when {
            isInteracted || onCheck -> 0.95f
            else -> 1f
        },
        animationSpec = springSpec
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isChecked) colors.checkedBackgroundColor(enabled) else colors.uncheckedBackgroundColor(enabled),
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val foregroundColor by animateColorAsState(
        targetValue = if (isChecked) colors.checkedForegroundColor(enabled) else colors.uncheckedForegroundColor(enabled),
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val checkAlpha = remember { Animatable(if (isChecked) 1f else 0f) }
    val checkStartTrim = remember { Animatable(0.0f) }
    val checkEndTrim = remember { Animatable(if (isChecked) 0.803f else 0.1f) }

    LaunchedEffect(isChecked) {
        if (isChecked) {
            launch {
                checkAlpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 10,
                        easing = FastOutSlowInEasing
                    )
                )
            }
            launch {
                checkStartTrim.animateTo(
                    targetValue = 0.186f,
                    animationSpec = keyframes {
                        durationMillis = 100
                        0.186f at 100
                    }
                )
            }
            launch {
                checkEndTrim.animateTo(
                    targetValue = 0.803f,
                    animationSpec = keyframes {
                        durationMillis = 300
                        0.845f at 200
                        0.803f at 300
                    }
                )
            }
        } else {
            launch {
                checkAlpha.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = 150,
                        easing = FastOutSlowInEasing
                    )
                )
            }
            launch {
                checkStartTrim.animateTo(
                    targetValue = 0.0f,
                    animationSpec = keyframes {
                        durationMillis = 300
                        0.0f at 300
                    }
                )
            }
            launch {
                checkEndTrim.animateTo(
                    targetValue = 0.1f,
                    animationSpec = keyframes {
                        durationMillis = 300
                        0.1f at 300
                    }
                )
            }
        }
    }

    val currentIndication = LocalIndication.current
    val toggleableModifier = remember(isChecked, currentOnCheckedChange, enabled, interactionSource, currentIndication) {
        if (currentOnCheckedChange != null) {
            Modifier.toggleable(
                value = isChecked,
                onValueChange = {
                    currentOnCheckedChange?.invoke(it)
                    onCheck = true
                    hapticFeedback.performHapticFeedback(
                        if (it) HapticFeedbackType.ToggleOn else HapticFeedbackType.ToggleOff
                    )
                },
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                indication = currentIndication
            )
        } else {
            Modifier
        }
    }

    val boxModifier = remember(modifier, scale, backgroundColor, toggleableModifier) {
        modifier
            .size(25.5.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(backgroundColor)
            .then(toggleableModifier)
    }

    Box(
        modifier = boxModifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(25.5.dp)) {
            drawTrimmedCheckmark(
                color = foregroundColor,
                alpha = checkAlpha.value,
                trimStart = checkStartTrim.value,
                trimEnd = checkEndTrim.value
            )
        }
    }
}

private fun DrawScope.drawTrimmedCheckmark(
    color: Color,
    alpha: Float = 1f,
    trimStart: Float,
    trimEnd: Float
) {
    val viewportSize = 23f
    val strokeWidth = size.width * 0.09f

    val centerX = size.width / 2
    val centerY = size.height / 2
    val viewportCenterX = viewportSize / 2
    val viewportCenterY = viewportSize / 2

    val leftPoint = Offset(
        centerX + ((5f - viewportCenterX) / viewportSize * size.width),
        centerY + ((9.4f - viewportCenterY) / viewportSize * size.height)
    )
    val middlePoint = Offset(
        centerX + ((10.3f - viewportCenterX) / viewportSize * size.width),
        centerY + ((14.9f - viewportCenterY) / viewportSize * size.height)
    )
    val rightPoint = Offset(
        centerX + ((17.9f - viewportCenterX) / viewportSize * size.width),
        centerY + ((5.1f - viewportCenterY) / viewportSize * size.height)
    )

    val firstSegmentLength = (middlePoint - leftPoint).getDistance()
    val secondSegmentLength = (rightPoint - middlePoint).getDistance()
    val totalLength = firstSegmentLength + secondSegmentLength

    val startDistance = totalLength * trimStart
    val endDistance = totalLength * trimEnd

    val path = Path()

    if (startDistance < firstSegmentLength && endDistance > 0) {
        val segStartRatio = (startDistance / firstSegmentLength).coerceIn(0f, 1f)
        val segEndRatio = (endDistance / firstSegmentLength).coerceIn(0f, 1f)

        val start = Offset(
            leftPoint.x + (middlePoint.x - leftPoint.x) * segStartRatio,
            leftPoint.y + (middlePoint.y - leftPoint.y) * segStartRatio
        )
        val end = Offset(
            leftPoint.x + (middlePoint.x - leftPoint.x) * segEndRatio,
            leftPoint.y + (middlePoint.y - leftPoint.y) * segEndRatio
        )

        path.moveTo(start.x, start.y)
        path.lineTo(end.x, end.y)
    }

    if (endDistance > firstSegmentLength) {
        val segStartRatio = ((startDistance - firstSegmentLength) / secondSegmentLength).coerceIn(0f, 1f)
        val segEndRatio = ((endDistance - firstSegmentLength) / secondSegmentLength).coerceIn(0f, 1f)

        val start = Offset(
            middlePoint.x + (rightPoint.x - middlePoint.x) * segStartRatio,
            middlePoint.y + (rightPoint.y - middlePoint.y) * segStartRatio
        )
        val end = Offset(
            middlePoint.x + (rightPoint.x - middlePoint.x) * segEndRatio,
            middlePoint.y + (rightPoint.y - middlePoint.y) * segEndRatio
        )

        if (startDistance < firstSegmentLength) {
            path.lineTo(end.x, end.y)
        } else {
            path.moveTo(start.x, start.y)
        }
        path.lineTo(end.x, end.y)
    }

    drawPath(
        path = path,
        color = color.copy(alpha = alpha),
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round,
            miter = 10.0f,
        )
    )
}

object CheckboxDefaults {
    @Composable
    fun checkboxColors(
        checkedForegroundColor: Color = MiuixTheme.colorScheme.onPrimary,
        uncheckedForegroundColor: Color = MiuixTheme.colorScheme.secondary,
        disabledCheckedForegroundColor: Color = MiuixTheme.colorScheme.disabledOnPrimary,
        disabledUncheckedForegroundColor: Color = MiuixTheme.colorScheme.disabledOnPrimary,
        checkedBackgroundColor: Color = MiuixTheme.colorScheme.primary,
        uncheckedBackgroundColor: Color = MiuixTheme.colorScheme.secondary,
        disabledCheckedBackgroundColor: Color = MiuixTheme.colorScheme.disabledPrimary,
        disabledUncheckedBackgroundColor: Color = MiuixTheme.colorScheme.disabledSecondary
    ): CheckboxColors = CheckboxColors(
        checkedForegroundColor = checkedForegroundColor,
        uncheckedForegroundColor = uncheckedForegroundColor,
        disabledCheckedForegroundColor = disabledCheckedForegroundColor,
        disabledUncheckedForegroundColor = disabledUncheckedForegroundColor,
        checkedBackgroundColor = checkedBackgroundColor,
        uncheckedBackgroundColor = uncheckedBackgroundColor,
        disabledCheckedBackgroundColor = disabledCheckedBackgroundColor,
        disabledUncheckedBackgroundColor = disabledUncheckedBackgroundColor
    )
}

@Immutable
class CheckboxColors(
    private val checkedForegroundColor: Color,
    private val uncheckedForegroundColor: Color,
    private val disabledCheckedForegroundColor: Color,
    private val disabledUncheckedForegroundColor: Color,
    private val checkedBackgroundColor: Color,
    private val uncheckedBackgroundColor: Color,
    private val disabledCheckedBackgroundColor: Color,
    private val disabledUncheckedBackgroundColor: Color
) {
    @Stable
    internal fun checkedForegroundColor(enabled: Boolean): Color =
        if (enabled) checkedForegroundColor else disabledCheckedForegroundColor

    @Stable
    internal fun uncheckedForegroundColor(enabled: Boolean): Color =
        if (enabled) uncheckedForegroundColor else disabledUncheckedForegroundColor

    @Stable
    internal fun checkedBackgroundColor(enabled: Boolean): Color =
        if (enabled) checkedBackgroundColor else disabledCheckedBackgroundColor

    @Stable
    internal fun uncheckedBackgroundColor(enabled: Boolean): Color =
        if (enabled) uncheckedBackgroundColor else disabledUncheckedBackgroundColor
}