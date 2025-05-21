// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.icon.icons.useful

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.icon.MiuixIcons

val MiuixIcons.Useful.Confirm: ImageVector
    get() {
        if (_confirm != null) return _confirm!!
        _confirm = ImageVector.Builder("Confirm", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(23.571f, 4.818f)
                curveTo(23.518f, 4.739f, 23.428f, 4.673f, 23.249f, 4.54f)
                curveTo(23.069f, 4.406f, 22.979f, 4.34f, 22.889f, 4.312f)
                curveTo(22.726f, 4.261f, 22.549f, 4.287f, 22.408f, 4.383f)
                curveTo(22.329f, 4.436f, 22.263f, 4.525f, 22.129f, 4.705f)
                lineTo(10.625f, 20.196f)
                lineTo(3.889f, 13.13f)
                curveTo(3.735f, 12.968f, 3.658f, 12.887f, 3.573f, 12.844f)
                curveTo(3.422f, 12.767f, 3.243f, 12.762f, 3.088f, 12.833f)
                curveTo(3.001f, 12.872f, 2.92f, 12.949f, 2.758f, 13.103f)
                curveTo(2.597f, 13.257f, 2.516f, 13.334f, 2.472f, 13.419f)
                curveTo(2.395f, 13.571f, 2.391f, 13.75f, 2.461f, 13.905f)
                curveTo(2.5f, 13.991f, 2.577f, 14.072f, 2.731f, 14.234f)
                lineTo(10.044f, 21.904f)
                curveTo(10.296f, 22.169f, 10.422f, 22.301f, 10.566f, 22.344f)
                curveTo(10.692f, 22.381f, 10.827f, 22.373f, 10.947f, 22.32f)
                curveTo(11.085f, 22.261f, 11.194f, 22.114f, 11.412f, 21.82f)
                lineTo(23.414f, 5.659f)
                curveTo(23.547f, 5.479f, 23.614f, 5.39f, 23.642f, 5.299f)
                curveTo(23.693f, 5.136f, 23.667f, 4.959f, 23.571f, 4.818f)
                close()
            }
        }.build()
        return _confirm!!
    }

private var _confirm: ImageVector? = null