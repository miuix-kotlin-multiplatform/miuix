package top.yukonga.miuix.kmp.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * A divider is a thin line that groups content in lists and layouts.
 *
 * @param modifier the [Modifier] to be applied to this divider line.
 * @param thickness thickness of this divider line. Using [Dp.Hairline] will produce a single pixel
 *   divider regardless of screen density.
 * @param color color of this divider line.
 */
@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp,
    color: Color,
) =
    Canvas(modifier.fillMaxWidth().height(thickness)) {
        drawLine(
            color = color,
            strokeWidth = thickness.toPx(),
            start = Offset(0f, thickness.toPx() / 2),
            end = Offset(size.width, thickness.toPx() / 2),
        )
    }