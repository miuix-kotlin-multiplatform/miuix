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

val MiuixIcons.Basic.Search: ImageVector
    get() {
        if (_search != null) return _search!!
        _search = ImageVector.Builder("Search", 20.0f.dp, 20.0f.dp, 20.0f, 20.0f).apply {
            path(
                fill = SolidColor(Color.White),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(12.572f, 13.379f)
                curveTo(11.541f, 14.183f, 10.244f, 14.662f, 8.835f, 14.662f)
                curveTo(5.477f, 14.662f, 2.754f, 11.94f, 2.754f, 8.581f)
                curveTo(2.754f, 5.223f, 5.477f, 2.5f, 8.835f, 2.5f)
                curveTo(12.194f, 2.5f, 14.916f, 5.223f, 14.916f, 8.581f)
                curveTo(14.916f, 9.99f, 14.437f, 11.287f, 13.633f, 12.318f)
                lineTo(17.464f, 16.149f)
                curveTo(17.563f, 16.248f, 17.612f, 16.297f, 17.645f, 16.346f)
                curveTo(17.78f, 16.548f, 17.78f, 16.811f, 17.645f, 17.013f)
                curveTo(17.612f, 17.062f, 17.563f, 17.111f, 17.464f, 17.21f)
                curveTo(17.366f, 17.308f, 17.316f, 17.358f, 17.267f, 17.39f)
                curveTo(17.065f, 17.525f, 16.802f, 17.525f, 16.601f, 17.39f)
                curveTo(16.551f, 17.358f, 16.502f, 17.308f, 16.403f, 17.21f)
                lineTo(12.572f, 13.379f)
                close()
                moveTo(13.416f, 8.581f)
                curveTo(13.416f, 11.111f, 11.365f, 13.162f, 8.835f, 13.162f)
                curveTo(6.305f, 13.162f, 4.254f, 11.111f, 4.254f, 8.581f)
                curveTo(4.254f, 6.051f, 6.305f, 4f, 8.835f, 4f)
                curveTo(11.365f, 4f, 13.416f, 6.051f, 13.416f, 8.581f)
                close()
            }
        }.build()
        return _search!!
    }

private var _search: ImageVector? = null