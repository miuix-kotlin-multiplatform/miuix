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

val MiuixIcons.Useful.Remove: ImageVector
    get() {
        if (_remove != null) return _remove!!
        _remove = ImageVector.Builder("Remove", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(6.927f, 19.073f)
                curveTo(10.281f, 22.428f, 15.719f, 22.428f, 19.073f, 19.073f)
                curveTo(22.428f, 15.719f, 22.428f, 10.281f, 19.073f, 6.927f)
                curveTo(15.719f, 3.572f, 10.281f, 3.572f, 6.927f, 6.927f)
                curveTo(3.572f, 10.281f, 3.572f, 15.719f, 6.927f, 19.073f)
                close()
                moveTo(5.795f, 20.205f)
                curveTo(9.774f, 24.184f, 16.226f, 24.184f, 20.205f, 20.205f)
                curveTo(24.184f, 16.226f, 24.184f, 9.774f, 20.205f, 5.795f)
                curveTo(16.226f, 1.816f, 9.774f, 1.816f, 5.795f, 5.795f)
                curveTo(1.816f, 9.774f, 1.816f, 16.226f, 5.795f, 20.205f)
                close()
            }
            path(
                fill = SolidColor(Color.White),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(8.178f, 12.196f)
                curveTo(7.957f, 12.196f, 7.846f, 12.196f, 7.757f, 12.226f)
                curveTo(7.594f, 12.283f, 7.466f, 12.411f, 7.409f, 12.575f)
                curveTo(7.378f, 12.664f, 7.378f, 12.774f, 7.378f, 12.996f)
                curveTo(7.378f, 13.217f, 7.378f, 13.328f, 7.409f, 13.417f)
                curveTo(7.466f, 13.58f, 7.594f, 13.709f, 7.757f, 13.765f)
                curveTo(7.846f, 13.796f, 7.957f, 13.796f, 8.178f, 13.796f)
                horizontalLineTo(17.822f)
                curveTo(18.043f, 13.796f, 18.154f, 13.796f, 18.243f, 13.765f)
                curveTo(18.406f, 13.709f, 18.535f, 13.58f, 18.591f, 13.417f)
                curveTo(18.622f, 13.328f, 18.622f, 13.217f, 18.622f, 12.996f)
                curveTo(18.622f, 12.774f, 18.622f, 12.664f, 18.591f, 12.575f)
                curveTo(18.535f, 12.411f, 18.406f, 12.283f, 18.243f, 12.226f)
                curveTo(18.154f, 12.196f, 18.043f, 12.196f, 17.822f, 12.196f)
                horizontalLineTo(8.178f)
                close()
            }
        }.build()
        return _remove!!
    }
private var _remove: ImageVector? = null