// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.icon.icons.basic

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.icon.MiuixIcons

val MiuixIcons.Basic.ArrowUpDownIntegrated: ImageVector
    get() {
        if (_arrowUpDownIntegrated != null) return _arrowUpDownIntegrated!!
        _arrowUpDownIntegrated = ImageVector.Builder("ArrowUpDownIntegrated", 10.dp, 16.dp, 10f, 16f).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(2.397f, 4.7384f)
                lineTo(4.5688f, 2.5665f)
                lineTo(5.0075f, 2.1278f)
                lineTo(5.4266f, 2.5469f)
                lineTo(7.5985f, 4.7187f)
                lineTo(8.531f, 5.6512f)
                curveTo(8.8282f, 5.9485f, 9.3102f, 5.9485f, 9.6075f, 5.6512f)
                curveTo(9.9047f, 5.354f, 9.9047f, 4.872f, 9.6075f, 4.5747f)
                lineTo(8.675f, 3.6423f)
                lineTo(6.5031f, 1.4704f)
                lineTo(5.5706f, 0.5379f)
                curveTo(5.3595f, 0.3267f, 5.0551f, 0.2656f, 4.7899f, 0.3544f)
                curveTo(4.6561f, 0.3855f, 4.5291f, 0.4532f, 4.4248f, 0.5575f)
                lineTo(3.4924f, 1.49f)
                lineTo(1.3205f, 3.6619f)
                lineTo(0.388f, 4.5943f)
                curveTo(0.0907f, 4.8916f, 0.0907f, 5.3736f, 0.388f, 5.6708f)
                curveTo(0.6853f, 5.9681f, 1.1672f, 5.9681f, 1.4645f, 5.6708f)
                lineTo(2.397f, 4.7384f)
                close()
                moveTo(2.397f, 11.257f)
                lineTo(4.5688f, 13.4289f)
                lineTo(5.0075f, 13.8675f)
                lineTo(5.4266f, 13.4485f)
                lineTo(7.5985f, 11.2766f)
                lineTo(8.531f, 10.3441f)
                curveTo(8.8282f, 10.0468f, 9.3102f, 10.0468f, 9.6075f, 10.3441f)
                curveTo(9.9047f, 10.6414f, 9.9047f, 11.1233f, 9.6075f, 11.4206f)
                lineTo(8.675f, 12.3531f)
                lineTo(6.5031f, 14.525f)
                lineTo(5.5706f, 15.4574f)
                curveTo(5.3594f, 15.6686f, 5.0551f, 15.7298f, 4.7899f, 15.6409f)
                curveTo(4.6561f, 15.6098f, 4.5291f, 15.5421f, 4.4248f, 15.4378f)
                lineTo(3.4924f, 14.5053f)
                lineTo(1.3205f, 12.3335f)
                lineTo(0.388f, 11.401f)
                curveTo(0.0907f, 11.1037f, 0.0907f, 10.6217f, 0.388f, 10.3245f)
                curveTo(0.6853f, 10.0272f, 1.1672f, 10.0272f, 1.4645f, 10.3245f)
                lineTo(2.397f, 11.257f)
                close()
            }
        }.build()
        return _arrowUpDownIntegrated!!
    }

private var _arrowUpDownIntegrated: ImageVector? = null