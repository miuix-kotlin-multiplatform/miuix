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
                curveTo(9.593f, 21.589f, 6.649f, 19.605f, 5.261f, 16.73f)
                lineTo(6.643f, 15.795f)
                curveTo(7.285f, 15.362f, 7.606f, 15.145f, 7.647f, 14.952f)
                curveTo(7.683f, 14.785f, 7.63f, 14.611f, 7.507f, 14.493f)
                curveTo(7.364f, 14.356f, 6.977f, 14.356f, 6.203f, 14.356f)
                horizontalLineTo(4.108f)
                curveTo(4.04f, 14.35f, 3.952f, 14.35f, 3.824f, 14.35f)
                curveTo(3.655f, 14.35f, 3.543f, 14.35f, 3.456f, 14.363f)
                curveTo(3.355f, 14.371f, 3.282f, 14.389f, 3.22f, 14.426f)
                curveTo(3.123f, 14.484f, 3.039f, 14.589f, 3.004f, 14.697f)
                curveTo(2.962f, 14.826f, 2.994f, 14.965f, 3.059f, 15.244f)
                lineTo(3.067f, 15.281f)
                curveTo(4.103f, 19.81f, 8.157f, 23.189f, 13.0f, 23.189f)
                curveTo(15.243f, 23.189f, 17.317f, 22.464f, 19.0f, 21.236f)
                curveTo(21.539f, 19.383f, 23.189f, 16.384f, 23.189f, 13.0f)
                curveTo(23.189f, 10.084f, 21.964f, 7.453f, 20.0f, 5.596f)
                curveTo(18.174f, 3.869f, 15.711f, 2.811f, 13.0f, 2.811f)
                curveTo(8.149f, 2.811f, 4.09f, 6.2f, 3.062f, 10.74f)
                curveTo(3.012f, 10.962f, 2.987f, 11.074f, 3.006f, 11.185f)
                curveTo(3.037f, 11.364f, 3.176f, 11.537f, 3.343f, 11.606f)
                curveTo(3.447f, 11.65f, 3.573f, 11.65f, 3.824f, 11.65f)
                curveTo(4.027f, 11.65f, 4.128f, 11.65f, 4.208f, 11.626f)
                curveTo(4.352f, 11.582f, 4.454f, 11.502f, 4.53f, 11.373f)
                curveTo(4.572f, 11.301f, 4.598f, 11.191f, 4.652f, 10.971f)
                curveTo(5.564f, 7.207f, 8.955f, 4.411f, 13.0f, 4.411f)
                curveTo(15.335f, 4.411f, 17.452f, 5.342f, 19.0f, 6.854f)
                curveTo(20.597f, 8.414f, 21.589f, 10.591f, 21.589f, 13.0f)
                curveTo(21.589f, 15.409f, 20.597f, 17.586f, 19.0f, 19.146f)
                curveTo(17.452f, 20.658f, 15.334f, 21.589f, 13.0f, 21.589f)
                close()
            }
        }.build()
        return _reboot!!
    }

private var _reboot: ImageVector? = null