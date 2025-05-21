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

val MiuixIcons.Useful.Play: ImageVector
    get() {
        if (_play != null) return _play!!
        _play = ImageVector.Builder("Play", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(21.914f, 12.431f)
                curveTo(21.729f, 12.015f, 21.178f, 11.697f, 20.075f, 11.06f)
                lineTo(8.228f, 4.22f)
                curveTo(7.125f, 3.583f, 6.574f, 3.265f, 6.122f, 3.313f)
                curveTo(5.727f, 3.354f, 5.369f, 3.561f, 5.135f, 3.882f)
                curveTo(4.868f, 4.25f, 4.868f, 4.887f, 4.868f, 6.16f)
                verticalLineTo(19.84f)
                curveTo(4.868f, 21.113f, 4.868f, 21.75f, 5.135f, 22.118f)
                curveTo(5.369f, 22.439f, 5.727f, 22.646f, 6.122f, 22.687f)
                curveTo(6.574f, 22.735f, 7.125f, 22.416f, 8.228f, 21.78f)
                lineTo(20.075f, 14.94f)
                curveTo(21.178f, 14.303f, 21.729f, 13.985f, 21.914f, 13.569f)
                curveTo(22.076f, 13.207f, 22.076f, 12.793f, 21.914f, 12.431f)
                close()
                moveTo(20.018f, 12.919f)
                curveTo(19.991f, 12.859f, 19.913f, 12.814f, 19.755f, 12.723f)
                lineTo(6.946f, 5.327f)
                curveTo(6.788f, 5.236f, 6.71f, 5.191f, 6.645f, 5.198f)
                curveTo(6.589f, 5.204f, 6.537f, 5.233f, 6.504f, 5.279f)
                curveTo(6.466f, 5.332f, 6.466f, 5.423f, 6.466f, 5.605f)
                lineTo(6.466f, 20.395f)
                curveTo(6.466f, 20.577f, 6.466f, 20.668f, 6.504f, 20.721f)
                curveTo(6.537f, 20.767f, 6.589f, 20.796f, 6.645f, 20.802f)
                curveTo(6.71f, 20.809f, 6.788f, 20.764f, 6.946f, 20.673f)
                lineTo(19.755f, 13.277f)
                curveTo(19.913f, 13.186f, 19.991f, 13.141f, 20.018f, 13.081f)
                curveTo(20.041f, 13.03f, 20.041f, 12.97f, 20.018f, 12.919f)
                close()
            }
        }.build()
        return _play!!
    }

private var _play: ImageVector? = null