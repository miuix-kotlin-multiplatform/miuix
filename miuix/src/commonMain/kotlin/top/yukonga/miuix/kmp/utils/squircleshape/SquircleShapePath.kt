/*
 * Copyright (c) 2023 Stoyan Vuchev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package top.yukonga.miuix.kmp.utils.squircleshape

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.translate

/**
 *
 *  The path used for drawing a Squircle shape.
 *
 *  @param size The size of the shape in pixels.
 *  @param topLeftCorner The top left corner radius in pixels.
 *  @param topRightCorner The top right corner radius in pixels.
 *  @param bottomLeftCorner The bottom left corner radius in pixels.
 *  @param bottomRightCorner The bottom right corner radius in pixels.
 *  @param cornerSmoothing (0.55f - rounded corner shape, 1f - fully pronounced squircle).
 *
 **/
fun squircleShapePath(
    size: Size,
    topLeftCorner: Float,
    topRightCorner: Float,
    bottomLeftCorner: Float,
    bottomRightCorner: Float,
    cornerSmoothing: Float = 0.72f
): Path {

    // Draw the [Path].
    return Path().apply {

        // Extract the shape width & height
        val width = size.width
        val height = size.height

        // Set the starting point at the coordinate of (x = topLeft corner, y = 0).
        moveTo(
            x = topLeftCorner,
            y = 0f
        )

        // Draw a Line to the coordinate of (x = the width - the top right corner).
        lineTo(
            x = width - topRightCorner,
            y = 0f
        )

        // Draw a Cubic from the coordinate of (x1 = the width - the top right corner * (1 - the corner smoothing), y1 = 0)
        // with a mid point at the coordinate of (x2 = the width, y2 = the top right corner * (1 - the corner smoothing))
        // to the end point at the coordinate of (x3 = the width, y3 = the top right corner).
        cubicTo(
            x1 = width - topRightCorner * (1 - cornerSmoothing),
            y1 = 0f,
            x2 = width,
            y2 = topRightCorner * (1 - cornerSmoothing),
            x3 = width,
            y3 = topRightCorner
        )

        // Draw a Line to the coordinate of (x = the width, y = the height - the bottom right corner).
        lineTo(
            x = width,
            y = height - bottomRightCorner
        )

        // Draw a Cubic from the coordinate of (x1 = the width, y1 = the height - the bottom right corner * (1 - the corner smoothing))
        // with a mid point at the coordinate of (x2 = the width - the bottom right corner * (1 - the corner smoothing), y2 = the height)
        // to the end point at the coordinate of (x3 = the width - the bottom right corner, y3 = the height).
        cubicTo(
            x1 = width,
            y1 = height - bottomRightCorner * (1 - cornerSmoothing),
            x2 = width - bottomRightCorner * (1 - cornerSmoothing),
            y2 = height,
            x3 = width - bottomRightCorner,
            y3 = height
        )

        // Draw a Line to the coordinate of (x = the bottom left corner, y = the height).
        lineTo(
            x = bottomLeftCorner,
            y = height
        )

        // Draw a Cubic from the coordinate of (x1 = the bottom left corner * (1 - the corner smoothing), y1 = the height)
        // with a mid point at the coordinate of (x2 = 0, y2 = the height - the bottom left corner * (1 - the corner smoothing))
        // to the end point at the coordinate of (x3 = 0, y3 = the height - the bottom left corner).
        cubicTo(
            x1 = bottomLeftCorner * (1 - cornerSmoothing),
            y1 = height,
            x2 = 0f,
            y2 = height - bottomLeftCorner * (1 - cornerSmoothing),
            x3 = 0f,
            y3 = height - bottomLeftCorner
        )

        // Draw a Line to the coordinate of (x = 0, y = the top left corner).
        lineTo(
            x = 0f,
            y = topLeftCorner
        )

        // Draw a Cubic from the coordinate of (x1 = 0, y1 = the top left corner * (1 - the corner smoothing))
        // with a mid point at the coordinate of (x2 = the top left corner * (1 - the corner smoothing), y2 = 0)
        // to the end point at the coordinate of (x3 = the top left corner, y3 = 0.
        cubicTo(
            x1 = 0f,
            y1 = topLeftCorner * (1 - cornerSmoothing),
            x2 = topLeftCorner * (1 - cornerSmoothing),
            y2 = 0f,
            x3 = topLeftCorner,
            y3 = 0f
        )

        // Close the [Path].
        close()

    }

}

/**
 *
 * Draws a Squircle with the given [Color]. Whether the Squircle is
 * filled or stroked (or both) is controlled by [Paint.style].
 *
 * @param color The color to be applied to the Squircle.
 * @param topLeft Offset from the local origin of 0, 0 relative to the current translation.
 * @param size Dimensions of the Squircle to draw.
 * @param topLeftCorner The top left corner radius in pixels.
 * @param topRightCorner The top right corner radius in pixels.
 * @param bottomLeftCorner The bottom left corner radius in pixels.
 * @param bottomRightCorner The bottom right corner radius in pixels.
 * @param cornerSmoothing (0.55f - rounded corner shape, 1f - fully pronounced squircle).
 * @param alpha Opacity to be applied to Squircle from 0.0f to 1.0f representing fully transparent to fully opaque respectively.
 * @param style Specifies whether the Squircle is stroked or filled in.
 * @param colorFilter ColorFilter to apply to the [color] when drawn into the destination.
 * @param blendMode Blending algorithm to be applied to the color.
 *
 */
fun DrawScope.drawSquircle(
    color: Color,
    topLeft: Offset = Offset.Zero,
    size: Size,
    topLeftCorner: Float,
    topRightCorner: Float,
    bottomLeftCorner: Float,
    bottomRightCorner: Float,
    cornerSmoothing: Float = 0.72f,
    style: DrawStyle = Fill,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) {

    val squircleShapePath = squircleShapePath(
        size = size,
        topLeftCorner = clampedCornerRadius(topLeftCorner, size),
        topRightCorner = clampedCornerRadius(topRightCorner, size),
        bottomLeftCorner = clampedCornerRadius(bottomLeftCorner, size),
        bottomRightCorner = clampedCornerRadius(bottomRightCorner, size),
        cornerSmoothing = cornerSmoothing
    )

    translate(
        left = topLeft.x,
        top = topLeft.y
    ) {

        drawPath(
            path = squircleShapePath,
            color = color,
            alpha = alpha,
            style = style,
            colorFilter = colorFilter,
            blendMode = blendMode
        )

    }

}

/**
 *
 * Draws a Squircle with the given [Brush]. Whether the Squircle is
 * filled or stroked (or both) is controlled by [Paint.style].
 *
 * @param brush The brush to be applied to the Squircle.
 * @param topLeft Offset from the local origin of 0, 0 relative to the current translation.
 * @param size Dimensions of the Squircle to draw.
 * @param topLeftCorner The top left corner radius in pixels.
 * @param topRightCorner The top right corner radius in pixels.
 * @param bottomLeftCorner The bottom left corner radius in pixels.
 * @param bottomRightCorner The bottom right corner radius in pixels.
 * @param cornerSmoothing (0.55f - rounded corner shape, 1f - fully pronounced squircle).
 * @param alpha Opacity to be applied to Squircle from 0.0f to 1.0f representing fully transparent to fully opaque respectively.
 * @param style Specifies whether the Squircle is stroked or filled in.
 * @param colorFilter ColorFilter to apply to the [brush] when drawn into the destination.
 * @param blendMode Blending algorithm to be applied to the color.
 *
 */
fun DrawScope.drawSquircle(
    brush: Brush,
    topLeft: Offset = Offset.Zero,
    size: Size,
    topLeftCorner: Float,
    topRightCorner: Float,
    bottomLeftCorner: Float,
    bottomRightCorner: Float,
    cornerSmoothing: Float = 0.67f,
    style: DrawStyle = Fill,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) {

    val squircleShapePath = squircleShapePath(
        size = size,
        topLeftCorner = clampedCornerRadius(topLeftCorner, size),
        topRightCorner = clampedCornerRadius(topRightCorner, size),
        bottomLeftCorner = clampedCornerRadius(bottomLeftCorner, size),
        bottomRightCorner = clampedCornerRadius(bottomRightCorner, size),
        cornerSmoothing = cornerSmoothing
    )

    translate(
        left = topLeft.x,
        top = topLeft.y
    ) {

        drawPath(
            path = squircleShapePath,
            brush = brush,
            alpha = alpha,
            style = style,
            colorFilter = colorFilter,
            blendMode = blendMode
        )

    }

}