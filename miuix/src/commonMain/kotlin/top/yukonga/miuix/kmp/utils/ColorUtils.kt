// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.annotation.IntRange
import androidx.annotation.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.hsv
import kotlin.math.cbrt
import kotlin.math.pow


/**
 * User-friendly OkLab representation.
 * - l: 0.0..100.0 (lightness percent)
 * - a: -100.0..100.0 (green-red axis, scaled to typical [-0.4, 0.4] internally)
 * - b: -100.0..100.0 (blue-yellow axis, scaled to typical [-0.4, 0.4] internally)
 */
data class OkLab(val l: Double, val a: Double, val b: Double) {
    fun toColor(alpha: Float = 1f): Color {
        val lN = (l / 100.0).coerceIn(0.0, 1.0).toFloat()
        val aN = ((a / 100.0) * 0.4).toFloat().coerceIn(-0.4f, 0.4f)
        val bN = ((b / 100.0) * 0.4).toFloat().coerceIn(-0.4f, 0.4f)
        val ok = floatArrayOf(lN, aN, bN)
        return ColorUtils.okLabToColor(ok, alpha)
    }
}

/**
 * User-friendly HSV-like representation.
 * - h: hue in degrees [0, 360)
 * - v: value/brightness in percent [0.0, 100.0]
 * - s: saturation in percent [0.0, 100.0]
 */
data class Hsv(val h: Double, val v: Double, val s: Double) {
    fun toColor(alpha: Float = 1f): Color {
        val hue = (((h % 360.0) + 360.0) % 360.0).toFloat()
        val vN = (v / 100.0).coerceIn(0.0, 1.0).toFloat()
        val sN = (s / 100.0).coerceIn(0.0, 1.0).toFloat()
        return hsv(hue, sN, vN, alpha)
    }
}

/** Convert Compose Color to user-friendly OkLab. */
fun Color.toOkLab(): OkLab {
    val lab = ColorUtils.colorToOkLab(this)
    val l = (lab[0] * 100.0).coerceIn(0.0, 100.0)
    val a = (lab[1] / 0.4 * 100.0).coerceIn(-100.0, 100.0)
    val b = (lab[2] / 0.4 * 100.0).coerceIn(-100.0, 100.0)
    return OkLab(l, a, b)
}

/** Convert Compose Color to Hvs. */
fun Color.toHsv(): Hsv {
    val hsvArr = ColorUtils.colorToHsv(this)
    val h = hsvArr[0].toDouble()
    val s = (hsvArr[1] * 100.0).coerceIn(0.0, 100.0)
    val v = (hsvArr[2] * 100.0).coerceIn(0.0, 100.0)
    return Hsv(h, v, s)
}

object ColorUtils {
    /**
     * Convert an RGB color-space vector (sRGB, 0..1) to OkLab color space.
     * @param color RGB vector [r, g, b] with each component in 0..1
     * @return OkLab vector [L, a, b], where L is approximately 0..1
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
     * Convert an OkLab color-space vector to sRGB.
     * @param color OkLab vector [L, a, b]
     * @return sRGB vector [r, g, b], each component clamped to 0..1
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
     * Convert an OkLab color vector to a Compose Color (sRGB).
     * @param okLab OkLab vector [L, a, b]
     * @param alpha Alpha in 0..1
     * @return Compose Color in sRGB
     */
    fun okLabToColor(okLab: FloatArray, alpha: Float = 1f): Color {
        val rgb = okLabToRgb(okLab)
        return Color(rgb[0], rgb[1], rgb[2], alpha)
    }

    /**
     * Convert a Compose Color (sRGB) to OkLab.
     * @param color Compose Color in sRGB
     * @return OkLab vector [L, a, b]
     */
    fun colorToOkLab(color: Color): FloatArray {
        val rgb = floatArrayOf(color.red, color.green, color.blue)
        return rgbToOkLab(rgb)
    }

    /**
     * Convert 8-bit sRGB (0..255) to HSV color space.
     * @param r Red 0..255
     * @param g Green 0..255
     * @param b Blue 0..255
     * @param hsv Output array [h, s, v]: h in degrees 0..360, s in 0..1, v in 0..1
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
     * Convert a Compose Color (sRGB) to HSV.
     * @param color Color in sRGB
     * @return HSV array [h, s, v]: h in degrees 0..360, s in 0..1, v in 0..1
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
     * Convert sRGB (0..1) to OkHSV color space.
     * @return OkHSV array [h, s, v] with h in 0..1, s in 0..1, v in 0..1
     */
    fun srgbToOkhsv(r: Float, g: Float, b: Float): FloatArray {
        val lab = linearSrgbToOklab(
            srgbTransferFunctionInv(r),
            srgbTransferFunctionInv(g),
            srgbTransferFunctionInv(b)
        )

        val C = kotlin.math.sqrt(lab[1] * lab[1] + lab[2] * lab[2])
        val a_ = if (C == 0f) 0f else lab[1] / C
        val b_ = if (C == 0f) 0f else lab[2] / C

        val L = lab[0]
        val h = 0.5f + (0.5f * kotlin.math.atan2(-lab[2], -lab[1])) / kotlin.math.PI.toFloat()

        val STMax = getSTMax(a_, b_)
        val SMax = STMax[0]
        val S0 = 0.5f
        val T = STMax[1]
        val k = 1f - S0 / SMax

        val t = T / (C + L * T)
        val Lv = t * L
        val Cv = t * C

        val Lvt = toeInv(Lv)
        val Cvt = (Cv * Lvt) / Lv

        val rgbScale = oklabToLinearSrgb(Lvt, a_ * Cvt, b_ * Cvt)
        val scaleL = cbrt(
            1f / maxOf(rgbScale[0], rgbScale[1], rgbScale[2], 0f)
        )

        var L2 = L / scaleL
        L2 = toe(L2)

        val v = L2 / Lv
        val s = ((S0 + T) * Cv) / (T * S0 + T * k * Cv)

        return floatArrayOf(h, s, v)
    }

    /**
     * Convert OkHSV to sRGB color space.
     * @param h Hue in 0..1
     * @param s Saturation in 0..1
     * @param v Value in 0..1
     * @return sRGB array [r, g, b] in 0..1
     */
    fun okhsvToSrgb(h: Float, s: Float, v: Float): FloatArray {
        val a_ = kotlin.math.cos(2f * kotlin.math.PI.toFloat() * h)
        val b_ = kotlin.math.sin(2f * kotlin.math.PI.toFloat() * h)

        val STMax = getSTMax(a_, b_)
        val SMax = STMax[0]
        val S0 = 0.5f
        val T = STMax[1]
        val k = 1f - S0 / SMax

        val Lv = 1f - (s * S0) / (S0 + T - T * k * s)
        val Cv = (s * T * S0) / (S0 + T - T * k * s)

        var L = v * Lv
        var C = v * Cv

        val Lvt = toeInv(Lv)
        val Cvt = (Cv * Lvt) / Lv

        val LNew = toeInv(L)
        C = (C * LNew) / L
        L = LNew

        val rgbScale = oklabToLinearSrgb(Lvt, a_ * Cvt, b_ * Cvt)
        val scaleL = cbrt(
            1f / maxOf(rgbScale[0], rgbScale[1], rgbScale[2], 0f)
        )

        L = L * scaleL
        C = C * scaleL

        val rgb = oklabToLinearSrgb(L, C * a_, C * b_)
        return floatArrayOf(
            srgbTransferFunction(rgb[0]),
            srgbTransferFunction(rgb[1]),
            srgbTransferFunction(rgb[2])
        )
    }

    /**
     * Convert a Compose Color (sRGB) to OkHSV.
     * @return OkHSV array [h, s, v] with h in 0..1
     */
    fun colorToOkhsv(color: Color): FloatArray {
        return srgbToOkhsv(color.red, color.green, color.blue)
    }

    /**
     * Convert OkHSV to Compose Color (sRGB).
     * @param h Hue in 0..1
     * @param s Saturation in 0..1
     * @param v Value in 0..1
     * @param alpha Alpha in 0..1
     */
    fun okhsvToColor(h: Float, s: Float, v: Float, alpha: Float = 1f): Color {
        val srgb = okhsvToSrgb(h, s, v)
        return Color(srgb[0], srgb[1], srgb[2], alpha)
    }

    private fun srgbTransferFunction(a: Float): Float {
        return if (0.0031308f >= a) {
            12.92f * a
        } else {
            1.055f * a.pow(0.4166666666666667f) - 0.055f
        }
    }

    private fun srgbTransferFunctionInv(a: Float): Float {
        return if (0.04045f < a) {
            ((a + 0.055f) / 1.055f).pow(2.4f)
        } else {
            a / 12.92f
        }
    }

    private fun linearSrgbToOklab(r: Float, g: Float, b: Float): FloatArray {
        val l = 0.4122214708f * r + 0.5363325363f * g + 0.0514459929f * b
        val m = 0.2119034982f * r + 0.6806995451f * g + 0.1073969566f * b
        val s = 0.0883024619f * r + 0.2817188376f * g + 0.6299787005f * b

        val l_ = cbrt(l)
        val m_ = cbrt(m)
        val s_ = cbrt(s)

        return floatArrayOf(
            0.2104542553f * l_ + 0.793617785f * m_ - 0.0040720468f * s_,
            1.9779984951f * l_ - 2.428592205f * m_ + 0.4505937099f * s_,
            0.0259040371f * l_ + 0.7827717662f * m_ - 0.808675766f * s_
        )
    }

    private fun oklabToLinearSrgb(L: Float, a: Float, b: Float): FloatArray {
        val l_ = L + 0.3963377774f * a + 0.2158037573f * b
        val m_ = L - 0.1055613458f * a - 0.0638541728f * b
        val s_ = L - 0.0894841775f * a - 1.291485548f * b

        val l = l_ * l_ * l_
        val m = m_ * m_ * m_
        val s = s_ * s_ * s_

        return floatArrayOf(
            4.0767416621f * l - 3.3077115913f * m + 0.2309699292f * s,
            -1.2684380046f * l + 2.6097574011f * m - 0.3413193965f * s,
            -0.0041960863f * l - 0.7034186147f * m + 1.707614701f * s
        )
    }

    private fun toe(x: Float): Float {
        val k1 = 0.206f
        val k2 = 0.03f
        val k3 = (1f + k1) / (1f + k2)

        return 0.5f * (k3 * x - k1 + kotlin.math.sqrt((k3 * x - k1) * (k3 * x - k1) + 4f * k2 * k3 * x))
    }

    private fun toeInv(x: Float): Float {
        val k1 = 0.206f
        val k2 = 0.03f
        val k3 = (1f + k1) / (1f + k2)
        return (x * x + k1 * x) / (k3 * (x + k2))
    }

    private fun computeMaxSaturation(a: Float, b: Float): Float {
        var k0: Float
        var k1: Float
        var k2: Float
        var k3: Float
        var k4: Float
        var wl: Float
        var wm: Float
        var ws: Float

        if (-1.88170328f * a - 0.80936493f * b > 1f) {
            // Red component
            k0 = 1.19086277f
            k1 = 1.76576728f
            k2 = 0.59662641f
            k3 = 0.75515197f
            k4 = 0.56771245f
            wl = 4.0767416621f
            wm = -3.3077115913f
            ws = 0.2309699292f
        } else if (1.81444104f * a - 1.19445276f * b > 1f) {
            // Green component
            k0 = 0.73956515f
            k1 = -0.45954404f
            k2 = 0.08285427f
            k3 = 0.1254107f
            k4 = 0.14503204f
            wl = -1.2684380046f
            wm = 2.6097574011f
            ws = -0.3413193965f
        } else {
            // Blue component
            k0 = 1.35733652f
            k1 = -0.00915799f
            k2 = -1.1513021f
            k3 = -0.50559606f
            k4 = 0.00692167f
            wl = -0.0041960863f
            wm = -0.7034186147f
            ws = 1.707614701f
        }

        var S = k0 + k1 * a + k2 * b + k3 * a * a + k4 * a * b

        val kL = 0.3963377774f * a + 0.2158037573f * b
        val kM = -0.1055613458f * a - 0.0638541728f * b
        val kS = -0.0894841775f * a - 1.291485548f * b

        val l_ = 1f + S * kL
        val m_ = 1f + S * kM
        val s_ = 1f + S * kS

        val l = l_ * l_ * l_
        val m = m_ * m_ * m_
        val s = s_ * s_ * s_

        val lDS = 3f * kL * l_ * l_
        val mDS = 3f * kM * m_ * m_
        val sDS = 3f * kS * s_ * s_

        val lDS2 = 6f * kL * kL * l_
        val mDS2 = 6f * kM * kM * m_
        val sDS2 = 6f * kS * kS * s_

        val f = wl * l + wm * m + ws * s
        val f1 = wl * lDS + wm * mDS + ws * sDS
        val f2 = wl * lDS2 + wm * mDS2 + ws * sDS2

        S = S - (f * f1) / (f1 * f1 - 0.5f * f * f2)

        return S
    }

    private fun findCusp(a: Float, b: Float): FloatArray {
        val sCusp = computeMaxSaturation(a, b)
        val rgbAtMax = oklabToLinearSrgb(1f, sCusp * a, sCusp * b)
        val lCusp = cbrt(
            1f / maxOf(rgbAtMax[0], rgbAtMax[1], rgbAtMax[2])
        )
        val cCusp = lCusp * sCusp
        return floatArrayOf(lCusp, cCusp)
    }

    private fun getSTMax(a_: Float, b_: Float, cusp: FloatArray? = null): FloatArray {
        val cuspActual = cusp ?: findCusp(a_, b_)
        val L = cuspActual[0]
        val C = cuspActual[1]
        return floatArrayOf(C / L, C / (1f - L))
    }

    /**
     * Generate HSV hue colors for the hue slider (using Hsv API, bright and saturated).
     */
    fun generateHsvHueColors(): List<Color> {
        val steps = 36
        return (0 until steps).map { i ->
            val hue = i.toDouble() / steps.toDouble() * 360.0
            Hsv(hue, 100.0, 100.0).toColor()
        }
    }

    /**
     * Generate OkHSV hue colors for the hue slider (perceptual smoothing).
     */
    fun generateOkHsvHueColors(): List<Color> {
        val steps = 36
        return (0 until steps).map { i ->
            val hue = i.toFloat() / steps.toFloat()
            okhsvToColor(hue, 1f, 1f)
        }
    }
}
