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

val MiuixIcons.Basic.ArrowUpDown: ImageVector
    get() {
        if (_arrowUpDown != null) return _arrowUpDown!!
        _arrowUpDown = ImageVector.Builder("ArrowUpDown", 15.dp, 15.dp, 15f, 15f).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(7.492f, 3.996f)
                lineTo(5.629f, 5.86f)
                lineTo(5.157f, 6.332f)
                curveTo(4.874f, 6.615f, 4.414f, 6.615f, 4.131f, 6.332f)
                curveTo(3.847f, 6.048f, 3.847f, 5.588f, 4.131f, 5.305f)
                lineTo(4.603f, 4.833f)
                lineTo(6.466f, 2.97f)
                lineTo(6.938f, 2.498f)
                curveTo(7.092f, 2.344f, 7.298f, 2.273f, 7.5f, 2.287f)
                curveTo(7.701f, 2.274f, 7.907f, 2.344f, 8.061f, 2.498f)
                lineTo(8.533f, 2.97f)
                lineTo(10.396f, 4.833f)
                lineTo(10.868f, 5.305f)
                curveTo(11.152f, 5.589f, 11.152f, 6.048f, 10.868f, 6.332f)
                curveTo(10.585f, 6.615f, 10.125f, 6.615f, 9.842f, 6.332f)
                lineTo(9.37f, 5.86f)
                lineTo(7.507f, 3.997f)
                lineTo(7.499f, 3.989f)
                lineTo(7.492f, 3.996f)
                close()
                moveTo(7.492f, 11.003f)
                lineTo(5.629f, 9.14f)
                lineTo(5.157f, 8.668f)
                curveTo(4.874f, 8.385f, 4.414f, 8.385f, 4.131f, 8.668f)
                curveTo(3.847f, 8.952f, 3.847f, 9.411f, 4.131f, 9.695f)
                lineTo(4.603f, 10.167f)
                lineTo(6.466f, 12.03f)
                lineTo(6.938f, 12.502f)
                curveTo(7.092f, 12.656f, 7.298f, 12.726f, 7.5f, 12.713f)
                curveTo(7.701f, 12.726f, 7.907f, 12.656f, 8.061f, 12.502f)
                lineTo(8.533f, 12.03f)
                lineTo(10.396f, 10.167f)
                lineTo(10.868f, 9.695f)
                curveTo(11.152f, 9.411f, 11.152f, 8.952f, 10.868f, 8.668f)
                curveTo(10.585f, 8.385f, 10.125f, 8.385f, 9.842f, 8.668f)
                lineTo(9.37f, 9.14f)
                lineTo(7.507f, 11.003f)
                lineTo(7.499f, 11.01f)
                lineTo(7.492f, 11.003f)
                close()
            }
        }.build()
        return _arrowUpDown!!
    }

private var _arrowUpDown: ImageVector? = null