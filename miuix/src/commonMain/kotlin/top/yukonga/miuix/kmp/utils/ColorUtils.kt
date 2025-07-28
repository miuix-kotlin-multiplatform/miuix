// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.annotation.IntRange
import androidx.annotation.Size

object ColorUtils {
    /**
     * Converts RGB color values to HSV color space.
     *
     * @param r Red component (0-255)
     * @param g Green component (0-255)
     * @param b Blue component (0-255)
     * @param hsv Output array to store HSV values [hue, saturation, value]
     *            - hue: 0-360 degrees
     *            - saturation: 0.0-1.0
     *            - value: 0.0-1.0
     */
    fun rgbToHsv(
        @IntRange(from = 0, to = 255) r: Int,
        @IntRange(from = 0, to = 255) g: Int,
        @IntRange(from = 0, to = 255) b: Int,
        @Size(3) hsv: FloatArray,
    ) {
        val rf = r * INV_255
        val gf = g * INV_255
        val bf = b * INV_255

        val max = maxOf(rf, gf, bf)
        val min = minOf(rf, gf, bf)
        val delta = max - min

        val hue = when {
            delta == 0f -> 0f
            max == rf -> (60f * ((gf - bf) / delta) + 360f) % 360f
            max == gf -> (60f * ((bf - rf) / delta) + 120f) % 360f
            else -> (60f * ((rf - gf) / delta) + 240f) % 360f
        }

        hsv[0] = hue
        hsv[1] = if (max > 0f) delta / max else 0f
        hsv[2] = max
    }

    private const val INV_255 = 1f / 255f
}