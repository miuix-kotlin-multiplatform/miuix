// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

// Form https://github.com/Kyant0/Capsule

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.LayoutDirection.Ltr
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCoerceAtMost
import androidx.compose.ui.util.fastCoerceIn
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Immutable
open class G2RoundedCornerShape(
    topStart: CornerSize,
    topEnd: CornerSize,
    bottomEnd: CornerSize,
    bottomStart: CornerSize,
    val cornerSmoothness: CornerSmoothness = CornerSmoothness.Default
) :
    CornerBasedShape(
        topStart = topStart,
        topEnd = topEnd,
        bottomEnd = bottomEnd,
        bottomStart = bottomStart
    ) {

    override fun createOutline(
        size: Size,
        topStart: Float,
        topEnd: Float,
        bottomEnd: Float,
        bottomStart: Float,
        layoutDirection: LayoutDirection
    ): Outline {
        if (topStart + topEnd + bottomEnd + bottomStart == 0f) {
            return Outline.Rectangle(size.toRect())
        }

        val (width, height) = size
        val (centerX, centerY) = size.center

        val maxR = min(centerX, centerY)
        val topLeft = (if (layoutDirection == Ltr) topStart else topEnd).fastCoerceIn(0f, maxR)
        val topRight = (if (layoutDirection == Ltr) topEnd else topStart).fastCoerceIn(0f, maxR)
        val bottomRight = (if (layoutDirection == Ltr) bottomEnd else bottomStart).fastCoerceIn(0f, maxR)
        val bottomLeft = (if (layoutDirection == Ltr) bottomStart else bottomEnd).fastCoerceIn(0f, maxR)

        if (cornerSmoothness.circleFraction >= 1f ||
            (width == height &&
                    topLeft == centerX &&
                    topLeft == topRight && bottomLeft == bottomRight)
        ) {
            return Outline.Rounded(
                RoundRect(
                    rect = size.toRect(),
                    topLeft = CornerRadius(topLeft),
                    topRight = CornerRadius(topRight),
                    bottomRight = CornerRadius(bottomRight),
                    bottomLeft = CornerRadius(bottomLeft)
                )
            )
        }

        return Outline.Generic(
            cornerSmoothness.createRoundedRectanglePath(
                size = size,
                topRight = topRight,
                topLeft = topLeft,
                bottomLeft = bottomLeft,
                bottomRight = bottomRight
            )
        )
    }

    override fun copy(
        topStart: CornerSize,
        topEnd: CornerSize,
        bottomEnd: CornerSize,
        bottomStart: CornerSize
    ): G2RoundedCornerShape {
        return G2RoundedCornerShape(
            topStart = topStart,
            topEnd = topEnd,
            bottomEnd = bottomEnd,
            bottomStart = bottomStart,
            cornerSmoothness = cornerSmoothness
        )
    }

    fun copy(
        topStart: CornerSize = this.topStart,
        topEnd: CornerSize = this.topEnd,
        bottomEnd: CornerSize = this.bottomEnd,
        bottomStart: CornerSize = this.bottomStart,
        cornerSmoothness: CornerSmoothness = this.cornerSmoothness
    ): G2RoundedCornerShape {
        return G2RoundedCornerShape(
            topStart = topStart,
            topEnd = topEnd,
            bottomEnd = bottomEnd,
            bottomStart = bottomStart,
            cornerSmoothness = cornerSmoothness
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is G2RoundedCornerShape) return false

        if (topStart != other.topStart) return false
        if (topEnd != other.topEnd) return false
        if (bottomEnd != other.bottomEnd) return false
        if (bottomStart != other.bottomStart) return false
        if (cornerSmoothness != other.cornerSmoothness) return false

        return true
    }

    override fun hashCode(): Int {
        var result = topStart.hashCode()
        result = 31 * result + topEnd.hashCode()
        result = 31 * result + bottomEnd.hashCode()
        result = 31 * result + bottomStart.hashCode()
        result = 31 * result + cornerSmoothness.hashCode()
        return result
    }

    override fun toString(): String {
        return "G2RoundedCornerShape(topStart=$topStart, topEnd=$topEnd, bottomEnd=$bottomEnd, " +
                "bottomStart=$bottomStart, cornerSmoothing=$cornerSmoothness)"
    }
}

@Stable
val G2RectangleShape: G2RoundedCornerShape = G2RoundedCornerShape(0f)

@Stable
val CapsuleShape: G2RoundedCornerShape = CapsuleShape()

@Suppress("FunctionName")
@Stable
fun CapsuleShape(
    cornerSmoothness: CornerSmoothness = CornerSmoothness.Default
): G2RoundedCornerShape =
    G2RoundedCornerShape(
        topStartPercent = 50,
        topEndPercent = 50,
        bottomEndPercent = 50,
        bottomStartPercent = 50,
        cornerSmoothness = cornerSmoothness
    )

@Stable
fun G2RoundedCornerShape(
    corner: CornerSize,
    cornerSmoothness: CornerSmoothness = CornerSmoothness.Default
): G2RoundedCornerShape =
    G2RoundedCornerShape(
        topStart = corner,
        topEnd = corner,
        bottomEnd = corner,
        bottomStart = corner,
        cornerSmoothness = cornerSmoothness
    )

@Stable
fun G2RoundedCornerShape(
    size: Dp,
    cornerSmoothness: CornerSmoothness = CornerSmoothness.Default
): G2RoundedCornerShape =
    G2RoundedCornerShape(
        corner = CornerSize(size),
        cornerSmoothness = cornerSmoothness
    )

@Stable
fun G2RoundedCornerShape(
    @FloatRange(from = 0.0) size: Float,
    cornerSmoothness: CornerSmoothness = CornerSmoothness.Default
): G2RoundedCornerShape =
    G2RoundedCornerShape(
        corner = CornerSize(size),
        cornerSmoothness = cornerSmoothness
    )

@Stable
fun G2RoundedCornerShape(
    @IntRange(from = 0, to = 100) percent: Int,
    cornerSmoothness: CornerSmoothness = CornerSmoothness.Default
): G2RoundedCornerShape =
    G2RoundedCornerShape(
        corner = CornerSize(percent),
        cornerSmoothness = cornerSmoothness
    )

@Stable
fun G2RoundedCornerShape(
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp,
    cornerSmoothness: CornerSmoothness = CornerSmoothness.Default
): G2RoundedCornerShape =
    G2RoundedCornerShape(
        topStart = CornerSize(topStart),
        topEnd = CornerSize(topEnd),
        bottomEnd = CornerSize(bottomEnd),
        bottomStart = CornerSize(bottomStart),
        cornerSmoothness = cornerSmoothness
    )

@Stable
fun G2RoundedCornerShape(
    @FloatRange(from = 0.0) topStart: Float = 0f,
    @FloatRange(from = 0.0) topEnd: Float = 0f,
    @FloatRange(from = 0.0) bottomEnd: Float = 0f,
    @FloatRange(from = 0.0) bottomStart: Float = 0f,
    cornerSmoothness: CornerSmoothness = CornerSmoothness.Default
): G2RoundedCornerShape =
    G2RoundedCornerShape(
        topStart = CornerSize(topStart),
        topEnd = CornerSize(topEnd),
        bottomEnd = CornerSize(bottomEnd),
        bottomStart = CornerSize(bottomStart),
        cornerSmoothness = cornerSmoothness
    )

@Stable
fun G2RoundedCornerShape(
    @IntRange(from = 0, to = 100) topStartPercent: Int = 0,
    @IntRange(from = 0, to = 100) topEndPercent: Int = 0,
    @IntRange(from = 0, to = 100) bottomEndPercent: Int = 0,
    @IntRange(from = 0, to = 100) bottomStartPercent: Int = 0,
    cornerSmoothness: CornerSmoothness = CornerSmoothness.Default
): G2RoundedCornerShape =
    G2RoundedCornerShape(
        topStart = CornerSize(topStartPercent),
        topEnd = CornerSize(topEndPercent),
        bottomEnd = CornerSize(bottomEndPercent),
        bottomStart = CornerSize(bottomStartPercent),
        cornerSmoothness = cornerSmoothness
    )

@Immutable
data class CornerSmoothness(
    @param:FloatRange(from = 0.0, to = 1.0) val circleFraction: Float,
    @param:FloatRange(from = 0.0) val extendedFraction: Float
) {

    private val circleRadians = HalfPI * circleFraction
    private val bezierRadians = (HalfPI - circleRadians) / 2f

    private val sin = sin(bezierRadians)
    private val cos = cos(bezierRadians)
    private val a = 1f - sin / (1f + cos)
    private val d = 1.5f * sin / (1f + cos) / (1f + cos)
    private val ad = a + d

    private fun Path.topRightCorner0(size: Size, r: Float, dy: Float) {
        val w = size.width
        cubicTo(
            w,
            r * ad,
            w,
            r * a,
            w - r * (1f - cos),
            r * (1f - sin)
        )
    }

    private fun Path.topRightCircle(size: Size, r: Float) {
        if (circleRadians > 0f) {
            arcToRad(
                rect = Rect(
                    center = Offset(size.width - r, r),
                    radius = r
                ),
                startAngleRadians = -bezierRadians,
                sweepAngleRadians = -circleRadians,
                forceMoveTo = false
            )
        }
    }

    private fun Path.topRightCorner1(size: Size, r: Float, dx: Float) {
        val w = size.width
        cubicTo(
            w - r * a,
            0f,
            w - r * ad,
            0f,
            w - r - dx,
            0f
        )
    }

    private fun Path.topLeftCorner1(size: Size, r: Float, dx: Float) {
        cubicTo(
            r * ad,
            0f,
            r * a,
            0f,
            r * (1f - sin),
            r * (1f - cos)
        )
    }

    private fun Path.topLeftCircle(size: Size, r: Float) {
        if (circleRadians > 0f) {
            arcToRad(
                rect = Rect(
                    center = Offset(r, r),
                    radius = r
                ),
                startAngleRadians = -(HalfPI + bezierRadians),
                sweepAngleRadians = -circleRadians,
                forceMoveTo = false
            )
        }
    }

    private fun Path.topLeftCorner0(size: Size, r: Float, dy: Float) {
        cubicTo(
            0f,
            r * a,
            0f,
            r * ad,
            0f,
            r + dy
        )
    }

    private fun Path.bottomLeftCorner0(size: Size, r: Float, dy: Float) {
        val h = size.height
        cubicTo(
            0f,
            h - r * ad,
            0f,
            h - r * a,
            r * (1f - cos),
            h - r * (1f - sin)
        )
    }

    private fun Path.bottomLeftCircle(size: Size, r: Float) {
        if (circleRadians > 0f) {
            arcToRad(
                rect = Rect(
                    center = Offset(r, size.height - r),
                    radius = r
                ),
                startAngleRadians = -(HalfPI * 2f + bezierRadians),
                sweepAngleRadians = -circleRadians,
                forceMoveTo = false
            )
        }
    }

    private fun Path.bottomLeftCorner1(size: Size, r: Float, dx: Float) {
        val h = size.height
        cubicTo(
            r * a,
            h,
            r * ad,
            h,
            r - dx,
            h
        )
    }

    private fun Path.bottomRightCorner1(size: Size, r: Float, dx: Float) {
        val w = size.width
        val h = size.height
        cubicTo(
            w - r * ad,
            h,
            w - r * a,
            h,
            w - r * (1f - sin),
            h - r * (1f - cos)
        )
    }

    private fun Path.bottomRightCircle(size: Size, r: Float) {
        if (circleRadians > 0f) {
            arcToRad(
                rect = Rect(
                    center = Offset(size.width - r, size.height - r),
                    radius = r
                ),
                startAngleRadians = -(HalfPI * 3f + bezierRadians),
                sweepAngleRadians = -circleRadians,
                forceMoveTo = false
            )
        }
    }

    private fun Path.bottomRightCorner0(size: Size, r: Float, dy: Float) {
        val w = size.width
        val h = size.height
        cubicTo(
            w,
            h - r * a,
            w,
            h - r * ad,
            w,
            h - r + dy
        )
    }

    private fun Path.rightCircle(size: Size, r: Float) {
        arcToRad(
            rect = Rect(
                center = Offset(size.width - r, r),
                radius = r
            ),
            startAngleRadians = bezierRadians + circleRadians,
            sweepAngleRadians = -(bezierRadians + circleRadians) * 2f,
            forceMoveTo = false
        )
    }

    private fun Path.leftCircle(size: Size, r: Float) {
        arcToRad(
            rect = Rect(
                center = Offset(r, r),
                radius = r
            ),
            startAngleRadians = -(HalfPI + bezierRadians),
            sweepAngleRadians = -(bezierRadians + circleRadians) * 2f,
            forceMoveTo = false
        )
    }

    private fun Path.topCircle(size: Size, r: Float) {
        arcToRad(
            rect = Rect(
                center = Offset(r, r),
                radius = r
            ),
            startAngleRadians = -bezierRadians,
            sweepAngleRadians = -(bezierRadians + circleRadians) * 2f,
            forceMoveTo = false
        )
    }

    private fun Path.bottomCircle(size: Size, r: Float) {
        arcToRad(
            rect = Rect(
                center = Offset(r, size.height - r),
                radius = r
            ),
            startAngleRadians = -(HalfPI * 2f + bezierRadians),
            sweepAngleRadians = -(bezierRadians + circleRadians) * 2f,
            forceMoveTo = false
        )
    }

    internal fun createRoundedRectanglePath(
        size: Size,
        topRight: Float,
        topLeft: Float,
        bottomLeft: Float,
        bottomRight: Float
    ): Path {
        val (width, height) = size
        val (centerX, centerY) = size.center
        val maxR = min(centerX, centerY)

        val topRightDy = (topRight * extendedFraction).fastCoerceAtMost(centerY - topRight)
        val topRightDx = (topRight * extendedFraction).fastCoerceAtMost(centerX - topRight)
        val topLeftDx = (topLeft * extendedFraction).fastCoerceAtMost(centerX - topLeft)
        val topLeftDy = (topLeft * extendedFraction).fastCoerceAtMost(centerY - topLeft)
        val bottomLeftDy = (bottomLeft * extendedFraction).fastCoerceAtMost(centerY - bottomLeft)
        val bottomLeftDx = (bottomLeft * extendedFraction).fastCoerceAtMost(centerX - bottomLeft)
        val bottomRightDx = (bottomRight * extendedFraction).fastCoerceAtMost(centerX - bottomRight)
        val bottomRightDy = (bottomRight * extendedFraction).fastCoerceAtMost(centerY - bottomRight)

        return Path().apply {
            when {
                // capsule
                topRight == maxR && topLeft == maxR && bottomLeft == maxR && bottomRight == maxR -> {
                    if (width > height) {
                        // right circle
                        rightCircle(size, maxR)
                        // top right corner
                        topRightCorner1(size, topRight, topRightDx)
                        // top line
                        lineTo(topLeft + topLeftDx, 0f)
                        // top left corner
                        topLeftCorner1(size, topLeft, topLeftDx)
                        // left circle
                        leftCircle(size, maxR)
                        // bottom left corner
                        bottomLeftCorner1(size, bottomLeft, -bottomLeftDx)
                        // bottom line
                        lineTo(width - bottomRight - bottomRightDx, height)
                        // bottom right corner
                        bottomRightCorner1(size, bottomRight, -bottomRightDx)
                    } else {
                        // right line
                        moveTo(width, height - bottomRight - bottomRightDy)
                        lineTo(width, topRight + topRightDy)
                        // top right corner
                        topRightCorner0(size, topRight, -topRightDy)
                        // top circle
                        topCircle(size, maxR)
                        // top left corner
                        topLeftCorner0(size, topLeft, topLeftDy)
                        // left line
                        lineTo(0f, height - bottomLeft - bottomLeftDy)
                        // bottom left corner
                        bottomLeftCorner0(size, bottomLeft, bottomLeftDy)
                        // bottom circle
                        bottomCircle(size, maxR)
                        // bottom right corner
                        bottomRightCorner0(size, bottomRight, -bottomRightDy)
                    }
                }

                // rounded rectangle
                else -> {
                    // right line
                    moveTo(width, height - bottomRight - bottomRightDy)
                    lineTo(width, topRight + topRightDy)

                    // top right corner
                    if (topRight > 0f) {
                        topRightCorner0(size, topRight, -topRightDy)
                        topRightCircle(size, topRight)
                        topRightCorner1(size, topRight, topRightDx)
                    }

                    // top line
                    lineTo(topLeft + topLeftDx, 0f)

                    // top left corner
                    if (topLeft > 0f) {
                        topLeftCorner1(size, topLeft, topLeftDx)
                        topLeftCircle(size, topLeft)
                        topLeftCorner0(size, topLeft, topLeftDy)
                    }

                    // left line
                    lineTo(0f, height - bottomLeft - bottomLeftDy)

                    // bottom left corner
                    if (bottomLeft > 0f) {
                        bottomLeftCorner0(size, bottomLeft, bottomLeftDy)
                        bottomLeftCircle(size, bottomLeft)
                        bottomLeftCorner1(size, bottomLeft, -bottomLeftDx)
                    }

                    // bottom line
                    lineTo(width - bottomRight - bottomRightDx, height)

                    // bottom right corner
                    if (bottomRight > 0f) {
                        bottomRightCorner1(size, bottomRight, -bottomRightDx)
                        bottomRightCircle(size, bottomRight)
                        bottomRightCorner0(size, bottomRight, -bottomRightDy)
                    }
                }
            }
        }
    }

    companion object {

        @Stable
        val Default: CornerSmoothness =
            CornerSmoothness(
                circleFraction = 0.2f,
                extendedFraction = 1f
            )

        @Stable
        val None: CornerSmoothness =
            CornerSmoothness(
                circleFraction = 1f,
                extendedFraction = 0f
            )
    }
}

private const val HalfPI = (PI / 2f).toFloat()