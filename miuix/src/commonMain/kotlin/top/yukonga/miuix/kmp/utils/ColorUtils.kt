// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

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
        r: Int,
        g: Int,
        b: Int,
        hsv: FloatArray,
    ) {
        require(hsv.size >= 3) { "HSV array must have at least 3 elements" }
        require(r in 0..255) { "Red component must be in range 0-255, got $r" }
        require(g in 0..255) { "Green component must be in range 0-255, got $g" }
        require(b in 0..255) { "Blue component must be in range 0-255, got $b" }

        // Normalize RGB values to 0.0-1.0 range
        val rf = r * INV_255
        val gf = g * INV_255
        val bf = b * INV_255

        val max = maxOf(rf, gf, bf)
        val min = minOf(rf, gf, bf)
        val delta = max - min

        // Value (brightness)
        hsv[2] = max

        // Saturation
        hsv[1] = if (max > 0f) delta / max else 0f

        // Hue
        hsv[0] = when {
            delta == 0f -> 0f
            max == rf -> {
                val hue = (gf - bf) / delta * 60f
                if (hue < 0f) hue + 360f else hue
            }

            max == gf -> (bf - rf) / delta * 60f + 120f
            else -> (rf - gf) / delta * 60f + 240f
        }
    }

    private const val INV_255 = 1f / 255f
}
