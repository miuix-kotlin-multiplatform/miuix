package top.yukonga.miuix.kmp.utils

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

fun SmoothRoundedCornerShape(
    size: Dp,
    rndPrc: Float = 0.08f
) = SmoothRoundedCornerShape(CornerSize(size), rndPrc)

fun SmoothRoundedCornerShape(
    corner: CornerSize,
    rndPrc: Float = 0.08f
) = SmoothRoundedCornerShape(corner, corner, corner, corner, rndPrc)

fun SmoothRoundedCornerShape(
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp,
    rndPrc: Float = 0.08f
) = SmoothRoundedCornerShape(
    topStart = CornerSize(topStart),
    topEnd = CornerSize(topEnd),
    bottomEnd = CornerSize(bottomEnd),
    bottomStart = CornerSize(bottomStart),
    rndPrc = rndPrc
)

class SmoothRoundedCornerShape(
    topStart: CornerSize,
    topEnd: CornerSize,
    bottomEnd: CornerSize,
    bottomStart: CornerSize,
    private val rndPrc: Float = 0.08f
) : CornerBasedShape(
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
    ) = if (topStart + topEnd + bottomEnd + bottomStart == 0f) {
        Outline.Rectangle(size.toRect())
    } else {
        Outline.Generic(
            Path().apply {
                val ww = size.width
                val hh = size.height

                val (aa1, bb1, cc1, dd1, ee1, ff1, gg1) = calcCoordinates(topStart)
                val (aa2, bb2, cc2, dd2, ee2, ff2, gg2) = calcCoordinates(bottomStart)
                val (aa3, bb3, cc3, dd3, ee3, ff3, gg3) = calcCoordinates(bottomEnd)
                val (aa4, bb4, cc4, dd4, ee4, ff4, gg4) = calcCoordinates(topEnd)

                // top left corner
                moveTo(aa1, 0f)
                if (topStart != 0f) {
                    cubicTo(bb1, 0f, cc1, 0f, dd1, ee1)
                    cubicTo(gg1, ff1, ff1, gg1, ee1, dd1) // circle part
                    cubicTo(0f, cc1, 0f, bb1, 0f, aa1)
                }

                // left line
                lineTo(0f, hh - aa2)

                // bottom left corner
                if (bottomStart != 0f) {
                    cubicTo(0f, hh - bb2, 0f, hh - cc2, ee2, hh - dd2)
                    cubicTo(ff2, hh - gg2, gg2, hh - ff2, dd2, hh - ee2) // circle part
                    cubicTo(cc2, hh, bb2, hh, aa2, hh)
                }

                // bottom line
                lineTo(ww - aa3, hh)

                // bottom right corner
                if (bottomEnd != 0f) {
                    cubicTo(ww - bb3, hh, ww - cc3, hh, ww - dd3, hh - ee3)
                    cubicTo(ww - gg3, hh - ff3, ww - ff3, hh - gg3, ww - ee3, hh - dd3) // circle part
                    cubicTo(ww, hh - cc3, ww, hh - bb3, ww, hh - aa3)
                }

                // right line
                lineTo(ww, aa4)

                // top right corner
                if (topEnd != 0f) {
                    cubicTo(ww, bb4, ww, cc4, ww - ee4, dd4)
                    cubicTo(ww - ff4, gg4, ww - gg4, ff4, ww - dd4, ee4) // circle part
                    cubicTo(ww - cc4, 0f, ww - bb4, 0f, ww - aa4, 0f)
                }

                // top line
                lineTo(aa1, 0f)

                close()
            }
        )
    }

    private fun calcCoordinates(rad: Float): FloatArray {
        val x = rndPrc * rad * 2f / 3f

        val sqRnd = sqrt(1f + rndPrc * rndPrc)

        val ddd = sqRnd * x
        val j = rndPrc * x / sqRnd
        val a = j * rndPrc
        val jj = j + rad / sqRnd // distance to circle center
        val dj = jj * (1f - rndPrc)

        val aa = dj + ddd * 4f
        val bb = dj + ddd * 2f
        val cc = dj + ddd
        val dd = dj + a

        // // circular portion
        val dx = rad / sqRnd * (1f - rndPrc)

        val d = sqrt(rad * rad - dx * dx / 2f)
        val handlePrc = (rad - d) / dx * sqrt(2f) * 4f / 3f

        val vx = rad / sqRnd * rndPrc
        val vy = rad / sqRnd

        val ee = j + vx * handlePrc
        val ff = dd - vy * handlePrc

        return floatArrayOf(aa, bb, cc, dd, j, ee, ff)
    }

    override fun copy(
        topStart: CornerSize,
        topEnd: CornerSize,
        bottomEnd: CornerSize,
        bottomStart: CornerSize
    ) = SmoothRoundedCornerShape(
        topStart = topStart,
        topEnd = topEnd,
        bottomEnd = bottomEnd,
        bottomStart = bottomStart
    )
}

private operator fun FloatArray.component6(): Float = this[5]

private operator fun FloatArray.component7(): Float = this[6]
