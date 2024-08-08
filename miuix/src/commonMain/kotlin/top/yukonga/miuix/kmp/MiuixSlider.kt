package top.yukonga.miuix.kmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import kotlin.math.pow
import kotlin.math.round

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
    var dragOffset by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    var currentValue by remember { mutableStateOf(progress) }
    val color = MiuixTheme.colorScheme.primary
    val backgroundColor = MiuixTheme.colorScheme.sliderBackground

    val factor = 10f.pow(decimalPlaces)
    fun calculateProgress(offset: Float, width: Int): Float {
        val newValue = (offset / width) * (maxValue - minValue) + minValue
        return (round(newValue * factor) / factor).coerceIn(minValue, maxValue)
    }

    MiuixBox(
        modifier = modifier
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = { offset ->
                        isDragging = true
                        dragOffset = offset.x
                        currentValue = calculateProgress(dragOffset, size.width)
                        onProgressChange(currentValue)
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        dragOffset = (dragOffset + dragAmount).coerceIn(0f, size.width.toFloat())
                        currentValue = calculateProgress(dragOffset, size.width)
                        onProgressChange(currentValue)
                    },
                    onDragEnd = {
                        isDragging = false
                    }
                )
            }
    ) {
        MiuixBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(RoundedCornerShape(50.dp))
                .background(backgroundColor),
            contentAlignment = Alignment.CenterStart
        ) {
            Canvas(modifier = Modifier.fillMaxWidth()) {
                val barHeight = height.toPx()
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
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = isDragging && dragShow,
                enter = fadeIn(animationSpec = tween(200)),
                exit = fadeOut(animationSpec = tween(400))
            ) {
                MiuixText(
                    text = if (decimalPlaces == 0) currentValue.toInt().toString() else currentValue.toString(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}