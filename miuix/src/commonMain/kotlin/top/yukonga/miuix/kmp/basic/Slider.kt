// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

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
import androidx.compose.ui.hapticfeedback.HapticFeedback
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
 * @param hapticEffect The haptic effect of the [Slider].
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
    decimalPlaces: Int = 2,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val hapticFeedback = LocalHapticFeedback.current
    var dragOffset by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    var currentValue by remember { mutableStateOf(progress) }
    val updatedOnProgressChange by rememberUpdatedState(onProgressChange)
    val factor = remember(decimalPlaces) { 10f.pow(decimalPlaces) }
    val hapticState = remember { SliderHapticState() }
    val interactionSource = remember { MutableInteractionSource() }
    val shape = remember(height) { SmoothRoundedCornerShape(height) }

    val calculateProgress = remember(minValue, maxValue, factor) {
        { offset: Float, width: Int ->
            val newValue = (offset / width) * (maxValue - minValue) + minValue
            (round(newValue * factor) / factor).coerceIn(minValue, maxValue)
        }
    }

    val currentLocalIndication = LocalIndication.current
    val boxModifier = remember(
        modifier,
        enabled,
        interactionSource,
        currentLocalIndication,
        calculateProgress,
        updatedOnProgressChange,
        hapticEffect,
        hapticFeedback,
        hapticState
    ) {
        if (enabled) {
            modifier.pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = { offset ->
                        isDragging = true
                        dragOffset = offset.x
                        val calculatedValue = calculateProgress(dragOffset, size.width)
                        currentValue = calculatedValue
                        updatedOnProgressChange(calculatedValue)
                        hapticState.reset(calculatedValue)
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        dragOffset = (dragOffset + dragAmount).coerceIn(0f, size.width.toFloat())
                        val calculatedValue = calculateProgress(dragOffset, size.width)
                        currentValue = calculatedValue
                        updatedOnProgressChange(calculatedValue)
                        hapticState.handleHapticFeedback(calculatedValue, hapticEffect, hapticFeedback)
                    },
                    onDragEnd = {
                        isDragging = false
                    }
                )
            }.indication(interactionSource, currentLocalIndication)
        } else {
            modifier
        }
    }

    Box(
        modifier = boxModifier,
        contentAlignment = Alignment.CenterStart
    ) {
        SliderTrack(
            modifier = Modifier.fillMaxWidth().height(height),
            shape = shape,
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

/**
 * Internal slider track renderer
 */
@Composable
private fun SliderTrack(
    modifier: Modifier,
    shape: SmoothRoundedCornerShape,
    backgroundColor: Color,
    foregroundColor: Color,
    effect: Boolean,
    progress: Float,
    minValue: Float,
    maxValue: Float,
    isDragging: Boolean,
) {
    val animationSpec = remember { tween<Float>(150) }
    val backgroundAlpha by animateFloatAsState(
        targetValue = if (isDragging) 0.044f else 0f,
        animationSpec = animationSpec
    )

    val canvasBaseModifier = remember(modifier, shape, backgroundColor) {
        modifier.clip(shape).background(backgroundColor)
    }

    Canvas(
        modifier = canvasBaseModifier
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

/**
 * Manages haptic feedback state for the slider.
 */
internal class SliderHapticState {
    private var edgeFeedbackTriggered: Boolean = false
    private var lastStep: Float = 0f

    fun reset(currentValue: Float) {
        edgeFeedbackTriggered = false
        lastStep = currentValue
    }

    fun handleHapticFeedback(
        currentValue: Float,
        hapticEffect: SliderDefaults.SliderHapticEffect,
        hapticFeedback: HapticFeedback
    ) {
        if (hapticEffect == SliderDefaults.SliderHapticEffect.None) return

        val isAtEdge = currentValue == 0f || currentValue == 1f
        if (isAtEdge && !edgeFeedbackTriggered) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.GestureThresholdActivate)
            edgeFeedbackTriggered = true
        } else if (!isAtEdge) {
            edgeFeedbackTriggered = false
        }

        if (hapticEffect == SliderDefaults.SliderHapticEffect.Step) {
            val isNotAtEdge = currentValue != 0f && currentValue != 1f
            if (currentValue != lastStep && isNotAtEdge) {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                lastStep = currentValue
            }
        }
    }
}

object SliderDefaults {
    val MinHeight = 30.dp

    /**
     * The type of haptic feedback to be used for the slider.
     */
    enum class SliderHapticEffect {
        /** No haptic feedback. */
        None,

        /** Haptic feedback at 0% and 100%. */
        Edge,

        /** Haptic feedback at steps. */
        Step
    }

    val DefaultHapticEffect = SliderHapticEffect.Edge

    @Composable
    fun sliderColors(
        foregroundColor: Color = MiuixTheme.colorScheme.primary,
        disabledForegroundColor: Color = MiuixTheme.colorScheme.disabledPrimarySlider,
        backgroundColor: Color = MiuixTheme.colorScheme.tertiaryContainerVariant
    ): SliderColors = SliderColors(
        foregroundColor = foregroundColor,
        disabledForegroundColor = disabledForegroundColor,
        backgroundColor = backgroundColor
    )
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