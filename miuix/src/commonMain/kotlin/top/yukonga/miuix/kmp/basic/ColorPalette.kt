// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/** * A color palette component that allows users to select colors from a grid of HSV values.
 *
 * @param initialColor The initial color to display in the palette.
 * @param onColorChanged Callback invoked when the selected color changes.
 * @param modifier Modifier for styling the palette.
 * @param rows Number of rows in the color grid.
 * @param hueColumns Number of columns for hue variations.
 * @param includeGrayColumn Whether to include a gray column in the palette.
 * @param showPreview Whether to show a preview of the selected color.
 * @param cornerRadius Corner radius for the palette's shape.
 * @param indicatorRadius Radius of the selection indicator circle.
 */
@Composable
fun ColorPalette(
    initialColor: Color,
    onColorChanged: (Color) -> Unit,
    modifier: Modifier = Modifier,
    rows: Int = 7,
    hueColumns: Int = 12,
    includeGrayColumn: Boolean = true,
    showPreview: Boolean = true,
    cornerRadius: Dp = 16.dp,
    indicatorRadius: Dp = 10.dp,
) {
    val totalColumns = hueColumns + if (includeGrayColumn) 1 else 0

    val rowSV = remember(rows) { buildRowSV(rows) }
    val grayV = remember(rows) { buildGrayV(rows) }

    var selectedRow by remember { mutableStateOf(0) }
    var selectedCol by remember { mutableStateOf(0) }
    var alpha by remember { mutableStateOf(initialColor.alpha.coerceIn(0f, 1f)) }
    var lastEmittedColor by remember { mutableStateOf<Color?>(null) }
    var lastAcceptedHSV by remember { mutableStateOf<Triple<Float, Float, Float>?>(null) }

    LaunchedEffect(initialColor, rows, hueColumns, includeGrayColumn) {
        if (lastEmittedColor?.let { colorsEqualApprox(it, initialColor) } == true) return@LaunchedEffect

        val (h, s, v) = colorToHsvLocal(initialColor)
        val currentHSV = Triple(h, s, v)
        if (lastAcceptedHSV?.let { hsvEqualApprox(it, currentHSV) } == true) {
            alpha = initialColor.alpha
            lastAcceptedHSV = currentHSV
            return@LaunchedEffect
        }

        val isGray = includeGrayColumn && s < 0.05f
        val col = if (isGray) {
            totalColumns - 1
        } else {
            val k = (h % 360f) / 360f * hueColumns
            k.roundToInt().coerceIn(0, hueColumns - 1)
        }
        val row = if (isGray) {
            grayV.indexOfMinBy { rv -> sq(v - rv) }
        } else {
            rowSV.indexOfMinBy { (rs, rv) -> sq(s - rs) + sq(v - rv) }
        }
        selectedCol = col
        selectedRow = row
        alpha = initialColor.alpha
        lastAcceptedHSV = currentHSV
    }

    val onColorChangedState = rememberUpdatedState(onColorChanged)

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
                    .background(lastEmittedColor ?: initialColor)
            )
        }

        PaletteCanvas(
            rows = rows,
            hueColumns = hueColumns,
            includeGrayColumn = includeGrayColumn,
            cornerRadius = cornerRadius,
            indicatorRadius = indicatorRadius,
            selectedRow = selectedRow,
            selectedCol = selectedCol,
            onSelect = { r, c ->
                selectedRow = r
                selectedCol = c
                val base = cellColor(c, r, rowSV, grayV, hueColumns, includeGrayColumn)
                val newColor = base.copy(alpha = alpha)
                lastAcceptedHSV = colorToHsvLocal(base)
                lastEmittedColor = newColor
                onColorChangedState.value(newColor)
            }
        )

        val base = cellColor(selectedCol, selectedRow, rowSV, grayV, hueColumns, includeGrayColumn)
        val (h, s, v) = colorToHsvLocal(base)

        HsvAlphaSlider(
            currentHue = h,
            currentSaturation = s,
            currentValue = v,
            currentAlpha = alpha,
            onAlphaChanged = {
                alpha = it
                val newColor = base.copy(alpha = it)
                lastAcceptedHSV = colorToHsvLocal(base)
                lastEmittedColor = newColor
                onColorChangedState.value(newColor)
            }
        )
    }
}

@Composable
private fun PaletteCanvas(
    rows: Int,
    hueColumns: Int,
    includeGrayColumn: Boolean,
    cornerRadius: Dp,
    indicatorRadius: Dp,
    selectedRow: Int,
    selectedCol: Int,
    onSelect: (row: Int, col: Int) -> Unit,
) {
    val totalColumns = hueColumns + if (includeGrayColumn) 1 else 0
    val rowSV = remember(rows) { buildRowSV(rows) }
    val grayV = remember(rows) { buildGrayV(rows) }
    val shape = SmoothRoundedCornerShape(cornerRadius)

    var sizePx by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = Modifier
            .clip(shape)
            .onGloballyPositioned { sizePx = it.size }
            .pointerInput(rows, hueColumns, includeGrayColumn) {
                detectTapGestures { pos ->
                    if (sizePx.width == 0 || sizePx.height == 0) return@detectTapGestures
                    val (r, c) = pointToCell(pos, sizePx, rows, totalColumns)
                    onSelect(r, c)
                }
            }
            .pointerInput(rows, hueColumns, includeGrayColumn) {
                detectDragGestures { change, _ ->
                    if (sizePx.width == 0 || sizePx.height == 0) return@detectDragGestures
                    val (r, c) = pointToCell(change.position, sizePx, rows, totalColumns)
                    onSelect(r, c)
                }
            }
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val w = size.width.toInt()
            val h = size.height.toInt()

            val colEdges = IntArray(totalColumns + 1) { i -> (i * w) / totalColumns }
            val rowEdges = IntArray(rows + 1) { i -> (i * h) / rows }

            for (r in 0 until rows) {
                val top = rowEdges[r].toFloat()
                val bottom = rowEdges[r + 1].toFloat()
                val cellH = bottom - top
                for (c in 0 until totalColumns) {
                    val left = colEdges[c].toFloat()
                    val right = colEdges[c + 1].toFloat()
                    val cellW = right - left
                    val color = cellColor(c, r, rowSV, grayV, hueColumns, includeGrayColumn)
                    drawRect(
                        color = color,
                        topLeft = Offset(left, top),
                        size = Size(cellW, cellH)
                    )
                }
            }
        }

        if (sizePx.width > 0 && sizePx.height > 0) {
            val w = sizePx.width
            val h = sizePx.height
            val colEdges = IntArray(totalColumns + 1) { i -> (i * w) / totalColumns }
            val rowEdges = IntArray(rows + 1) { i -> (i * h) / rows }
            val left = colEdges[selectedCol]
            val right = colEdges[selectedCol + 1]
            val top = rowEdges[selectedRow]
            val bottom = rowEdges[selectedRow + 1]
            val cxPx = (left + right) / 2f
            val cyPx = (top + bottom) / 2f

            val indicatorSize = indicatorRadius * 2
            val density = LocalDensity.current
            Box(
                modifier = Modifier
                    .offset(
                        x = with(density) { cxPx.toDp() - indicatorSize / 2 },
                        y = with(density) { cyPx.toDp() - indicatorSize / 2 }
                    )
                    .size(indicatorSize)
                    .clip(CircleShape)
                    .border(6.dp, Color.White, CircleShape)
                    .background(Color.Transparent, CircleShape)
            )
        }
    }
}

private fun buildRowSV(rows: Int): List<Pair<Float, Float>> {
    if (rows <= 1) return listOf(1f to 1f)

    if (rows == 7) {
        val sArr = floatArrayOf(0.10f, 0.35f, 0.70f, 1.00f, 1.00f, 1.00f, 1.00f)
        val vArr = floatArrayOf(1.00f, 1.00f, 1.00f, 0.85f, 0.65f, 0.45f, 0.20f)
        return List(7) { i -> sArr[i] to vArr[i] }
    }

    val topBrightCut = minOf(0.34f, 2f / (rows - 1f))
    return List(rows) { i ->
        val t = i / (rows - 1f)
        val sRamp = (t / 0.35f).coerceIn(0f, 1f)
        val s = (0.10f + 0.90f * sRamp).coerceIn(0f, 1f)
        val v = if (t <= topBrightCut) 1f else {
            val k = ((t - topBrightCut) / (1f - topBrightCut)).coerceIn(0f, 1f)
            lerp(1f, 0.20f, k)
        }
        s to v
    }
}

private fun buildGrayV(rows: Int): List<Float> {
    if (rows <= 1) return listOf(1f)
    return List(rows) { i -> 1f - (i / (rows - 1f)) }
}

private fun cellColor(
    col: Int,
    row: Int,
    rowSV: List<Pair<Float, Float>>,
    grayV: List<Float>,
    hueColumns: Int,
    includeGrayColumn: Boolean,
): Color {
    val totalColumns = hueColumns + if (includeGrayColumn) 1 else 0
    val (s, v) = rowSV[row]
    return if (includeGrayColumn && col == totalColumns - 1) {
        Color.hsv(0f, 0f, grayV[row])
    } else {
        val step = 360f / hueColumns
        val h = (col * step) % 360f
        Color.hsv(h, s, v)
    }
}

private fun pointToCell(pos: Offset, size: IntSize, rows: Int, totalColumns: Int): Pair<Int, Int> {
    val x = pos.x.coerceIn(0f, size.width.toFloat() - 1)
    val y = pos.y.coerceIn(0f, size.height.toFloat() - 1)
    val col = ((x / size.width) * totalColumns).toInt().coerceIn(0, totalColumns - 1)
    val row = ((y / size.height) * rows).toInt().coerceIn(0, rows - 1)
    return row to col
}

private fun colorsEqualApprox(a: Color, b: Color, eps: Float = 0.002f): Boolean {
    return abs(a.red - b.red) < eps &&
            abs(a.green - b.green) < eps &&
            abs(a.blue - b.blue) < eps &&
            abs(a.alpha - b.alpha) < eps
}

private fun hsvEqualApprox(
    a: Triple<Float, Float, Float>,
    b: Triple<Float, Float, Float>,
    epsH: Float = 1.5f,
    eps: Float = 0.02f
): Boolean {
    val dhRaw = abs(a.first - b.first)
    val dh = min(dhRaw, 360f - dhRaw)
    return dh <= epsH && abs(a.second - b.second) <= eps && abs(a.third - b.third) <= eps
}

private fun colorToHsvLocal(color: Color): Triple<Float, Float, Float> {
    val r = color.red
    val g = color.green
    val b = color.blue

    val max = max(r, max(g, b))
    val min = min(r, min(g, b))
    val delta = max - min

    val h = when {
        delta == 0f -> 0f
        max == r -> ((60f * ((g - b) / delta) + 360f) % 360f)
        max == g -> (60f * ((b - r) / delta) + 120f)
        else -> (60f * ((r - g) / delta) + 240f)
    }
    val s = if (max == 0f) 0f else delta / max
    val v = max
    return Triple(h.coerceIn(0f, 360f), s.coerceIn(0f, 1f), v.coerceIn(0f, 1f))
}

private fun <T> List<T>.indexOfMinBy(selector: (T) -> Float): Int {
    var idx = 0
    var minVal = Float.POSITIVE_INFINITY
    for (i in indices) {
        val v = selector(this[i])
        if (v < minVal) {
            minVal = v; idx = i
        }
    }
    return idx
}

private fun sq(v: Float) = v * v

