// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme

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
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.DividerColor
) {
    val canvasModifier = remember(modifier, thickness) {
        modifier.fillMaxWidth().height(thickness)
    }
    Canvas(canvasModifier) {
        val strokeWidthPx = thickness.toPx()
        if (strokeWidthPx <= 0f) return@Canvas
        drawLine(
            color = color,
            strokeWidth = strokeWidthPx,
            start = Offset(0f, strokeWidthPx / 2),
            end = Offset(size.width, strokeWidthPx / 2),
        )
    }
}

/**
 * A divider is a thin line that groups content in lists and layouts.
 *
 * @param modifier the [Modifier] to be applied to this divider line.
 * @param thickness thickness of this divider line. Using [Dp.Hairline] will produce a single pixel
 *   divider regardless of screen density.
 * @param color color of this divider line.
 */
@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.DividerColor
) {
    val canvasModifier = remember(modifier, thickness) {
        modifier.fillMaxHeight().width(thickness)
    }
    Canvas(canvasModifier) {
        val strokeWidthPx = thickness.toPx()
        if (strokeWidthPx <= 0f) return@Canvas
        drawLine(
            color = color,
            strokeWidth = strokeWidthPx,
            start = Offset(strokeWidthPx / 2, 0f),
            end = Offset(strokeWidthPx / 2, size.height),
        )
    }
}

object DividerDefaults {

    /**
     * Default thickness of the divider line.
     */
    val Thickness = 0.75.dp

    /**
     * Default color of the divider line.
     */
    val DividerColor @Composable get() = MiuixTheme.colorScheme.dividerLine

}