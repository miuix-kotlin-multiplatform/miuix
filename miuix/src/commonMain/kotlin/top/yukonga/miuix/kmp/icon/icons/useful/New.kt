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

val MiuixIcons.Useful.New: ImageVector
    get() {
        if (_new != null) return _new!!
        _new = ImageVector.Builder("New", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(6.926f, 19.073f)
                curveTo(10.281f, 22.428f, 15.719f, 22.428f, 19.073f, 19.073f)
                curveTo(22.428f, 15.719f, 22.428f, 10.281f, 19.073f, 6.926f)
                curveTo(15.719f, 3.572f, 10.281f, 3.572f, 6.926f, 6.926f)
                curveTo(3.572f, 10.281f, 3.572f, 15.719f, 6.926f, 19.073f)
                close()
                moveTo(5.795f, 20.205f)
                curveTo(9.774f, 24.184f, 16.226f, 24.184f, 20.205f, 20.205f)
                curveTo(24.184f, 16.226f, 24.184f, 9.774f, 20.205f, 5.795f)
                curveTo(16.226f, 1.816f, 9.774f, 1.816f, 5.795f, 5.795f)
                curveTo(1.816f, 9.774f, 1.816f, 16.226f, 5.795f, 20.205f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(13.421f, 7.405f)
                curveTo(13.332f, 7.374f, 13.221f, 7.374f, 13.0f, 7.374f)
                curveTo(12.779f, 7.374f, 12.668f, 7.374f, 12.579f, 7.405f)
                curveTo(12.415f, 7.461f, 12.287f, 7.589f, 12.231f, 7.753f)
                curveTo(12.2f, 7.842f, 12.2f, 7.953f, 12.2f, 8.174f)
                verticalLineTo(12.196f)
                horizontalLineTo(8.178f)
                curveTo(7.957f, 12.196f, 7.846f, 12.196f, 7.757f, 12.226f)
                curveTo(7.594f, 12.283f, 7.465f, 12.411f, 7.409f, 12.575f)
                curveTo(7.378f, 12.664f, 7.378f, 12.774f, 7.378f, 12.996f)
                curveTo(7.378f, 13.217f, 7.378f, 13.328f, 7.409f, 13.417f)
                curveTo(7.465f, 13.58f, 7.594f, 13.709f, 7.757f, 13.765f)
                curveTo(7.846f, 13.796f, 7.957f, 13.796f, 8.178f, 13.796f)
                horizontalLineTo(12.2f)
                verticalLineTo(17.817f)
                curveTo(12.2f, 18.039f, 12.2f, 18.149f, 12.231f, 18.239f)
                curveTo(12.287f, 18.402f, 12.415f, 18.53f, 12.579f, 18.587f)
                curveTo(12.668f, 18.617f, 12.779f, 18.617f, 13.0f, 18.617f)
                curveTo(13.221f, 18.617f, 13.332f, 18.617f, 13.421f, 18.587f)
                curveTo(13.585f, 18.53f, 13.713f, 18.402f, 13.769f, 18.239f)
                curveTo(13.8f, 18.149f, 13.8f, 18.039f, 13.8f, 17.817f)
                verticalLineTo(13.796f)
                horizontalLineTo(17.822f)
                curveTo(18.043f, 13.796f, 18.154f, 13.796f, 18.243f, 13.765f)
                curveTo(18.406f, 13.709f, 18.535f, 13.58f, 18.591f, 13.417f)
                curveTo(18.622f, 13.328f, 18.622f, 13.217f, 18.622f, 12.996f)
                curveTo(18.622f, 12.774f, 18.622f, 12.664f, 18.591f, 12.575f)
                curveTo(18.535f, 12.411f, 18.406f, 12.283f, 18.243f, 12.226f)
                curveTo(18.154f, 12.196f, 18.043f, 12.196f, 17.822f, 12.196f)
                horizontalLineTo(13.8f)
                verticalLineTo(8.174f)
                curveTo(13.8f, 7.953f, 13.8f, 7.842f, 13.769f, 7.753f)
                curveTo(13.713f, 7.589f, 13.585f, 7.461f, 13.421f, 7.405f)
                close()
            }
        }.build()
        return _new!!
    }

private var _new: ImageVector? = null