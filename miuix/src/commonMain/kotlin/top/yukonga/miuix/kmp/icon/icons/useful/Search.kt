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

val MiuixIcons.Useful.Search: ImageVector
    get() {
        if (_search != null) return _search!!
        _search = ImageVector.Builder("Search", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(15.712f, 16.843f)
                curveTo(14.35f, 17.955f, 12.611f, 18.622f, 10.716f, 18.622f)
                curveTo(6.35f, 18.622f, 2.811f, 15.082f, 2.811f, 10.716f)
                curveTo(2.811f, 6.35f, 6.35f, 2.811f, 10.716f, 2.811f)
                curveTo(15.082f, 2.811f, 18.622f, 6.35f, 18.622f, 10.716f)
                curveTo(18.622f, 12.611f, 17.955f, 14.35f, 16.843f, 15.712f)
                lineTo(22.32f, 21.189f)
                curveTo(22.477f, 21.345f, 22.555f, 21.424f, 22.596f, 21.508f)
                curveTo(22.672f, 21.664f, 22.672f, 21.845f, 22.596f, 22.001f)
                curveTo(22.555f, 22.085f, 22.477f, 22.164f, 22.32f, 22.32f)
                curveTo(22.164f, 22.477f, 22.085f, 22.555f, 22.001f, 22.596f)
                curveTo(21.845f, 22.672f, 21.664f, 22.672f, 21.508f, 22.596f)
                curveTo(21.424f, 22.555f, 21.345f, 22.477f, 21.189f, 22.32f)
                lineTo(15.712f, 16.843f)
                close()
                moveTo(17.022f, 10.716f)
                curveTo(17.022f, 14.199f, 14.199f, 17.022f, 10.716f, 17.022f)
                curveTo(7.234f, 17.022f, 4.411f, 14.199f, 4.411f, 10.716f)
                curveTo(4.411f, 7.234f, 7.234f, 4.411f, 10.716f, 4.411f)
                curveTo(14.199f, 4.411f, 17.022f, 7.234f, 17.022f, 10.716f)
                close()
            }
        }.build()
        return _search!!
    }

private var _search: ImageVector? = null
