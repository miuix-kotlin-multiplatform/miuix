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

val MiuixIcons.Basic.SearchCleanup: ImageVector
    get() {
        if (_searchCleanup != null) return _searchCleanup!!
        _searchCleanup = ImageVector.Builder("SearchCleanup", 18.199984f.dp, 18.199984f.dp, 68f, 68f).apply {
            path(
                fill = SolidColor(Color.White),
                fillAlpha = 0.06f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(34f, 66f)
                curveTo(51.6731f, 66f, 66f, 51.6731f, 66f, 34f)
                curveTo(66f, 16.3269f, 51.6731f, 2f, 34f, 2f)
                curveTo(16.3269f, 2f, 2f, 16.3269f, 2f, 34f)
                curveTo(2f, 51.6731f, 16.3269f, 66f, 34f, 66f)
                close()
            }
            path(
                fill = SolidColor(Color.Transparent),
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0.1f,
                strokeLineWidth = 0.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(34f, 67f)
                curveTo(52.2254f, 67f, 67f, 52.2254f, 67f, 34f)
                curveTo(67f, 15.7746f, 52.2254f, 1f, 34f, 1f)
                curveTo(15.7746f, 1f, 1f, 15.7746f, 1f, 34f)
                curveTo(1f, 52.2254f, 15.7746f, 67f, 34f, 67f)
                close()
            }
            path(
                fill = SolidColor(Color.White),
                fillAlpha = 0.3f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(44.6066f, 27.8492f)
                curveTo(45.7782f, 26.6777f, 45.7782f, 24.7782f, 44.6066f, 23.6066f)
                curveTo(43.435f, 22.435f, 41.5355f, 22.435f, 40.364f, 23.6066f)
                lineTo(34f, 29.9706f)
                lineTo(27.636f, 23.6066f)
                curveTo(26.4645f, 22.435f, 24.565f, 22.435f, 23.3934f, 23.6066f)
                curveTo(22.2218f, 24.7782f, 22.2218f, 26.6777f, 23.3934f, 27.8492f)
                lineTo(29.7574f, 34.2132f)
                lineTo(23.3934f, 40.5772f)
                curveTo(22.2218f, 41.7487f, 22.2218f, 43.6482f, 23.3934f, 44.8198f)
                curveTo(24.565f, 45.9914f, 26.4645f, 45.9914f, 27.636f, 44.8198f)
                lineTo(34f, 38.4558f)
                lineTo(40.364f, 44.8198f)
                curveTo(41.5355f, 45.9914f, 43.435f, 45.9914f, 44.6066f, 44.8198f)
                curveTo(45.7782f, 43.6482f, 45.7782f, 41.7487f, 44.6066f, 40.5772f)
                lineTo(38.2426f, 34.2132f)
                lineTo(44.6066f, 27.8492f)
                close()
            }
        }.build()
        return _searchCleanup!!
    }

private var _searchCleanup: ImageVector? = null