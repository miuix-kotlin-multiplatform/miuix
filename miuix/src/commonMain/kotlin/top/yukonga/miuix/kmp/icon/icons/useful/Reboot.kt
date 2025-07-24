// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.icon.icons.useful

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.icon.MiuixIcons

val MiuixIcons.Useful.Reboot: ImageVector
    get() {
        if (_reboot != null) {
            return _reboot!!
        }
        _reboot = ImageVector.Builder("Reboot", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(13.0f, 21.589f)
                curveTo(16.407f, 21.589f, 19.351f, 19.605f, 20.739f, 16.73f)
                lineTo(19.357f, 15.795f)
                curveTo(18.715f, 15.362f, 18.394f, 15.145f, 18.353f, 14.952f)
                curveTo(18.317f, 14.785f, 18.37f, 14.611f, 18.493f, 14.493f)
                curveTo(18.636f, 14.356f, 19.023f, 14.356f, 19.797f, 14.356f)
                horizontalLineTo(21.892f)
                curveTo(21.96f, 14.35f, 22.048f, 14.35f, 22.176f, 14.35f)
                curveTo(22.345f, 14.35f, 22.457f, 14.35f, 22.544f, 14.363f)
                curveTo(22.645f, 14.371f, 22.718f, 14.389f, 22.78f, 14.426f)
                curveTo(22.877f, 14.484f, 22.961f, 14.589f, 22.996f, 14.697f)
                curveTo(23.038f, 14.826f, 23.006f, 14.965f, 22.941f, 15.244f)
                lineTo(22.933f, 15.281f)
                curveTo(21.897f, 19.81f, 17.843f, 23.189f, 13.0f, 23.189f)
                curveTo(10.757f, 23.189f, 8.683f, 22.464f, 7.0f, 21.236f)
                curveTo(4.461f, 19.383f, 2.811f, 16.384f, 2.811f, 13.0f)
                curveTo(2.811f, 10.084f, 4.036f, 7.453f, 6.0f, 5.596f)
                curveTo(7.826f, 3.869f, 10.289f, 2.811f, 13.0f, 2.811f)
                curveTo(17.851f, 2.811f, 21.91f, 6.2f, 22.938f, 10.74f)
                curveTo(22.988f, 10.962f, 23.013f, 11.074f, 22.994f, 11.185f)
                curveTo(22.963f, 11.364f, 22.824f, 11.537f, 22.657f, 11.606f)
                curveTo(22.553f, 11.65f, 22.427f, 11.65f, 22.176f, 11.65f)
                curveTo(21.973f, 11.65f, 21.872f, 11.65f, 21.792f, 11.626f)
                curveTo(21.648f, 11.582f, 21.546f, 11.502f, 21.47f, 11.373f)
                curveTo(21.428f, 11.301f, 21.402f, 11.191f, 21.348f, 10.971f)
                curveTo(20.436f, 7.207f, 17.045f, 4.411f, 13.0f, 4.411f)
                curveTo(10.665f, 4.411f, 8.548f, 5.342f, 7.0f, 6.854f)
                curveTo(5.403f, 8.414f, 4.411f, 10.591f, 4.411f, 13.0f)
                curveTo(4.411f, 15.409f, 5.403f, 17.586f, 7.0f, 19.146f)
                curveTo(8.548f, 20.658f, 10.666f, 21.589f, 13.0f, 21.589f)
                close()
            }
        }.build()
        return _reboot!!
    }

private var _reboot: ImageVector? = null