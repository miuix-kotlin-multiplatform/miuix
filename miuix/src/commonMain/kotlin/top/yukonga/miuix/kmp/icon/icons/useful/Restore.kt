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

val MiuixIcons.Useful.Restore: ImageVector
    get() {
        if (_restore != null) return _restore!!
        _restore = ImageVector.Builder("Restore", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(5.546f, 7.018f)
                lineTo(8.486f, 4.287f)
                curveTo(8.65f, 4.135f, 8.732f, 4.058f, 8.776f, 3.974f)
                curveTo(8.856f, 3.824f, 8.862f, 3.645f, 8.794f, 3.489f)
                curveTo(8.756f, 3.402f, 8.68f, 3.32f, 8.528f, 3.156f)
                curveTo(8.375f, 2.992f, 8.299f, 2.911f, 8.215f, 2.866f)
                curveTo(8.064f, 2.787f, 7.886f, 2.78f, 7.73f, 2.848f)
                curveTo(7.642f, 2.886f, 7.561f, 2.963f, 7.397f, 3.115f)
                lineTo(2.956f, 7.241f)
                curveTo(2.793f, 7.393f, 2.7f, 7.605f, 2.7f, 7.827f)
                curveTo(2.7f, 8.05f, 2.793f, 8.262f, 2.956f, 8.414f)
                lineTo(7.397f, 12.54f)
                curveTo(7.561f, 12.693f, 7.642f, 12.769f, 7.73f, 12.807f)
                curveTo(7.886f, 12.875f, 8.064f, 12.868f, 8.215f, 12.789f)
                curveTo(8.299f, 12.745f, 8.375f, 12.663f, 8.528f, 12.499f)
                curveTo(8.68f, 12.335f, 8.756f, 12.253f, 8.794f, 12.166f)
                curveTo(8.862f, 12.01f, 8.856f, 11.831f, 8.776f, 11.681f)
                curveTo(8.732f, 11.597f, 8.65f, 11.52f, 8.486f, 11.368f)
                lineTo(5.526f, 8.618f)
                horizontalLineTo(15.819f)
                curveTo(19.064f, 8.618f, 21.694f, 11.249f, 21.694f, 14.493f)
                curveTo(21.694f, 17.738f, 19.064f, 20.368f, 15.819f, 20.368f)
                horizontalLineTo(6.07f)
                curveTo(5.847f, 20.368f, 5.735f, 20.368f, 5.645f, 20.4f)
                curveTo(5.484f, 20.456f, 5.358f, 20.583f, 5.302f, 20.743f)
                curveTo(5.27f, 20.833f, 5.27f, 20.945f, 5.27f, 21.169f)
                curveTo(5.27f, 21.392f, 5.27f, 21.504f, 5.302f, 21.594f)
                curveTo(5.358f, 21.754f, 5.484f, 21.881f, 5.645f, 21.937f)
                curveTo(5.735f, 21.969f, 5.847f, 21.969f, 6.07f, 21.969f)
                horizontalLineTo(15.819f)
                curveTo(19.948f, 21.969f, 23.295f, 18.622f, 23.295f, 14.493f)
                curveTo(23.295f, 10.365f, 19.948f, 7.018f, 15.819f, 7.018f)
                horizontalLineTo(5.546f)
                close()
            }
        }.build()
        return _restore!!
    }

private var _restore: ImageVector? = null