// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

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

/**
 * Creates a [Path] for a rectangle with smoothly rounded corners.
 *
 * @param smoothing The degree of smoothing to apply to the corners, between 0.0 and 1.0.
 * @param size The size of the rectangle.
 * @param topLeft The radius of the top-left corner.
 * @param topRight The radius of the top-right corner.
 * @param bottomLeft The radius of the bottom-left corner.
 * @param bottomRight The radius of the bottom-right corner.
 */
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

/**
 * Creates a [SmoothRoundedCornerShape] with all corners having the same size and smoothing.
 *
 * @param corner The corner size for all corners.
 * @param smoothing The degree of smoothing to apply, between 0.0 and 1.0.
 */
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

/**
 * A [CornerBasedShape] that draws a rectangle with smoothly rounded corners.
 * The smoothing is controlled by the `smoothing` parameter, which affects how
 * the corners transition from straight edges to curves, often referred to as "squircles".
 *
 * @property smoothing The degree of smoothing to apply to the corners, between 0.0 and 1.0.
 * @param topStart The [CornerSize] for the top-start corner.
 * @param topEnd The [CornerSize] for the top-end corner.
 * @param bottomEnd The [CornerSize] for the bottom-end corner.
 * @param bottomStart The [CornerSize] for the bottom-start corner.
 */
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
    /**
     * Creates a [SmoothRoundedCornerShape] with corner sizes defined in [Dp].
     *
     * @param smoothing The degree of smoothing to apply.
     * @param topStart The Dp value for the top-start corner.
     * @param topEnd The Dp value for the top-end corner.
     * @param bottomEnd The Dp value for the bottom-end corner.
     * @param bottomStart The Dp value for the bottom-start corner.
     */
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

/**
 * Converts a [RoundedPolygon] from the AndroidX graphics shapes library to a Jetpack Compose [Path].
 *
 * @param path An optional existing [Path] to reuse. If provided, it will be rewound and populated.
 *   Otherwise, a new [Path] will be created.
 */
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
 * A value of 0.6 is often used to achieve a "squircle" or "superellipse" like appearance.
 */
const val DefaultSmoothing = 0.6f

