// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.annotation.IntRange
import androidx.annotation.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.hsv
import kotlin.math.cbrt

object ColorUtils {
    /**
     * Convert an RGB colour space vector to OkLab colour space
     * @param color RGB colour space vector (values 0-1)
     * @return OkLab colour space vector
     */
    fun rgbToOkLab(color: FloatArray): FloatArray {
        val l = cbrt(0.4122214708f * color[0] + 0.5363325363f * color[1] + 0.0514459929f * color[2])
        val m = cbrt(0.2119034982f * color[0] + 0.6806995451f * color[1] + 0.1073969566f * color[2])
        val s = cbrt(0.0883024619f * color[0] + 0.2817188376f * color[1] + 0.6299787005f * color[2])

        return floatArrayOf(
            0.2104542553f * l + 0.7936177850f * m - 0.0040720468f * s,
            1.9779984951f * l - 2.4285922050f * m + 0.4505937099f * s,
            0.0259040371f * l + 0.7827717662f * m - 0.8086757660f * s
        )
    }

    /**
     * Convert an OkLab colour space vector to RGB colour space
     * @param color OkLab colour space vector
     * @return RGB colour space vector (values 0-1)
     */
    fun okLabToRgb(color: FloatArray): FloatArray {
        val l_ = color[0] + 0.3963377774f * color[1] + 0.2158037573f * color[2]
        val m_ = color[0] - 0.1055613458f * color[1] - 0.0638541728f * color[2]
        val s_ = color[0] - 0.0894841775f * color[1] - 1.2914855480f * color[2]

        val l = l_ * l_ * l_
        val m = m_ * m_ * m_
        val s = s_ * s_ * s_

        return floatArrayOf(
            (+4.0767416621f * l - 3.3077115913f * m + 0.2309699292f * s).coerceIn(0f, 1f),
            (-1.2684380046f * l + 2.6097574011f * m - 0.3413193965f * s).coerceIn(0f, 1f),
            (-0.0041960863f * l - 0.7034186147f * m + 1.7076147010f * s).coerceIn(0f, 1f)
        )
    }

    /**
     * Create a safe OkLab color with clamped values
     * @param l Lightness (0.0 to 1.0)
     * @param a Green-Red axis (-0.4 to 0.4, typical range)
     * @param b Blue-Yellow axis (-0.4 to 0.4, typical range)
     * @return Safe OkLab color array
     */
    fun createOkLabColor(l: Float, a: Float, b: Float): FloatArray {
        return floatArrayOf(
            l.coerceIn(0f, 1f),
            a.coerceIn(-0.4f, 0.4f),
            b.coerceIn(-0.4f, 0.4f)
        )
    }

    /**
     * Convert OkLab color to Compose Color
     * @param okLab OkLab color array [L, A, B]
     * @param alpha Alpha value (0.0 to 1.0)
     * @return Compose Color
     */
    fun okLabToColor(okLab: FloatArray, alpha: Float = 1f): Color {
        val rgb = okLabToRgb(okLab)
        return Color(rgb[0], rgb[1], rgb[2], alpha)
    }

    /**
     * Convert Compose Color to OkLab
     * @param color Compose Color
     * @return OkLab color array [L, A, B]
     */
    fun colorToOkLab(color: Color): FloatArray {
        val rgb = floatArrayOf(color.red, color.green, color.blue)
        return rgbToOkLab(rgb)
    }

    /**
     * Convert RGB color values to HSV color space.
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
        @IntRange(0, 255) r: Int,
        @IntRange(0, 255) g: Int,
        @IntRange(0, 255) b: Int,
        @Size(3) hsv: FloatArray,
    ) {
        val rf = r / 255f
        val gf = g / 255f
        val bf = b / 255f

        val max = maxOf(rf, gf, bf)
        val min = minOf(rf, gf, bf)
        val delta = max - min

        val hue = when {
            delta == 0f -> 0f
            max == rf -> (60f * ((gf - bf) / delta) + 360f) % 360f
            max == gf -> (60f * ((bf - rf) / delta) + 120f) % 360f
            else -> (60f * ((rf - gf) / delta) + 240f) % 360f
        }

        hsv[0] = hue.coerceIn(0f, 360f)
        hsv[1] = (if (max > 0f) delta / max else 0f).coerceIn(0f, 1f)
        hsv[2] = max.coerceIn(0f, 1f)
    }

    /**
     * Convert a Color to HSV color space.
     *
     * @param color The Color to convert.
     * @return An array containing [hue, saturation, value].
     *         - hue: 0-360 degrees
     *         - saturation: 0.0-1.0
     *         - value: 0.0-1.0
     */
    fun colorToHsv(color: Color): FloatArray {
        val hsv = FloatArray(3)
        rgbToHsv(
            (color.red * 255).toInt(),
            (color.green * 255).toInt(),
            (color.blue * 255).toInt(),
            hsv
        )
        return hsv
    }

    /**
     * Alternative: Generate pure HSV hue colors for maximum brightness
     * Use this if you prefer the brightest possible hue colors
     */
    fun generatePureHueColors(): List<Color> {
        val steps = 36
        return (0 until steps).map { i ->
            val hue = (i.toFloat() / steps.toFloat()) * 360f
            hsv(hue, 1f, 1f)
        }
    }
}