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

val MiuixIcons.Useful.Info: ImageVector
    get() {
        if (_info != null) return _info!!
        _info = ImageVector.Builder("Info", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(13.0f, 21.594f)
                curveTo(17.744f, 21.594f, 21.589f, 17.748f, 21.589f, 13.004f)
                curveTo(21.589f, 8.261f, 17.744f, 4.415f, 13.0f, 4.415f)
                curveTo(8.256f, 4.415f, 4.411f, 8.261f, 4.411f, 13.004f)
                curveTo(4.411f, 17.748f, 8.256f, 21.594f, 13.0f, 21.594f)
                close()
                moveTo(13.0f, 23.194f)
                curveTo(18.627f, 23.194f, 23.189f, 18.632f, 23.189f, 13.004f)
                curveTo(23.189f, 7.377f, 18.627f, 2.815f, 13.0f, 2.815f)
                curveTo(7.373f, 2.815f, 2.811f, 7.377f, 2.811f, 13.004f)
                curveTo(2.811f, 18.632f, 7.373f, 23.194f, 13.0f, 23.194f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(14.1f, 8.83f)
                curveTo(14.1f, 9.437f, 13.608f, 9.93f, 13.0f, 9.93f)
                curveTo(12.392f, 9.93f, 11.9f, 9.437f, 11.9f, 8.83f)
                curveTo(11.9f, 8.222f, 12.392f, 7.73f, 13.0f, 7.73f)
                curveTo(13.608f, 7.73f, 14.1f, 8.222f, 14.1f, 8.83f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(13.0f, 11.243f)
                curveTo(12.779f, 11.243f, 12.668f, 11.243f, 12.579f, 11.274f)
                curveTo(12.415f, 11.33f, 12.287f, 11.459f, 12.231f, 11.622f)
                curveTo(12.2f, 11.711f, 12.2f, 11.822f, 12.2f, 12.043f)
                verticalLineTo(17.47f)
                curveTo(12.2f, 17.692f, 12.2f, 17.802f, 12.231f, 17.892f)
                curveTo(12.287f, 18.055f, 12.415f, 18.183f, 12.579f, 18.24f)
                curveTo(12.668f, 18.27f, 12.779f, 18.27f, 13.0f, 18.27f)
                curveTo(13.221f, 18.27f, 13.332f, 18.27f, 13.421f, 18.24f)
                curveTo(13.585f, 18.183f, 13.713f, 18.055f, 13.769f, 17.892f)
                curveTo(13.8f, 17.802f, 13.8f, 17.692f, 13.8f, 17.47f)
                lineTo(13.8f, 12.043f)
                curveTo(13.8f, 11.822f, 13.8f, 11.711f, 13.769f, 11.622f)
                curveTo(13.713f, 11.459f, 13.585f, 11.33f, 13.421f, 11.274f)
                curveTo(13.332f, 11.243f, 13.221f, 11.243f, 13.0f, 11.243f)
                close()
            }
        }.build()
        return _info!!
    }

private var _info: ImageVector? = null