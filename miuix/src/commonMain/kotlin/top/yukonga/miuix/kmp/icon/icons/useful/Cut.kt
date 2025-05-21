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

val MiuixIcons.Useful.Cut: ImageVector
    get() {
        if (_cut != null) return _cut!!
        _cut = ImageVector.Builder("Cut", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(8.907f, 11.053f)
                curveTo(8.261f, 11.609f, 7.42f, 11.946f, 6.5f, 11.946f)
                curveTo(4.463f, 11.946f, 2.811f, 10.294f, 2.811f, 8.257f)
                curveTo(2.811f, 6.219f, 4.463f, 4.568f, 6.5f, 4.568f)
                curveTo(8.538f, 4.568f, 10.189f, 6.219f, 10.189f, 8.257f)
                curveTo(10.189f, 8.795f, 10.074f, 9.307f, 9.867f, 9.768f)
                lineTo(13.512f, 12.102f)
                lineTo(21.285f, 7.124f)
                curveTo(21.46f, 7.012f, 21.547f, 6.956f, 21.641f, 6.935f)
                curveTo(21.725f, 6.915f, 21.811f, 6.915f, 21.894f, 6.935f)
                curveTo(21.989f, 6.956f, 22.076f, 7.012f, 22.251f, 7.124f)
                lineTo(22.662f, 7.388f)
                curveTo(22.858f, 7.513f, 22.956f, 7.576f, 22.99f, 7.656f)
                curveTo(23.019f, 7.726f, 23.019f, 7.805f, 22.99f, 7.874f)
                curveTo(22.956f, 7.954f, 22.858f, 8.017f, 22.662f, 8.142f)
                lineTo(9.902f, 16.314f)
                curveTo(10.087f, 16.754f, 10.189f, 17.237f, 10.189f, 17.743f)
                curveTo(10.189f, 19.781f, 8.538f, 21.432f, 6.5f, 21.432f)
                curveTo(4.463f, 21.432f, 2.811f, 19.781f, 2.811f, 17.743f)
                curveTo(2.811f, 15.706f, 4.463f, 14.054f, 6.5f, 14.054f)
                curveTo(7.453f, 14.054f, 8.321f, 14.415f, 8.976f, 15.008f)
                lineTo(12.029f, 13.052f)
                lineTo(8.907f, 11.053f)
                close()
                moveTo(8.589f, 8.257f)
                curveTo(8.589f, 9.411f, 7.654f, 10.346f, 6.5f, 10.346f)
                curveTo(5.346f, 10.346f, 4.411f, 9.411f, 4.411f, 8.257f)
                curveTo(4.411f, 7.103f, 5.346f, 6.168f, 6.5f, 6.168f)
                curveTo(7.654f, 6.168f, 8.589f, 7.103f, 8.589f, 8.257f)
                close()
                moveTo(8.589f, 17.743f)
                curveTo(8.589f, 18.897f, 7.654f, 19.832f, 6.5f, 19.832f)
                curveTo(5.346f, 19.832f, 4.411f, 18.897f, 4.411f, 17.743f)
                curveTo(4.411f, 16.589f, 5.346f, 15.654f, 6.5f, 15.654f)
                curveTo(7.654f, 15.654f, 8.589f, 16.589f, 8.589f, 17.743f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(21.285f, 18.98f)
                curveTo(21.46f, 19.093f, 21.547f, 19.149f, 21.641f, 19.17f)
                curveTo(21.725f, 19.19f, 21.811f, 19.19f, 21.894f, 19.17f)
                curveTo(21.989f, 19.149f, 22.076f, 19.093f, 22.251f, 18.98f)
                lineTo(22.662f, 18.717f)
                curveTo(22.858f, 18.592f, 22.956f, 18.529f, 22.99f, 18.449f)
                curveTo(23.019f, 18.379f, 23.019f, 18.301f, 22.99f, 18.231f)
                curveTo(22.956f, 18.151f, 22.858f, 18.088f, 22.662f, 17.963f)
                lineTo(16.778f, 14.194f)
                curveTo(16.603f, 14.082f, 16.515f, 14.026f, 16.421f, 14.004f)
                curveTo(16.338f, 13.985f, 16.251f, 13.985f, 16.168f, 14.004f)
                curveTo(16.074f, 14.026f, 15.986f, 14.082f, 15.811f, 14.195f)
                lineTo(15.401f, 14.458f)
                curveTo(15.205f, 14.584f, 15.107f, 14.646f, 15.074f, 14.726f)
                curveTo(15.044f, 14.796f, 15.044f, 14.875f, 15.074f, 14.944f)
                curveTo(15.108f, 15.024f, 15.205f, 15.087f, 15.401f, 15.212f)
                lineTo(21.285f, 18.98f)
                close()
            }
        }.build()
        return _cut!!
    }

private var _cut: ImageVector? = null