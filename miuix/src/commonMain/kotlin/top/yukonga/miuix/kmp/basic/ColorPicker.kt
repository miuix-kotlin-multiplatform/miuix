// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.utils.ColorUtils
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape

/**
 * A [ColorPicker] component with Miuix style.
 *
 * @param initialColor The initial color of the picker.
 * @param onColorChanged The callback to be called when the color changes.
 * @param showPreview Whether to show the color preview.
 * @param hapticEffect The haptic effect of the [ColorSlider].
 * @param modifier The modifier to be applied to the color picker.
 */
@Composable
fun ColorPicker(
    initialColor: Color,
    onColorChanged: (Color) -> Unit,
    showPreview: Boolean = true,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect,
    modifier: Modifier = Modifier
) {
    var initialSetup by remember { mutableStateOf(true) }
    var currentHue by remember { mutableStateOf(0f) }
    var currentSaturation by remember { mutableStateOf(0f) }
    var currentValue by remember { mutableStateOf(0f) }
    var currentAlpha by remember { mutableStateOf(1f) }

    val currentOnColorChanged by rememberUpdatedState(onColorChanged)

    val selectedColor = remember(currentHue, currentSaturation, currentValue, currentAlpha) {
        Color.hsv(currentHue, currentSaturation, currentValue, currentAlpha)
    }

    LaunchedEffect(initialColor, initialSetup) {
        if (initialSetup) {
            val hsv = FloatArray(3)
            ColorUtils.rgbToHsv(
                (initialColor.red * 255).toInt(),
                (initialColor.green * 255).toInt(),
                (initialColor.blue * 255).toInt(),
                hsv
            )
            currentHue = hsv[0]
            currentSaturation = hsv[1]
            currentValue = hsv[2]
            currentAlpha = initialColor.alpha
            initialSetup = false
        }
    }

    var previousColor by remember { mutableStateOf(selectedColor) }

    LaunchedEffect(selectedColor) {
        if (!initialSetup && selectedColor != previousColor) {
            previousColor = selectedColor
            currentOnColorChanged(selectedColor)
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Color preview
        if (showPreview) {
            val previewShape = remember { SmoothRoundedCornerShape(50.dp) }
            val previewModifier = remember(selectedColor, previewShape) {
                Modifier
                    .fillMaxWidth()
                    .height(26.dp)
                    .clip(previewShape)
                    .background(selectedColor)
            }
            Box(
                modifier = previewModifier
            )
        }

        // Hue selection
        HueSlider(
            currentHue = currentHue,
            onHueChanged = { newHue -> currentHue = newHue * 360f },
            hapticEffect = hapticEffect
        )

        // Saturation selection
        SaturationSlider(
            currentHue = currentHue,
            currentSaturation = currentSaturation,
            onSaturationChanged = { currentSaturation = it },
            hapticEffect = hapticEffect
        )

        // Value selection
        ValueSlider(
            currentHue = currentHue,
            currentSaturation = currentSaturation,
            currentValue = currentValue,
            onValueChanged = { currentValue = it },
            hapticEffect = hapticEffect
        )

        // Alpha selection
        AlphaSlider(
            currentHue = currentHue,
            currentSaturation = currentSaturation,
            currentValue = currentValue,
            currentAlpha = currentAlpha,
            onAlphaChanged = { currentAlpha = it },
            hapticEffect = hapticEffect
        )
    }
}

/**
 * A [HueSlider] component for selecting the hue of a color.
 *
 * @param currentHue The current hue value.
 * @param onHueChanged The callback to be called when the hue changes.
 * @param hapticEffect The haptic effect of the [HueSlider].
 */
@Composable
fun HueSlider(
    currentHue: Float,
    onHueChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val currentOnHueChanged by rememberUpdatedState(onHueChanged)
    val hueColors = remember { List(36) { i -> Color.hsv(i * 10f, 1f, 1f) } }
    ColorSlider(
        value = currentHue / 360f,
        onValueChanged = currentOnHueChanged,
        drawBrushColors = hueColors,
        modifier = Modifier.fillMaxWidth(),
        hapticEffect = hapticEffect
    )
}

/**
 * A [SaturationSlider] component for selecting the saturation of a color.
 *
 * @param currentHue The current hue value.
 * @param currentSaturation The current saturation value.
 * @param onSaturationChanged The callback to be called when the saturation changes.
 * @param hapticEffect The haptic effect of the [SaturationSlider].
 */
@Composable
fun SaturationSlider(
    currentHue: Float,
    currentSaturation: Float,
    onSaturationChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val currentOnSaturationChanged by rememberUpdatedState(onSaturationChanged)
    val saturationColors = remember(currentHue) {
        listOf(Color.hsv(currentHue, 0f, 1f, 1f), Color.hsv(currentHue, 1f, 1f, 1f))
    }
    ColorSlider(
        value = currentSaturation,
        onValueChanged = currentOnSaturationChanged,
        drawBrushColors = saturationColors,
        modifier = Modifier.fillMaxWidth(),
        hapticEffect = hapticEffect
    )
}


/**
 * A [ValueSlider] component for selecting the value of a color.
 *
 * @param currentHue The current hue value.
 * @param currentSaturation The current saturation value.
 * @param currentValue The current value value.
 * @param onValueChanged The callback to be called when the value changes.
 * @param hapticEffect The haptic effect of the [ValueSlider].
 */
@Composable
fun ValueSlider(
    currentHue: Float,
    currentSaturation: Float,
    currentValue: Float,
    onValueChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val currentOnValueChanged by rememberUpdatedState(onValueChanged)
    val valueColors = remember(currentHue, currentSaturation) {
        listOf(Color.Black, Color.hsv(currentHue, currentSaturation, 1f))
    }
    ColorSlider(
        value = currentValue,
        onValueChanged = currentOnValueChanged,
        drawBrushColors = valueColors,
        modifier = Modifier.fillMaxWidth(),
        hapticEffect = hapticEffect
    )
}

/**
 * A [AlphaSlider] component for selecting the alpha of a color.
 *
 * @param currentHue The current hue value.
 * @param currentSaturation The current saturation value.
 * @param currentValue The current value value.
 * @param currentAlpha The current alpha value.
 * @param onAlphaChanged The callback to be called when the alpha changes.
 * @param hapticEffect The haptic effect of the [AlphaSlider].
 */
@Composable
fun AlphaSlider(
    currentHue: Float,
    currentSaturation: Float,
    currentValue: Float,
    currentAlpha: Float,
    onAlphaChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val density = LocalDensity.current

    val currentOnAlphaChanged by rememberUpdatedState(onAlphaChanged)
    val baseColor = remember(currentHue, currentSaturation, currentValue) {
        Color.hsv(currentHue, currentSaturation, currentValue)
    }
    val startColor = remember(baseColor) { baseColor.copy(alpha = 0f) }
    val endColor = remember(baseColor) { baseColor.copy(alpha = 1f) }
    val alphaColors = remember(startColor, endColor) { listOf(startColor, endColor) }

    val actualCheckerBrush = remember(density) {
        val lightColor = Color(0xFFCCCCCC)
        val darkColor = Color(0xFFAAAAAA)
        val checkerSizeDp = 3.dp

        val pixelSize = with(density) { checkerSizeDp.toPx() }
        val tileBitmapSideLengthPx = (2 * pixelSize).toInt().coerceAtLeast(1)

        val imageBitmap = ImageBitmap(tileBitmapSideLengthPx, tileBitmapSideLengthPx)
        val canvasForBitmap = androidx.compose.ui.graphics.Canvas(imageBitmap)

        val lightPaint = Paint().apply { color = lightColor }
        val darkPaint = Paint().apply { color = darkColor }

        canvasForBitmap.drawRect(0f, 0f, tileBitmapSideLengthPx.toFloat(), tileBitmapSideLengthPx.toFloat(), lightPaint)
        canvasForBitmap.drawRect(pixelSize, 0f, 2 * pixelSize, pixelSize, darkPaint)
        canvasForBitmap.drawRect(0f, pixelSize, pixelSize, 2 * pixelSize, darkPaint)

        val shader = ImageShader(imageBitmap, TileMode.Repeated, TileMode.Repeated)
        ShaderBrush(shader)
    }

    ColorSlider(
        value = currentAlpha,
        onValueChanged = currentOnAlphaChanged,
        drawBrushColors = alphaColors,
        modifier = Modifier.fillMaxWidth(),
        hapticEffect = hapticEffect,
        drawBackground = { _, _ -> drawRect(brush = actualCheckerBrush) }
    )
}

/**
 * Generic slider component for color selection.
 */
@Composable
private fun ColorSlider(
    value: Float,
    onValueChanged: (Float) -> Unit,
    drawBrushColors: List<Color>,
    modifier: Modifier = Modifier,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect,
    drawBackground: (DrawScope.(width: Float, height: Float) -> Unit)? = null
) {
    val density = LocalDensity.current
    var sliderWidth by remember { mutableStateOf(0.dp) }
    val indicatorSizeDp = 20.dp
    val sliderHeightDp = 26.dp
    val sliderHeightPx = with(density) { remember(sliderHeightDp) { sliderHeightDp.toPx() } }
    val sliderWidthPx = with(density) { sliderWidth.toPx() }
    val halfSliderHeightPx = remember(sliderHeightPx) { sliderHeightPx / 2f }
    val borderShape = remember { SmoothRoundedCornerShape(50.dp) }
    val borderStroke = remember { 0.5.dp }
    val borderColor = remember { Color.Gray.copy(0.1f) }
    val hapticFeedback = LocalHapticFeedback.current
    val hapticState = remember { SliderHapticState() }
    val currentOnValueChanged by rememberUpdatedState(onValueChanged)

    val gradientBrush = remember(drawBrushColors, sliderWidthPx, halfSliderHeightPx) {
        Brush.horizontalGradient(
            colors = drawBrushColors,
            startX = halfSliderHeightPx,
            endX = sliderWidthPx - halfSliderHeightPx,
            tileMode = TileMode.Clamp
        )
    }

    val dragHandler = remember(currentOnValueChanged, sliderHeightPx) {
        { posX: Float, width: Float ->
            handleSliderInteraction(posX, width, sliderHeightPx).coerceIn(0f, 1f)
        }
    }

    val boxModifier = remember(modifier, borderShape, borderStroke, borderColor, sliderHeightDp) {
        modifier
            .height(sliderHeightDp)
            .clip(borderShape)
            .border(borderStroke, borderColor, borderShape)
    }

    Box(
        modifier = boxModifier
    ) {
        val canvasModifier = remember(dragHandler) {
            Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    sliderWidth = with(density) { coordinates.size.width.toDp() }
                }
                .pointerInput(dragHandler) {
                    detectHorizontalDragGestures(
                        onDragStart = { offset ->
                            val newValue = dragHandler(offset.x, size.width.toFloat())
                            currentOnValueChanged(newValue)
                            hapticState.reset(newValue)
                        },
                        onHorizontalDrag = { change, _ ->
                            change.consume()
                            val newValue = dragHandler(change.position.x, size.width.toFloat())
                            currentOnValueChanged(newValue)
                            hapticState.handleHapticFeedback(newValue, hapticEffect, hapticFeedback)
                        }
                    )
                }
        }
        Canvas(
            modifier = canvasModifier
        ) {
            drawBackground?.invoke(this, size.width, size.height)
            drawRect(gradientBrush)
        }

        SliderIndicator(
            modifier = Modifier.align(Alignment.CenterStart),
            value = value,
            sliderWidth = sliderWidth,
            sliderSizePx = sliderHeightPx,
            indicatorSize = indicatorSizeDp
        )
    }
}

@Composable
private fun SliderIndicator(
    modifier: Modifier,
    value: Float,
    sliderWidth: Dp,
    sliderSizePx: Float,
    indicatorSize: Dp
) {
    val density = LocalDensity.current
    val indicatorOffsetXDp = with(density) {
        val sliderWidthPx = sliderWidth.toPx()
        val effectiveWidthPx = sliderWidthPx - sliderSizePx
        val indicatorPositionPx = (value * effectiveWidthPx) + (sliderSizePx / 2)
        indicatorPositionPx.toDp() - (indicatorSize / 2)
    }

    val indicatorBoxModifier = remember(modifier, indicatorOffsetXDp, indicatorSize) {
        modifier
            .offset(x = indicatorOffsetXDp)
            .size(indicatorSize)
            .clip(CircleShape)
            .border(6.dp, Color.White, CircleShape)
            .background(Color.Transparent, CircleShape)
    }
    Box(
        modifier = indicatorBoxModifier
    )
}

/**
 * Handle slider interaction and calculate new value.
 */
private fun handleSliderInteraction(
    positionX: Float,
    totalWidth: Float,
    sliderSizePx: Float
): Float {
    val sliderHalfSizePx = sliderSizePx / 2
    val effectiveWidth = totalWidth - sliderSizePx
    val constrainedX = positionX.coerceIn(sliderHalfSizePx, totalWidth - sliderHalfSizePx)
    val newPosition = (constrainedX - sliderHalfSizePx) / effectiveWidth
    return newPosition.coerceIn(0f, 1f)
}