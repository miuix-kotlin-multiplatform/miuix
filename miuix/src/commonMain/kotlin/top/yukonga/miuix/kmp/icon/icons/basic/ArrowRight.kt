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

val MiuixIcons.Basic.ArrowRight: ImageVector
    get() {
        if (_arrowRight != null) return _arrowRight!!
        _arrowRight = ImageVector.Builder("ArrowRight", 10.dp, 16.dp, 10f, 16f).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(1.65f, 1.469f)
                curveTo(1.929f, 1.19f, 2.381f, 1.19f, 2.66f, 1.469f)
                lineTo(8.721f, 7.53f)
                curveTo(9.0f, 7.809f, 9.0f, 8.261f, 8.721f, 8.54f)
                lineTo(2.66f, 14.601f)
                curveTo(2.381f, 14.88f, 1.929f, 14.88f, 1.65f, 14.601f)
                curveTo(1.371f, 14.322f, 1.371f, 13.87f, 1.65f, 13.591f)
                lineTo(7.205f, 8.035f)
                lineTo(1.65f, 2.479f)
                curveTo(1.371f, 2.2f, 1.371f, 1.748f, 1.65f, 1.469f)
                close()
            }
        }.build()
        return _arrowRight!!
    }

private var _arrowRight: ImageVector? = null