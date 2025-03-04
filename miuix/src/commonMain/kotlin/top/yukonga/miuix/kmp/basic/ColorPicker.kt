package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.ColorUtils
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape

/**
 * A [ColorPicker] component with Miuix style.
 *
 * @param initialColor The initial color of the picker.
 * @param onColorChanged The callback to be called when the color changes.
 * @param modifier The modifier to be applied to the color picker.
 */
@Composable
fun ColorPicker(
    initialColor: Color = MiuixTheme.colorScheme.primary,
    onColorChanged: (Color) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var initialSetup by remember { mutableStateOf(true) }
    var currentHue by remember { mutableStateOf(0f) }
    var currentSaturation by remember { mutableStateOf(0f) }
    var currentValue by remember { mutableStateOf(0f) }
    var currentAlpha by remember { mutableStateOf(1f) }

    // Set initial HSV values only once
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

    // Current selected color
    val selectedColor = Color.hsv(currentHue, currentSaturation, currentValue, currentAlpha)

    // Track previous color to prevent recomposition loops
    var previousColor by remember { mutableStateOf(selectedColor) }

    // Only trigger callback when colors actually change from user interaction
    LaunchedEffect(currentHue, currentSaturation, currentValue, currentAlpha) {
        if (!initialSetup && selectedColor != previousColor) {
            previousColor = selectedColor
            onColorChanged(selectedColor)
        }
    }

    // Color preview
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .clip(SmoothRoundedCornerShape(10.dp))
            .background(selectedColor)
            .border(1.dp, MiuixTheme.colorScheme.outline, SmoothRoundedCornerShape(10.dp))
    )

    Spacer(modifier = Modifier.height(6.dp))

    // Hue selection
    HueSlider(
        currentHue = currentHue,
        onHueChanged = { currentHue = it },
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .clip(SmoothRoundedCornerShape(10.dp))
            .border(1.dp, MiuixTheme.colorScheme.outline, SmoothRoundedCornerShape(10.dp))
    )

    Spacer(modifier = Modifier.height(6.dp))

    // Saturation-Value picker
    SaturationValuePicker(
        currentHue = currentHue,
        currentSaturation = currentSaturation,
        currentValue = currentValue,
        onSaturationValueChanged = { s, v ->
            println("Saturation: $s, Value: $v")
            currentSaturation = s
            currentValue = v
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(SmoothRoundedCornerShape(10.dp))
            .border(1.dp, MiuixTheme.colorScheme.outline, SmoothRoundedCornerShape(10.dp))
    )

    Spacer(modifier = Modifier.height(6.dp))

    Slider(
        progress = currentAlpha,
        onProgressChange = { currentAlpha = it },
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
    )
}

@Composable
private fun HueSlider(
    currentHue: Float,
    onHueChanged: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var sliderWidth by remember { mutableStateOf(0.dp) }
    var sliderPosition by remember { mutableStateOf(Offset.Zero) }

    Box(modifier = modifier.height(20.dp)) {
        // Hue gradient
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    sliderWidth = with(density) { coordinates.size.width.toDp() }
                    sliderPosition = coordinates.positionInParent()
                }
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        onHueChanged((offset.x / size.width * 360f).coerceIn(0f, 360f))
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        change.consume()
                        onHueChanged((change.position.x / size.width * 360f).coerceIn(0f, 360f))
                    }
                }
        ) {
            val width = size.width
            for (i in 0 until width.toInt()) {
                val hue = i / width * 360f
                drawLine(
                    color = Color.hsv(hue, 1f, 1f),
                    start = Offset(i.toFloat(), 0f),
                    end = Offset(i.toFloat(), size.height),
                    strokeWidth = 1f
                )
            }
        }

        // Current hue indicator - position calculated in dp units
        Box(
            modifier = Modifier
                .offset(
                    x = with(density) { (currentHue / 360f * sliderWidth.toPx()).toDp() - 8.dp }
                )
                .align(Alignment.CenterStart)
                .size(16.dp)
                .clip(SmoothRoundedCornerShape(10.dp))
                .border(1.dp, MiuixTheme.colorScheme.outline, SmoothRoundedCornerShape(10.dp))
                .background(Color.hsv(currentHue, 1f, 1f), SmoothRoundedCornerShape(10.dp))
        )
    }
}

@Composable
private fun SaturationValuePicker(
    currentHue: Float,
    currentSaturation: Float,
    currentValue: Float,
    onSaturationValueChanged: (Float, Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var pickerWidth by remember { mutableStateOf(0.dp) }
    var pickerHeight by remember { mutableStateOf(0.dp) }
    var pickerPosition by remember { mutableStateOf(Offset.Zero) }

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    pickerWidth = with(density) { coordinates.size.width.toDp() }
                    pickerHeight = with(density) { coordinates.size.height.toDp() }
                    pickerPosition = coordinates.positionInParent()
                }
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val saturation = (1f - offset.x / size.width).coerceIn(0f, 1f)
                        val value = (1f - offset.y / size.height).coerceIn(0f, 1f)
                        onSaturationValueChanged(saturation, value)
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        change.consume()
                        val saturation = (1f - change.position.x / size.width).coerceIn(0f, 1f)
                        val value = (1f - change.position.y / size.height).coerceIn(0f, 1f)
                        onSaturationValueChanged(saturation, value)
                    }
                }
        ) {
            // Draw value (brightness) gradient from top to bottom
            val shader = object : ShaderBrush() {
                override fun createShader(size: Size): Shader {
                    return LinearGradientShader(
                        colors = listOf(Color.hsv(currentHue, 1f, 1f), Color.White),
                        from = Offset.Zero,
                        to = Offset(size.width, 0f),
                        tileMode = TileMode.Clamp
                    )
                }
            }

            // Draw base color
            drawRect(shader)

            // Draw overlay for darkness gradient
            val overlayShader = object : ShaderBrush() {
                override fun createShader(size: Size): Shader {
                    return LinearGradientShader(
                        colors = listOf(Color.Transparent, Color.Black),
                        from = Offset(0f, 0f),
                        to = Offset(0f, size.height),
                        tileMode = TileMode.Clamp
                    )
                }
            }

            drawRect(overlayShader)
        }

        // Current saturation/value indicator - position calculated in dp units
        Box(
            modifier = Modifier
                .offset(
                    x = with(density) { ((1f - currentSaturation) * pickerWidth.toPx()).toDp() - 8.dp },
                    y = with(density) { ((1f - currentValue) * pickerHeight.toPx()).toDp() - 8.dp }
                )
                .size(16.dp)
                .clip(SmoothRoundedCornerShape(10.dp))
                .border(1.dp, MiuixTheme.colorScheme.outline, SmoothRoundedCornerShape(10.dp))
                .background(Color.hsv(currentHue, currentSaturation, currentValue), SmoothRoundedCornerShape(10.dp))
        )
    }
}