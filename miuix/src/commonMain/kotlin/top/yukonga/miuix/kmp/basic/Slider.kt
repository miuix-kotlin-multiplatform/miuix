package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.squircleshape.SquircleShape
import kotlin.math.pow
import kotlin.math.round

/**
 * A slider component with Miuix style.
 *
 * @param modifier The modifier to be applied to the [Slider].
 * @param progress The current progress of the [Slider].
 * @param enabled Whether the [Slider] is enabled.
 * @param minValue The minimum value of the [Slider]. It is required
 *   that [minValue] < [maxValue].
 * @param maxValue The maximum value of the [Slider].
 * @param height The height of the [Slider].
 * @param effect Whether to show the effect of the [Slider].
 * @param decimalPlaces The number of decimal places to be displayed in the drag indicator.
 * @param onProgressChange The callback to be called when the progress changes.
 */
@Composable
fun Slider(
    modifier: Modifier = Modifier,
    progress: Float,
    enabled: Boolean = true,
    minValue: Float = 0f,
    maxValue: Float = 1f,
    height: Dp = 30.dp,
    effect: Boolean = false,
    decimalPlaces: Int = 2,
    onProgressChange: (Float) -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current
    var dragOffset by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    var currentValue by remember { mutableStateOf(progress) }
    var hapticTriggered by remember { mutableStateOf(false) }
    val updatedOnProgressChange by rememberUpdatedState(onProgressChange)
    val factor = remember(decimalPlaces) { 10f.pow(decimalPlaces) }
    val calculateProgress = remember(minValue, maxValue, factor) {
        { offset: Float, width: Int ->
            val newValue = (offset / width) * (maxValue - minValue) + minValue
            (round(newValue * factor) / factor).coerceIn(minValue, maxValue)
        }
    }
    val foregroundColor = rememberUpdatedState(if (enabled) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.disabledPrimarySlider)
    val backgroundColor = rememberUpdatedState(MiuixTheme.colorScheme.tertiaryContainerVariant)

    Box(
        modifier = if (enabled) {
            modifier.pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = { offset ->
                        isDragging = true
                        dragOffset = offset.x
                        currentValue = calculateProgress(dragOffset, size.width)
                        updatedOnProgressChange(currentValue)
                        hapticTriggered = false
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        dragOffset = (dragOffset + dragAmount).coerceIn(0f, size.width.toFloat())
                        currentValue = calculateProgress(dragOffset, size.width)
                        updatedOnProgressChange(currentValue)
                        if ((currentValue == minValue || currentValue == maxValue) && !hapticTriggered) {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            hapticTriggered = true
                        } else if (currentValue != minValue && currentValue != maxValue) {
                            hapticTriggered = false
                        }
                    },
                    onDragEnd = {
                        isDragging = false
                    }
                )
            }
        } else {
            modifier
        },
        contentAlignment = Alignment.CenterStart
    ) {
        SliderBackground(
            modifier = Modifier.fillMaxWidth().height(height),
            backgroundColor = backgroundColor.value,
            foregroundColor = foregroundColor.value,
            effect = effect,
            progress = progress,
            minValue = minValue,
            maxValue = maxValue,
        )
    }
}

@Composable
fun SliderBackground(
    modifier: Modifier,
    backgroundColor: Color,
    foregroundColor: Color,
    effect: Boolean,
    progress: Float,
    minValue: Float,
    maxValue: Float,
) {
    Canvas(modifier = modifier.clip(SquircleShape(100.dp)).background(backgroundColor)) {
        val barHeight = size.height
        val barWidth = size.width
        val progressWidth = barWidth * ((progress - minValue) / (maxValue - minValue))
        val cornerRadius = if (effect) CornerRadius(barHeight / 2) else CornerRadius.Zero

        drawRoundRect(
            color = foregroundColor,
            size = Size(progressWidth, barHeight),
            topLeft = Offset(0f, center.y - barHeight / 2),
            cornerRadius = cornerRadius
        )
    }
}