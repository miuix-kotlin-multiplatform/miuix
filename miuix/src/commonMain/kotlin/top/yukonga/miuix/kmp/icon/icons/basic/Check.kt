// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.icon.icons.basic

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.icon.MiuixIcons

val MiuixIcons.Basic.Check: ImageVector
    get() {
        if (_check != null) return _check!!
        _check = ImageVector.Builder("Check", 26.dp, 26.dp, 56f, 56f).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(46.8171f, 18.1514f)
                curveTo(48.0496f, 16.6624f, 47.8417f, 14.4561f, 46.3527f, 13.2235f)
                curveTo(44.8636f, 11.991f, 42.6573f, 12.1989f, 41.4247f, 13.6879f)
                lineTo(22.9535f, 36.0031f)
                lineTo(13.4007f, 26.4502f)
                curveTo(12.0338f, 25.0833f, 9.8177f, 25.0833f, 8.4509f, 26.4502f)
                curveTo(7.0841f, 27.817f, 7.0841f, 30.0331f, 8.4509f, 31.3999f)
                lineTo(20.7077f, 43.6567f)
                curveTo(21.7243f, 44.6733f, 23.2108f, 44.9338f, 24.4682f, 44.4381f)
                curveTo(25.0159f, 44.2302f, 25.5189f, 43.8818f, 25.9192f, 43.3982f)
                lineTo(46.8171f, 18.1514f)
                close()
            }
        }.build()
        return _check!!
    }

private var _check: ImageVector? = null