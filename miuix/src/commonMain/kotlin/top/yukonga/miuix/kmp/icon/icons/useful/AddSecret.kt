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

val MiuixIcons.Useful.AddSecret: ImageVector
    get() {
        if (_addSecret != null) return _addSecret!!
        _addSecret = ImageVector.Builder("AddSecret", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(7.73f, 10.199f)
                verticalLineTo(8.081f)
                curveTo(7.73f, 5.17f, 10.089f, 2.811f, 13.0f, 2.811f)
                curveTo(15.911f, 2.811f, 18.27f, 5.17f, 18.27f, 8.081f)
                verticalLineTo(10.199f)
                curveTo(18.931f, 10.22f, 19.36f, 10.28f, 19.716f, 10.462f)
                curveTo(20.187f, 10.701f, 20.569f, 11.084f, 20.809f, 11.554f)
                curveTo(21.081f, 12.089f, 21.081f, 12.789f, 21.081f, 14.189f)
                verticalLineTo(19.189f)
                curveTo(21.081f, 20.589f, 21.081f, 21.289f, 20.809f, 21.824f)
                curveTo(20.569f, 22.295f, 20.187f, 22.677f, 19.716f, 22.917f)
                curveTo(19.181f, 23.189f, 18.481f, 23.189f, 17.081f, 23.189f)
                horizontalLineTo(8.919f)
                curveTo(7.519f, 23.189f, 6.819f, 23.189f, 6.284f, 22.917f)
                curveTo(5.813f, 22.677f, 5.431f, 22.295f, 5.191f, 21.824f)
                curveTo(4.919f, 21.289f, 4.919f, 20.589f, 4.919f, 19.189f)
                verticalLineTo(14.189f)
                curveTo(4.919f, 12.789f, 4.919f, 12.089f, 5.191f, 11.554f)
                curveTo(5.431f, 11.084f, 5.813f, 10.701f, 6.284f, 10.462f)
                curveTo(6.64f, 10.28f, 7.069f, 10.22f, 7.73f, 10.199f)
                close()
                moveTo(16.689f, 8.081f)
                verticalLineTo(10.189f)
                horizontalLineTo(9.311f)
                verticalLineTo(8.081f)
                curveTo(9.311f, 6.044f, 10.962f, 4.392f, 13.0f, 4.392f)
                curveTo(15.038f, 4.392f, 16.689f, 6.044f, 16.689f, 8.081f)
                close()
                moveTo(6.52f, 13.39f)
                curveTo(6.52f, 12.83f, 6.52f, 12.55f, 6.629f, 12.336f)
                curveTo(6.725f, 12.148f, 6.878f, 11.995f, 7.066f, 11.899f)
                curveTo(7.28f, 11.79f, 7.56f, 11.79f, 8.12f, 11.79f)
                horizontalLineTo(17.88f)
                curveTo(18.44f, 11.79f, 18.72f, 11.79f, 18.934f, 11.899f)
                curveTo(19.122f, 11.995f, 19.275f, 12.148f, 19.371f, 12.336f)
                curveTo(19.48f, 12.55f, 19.48f, 12.83f, 19.48f, 13.39f)
                verticalLineTo(19.99f)
                curveTo(19.48f, 20.55f, 19.48f, 20.83f, 19.371f, 21.044f)
                curveTo(19.275f, 21.232f, 19.122f, 21.385f, 18.934f, 21.481f)
                curveTo(18.72f, 21.59f, 18.44f, 21.59f, 17.88f, 21.59f)
                horizontalLineTo(8.12f)
                curveTo(7.56f, 21.59f, 7.28f, 21.59f, 7.066f, 21.481f)
                curveTo(6.878f, 21.385f, 6.725f, 21.232f, 6.629f, 21.044f)
                curveTo(6.52f, 20.83f, 6.52f, 20.55f, 6.52f, 19.99f)
                verticalLineTo(13.39f)
                close()
            }
        }.build()
        return _addSecret!!
    }

private var _addSecret: ImageVector? = null