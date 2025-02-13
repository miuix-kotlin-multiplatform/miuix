package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
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
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape
import kotlin.math.pow
import kotlin.math.round

/**
 * A [Slider] component with Miuix style.
 *
 * @param progress The current progress of the [Slider].
 * @param onProgressChange The callback to be called when the progress changes.
 * @param modifier The modifier to be applied to the [Slider].
 * @param enabled Whether the [Slider] is enabled.
 * @param minValue The minimum value of the [Slider]. It is required
 *   that [minValue] < [maxValue].
 * @param maxValue The maximum value of the [Slider].
 * @param height The height of the [Slider].
 * @param colors The [SliderColors] of the [Slider].
 * @param effect Whether to show the effect of the [Slider].
 * @param decimalPlaces The number of decimal places to be displayed in the drag indicator.
 */
@Composable
fun Slider(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    minValue: Float = 0f,
    maxValue: Float = 1f,
    height: Dp = SliderDefaults.MinHeight,
    colors: SliderColors = SliderDefaults.sliderColors(),
    effect: Boolean = false,
    decimalPlaces: Int = 2
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
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.GestureEnd)
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
                .indication(remember { MutableInteractionSource() }, LocalIndication.current)
        } else {
            modifier
        },
        contentAlignment = Alignment.CenterStart
    ) {
        SliderBackground(
            modifier = Modifier.fillMaxWidth().height(height),
            height = height,
            backgroundColor = colors.backgroundColor(),
            foregroundColor = colors.foregroundColor(enabled),
            effect = effect,
            progress = progress,
            minValue = minValue,
            maxValue = maxValue,
            isDragging = isDragging,
        )
    }
}

@Composable
fun SliderBackground(
    modifier: Modifier,
    height: Dp = 30.dp,
    backgroundColor: Color,
    foregroundColor: Color,
    effect: Boolean,
    progress: Float,
    minValue: Float,
    maxValue: Float,
    isDragging: Boolean,
) {
    val backgroundAlpha = animateFloatAsState(
        targetValue = if (isDragging) 0.044f else 0f,
        animationSpec = tween(150)
    ).value
    val shape = remember { derivedStateOf { SmoothRoundedCornerShape(height) } }
    Canvas(
        modifier = modifier
            .clip(shape.value)
            .background(backgroundColor)
            .drawBehind { drawRect(Color.Black.copy(alpha = backgroundAlpha)) }
    ) {
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

object SliderDefaults {

    /**
     * The default minimum height of the [Slider].
     */
    val MinHeight = 30.dp

    /**
     * The default [SliderColors] of the [Slider].
     */
    @Composable
    fun sliderColors(
        foregroundColor: Color = MiuixTheme.colorScheme.primary,
        disabledForegroundColor: Color = MiuixTheme.colorScheme.disabledPrimarySlider,
        backgroundColor: Color = MiuixTheme.colorScheme.tertiaryContainerVariant
    ): SliderColors {
        return SliderColors(
            foregroundColor = foregroundColor,
            disabledForegroundColor = disabledForegroundColor,
            backgroundColor = backgroundColor
        )
    }
}

@Immutable
class SliderColors(
    private val foregroundColor: Color,
    private val disabledForegroundColor: Color,
    private val backgroundColor: Color
) {
    @Stable
    internal fun foregroundColor(enabled: Boolean): Color =
        if (enabled) foregroundColor else disabledForegroundColor

    @Stable
    internal fun backgroundColor(): Color = backgroundColor
}