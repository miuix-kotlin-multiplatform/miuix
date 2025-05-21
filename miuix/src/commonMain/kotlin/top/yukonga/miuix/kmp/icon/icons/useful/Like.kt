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

val MiuixIcons.Useful.Like: ImageVector
    get() {
        if (_like != null) return _like!!
        _like = ImageVector.Builder("Like", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color(0xFFFA311B)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(21.9482f, 13.9362f)
                lineTo(13.4026f, 22.7729f)
                curveTo(13.1825f, 23.0005f, 12.8176f, 23.0005f, 12.5975f, 22.7729f)
                lineTo(4.0533f, 13.9376f)
                curveTo(4.0096f, 13.8937f, 3.9666f, 13.8492f, 3.9243f, 13.8041f)
                lineTo(3.9047f, 13.7839f)
                curveTo(2.8702f, 12.6721f, 2.2382f, 11.1816f, 2.2382f, 9.5433f)
                curveTo(2.2382f, 6.1045f, 5.0259f, 3.3167f, 8.4647f, 3.3167f)
                curveTo(10.2526f, 3.3167f, 11.8644f, 4.0703f, 13.0001f, 5.277f)
                curveTo(14.1357f, 4.0703f, 15.7475f, 3.3167f, 17.5354f, 3.3167f)
                curveTo(20.9742f, 3.3167f, 23.762f, 6.1045f, 23.762f, 9.5433f)
                curveTo(23.762f, 11.1816f, 23.1292f, 12.6721f, 22.0948f, 13.7839f)
                lineTo(22.0745f, 13.8056f)
                curveTo(22.033f, 13.8497f, 21.9909f, 13.8932f, 21.9482f, 13.9362f)
                close()
            }
        }.build()
        return _like!!
    }
private var _like: ImageVector? = null