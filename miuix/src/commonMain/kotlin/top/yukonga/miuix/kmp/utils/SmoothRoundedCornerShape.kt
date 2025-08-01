// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.annotation.FloatRange
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
import kotlin.jvm.JvmOverloads

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
    @FloatRange(from = 0.0, 1.0) smoothing: Float,
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
    ).toPath()
}

/**
 * Creates a [SmoothRoundedCornerShape] with all corners having the same size and smoothing.
 *
 * @param corner The corner size for all corners.
 * @param smoothing The degree of smoothing to apply, between 0.0 and 1.0.
 */
fun SmoothRoundedCornerShape(
    corner: Dp,
    @FloatRange(from = 0.0, 1.0) smoothing: Float = DefaultSmoothing
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
        @FloatRange(from = 0.0, 1.0) smoothing: Float = DefaultSmoothing,
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
 * Gets a [Path] representation for a [RoundedPolygon] shape. Note that there is some rounding
 * happening (to the nearest thousandth), to work around rendering artifacts introduced by some
 * points being just slightly off from each other (far less than a pixel). This also allows for a
 * more optimal path, as redundant curves (usually a single point) can be detected and not added to
 * the resulting path.
 *
 * @param path an optional [Path] object which, if supplied, will avoid the function having to
 *   create a new [Path] object
 */
@JvmOverloads
fun RoundedPolygon.toPath(path: Path = Path()): Path {
    pathFromCubics(path, cubics)
    return path
}

private fun pathFromCubics(path: Path, cubics: List<Cubic>) {
    var first = true
    path.rewind()
    for (i in 0 until cubics.size) {
        val cubic = cubics[i]
        if (first) {
            path.moveTo(cubic.anchor0X, cubic.anchor0Y)
            first = false
        }
        path.cubicTo(
            cubic.control0X,
            cubic.control0Y,
            cubic.control1X,
            cubic.control1Y,
            cubic.anchor1X,
            cubic.anchor1Y
        )
    }
    path.close()
}

/**
 * Default smoothing value for [SmoothRoundedCornerShape].
 * A value of 0.6 is often used to achieve a "squircle" or "superellipse" like appearance.
 */
const val DefaultSmoothing = 0.6f

