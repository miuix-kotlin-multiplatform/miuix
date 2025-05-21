// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.icon.icons.useful

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.icon.MiuixIcons

val MiuixIcons.Useful.Back: ImageVector
    get() {
        if (_back != null) return _back!!
        _back = ImageVector.Builder("Back", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(4.8009f, 12.2f)
                lineTo(10.1423f, 6.8586f)
                curveTo(10.3003f, 6.7006f, 10.3793f, 6.6216f, 10.4206f, 6.5359f)
                curveTo(10.4945f, 6.3823f, 10.4945f, 6.2035f, 10.4206f, 6.0499f)
                curveTo(10.3793f, 5.9642f, 10.3003f, 5.8852f, 10.1423f, 5.7272f)
                curveTo(9.9843f, 5.5692f, 9.9053f, 5.4902f, 9.8196f, 5.4489f)
                curveTo(9.6661f, 5.375f, 9.4872f, 5.375f, 9.3337f, 5.4489f)
                curveTo(9.2479f, 5.4902f, 9.169f, 5.5692f, 9.011f, 5.7272f)
                lineTo(2.3833f, 12.3548f)
                lineTo(2.3817f, 12.3564f)
                curveTo(2.373f, 12.3651f, 2.3383f, 12.3995f, 2.3072f, 12.4361f)
                curveTo(2.2675f, 12.4829f, 2.1895f, 12.5826f, 2.1412f, 12.7311f)
                curveTo(2.0844f, 12.9059f, 2.0844f, 13.0942f, 2.1412f, 13.2689f)
                curveTo(2.1895f, 13.4174f, 2.2675f, 13.5171f, 2.3072f, 13.564f)
                curveTo(2.3383f, 13.6006f, 2.373f, 13.6349f, 2.3817f, 13.6436f)
                lineTo(2.3833f, 13.6452f)
                lineTo(9.011f, 20.2728f)
                curveTo(9.169f, 20.4308f, 9.2479f, 20.5098f, 9.3337f, 20.5511f)
                curveTo(9.4872f, 20.625f, 9.6661f, 20.625f, 9.8196f, 20.5511f)
                curveTo(9.9053f, 20.5098f, 9.9843f, 20.4308f, 10.1423f, 20.2728f)
                curveTo(10.3003f, 20.1148f, 10.3793f, 20.0358f, 10.4206f, 19.9501f)
                curveTo(10.4945f, 19.7966f, 10.4945f, 19.6177f, 10.4206f, 19.4642f)
                curveTo(10.3793f, 19.3784f, 10.3003f, 19.2994f, 10.1423f, 19.1414f)
                lineTo(4.8009f, 13.8f)
                lineTo(23.0919f, 13.8f)
                curveTo(23.3133f, 13.8f, 23.424f, 13.8f, 23.5131f, 13.7693f)
                curveTo(23.6764f, 13.7129f, 23.8048f, 13.5846f, 23.8611f, 13.4212f)
                curveTo(23.8919f, 13.3321f, 23.8919f, 13.2214f, 23.8919f, 13.0f)
                curveTo(23.8919f, 12.7786f, 23.8919f, 12.6679f, 23.8611f, 12.5788f)
                curveTo(23.8048f, 12.4155f, 23.6764f, 12.2871f, 23.5131f, 12.2308f)
                curveTo(23.424f, 12.2f, 23.3133f, 12.2f, 23.0919f, 12.2f)
                lineTo(4.8009f, 12.2f)
                close()
            }
        }.build()
        return _back!!
    }
private var _back: ImageVector? = null