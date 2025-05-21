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

val MiuixIcons.Useful.Personal: ImageVector
    get() {
        if (_personal != null) return _personal!!
        _personal = ImageVector.Builder("Personal", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(13.0f, 11.055f)
                curveTo(14.93f, 11.055f, 16.495f, 9.49f, 16.495f, 7.56f)
                curveTo(16.495f, 5.63f, 14.93f, 4.066f, 13.0f, 4.066f)
                curveTo(11.07f, 4.066f, 9.505f, 5.63f, 9.505f, 7.56f)
                curveTo(9.505f, 9.49f, 11.07f, 11.055f, 13.0f, 11.055f)
                close()
                moveTo(13.0f, 12.655f)
                curveTo(15.814f, 12.655f, 18.095f, 10.374f, 18.095f, 7.56f)
                curveTo(18.095f, 4.747f, 15.814f, 2.466f, 13.0f, 2.466f)
                curveTo(10.186f, 2.466f, 7.905f, 4.747f, 7.905f, 7.56f)
                curveTo(7.905f, 10.374f, 10.186f, 12.655f, 13.0f, 12.655f)
                close()
            }
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(21.864f, 16.168f)
                curveTo(21.467f, 15.818f, 21.09f, 15.675f, 20.334f, 15.389f)
                curveTo(18.054f, 14.526f, 15.582f, 14.054f, 13.0f, 14.054f)
                curveTo(10.418f, 14.054f, 7.946f, 14.526f, 5.666f, 15.389f)
                curveTo(4.91f, 15.675f, 4.532f, 15.818f, 4.136f, 16.169f)
                curveTo(3.825f, 16.444f, 3.497f, 16.918f, 3.35f, 17.306f)
                curveTo(3.162f, 17.801f, 3.162f, 18.297f, 3.162f, 19.288f)
                verticalLineTo(19.415f)
                curveTo(3.162f, 20.367f, 3.162f, 20.843f, 3.347f, 21.207f)
                curveTo(3.51f, 21.527f, 3.77f, 21.787f, 4.09f, 21.95f)
                curveTo(4.454f, 22.135f, 4.93f, 22.135f, 5.882f, 22.135f)
                horizontalLineTo(20.118f)
                curveTo(21.07f, 22.135f, 21.546f, 22.135f, 21.91f, 21.95f)
                curveTo(22.229f, 21.787f, 22.49f, 21.527f, 22.653f, 21.207f)
                curveTo(22.838f, 20.843f, 22.838f, 20.367f, 22.838f, 19.415f)
                verticalLineTo(19.288f)
                curveTo(22.838f, 18.297f, 22.838f, 17.801f, 22.65f, 17.306f)
                curveTo(22.502f, 16.918f, 22.175f, 16.444f, 21.864f, 16.168f)
                close()
                moveTo(20.771f, 17.365f)
                curveTo(20.581f, 17.195f, 20.371f, 17.112f, 19.951f, 16.947f)
                curveTo(17.844f, 16.118f, 15.488f, 15.655f, 13.0f, 15.655f)
                curveTo(10.513f, 15.655f, 8.156f, 16.118f, 6.049f, 16.947f)
                curveTo(5.629f, 17.112f, 5.419f, 17.195f, 5.23f, 17.365f)
                curveTo(5.074f, 17.506f, 4.928f, 17.719f, 4.855f, 17.916f)
                curveTo(4.765f, 18.154f, 4.765f, 18.408f, 4.765f, 18.915f)
                verticalLineTo(19.895f)
                curveTo(4.765f, 20.119f, 4.765f, 20.231f, 4.809f, 20.316f)
                curveTo(4.847f, 20.391f, 4.908f, 20.453f, 4.984f, 20.491f)
                curveTo(5.069f, 20.535f, 5.181f, 20.535f, 5.405f, 20.535f)
                horizontalLineTo(20.595f)
                curveTo(20.819f, 20.535f, 20.931f, 20.535f, 21.017f, 20.491f)
                curveTo(21.092f, 20.453f, 21.153f, 20.391f, 21.191f, 20.316f)
                curveTo(21.235f, 20.231f, 21.235f, 20.119f, 21.235f, 19.895f)
                verticalLineTo(18.915f)
                curveTo(21.235f, 18.408f, 21.235f, 18.154f, 21.146f, 17.916f)
                curveTo(21.072f, 17.719f, 20.927f, 17.506f, 20.771f, 17.365f)
                close()
            }
        }.build()
        return _personal!!
    }

private var _personal: ImageVector? = null