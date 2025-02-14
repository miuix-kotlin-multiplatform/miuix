package top.yukonga.miuix.kmp.utils

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Cubic
import androidx.graphics.shapes.RoundedPolygon

fun Path.Companion.smoothRoundedRectangle(
    smoothing: Float,
    size: Size,
    topLeft: Float,
    topRight: Float,
    bottomLeft: Float,
    bottomRight: Float
): Path {
    if (size.width <= 0f || size.height <= 0f) return Path()

    return RoundedPolygon(
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
    ).toComposePath()
}

fun SmoothRoundedCornerShape(
    corner: Dp,
    smoothing: Float = DefaultSmoothing
): SmoothRoundedCornerShape = SmoothRoundedCornerShape(
    smoothing = smoothing,
    topStart = corner,
    topEnd = corner,
    bottomEnd = corner,
    bottomStart = corner
)

class SmoothRoundedCornerShape(
    val smoothing: Float = DefaultSmoothing,
    topStart: CornerSize,
    topEnd: CornerSize,
    bottomEnd: CornerSize,
    bottomStart: CornerSize
) : CornerBasedShape(
    topStart = topStart,
    topEnd = topEnd,
    bottomEnd = bottomEnd,
    bottomStart = bottomStart,
) {
    constructor(
        smoothing: Float = DefaultSmoothing,
        topStart: Dp,
        topEnd: Dp,
        bottomEnd: Dp,
        bottomStart: Dp,
    ) : this(
        smoothing = smoothing,
        topStart = CornerSize(topStart),
        topEnd = CornerSize(topEnd),
        bottomEnd = CornerSize(bottomEnd),
        bottomStart = CornerSize(bottomStart),
    )

    override fun copy(
        topStart: CornerSize,
        topEnd: CornerSize,
        bottomEnd: CornerSize,
        bottomStart: CornerSize
    ) = SmoothRoundedCornerShape(
        smoothing = smoothing,
        topStart = topStart,
        topEnd = topEnd,
        bottomEnd = bottomEnd,
        bottomStart = bottomStart,
    )

    override fun createOutline(
        size: Size,
        topStart: Float,
        topEnd: Float,
        bottomEnd: Float,
        bottomStart: Float,
        layoutDirection: LayoutDirection
    ): Outline {
        val topLeft = if (layoutDirection == LayoutDirection.Ltr) topStart else topEnd
        val topRight = if (layoutDirection == LayoutDirection.Ltr) topEnd else topStart
        val bottomLeft = if (layoutDirection == LayoutDirection.Ltr) bottomStart else bottomEnd
        val bottomRight = if (layoutDirection == LayoutDirection.Ltr) bottomEnd else bottomStart

        val path = Path.smoothRoundedRectangle(
            smoothing = smoothing,
            size = size,
            topLeft = topLeft,
            topRight = topRight,
            bottomRight = bottomRight,
            bottomLeft = bottomLeft,
        )
        return Outline.Generic(path)
    }
}

fun RoundedPolygon.toComposePath(path: Path = Path()): Path {
    pathFromCubicList(path, cubics)
    return path
}

private fun pathFromCubicList(
    path: Path,
    cubicList: List<Cubic>
) {
    var first = true
    path.rewind()
    for (element in cubicList) {
        if (first) {
            path.moveTo(element.anchor0X, element.anchor0Y)
            first = false
        }
        path.cubicTo(
            element.control0X, element.control0Y, element.control1X, element.control1Y,
            element.anchor1X, element.anchor1Y
        )
    }
    path.close()
}

const val DefaultSmoothing = 0.6f

