package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import kotlin.math.pow
import kotlin.math.round

/**
 * A slider component with Miuix style.
 *
 * @param modifier The modifier to be applied to the [MiuixSlider].
 * @param progress The current progress of the [MiuixSlider].
 * @param minValue The minimum value of the [MiuixSlider]. It is required
 *   that [minValue] < [maxValue].
 * @param maxValue The maximum value of the [MiuixSlider].
 * @param height The height of the [MiuixSlider].
 * @param effect Whether to show the effect of the [MiuixSlider].
 * @param dragShow Whether to show the drag indicator of the [MiuixSlider].
 * @param decimalPlaces The number of decimal places to be displayed in the drag indicator.
 * @param onProgressChange The callback to be called when the progress changes.
 */
@Composable
fun MiuixSlider(
    modifier: Modifier = Modifier,
    progress: Float,
    minValue: Float = 0f,
    maxValue: Float = 1f,
    height: Dp = 30.dp,
    effect: Boolean = false,
    dragShow: Boolean = false,
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
    val color = MiuixTheme.colorScheme.primary
    val backgroundColor = MiuixTheme.colorScheme.sliderBackground

    MiuixBox(
        modifier = modifier
            .pointerInput(Unit) {
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
            },
        contentAlignment = Alignment.CenterStart
    ) {
        SliderBackground(
            modifier = Modifier.fillMaxWidth().height(height),
            backgroundColor = backgroundColor,
            effect = effect,
            progress = progress,
            minValue = minValue,
            maxValue = maxValue,
            color = color
        )
        DragIndicator(
            isDragging = isDragging,
            dragShow = dragShow,
            currentValue = currentValue,
            decimalPlaces = decimalPlaces
        )
    }
}

@Composable
fun SliderBackground(
    modifier: Modifier,
    backgroundColor: Color,
    effect: Boolean,
    progress: Float,
    minValue: Float,
    maxValue: Float,
    color: Color
) {
    Canvas(modifier = modifier.clip(RoundedCornerShape(50.dp)).background(backgroundColor)) {
        val barHeight = size.height
        val barWidth = size.width
        val progressWidth = barWidth * ((progress - minValue) / (maxValue - minValue))
        val cornerRadius = if (effect) CornerRadius(barHeight / 2) else CornerRadius.Zero

        drawRoundRect(
            color = color,
            size = Size(progressWidth, barHeight),
            topLeft = Offset(0f, center.y - barHeight / 2),
            cornerRadius = cornerRadius
        )
    }
}

@Composable
fun DragIndicator(
    isDragging: Boolean,
    dragShow: Boolean,
    currentValue: Float,
    decimalPlaces: Int
) {
    AnimatedVisibility(
        modifier = Modifier.fillMaxWidth(),
        visible = isDragging && dragShow,
        enter = fadeIn(animationSpec = tween(200)),
        exit = fadeOut(animationSpec = tween(400))
    ) {
        MiuixText(
            text = if (decimalPlaces == 0) currentValue.toInt().toString() else currentValue.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize(),
        )
    }
}