// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

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

        val max = maxOf(rf, gf, bf)
        val min = minOf(rf, gf, bf)
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
}
