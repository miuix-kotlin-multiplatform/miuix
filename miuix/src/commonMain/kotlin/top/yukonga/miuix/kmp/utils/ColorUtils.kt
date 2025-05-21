// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

object ColorUtils {
    fun rgbToHsv(
        r: Int,
        g: Int,
        b: Int,
        hsv: FloatArray,
    ) {
        val rf = r / 255f
        val gf = g / 255f
        val bf = b / 255f

        val max = maxOf(rf, maxOf(gf, bf))
        val min = minOf(rf, minOf(gf, bf))
        val delta = max - min

        hsv[2] = max

        hsv[1] = if (max != 0f) delta / max else 0f

        hsv[0] =
            when {
                delta == 0f -> 0f
                max == rf -> ((gf - bf) / delta * 60f + 360f) % 360f
                max == gf -> ((bf - rf) / delta + 2f) * 60f
                else -> ((rf - gf) / delta + 4f) * 60f
            }
    }

    fun hsvToRgb(
        h: Float,
        s: Float,
        v: Float,
    ): Color {
        val c = v * s
        val x = c * (1f - abs((h / 60f % 2f) - 1f))
        val m = v - c

        val (r, g, b) =
            when {
                h < 60 -> Triple(c, x, 0f)
                h < 120 -> Triple(x, c, 0f)
                h < 180 -> Triple(0f, c, x)
                h < 240 -> Triple(0f, x, c)
                h < 300 -> Triple(x, 0f, c)
                else -> Triple(c, 0f, x)
            }

        return Color(r + m, g + m, b + m)
    }
}
