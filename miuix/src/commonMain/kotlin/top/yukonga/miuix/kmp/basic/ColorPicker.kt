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

    val selectedColor = Color.hsv(currentHue, currentSaturation, currentValue, currentAlpha)

    LaunchedEffect(initialColor) {
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

    LaunchedEffect(selectedColor) {
        if (!initialSetup) {
            onColorChanged(selectedColor)
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Color preview
        if (showPreview) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(26.dp)
                    .clip(SmoothRoundedCornerShape(50.dp))
                    .background(selectedColor)
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
    ColorSlider(
        value = currentHue / 360f,
        onValueChanged = onHueChanged,
        drawBrushColors = HUE_COLORS_FAST,
        modifier = Modifier.fillMaxWidth(),
        hapticEffect = hapticEffect
    )
}

private val HUE_COLORS_FAST = intArrayOf(
    0xFFFF0000.toInt(), // 0°   Red
    0xFFFF4000.toInt(), // 15°  Red-Orange
    0xFFFF8000.toInt(), // 30°  Orange
    0xFFFFBF00.toInt(), // 45°  Yellow-Orange
    0xFFFFFF00.toInt(), // 60°  Yellow
    0xFFBFFF00.toInt(), // 75°  Yellow-Green
    0xFF80FF00.toInt(), // 90°  Light Green
    0xFF40FF00.toInt(), // 105° Green-Yellow
    0xFF00FF00.toInt(), // 120° Green
    0xFF00FF40.toInt(), // 135° Green-Cyan
    0xFF00FF80.toInt(), // 150° Cyan-Green
    0xFF00FFBF.toInt(), // 165° Light Cyan
    0xFF00FFFF.toInt(), // 180° Cyan
    0xFF00BFFF.toInt(), // 195° Cyan-Blue
    0xFF0080FF.toInt(), // 210° Light Blue
    0xFF0040FF.toInt(), // 225° Blue-Cyan
    0xFF0000FF.toInt(), // 240° Blue
    0xFF4000FF.toInt(), // 255° Blue-Magenta
    0xFF8000FF.toInt(), // 270° Magenta-Blue
    0xFFBF00FF.toInt(), // 285° Light Magenta
    0xFFFF00FF.toInt(), // 300° Magenta
    0xFFFF00BF.toInt(), // 315° Magenta-Red
    0xFFFF0080.toInt(), // 330° Red-Magenta
    0xFFFF0040.toInt(),  // 345° Light Red
    0xFFFF0000.toInt()   // 360° Red (wrap around)
).map { Color(it) }

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
    val saturationColors = remember(currentHue) {
        listOf(Color.hsv(currentHue, 0f, 1f, 1f), Color.hsv(currentHue, 1f, 1f, 1f))
    }
    ColorSlider(
        value = currentSaturation,
        onValueChanged = onSaturationChanged,
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
    val valueColors = remember(currentHue, currentSaturation) {
        listOf(Color.Black, Color.hsv(currentHue, currentSaturation, 1f))
    }
    ColorSlider(
        value = currentValue,
        onValueChanged = onValueChanged,
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

    val alphaColors = remember(currentHue, currentSaturation, currentValue) {
        val baseColor = Color.hsv(currentHue, currentSaturation, currentValue)
        listOf(baseColor.copy(alpha = 0f), baseColor.copy(alpha = 1f))
    }

    val checkerBrush = remember(density) {
        createCheckerboardBrush(density)
    }

    ColorSlider(
        value = currentAlpha,
        onValueChanged = onAlphaChanged,
        drawBrushColors = alphaColors,
        modifier = Modifier.fillMaxWidth(),
        hapticEffect = hapticEffect,
        drawBackground = { _, _ -> drawRect(brush = checkerBrush) }
    )
}

private fun createCheckerboardBrush(density: androidx.compose.ui.unit.Density): ShaderBrush {
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
    return ShaderBrush(shader)
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
    val sliderHeightPx = with(density) { sliderHeightDp.toPx() }
    val sliderWidthPx = with(density) { sliderWidth.toPx() }
    val halfSliderHeightPx = sliderHeightPx / 2f
    val hapticFeedback = LocalHapticFeedback.current
    val hapticState = remember { SliderHapticState() }

    val gradientBrush = remember(drawBrushColors, sliderWidthPx, halfSliderHeightPx) {
        Brush.horizontalGradient(
            colors = drawBrushColors,
            startX = halfSliderHeightPx,
            endX = sliderWidthPx - halfSliderHeightPx,
            tileMode = TileMode.Clamp
        )
    }

    Box(
        modifier = modifier
            .height(sliderHeightDp)
            .clip(SmoothRoundedCornerShape(50.dp))
            .border(0.5.dp, Color.Gray.copy(0.1f), SmoothRoundedCornerShape(50.dp))
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    sliderWidth = with(density) { coordinates.size.width.toDp() }
                }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = { offset ->
                            val newValue = handleSliderInteraction(offset.x, size.width.toFloat(), sliderHeightPx).coerceIn(0f, 1f)
                            onValueChanged(newValue)
                            hapticState.reset(newValue)
                        },
                        onHorizontalDrag = { change, _ ->
                            change.consume()
                            val newValue = handleSliderInteraction(change.position.x, size.width.toFloat(), sliderHeightPx).coerceIn(0f, 1f)
                            onValueChanged(newValue)
                            hapticState.handleHapticFeedback(newValue, hapticEffect, hapticFeedback)
                        }
                    )
                }
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

    Box(
        modifier = modifier
            .offset(x = indicatorOffsetXDp)
            .size(indicatorSize)
            .clip(CircleShape)
            .border(6.dp, Color.White, CircleShape)
            .background(Color.Transparent, CircleShape)
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