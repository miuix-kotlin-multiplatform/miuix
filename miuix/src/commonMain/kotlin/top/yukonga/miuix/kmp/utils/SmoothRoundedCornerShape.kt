package top.yukonga.miuix.kmp.utils

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

expect fun Path.Companion.smoothRoundedRectangle(
    smoothing: Float,
    size: Size,
    topLeft: Float,
    topRight: Float,
    bottomLeft: Float,
    bottomRight: Float,
): Path

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

const val DefaultSmoothing = 0.6f

