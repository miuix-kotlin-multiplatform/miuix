package top.yukonga.miuix.kmp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.ui.MiuixTheme

@Composable
fun MiuixSlider(
    modifier: Modifier = Modifier,
    progress: Float,
    height: Dp = 30.dp,
    effect: Boolean = false,
    onProgressChange: (Float) -> Unit
) {
    var dragOffset by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    val color = MiuixTheme.colorScheme.primary
    val backgroundColor = MiuixTheme.colorScheme.sliderBackground

    MiuixBox(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        isDragging = true
                        dragOffset = offset.x
                        onProgressChange(dragOffset / size.width)
                    },
                    onDrag = { _, dragAmount ->
                        dragOffset = (dragOffset + dragAmount.x).coerceIn(0f, size.width.toFloat())
                        onProgressChange(dragOffset / size.width)
                    },
                    onDragEnd = {
                        isDragging = false
                    }
                )
            }
    ) {
        MiuixBox(
            modifier = Modifier.fillMaxWidth()
                .height(height)
                .clip(RoundedCornerShape(50.dp))
                .background(
                    color = backgroundColor
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Canvas(modifier = Modifier.fillMaxWidth()) {
                val barHeight = height.toPx()
                val barWidth = size.width
                val progressWidth = barWidth * progress
                val cornerRadius = if (effect) CornerRadius(barHeight / 2, barHeight / 2) else CornerRadius(0f, 0f)

                drawRoundRect(
                    color = color,
                    size = Size(progressWidth, barHeight),
                    topLeft = Offset(0f, center.y - barHeight / 2),
                    cornerRadius = cornerRadius
                )
            }
        }
    }
}