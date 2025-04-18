package top.yukonga.miuix.kmp.utils

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.graphics.shapes.CornerRounding
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
    val clampedSmoothing = smoothing.coerceIn(0f, 1f)

    return RoundedPolygon(
        vertices = floatArrayOf(
            0f, 0f,
            size.width, 0f,
            size.width, size.height,
            0f, size.height
        ),
        perVertexRounding = listOf(
            CornerRounding(radius = topLeft, smoothing = clampedSmoothing),
            CornerRounding(radius = topRight, smoothing = clampedSmoothing),
            CornerRounding(radius = bottomRight, smoothing = clampedSmoothing),
            CornerRounding(radius = bottomLeft, smoothing = clampedSmoothing),
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
    private val smoothing: Float = DefaultSmoothing,
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
            bottomLeft = bottomLeft,
            bottomRight = bottomRight
        )
        return Outline.Generic(path)
    }
}

fun RoundedPolygon.toComposePath(path: Path = Path()): Path {
    path.rewind()

    if (cubics.isEmpty()) return path
    path.moveTo(cubics[0].anchor0X, cubics[0].anchor0Y)
    for (cubic in cubics) {
        path.cubicTo(
            cubic.control0X, cubic.control0Y,
            cubic.control1X, cubic.control1Y,
            cubic.anchor1X, cubic.anchor1Y
        )
    }

    path.close()
    return path
}

/**
 * Default smoothing value for [SmoothRoundedCornerShape].
 */
const val DefaultSmoothing = 0.6f

