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

val MiuixIcons.Useful.More: ImageVector
    get() {
        if (_more != null) return _more!!
        _more = ImageVector.Builder("More", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(6.926f, 19.078f)
                curveTo(10.281f, 22.432f, 15.719f, 22.432f, 19.073f, 19.078f)
                curveTo(22.428f, 15.724f, 22.428f, 10.285f, 19.073f, 6.931f)
                curveTo(15.719f, 3.577f, 10.281f, 3.577f, 6.926f, 6.931f)
                curveTo(3.572f, 10.285f, 3.572f, 15.724f, 6.926f, 19.078f)
                close()
                moveTo(5.795f, 20.209f)
                curveTo(9.774f, 24.188f, 16.226f, 24.188f, 20.205f, 20.209f)
                curveTo(24.184f, 16.23f, 24.184f, 9.779f, 20.205f, 5.799f)
                curveTo(16.226f, 1.82f, 9.774f, 1.82f, 5.795f, 5.799f)
                curveTo(1.816f, 9.779f, 1.816f, 16.23f, 5.795f, 20.209f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(14.1f, 13.0f)
                curveTo(14.1f, 13.608f, 13.607f, 14.1f, 13.0f, 14.1f)
                curveTo(12.392f, 14.1f, 11.9f, 13.608f, 11.9f, 13.0f)
                curveTo(11.9f, 12.392f, 12.392f, 11.9f, 13.0f, 11.9f)
                curveTo(13.607f, 11.9f, 14.1f, 12.392f, 14.1f, 13.0f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(18.316f, 13.0f)
                curveTo(18.316f, 13.608f, 17.824f, 14.1f, 17.216f, 14.1f)
                curveTo(16.609f, 14.1f, 16.116f, 13.608f, 16.116f, 13.0f)
                curveTo(16.116f, 12.392f, 16.609f, 11.9f, 17.216f, 11.9f)
                curveTo(17.824f, 11.9f, 18.316f, 12.392f, 18.316f, 13.0f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(9.884f, 13.0f)
                curveTo(9.884f, 13.608f, 9.391f, 14.1f, 8.784f, 14.1f)
                curveTo(8.176f, 14.1f, 7.684f, 13.608f, 7.684f, 13.0f)
                curveTo(7.684f, 12.392f, 8.176f, 11.9f, 8.784f, 11.9f)
                curveTo(9.391f, 11.9f, 9.884f, 12.392f, 9.884f, 13.0f)
                close()
            }
        }
            .build()
        return _more!!
    }

private var _more: ImageVector? = null