package top.yukonga.miuix.kmp.utils

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asComposePath
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath

actual fun Path.Companion.smoothRoundedRectangle(
    smoothing: Float,
    size: Size,
    topLeft: Float,
    topRight: Float,
    bottomLeft: Float,
    bottomRight: Float
): Path =
    RoundedPolygon(
        vertices = floatArrayOf(
            0f, 0f,
            size.width, 0f,
            size.width, size.height,
            0f, size.height
        ),
        perVertexRounding = listOf(
            CornerRounding(radius = topLeft, smoothing = smoothing),
            CornerRounding(radius = topRight, smoothing = smoothing),
            CornerRounding(radius = bottomRight, smoothing = smoothing),
            CornerRounding(radius = bottomLeft, smoothing = smoothing),
        )
    ).toPath().asComposePath()