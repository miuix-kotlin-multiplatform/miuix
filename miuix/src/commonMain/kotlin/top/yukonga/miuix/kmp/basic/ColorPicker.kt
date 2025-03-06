package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
            onColorChanged(selectedColor)
        }
    }

    // Color preview
    if (showPreview) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(26.dp)
                .clip(SmoothRoundedCornerShape(50.dp))
                .background(selectedColor)
        )
        Spacer(modifier = Modifier.height(12.dp))
    }

    // Hue selection
    HueSlider(
        currentHue = currentHue,
        onHueChanged = { newHue -> currentHue = newHue * 360f },
        hapticEffect = hapticEffect
    )

    Spacer(modifier = Modifier.height(12.dp))

    // Saturation selection
    SaturationSlider(
        currentHue = currentHue,
        currentSaturation = currentSaturation,
        onSaturationChanged = { currentSaturation = it },
        hapticEffect = hapticEffect
    )

    Spacer(modifier = Modifier.height(12.dp))

    // Value selection
    ValueSlider(
        currentHue = currentHue,
        currentSaturation = currentSaturation,
        currentValue = currentValue,
        onValueChanged = { currentValue = it },
        hapticEffect = hapticEffect
    )

    Spacer(modifier = Modifier.height(12.dp))

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
    val hueColors = List(36) { i -> Color.hsv(i * 10f, 1f, 1f) }
    ColorSlider(
        value = currentHue / 360f,
        onValueChanged = onHueChanged,
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
    ColorSlider(
        value = currentSaturation,
        onValueChanged = onSaturationChanged,
        drawBrushColors = listOf(Color.hsv(currentHue, 0f, 1f, 1f), Color.hsv(currentHue, 1f, 1f, 1f)),
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
    ColorSlider(
        value = currentValue,
        onValueChanged = onValueChanged,
        drawBrushColors = listOf(Color.Black, Color.hsv(currentHue, currentSaturation, 1f)),
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
    val baseColor = Color.hsv(currentHue, currentSaturation, currentValue)
    val startColor = remember(baseColor) { baseColor.copy(alpha = 0f) }
    val endColor = remember(baseColor) { baseColor.copy(alpha = 1f) }

    val checkerBrush = remember {
        object {
            val light = Color(0xFFCCCCCC)
            val dark = Color(0xFFAAAAAA)
            val checkerSize = 3.dp

            fun draw(drawScope: DrawScope, width: Float, height: Float) {
                with(drawScope) {
                    val pixelSize = checkerSize.toPx()
                    val horizontalCount = (width / pixelSize).toInt() + 1
                    val verticalCount = (height / pixelSize).toInt() + 1

                    drawRect(light)

                    for (y in 0 until verticalCount) {
                        val isEvenRow = y % 2 == 0
                        val startX = if (isEvenRow) 0 else 1

                        for (x in startX until horizontalCount step 2) {
                            drawRect(
                                color = dark,
                                topLeft = Offset(x * pixelSize, y * pixelSize),
                                size = Size(pixelSize, pixelSize)
                            )
                        }
                    }
                }
            }
        }
    }

    ColorSlider(
        value = currentAlpha,
        onValueChanged = onAlphaChanged,
        drawBrushColors = listOf(startColor, endColor),
        modifier = Modifier.fillMaxWidth(),
        hapticEffect = hapticEffect,
        drawBackground = checkerBrush::draw
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
    val sliderSizePx = with(density) { remember { 26.dp.toPx() } }
    val halfSliderSizePx = remember(sliderSizePx) { sliderSizePx / 2f }
    val borderShape = remember { SmoothRoundedCornerShape(50.dp) }
    val borderStroke = remember { 0.5.dp }
    val borderColor = remember { Color.Gray.copy(0.1f) }
    val hapticFeedback = LocalHapticFeedback.current
    val hapticState = remember { SliderHapticState() }

    val dragHandler = remember(onValueChanged, sliderSizePx) {
        { posX: Float, width: Float ->
            handleSliderInteraction(posX, width, sliderSizePx).coerceIn(0f, 1f)
        }
    }

    Box(
        modifier = modifier
            .height(26.dp)
            .clip(borderShape)
            .border(borderStroke, borderColor, borderShape)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    sliderWidth = with(density) { coordinates.size.width.toDp() }
                }
                .pointerInput(dragHandler) {
                    detectHorizontalDragGestures(
                        onDragStart = { offset ->
                            val currentValue = dragHandler(offset.x, size.width.toFloat())
                            onValueChanged(currentValue)
                            hapticState.reset(currentValue)
                        },
                        onHorizontalDrag = { change, _ ->
                            change.consume()
                            val currentValue = dragHandler(change.position.x, size.width.toFloat())
                            onValueChanged(currentValue)
                            hapticState.handleHapticFeedback(currentValue, hapticEffect, hapticFeedback)
                        }
                    )
                }
        ) {
            drawBackground?.invoke(this, size.width, size.height)

            Brush.horizontalGradient(
                colors = drawBrushColors,
                startX = halfSliderSizePx,
                endX = size.width - halfSliderSizePx,
                tileMode = TileMode.Clamp
            ).let {
                drawRect(it)
            }
        }

        SliderIndicator(
            modifier = Modifier.align(Alignment.CenterStart),
            value = value,
            sliderWidth = sliderWidth,
            sliderSizePx = sliderSizePx,
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
    Box(
        modifier = modifier
            .offset(
                x = with(density) {
                    val effectiveWidth = sliderWidth.toPx() - sliderSizePx
                    ((value * effectiveWidth) + sliderSizePx / 2).toDp() - (indicatorSize / 2)
                }
            )
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