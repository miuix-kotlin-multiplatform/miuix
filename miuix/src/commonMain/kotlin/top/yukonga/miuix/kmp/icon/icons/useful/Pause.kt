// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.icon.icons.useful

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.icon.MiuixIcons

val MiuixIcons.Useful.Pause: ImageVector
    get() {
        if (_pause != null) return _pause!!
        _pause = ImageVector.Builder("Pause", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(7.065f, 22.631f)
                curveTo(7.154f, 22.662f, 7.265f, 22.662f, 7.486f, 22.662f)
                curveTo(7.708f, 22.662f, 7.819f, 22.662f, 7.908f, 22.631f)
                curveTo(8.071f, 22.575f, 8.199f, 22.447f, 8.256f, 22.283f)
                curveTo(8.286f, 22.194f, 8.286f, 22.083f, 8.286f, 21.862f)
                lineTo(8.286f, 4.138f)
                curveTo(8.286f, 3.916f, 8.286f, 3.806f, 8.256f, 3.717f)
                curveTo(8.199f, 3.553f, 8.071f, 3.425f, 7.908f, 3.368f)
                curveTo(7.819f, 3.338f, 7.708f, 3.338f, 7.486f, 3.338f)
                curveTo(7.265f, 3.338f, 7.154f, 3.338f, 7.065f, 3.368f)
                curveTo(6.902f, 3.425f, 6.774f, 3.553f, 6.717f, 3.717f)
                curveTo(6.687f, 3.806f, 6.687f, 3.916f, 6.687f, 4.138f)
                verticalLineTo(21.862f)
                curveTo(6.687f, 22.083f, 6.687f, 22.194f, 6.717f, 22.283f)
                curveTo(6.774f, 22.447f, 6.902f, 22.575f, 7.065f, 22.631f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(18.935f, 3.368f)
                curveTo(18.846f, 3.338f, 18.735f, 3.338f, 18.514f, 3.338f)
                curveTo(18.292f, 3.338f, 18.181f, 3.338f, 18.092f, 3.368f)
                curveTo(17.929f, 3.425f, 17.801f, 3.553f, 17.744f, 3.717f)
                curveTo(17.713f, 3.806f, 17.713f, 3.916f, 17.713f, 4.138f)
                verticalLineTo(21.862f)
                curveTo(17.713f, 22.083f, 17.713f, 22.194f, 17.744f, 22.283f)
                curveTo(17.801f, 22.447f, 17.929f, 22.575f, 18.092f, 22.631f)
                curveTo(18.181f, 22.662f, 18.292f, 22.662f, 18.514f, 22.662f)
                curveTo(18.735f, 22.662f, 18.846f, 22.662f, 18.935f, 22.631f)
                curveTo(19.098f, 22.575f, 19.226f, 22.447f, 19.283f, 22.283f)
                curveTo(19.313f, 22.194f, 19.313f, 22.083f, 19.313f, 21.862f)
                lineTo(19.313f, 4.138f)
                curveTo(19.313f, 3.916f, 19.313f, 3.806f, 19.283f, 3.717f)
                curveTo(19.226f, 3.553f, 19.098f, 3.425f, 18.935f, 3.368f)
                close()
            }
        }.build()
        return _pause!!
    }

private var _pause: ImageVector? = null