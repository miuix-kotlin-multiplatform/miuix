// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.utils.CapsuleShape
import top.yukonga.miuix.kmp.utils.ColorUtils
import top.yukonga.miuix.kmp.utils.Hsv
import top.yukonga.miuix.kmp.utils.OkLab
import top.yukonga.miuix.kmp.utils.toHsv
import top.yukonga.miuix.kmp.utils.toOkLab
import kotlin.math.ceil
import kotlin.math.min

/**
 * A [ColorPicker] component with Miuix style that supports multiple color spaces.
 *
 * @param initialColor The initial color of the picker.
 * @param onColorChanged The callback to be called when the color changes.
 * @param showPreview Whether to show a preview of the selected color.
 * @param hapticEffect The haptic effect of the [ColorSlider].
 * @param colorSpace The color space to use for the picker.
 * @param modifier The modifier to be applied to the color picker.
 */
@Composable
fun ColorPicker(
    initialColor: Color,
    onColorChanged: (Color) -> Unit,
    showPreview: Boolean = true,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect,
    colorSpace: ColorSpace = ColorSpace.HSV,
    modifier: Modifier = Modifier,
) {
    when (colorSpace) {
        ColorSpace.OKHSV -> {
            OkHsvColorPicker(
                initialColor = initialColor,
                onColorChanged = onColorChanged,
                showPreview = showPreview,
                hapticEffect = hapticEffect,
                modifier = modifier
            )
        }

        ColorSpace.OKLAB -> {
            OkLabColorPicker(
                initialColor = initialColor,
                onColorChanged = onColorChanged,
                showPreview = showPreview,
                hapticEffect = hapticEffect,
                modifier = modifier
            )
        }

        else -> {
            HsvColorPicker(
                initialColor = initialColor,
                onColorChanged = onColorChanged,
                showPreview = showPreview,
                hapticEffect = hapticEffect,
                modifier = modifier
            )
        }
    }
}

/**
 * A [HsvColorPicker] component with Miuix style using HSV color space.
 *
 * @param initialColor The initial color of the picker.
 * @param onColorChanged The callback to be called when the color changes.
 * @param showPreview Whether to show a preview of the selected color.
 * @param hapticEffect The haptic effect of the [ColorSlider].
 * @param modifier The modifier to be applied to the color picker.
 */
@Composable
fun HsvColorPicker(
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

    val selectedColor = Hsv(
        h = currentHue.toDouble(),
        v = (currentValue * 100.0),
        s = (currentSaturation * 100.0)
    ).toColor(currentAlpha)

    LaunchedEffect(initialColor) {
        if (initialSetup) {
            val hsv = initialColor.toHsv()
            currentHue = hsv.h.toFloat()
            currentSaturation = (hsv.s / 100.0).toFloat()
            currentValue = (hsv.v / 100.0).toFloat()
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
                    .clip(CapsuleShape())
                    .background(selectedColor)
            )
        }

        // Hue selection
        HsvHueSlider(
            currentHue = currentHue,
            onHueChanged = { newHue -> currentHue = newHue * 360f },
            hapticEffect = hapticEffect
        )

        // Saturation selection
        HsvSaturationSlider(
            currentHue = currentHue,
            currentSaturation = currentSaturation,
            onSaturationChanged = { currentSaturation = it },
            hapticEffect = hapticEffect
        )

        // Value selection
        HsvValueSlider(
            currentHue = currentHue,
            currentSaturation = currentSaturation,
            currentValue = currentValue,
            onValueChanged = { currentValue = it },
            hapticEffect = hapticEffect
        )

        // Alpha selection
        HsvAlphaSlider(
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
 * A [HsvHueSlider] component for selecting the hue of a color using pure HSV colors.
 *
 * @param currentHue The current hue value (0-360).
 * @param onHueChanged The callback to be called when the hue changes (0-1).
 * @param hapticEffect The haptic effect of the [HsvHueSlider].
 */
@Composable
fun HsvHueSlider(
    currentHue: Float,
    onHueChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val hsvHueColors = remember {
        ColorUtils.generateHsvHueColors()
    }

    ColorSlider(
        value = currentHue / 360f,
        onValueChanged = onHueChanged,
        drawBrushColors = hsvHueColors,
        modifier = Modifier.fillMaxWidth(),
        hapticEffect = hapticEffect
    )
}

/**
 * A [HsvSaturationSlider] component for selecting the saturation of a color.
 *
 * @param currentHue The current hue value.
 * @param currentSaturation The current saturation value.
 * @param onSaturationChanged The callback to be called when the saturation changes.
 * @param hapticEffect The haptic effect of the [HsvSaturationSlider].
 */
@Composable
fun HsvSaturationSlider(
    currentHue: Float,
    currentSaturation: Float,
    onSaturationChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val saturationColors = remember(currentHue) {
        listOf(
            Hsv(currentHue.toDouble(), 100.0, 0.0).toColor(1f),
            Hsv(currentHue.toDouble(), 100.0, 100.0).toColor(1f)
        )
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
 * A [HsvValueSlider] component for selecting the value/brightness of a color.
 *
 * @param currentHue The current hue value.
 * @param currentSaturation The current saturation value.
 * @param currentValue The current value value.
 * @param onValueChanged The callback to be called when the value changes.
 * @param hapticEffect The haptic effect of the [HsvValueSlider].
 */
@Composable
fun HsvValueSlider(
    currentHue: Float,
    currentSaturation: Float,
    currentValue: Float,
    onValueChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val valueColors = remember(currentHue, currentSaturation) {
        listOf(Color.Black, Hsv(currentHue.toDouble(), 100.0, (currentSaturation * 100.0)).toColor())
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
 * A [HsvAlphaSlider] component for selecting the alpha of a color.
 *
 * @param currentHue The current hue value.
 * @param currentSaturation The current saturation value.
 * @param currentValue The current value value.
 * @param currentAlpha The current alpha value.
 * @param onAlphaChanged The callback to be called when the alpha changes.
 * @param hapticEffect The haptic effect of the [HsvAlphaSlider].
 */
@Composable
fun HsvAlphaSlider(
    currentHue: Float,
    currentSaturation: Float,
    currentValue: Float,
    currentAlpha: Float,
    onAlphaChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val alphaColors = remember(currentHue, currentSaturation, currentValue) {
        val baseColor = Hsv(currentHue.toDouble(), (currentValue * 100.0), (currentSaturation * 100.0)).toColor()
        listOf(baseColor.copy(alpha = 0f), baseColor.copy(alpha = 1f))
    }


    ColorSlider(
        value = currentAlpha,
        onValueChanged = onAlphaChanged,
        drawBrushColors = alphaColors,
        modifier = Modifier
            .fillMaxWidth()
            .drawCheckerboard(),
        hapticEffect = hapticEffect
    )
}

/**
 * A [OkHsvColorPicker] component with Miuix style using OkHSV color space based on OkLab.
 * OkHSV provides better perceptual uniformity than traditional HSV.
 *
 * @param initialColor The initial color of the picker.
 * @param onColorChanged The callback to be called when the color changes.
 * @param showPreview Whether to show a preview of the selected color.
 * @param hapticEffect The haptic effect of the [ColorSlider].
 * @param modifier The modifier to be applied to the color picker.
 */
@Composable
fun OkHsvColorPicker(
    initialColor: Color,
    onColorChanged: (Color) -> Unit,
    showPreview: Boolean = true,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect,
    modifier: Modifier = Modifier
) {
    var initialSetup by remember { mutableStateOf(true) }
    var currentH by remember { mutableStateOf(0f) }
    var currentS by remember { mutableStateOf(0f) }
    var currentV by remember { mutableStateOf(0f) }
    var currentAlpha by remember { mutableStateOf(1f) }

    val selectedColor = remember(currentH, currentS, currentV, currentAlpha) {
        ColorUtils.okhsvToColor(currentH, currentS, currentV, currentAlpha)
    }

    LaunchedEffect(initialColor) {
        if (initialSetup) {
            val okhsv = ColorUtils.colorToOkhsv(initialColor)
            currentH = okhsv[0]
            currentS = okhsv[1]
            currentV = okhsv[2]
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
                    .clip(CapsuleShape())
                    .background(selectedColor)
            )
        }

        // Hue selection (OkHSV)
        OkHsvHueSlider(
            currentH = currentH,
            onHueChanged = { currentH = it },
            hapticEffect = hapticEffect
        )

        // Saturation selection (OkHSV)
        OkHsvSaturationSlider(
            currentH = currentH,
            currentS = currentS,
            onSaturationChanged = { currentS = it },
            hapticEffect = hapticEffect
        )

        // Value selection (OkHSV)
        OkHsvValueSlider(
            currentH = currentH,
            currentS = currentS,
            currentV = currentV,
            onValueChanged = { currentV = it },
            hapticEffect = hapticEffect
        )

        // Alpha selection (OkHSV)
        OkHsvAlphaSlider(
            currentH = currentH,
            currentS = currentS,
            currentV = currentV,
            currentAlpha = currentAlpha,
            onAlphaChanged = { currentAlpha = it },
            hapticEffect = hapticEffect
        )
    }
}

/**
 * A [OkHsvHueSlider] component for selecting the hue of a color using OkHSV color space.
 *
 * @param currentH The current hue value (0-1).
 * @param onHueChanged The callback to be called when the hue changes.
 * @param hapticEffect The haptic effect of the [OkHsvHueSlider].
 */
@Composable
fun OkHsvHueSlider(
    currentH: Float,
    onHueChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val okHsvHueColors = remember {
        ColorUtils.generateOkHsvHueColors()
    }

    ColorSlider(
        value = currentH,
        onValueChanged = onHueChanged,
        drawBrushColors = okHsvHueColors,
        modifier = Modifier.fillMaxWidth(),
        hapticEffect = hapticEffect
    )
}

/**
 * A [OkHsvSaturationSlider] component for selecting the saturation of a color using OkHSV.
 *
 * @param currentH The current hue value.
 * @param currentS The current saturation value.
 * @param onSaturationChanged The callback to be called when the saturation changes.
 * @param hapticEffect The haptic effect of the [OkHsvSaturationSlider].
 */
@Composable
fun OkHsvSaturationSlider(
    currentH: Float,
    currentS: Float,
    onSaturationChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val saturationColors = remember(currentH) {
        listOf(
            ColorUtils.okhsvToColor(currentH, 0f, 1f, 1f),
            ColorUtils.okhsvToColor(currentH, 1f, 1f, 1f)
        )
    }

    ColorSlider(
        value = currentS,
        onValueChanged = onSaturationChanged,
        drawBrushColors = saturationColors,
        modifier = Modifier.fillMaxWidth(),
        hapticEffect = hapticEffect
    )
}

/**
 * A [OkHsvValueSlider] component for selecting the value/brightness of a color using OkHSV.
 *
 * @param currentH The current hue value.
 * @param currentS The current saturation value.
 * @param currentV The current value value.
 * @param onValueChanged The callback to be called when the value changes.
 * @param hapticEffect The haptic effect of the [OkHsvValueSlider].
 */
@Composable
fun OkHsvValueSlider(
    currentH: Float,
    currentS: Float,
    currentV: Float,
    onValueChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val valueColors = remember(currentH, currentS) {
        listOf(
            ColorUtils.okhsvToColor(currentH, currentS, 0f, 1f),
            ColorUtils.okhsvToColor(currentH, currentS, 1f, 1f)
        )
    }

    ColorSlider(
        value = currentV,
        onValueChanged = onValueChanged,
        drawBrushColors = valueColors,
        modifier = Modifier.fillMaxWidth(),
        hapticEffect = hapticEffect
    )
}

/**
 * A [OkHsvAlphaSlider] component for selecting the alpha of a color using OkHSV.
 *
 * @param currentH The current hue value.
 * @param currentS The current saturation value.
 * @param currentV The current value value.
 * @param currentAlpha The current alpha value.
 * @param onAlphaChanged The callback to be called when the alpha changes.
 * @param hapticEffect The haptic effect of the [OkHsvAlphaSlider].
 */
@Composable
fun OkHsvAlphaSlider(
    currentH: Float,
    currentS: Float,
    currentV: Float,
    currentAlpha: Float,
    onAlphaChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val alphaColors = remember(currentH, currentS, currentV) {
        val baseColor = ColorUtils.okhsvToColor(currentH, currentS, currentV)
        listOf(baseColor.copy(alpha = 0f), baseColor.copy(alpha = 1f))
    }

    ColorSlider(
        value = currentAlpha,
        onValueChanged = onAlphaChanged,
        drawBrushColors = alphaColors,
        modifier = Modifier
            .fillMaxWidth()
            .drawCheckerboard(),
        hapticEffect = hapticEffect
    )
}


/**
 * A [OkLabColorPicker] component with Miuix style using OkLab color space.
 *
 * @param initialColor The initial color of the picker.
 * @param onColorChanged The callback to be called when the color changes.
 * @param showPreview Whether to show a preview of the selected color.
 * @param hapticEffect The haptic effect of the [ColorSlider].
 * @param modifier The modifier to be applied to the color picker.
 */
@Composable
fun OkLabColorPicker(
    initialColor: Color,
    onColorChanged: (Color) -> Unit,
    showPreview: Boolean = true,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect,
    modifier: Modifier = Modifier
) {
    var initialSetup by remember { mutableStateOf(true) }
    var currentL by remember { mutableStateOf(0f) }
    var currentA by remember { mutableStateOf(0f) }
    var currentB by remember { mutableStateOf(0f) }
    var currentAlpha by remember { mutableStateOf(1f) }

    val selectedColor = remember(currentL, currentA, currentB, currentAlpha) {
        OkLab(
            l = (currentL * 100.0),
            a = (currentA / 0.4 * 100.0),
            b = (currentB / 0.4 * 100.0)
        ).toColor(currentAlpha)
    }

    LaunchedEffect(initialColor) {
        if (initialSetup) {
            val ok = initialColor.toOkLab()
            currentL = (ok.l / 100.0).toFloat()
            currentA = ((ok.a / 100.0) * 0.4).toFloat()
            currentB = ((ok.b / 100.0) * 0.4).toFloat()
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
                    .clip(CapsuleShape())
                    .background(selectedColor)
            )
        }

        // Lightness selection
        OkLabLightnessSlider(
            currentL = currentL,
            currentA = currentA,
            currentB = currentB,
            onLightnessChanged = { currentL = it },
            hapticEffect = hapticEffect
        )

        // A channel selection (green-red axis)
        OkLabAChannelSlider(
            currentL = currentL,
            currentA = currentA,
            currentB = currentB,
            onAChanged = { currentA = it },
            hapticEffect = hapticEffect
        )

        // B channel selection (blue-yellow axis)
        OkLabBChannelSlider(
            currentL = currentL,
            currentA = currentA,
            currentB = currentB,
            onBChanged = { currentB = it },
            hapticEffect = hapticEffect
        )

        // Alpha selection
        OkLabAlphaSlider(
            currentL = currentL,
            currentA = currentA,
            currentB = currentB,
            currentAlpha = currentAlpha,
            onAlphaChanged = { currentAlpha = it },
            hapticEffect = hapticEffect
        )
    }
}

/**
 * A [OkLabLightnessSlider] component for selecting the lightness (L) of a color in OkLab space.
 */
@Composable
fun OkLabLightnessSlider(
    currentL: Float,
    currentA: Float,
    currentB: Float,
    onLightnessChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val lightnessColors = remember(currentA, currentB) {
        val steps = 7
        (0..steps).map { i ->
            val l = i.toFloat() / steps.toFloat()
            OkLab(
                l = l * 100.0,
                a = (currentA / 0.4 * 100.0),
                b = (currentB / 0.4 * 100.0)
            ).toColor()
        }
    }
    ColorSlider(
        value = currentL,
        onValueChanged = onLightnessChanged,
        drawBrushColors = lightnessColors,
        modifier = Modifier.fillMaxWidth(),
        hapticEffect = hapticEffect
    )
}

/**
 * A [OkLabAChannelSlider] component for selecting the A channel (green-red axis) of a color in OkLab space.
 */
@Composable
fun OkLabAChannelSlider(
    currentL: Float,
    currentA: Float,
    currentB: Float,
    onAChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val aChannelColors = remember(currentL, currentB) {
        val minA = -0.3f
        val maxA = 0.3f
        val steps = 8
        (0..steps).map { i ->
            val a = minA + (maxA - minA) * i.toFloat() / steps.toFloat()
            OkLab(
                l = currentL * 100.0,
                a = (a / 0.4 * 100.0),
                b = (currentB / 0.4 * 100.0)
            ).toColor()
        }
    }
    ColorSlider(
        value = (currentA + 0.3f) / 0.6f,
        onValueChanged = { normalizedValue ->
            onAChanged(normalizedValue * 0.6f - 0.3f)
        },
        drawBrushColors = aChannelColors,
        modifier = Modifier.fillMaxWidth(),
        hapticEffect = hapticEffect
    )
}

/**
 * A [OkLabBChannelSlider] component for selecting the B channel (blue-yellow axis) of a color in OkLab space.
 */
@Composable
fun OkLabBChannelSlider(
    currentL: Float,
    currentA: Float,
    currentB: Float,
    onBChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val bChannelColors = remember(currentL, currentA) {
        val minB = -0.3f
        val maxB = 0.3f
        val steps = 8
        (0..steps).map { i ->
            val b = minB + (maxB - minB) * i.toFloat() / steps.toFloat()
            OkLab(
                l = currentL * 100.0,
                a = (currentA / 0.4 * 100.0),
                b = (b / 0.4 * 100.0)
            ).toColor()
        }
    }
    ColorSlider(
        value = (currentB + 0.3f) / 0.6f,
        onValueChanged = { normalizedValue ->
            onBChanged(normalizedValue * 0.6f - 0.3f)
        },
        drawBrushColors = bChannelColors,
        modifier = Modifier.fillMaxWidth(),
        hapticEffect = hapticEffect
    )
}

/**
 * A [OkLabAlphaSlider] component for selecting the alpha of a color in OkLab space.
 */
@Composable
fun OkLabAlphaSlider(
    currentL: Float,
    currentA: Float,
    currentB: Float,
    currentAlpha: Float,
    onAlphaChanged: (Float) -> Unit,
    hapticEffect: SliderDefaults.SliderHapticEffect = SliderDefaults.DefaultHapticEffect
) {
    val alphaColors = remember(currentL, currentA, currentB) {
        val baseColor = OkLab(
            l = currentL * 100.0,
            a = (currentA / 0.4 * 100.0),
            b = (currentB / 0.4 * 100.0)
        ).toColor()
        listOf(baseColor.copy(alpha = 0f), baseColor.copy(alpha = 1f))
    }

    ColorSlider(
        value = currentAlpha,
        onValueChanged = onAlphaChanged,
        drawBrushColors = alphaColors,
        modifier = Modifier.fillMaxWidth()
            .drawCheckerboard(),
        hapticEffect = hapticEffect,
    )
}

fun Modifier.drawCheckerboard(
    cellSizeDp: Dp = 3.dp,
    lightColor: Color = Color(0xFFCCCCCC),
    darkColor: Color = Color(0xFFAAAAAA)
): Modifier = this.then(
    Modifier.drawWithCache {
        val cell = cellSizeDp.toPx().coerceAtLeast(1f)
        val rows = ceil(size.height / cell).toInt().coerceAtLeast(1)

        val darkPath = Path().apply {
            var y = 0f
            for (row in 0 until rows) {
                var x = if ((row and 1) == 0) cell else 0f
                while (x < size.width) {
                    addRect(
                        Rect(
                            x,
                            y,
                            min(x + cell, size.width),
                            min(y + cell, size.height)
                        )
                    )
                    x += cell * 2f
                }
                y += cell
            }
        }

        onDrawBehind {
            drawRect(color = lightColor)
            drawPath(path = darkPath, color = darkColor)
        }
    }
)

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
) {
    val density = LocalDensity.current
    var sliderWidth by remember { mutableStateOf(0.dp) }
    val indicatorSizeDp = 20.dp
    val sliderHeightDp = 26.dp
    val sliderHeightPx = with(density) { sliderHeightDp.toPx() }
    val hapticFeedback = LocalHapticFeedback.current
    val hapticState = remember { SliderHapticState() }

    val gradientBrush = remember(drawBrushColors, sliderWidth) {
        val widthPx = with(density) { sliderWidth.toPx() }
        val halfSliderHeightPx = with(density) { sliderHeightDp.toPx() } / 2f
        Brush.horizontalGradient(
            colors = drawBrushColors,
            startX = halfSliderHeightPx,
            endX = widthPx - halfSliderHeightPx,
            tileMode = TileMode.Clamp
        )
    }

    Box(
        modifier = Modifier
            .clip(CapsuleShape())
            .then(modifier)
            .height(sliderHeightDp)
            .onGloballyPositioned { coordinates ->
                sliderWidth = with(density) { coordinates.size.width.toDp() }
            }
            .drawBehind {
                drawRect(brush = gradientBrush)
                drawRect(
                    color = Color.Gray.copy(0.1f),
                    style = Stroke(width = with(density) { 0.5.dp.toPx() }),
                )
            }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = { offset ->
                        val newValue =
                            handleSliderInteraction(
                                offset.x,
                                size.width.toFloat(),
                                with(density) { sliderHeightDp.toPx() }).coerceIn(
                                0f,
                                1f
                            )
                        onValueChanged(newValue)
                        hapticState.reset(newValue)
                    },
                    onHorizontalDrag = { change, _ ->
                        change.consume()
                        val newValue = handleSliderInteraction(
                            change.position.x,
                            size.width.toFloat(),
                            with(density) { sliderHeightDp.toPx() }).coerceIn(0f, 1f)
                        onValueChanged(newValue)
                        hapticState.handleHapticFeedback(newValue, hapticEffect, hapticFeedback)
                    }
                )
            }
    ) {
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
            .border(6.dp, Color.White, CapsuleShape())
            .background(Color.Transparent, CapsuleShape())
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

enum class ColorSpace {
    HSV,
    OKHSV,
    OKLAB
}