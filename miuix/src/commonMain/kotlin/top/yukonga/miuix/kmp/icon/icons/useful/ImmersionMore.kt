// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.icon.icons.useful

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.icon.MiuixIcons

val MiuixIcons.Useful.ImmersionMore: ImageVector
    get() {
        if (_immersionMore != null) return _immersionMore!!
        _immersionMore = ImageVector.Builder("ImmersionMore", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(13.0f, 14.405f)
                curveTo(12.224f, 14.405f, 11.594f, 13.776f, 11.594f, 13.0f)
                curveTo(11.594f, 12.224f, 12.224f, 11.595f, 13.0f, 11.595f)
                curveTo(13.776f, 11.595f, 14.405f, 12.224f, 14.405f, 13.0f)
                curveTo(14.405f, 13.776f, 13.776f, 14.405f, 13.0f, 14.405f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(13.0f, 21.432f)
                curveTo(12.224f, 21.432f, 11.594f, 20.803f, 11.594f, 20.027f)
                curveTo(11.594f, 19.251f, 12.224f, 18.622f, 13.0f, 18.622f)
                curveTo(13.776f, 18.622f, 14.405f, 19.251f, 14.405f, 20.027f)
                curveTo(14.405f, 20.803f, 13.776f, 21.432f, 13.0f, 21.432f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(13.0f, 7.378f)
                curveTo(12.224f, 7.378f, 11.594f, 6.749f, 11.594f, 5.973f)
                curveTo(11.594f, 5.197f, 12.224f, 4.568f, 13.0f, 4.568f)
                curveTo(13.776f, 4.568f, 14.405f, 5.197f, 14.405f, 5.973f)
                curveTo(14.405f, 6.749f, 13.776f, 7.378f, 13.0f, 7.378f)
                close()
            }
        }.build()
        return _immersionMore!!
    }

private var _immersionMore: ImageVector? = null