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

val MiuixIcons.Useful.Unlike: ImageVector
    get() {
        if (_unlike != null) return _unlike!!
        _unlike = ImageVector.Builder("Unlike", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(13.4026f, 22.7729f)
                lineTo(21.9482f, 13.9362f)
                curveTo(21.9909f, 13.8932f, 22.033f, 13.8497f, 22.0745f, 13.8056f)
                lineTo(22.0955f, 13.7839f)
                curveTo(23.1299f, 12.6721f, 23.762f, 11.1816f, 23.762f, 9.5433f)
                curveTo(23.762f, 6.1045f, 20.9742f, 3.3167f, 17.5354f, 3.3167f)
                curveTo(15.7475f, 3.3167f, 14.1357f, 4.0703f, 13.0001f, 5.277f)
                curveTo(11.8644f, 4.0703f, 10.2526f, 3.3167f, 8.4647f, 3.3167f)
                curveTo(5.0259f, 3.3167f, 2.2382f, 6.1045f, 2.2382f, 9.5433f)
                curveTo(2.2382f, 11.1816f, 2.8709f, 12.6721f, 3.9053f, 13.7839f)
                lineTo(3.9243f, 13.8041f)
                curveTo(3.9666f, 13.8492f, 4.0096f, 13.8937f, 4.0533f, 13.9376f)
                lineTo(12.5975f, 22.7729f)
                curveTo(12.8176f, 23.0005f, 13.1825f, 23.0005f, 13.4026f, 22.7729f)
                close()
                moveTo(5.2318f, 12.8529f)
                curveTo(5.1653f, 12.788f, 5.1008f, 12.7212f, 5.0383f, 12.6525f)
                lineTo(5.0307f, 12.6445f)
                curveTo(4.2905f, 11.8264f, 3.84f, 10.7414f, 3.84f, 9.5512f)
                curveTo(3.84f, 7.004f, 5.9049f, 4.9391f, 8.4521f, 4.9391f)
                curveTo(10.1622f, 4.9391f, 11.6549f, 5.8698f, 12.4513f, 7.2524f)
                curveTo(12.566f, 7.4515f, 12.7723f, 7.5859f, 13.0021f, 7.5859f)
                curveTo(13.2319f, 7.5859f, 13.4381f, 7.4515f, 13.5528f, 7.2524f)
                curveTo(14.3493f, 5.8698f, 15.842f, 4.9391f, 17.5521f, 4.9391f)
                curveTo(20.0993f, 4.9391f, 22.1642f, 7.004f, 22.1642f, 9.5512f)
                curveTo(22.1642f, 10.8727f, 21.6084f, 12.0644f, 20.7178f, 12.9053f)
                lineTo(13.144f, 20.7507f)
                curveTo(13.0653f, 20.8321f, 12.9348f, 20.8321f, 12.8562f, 20.7507f)
                lineTo(5.2318f, 12.8529f)
                close()
            }
        }.build()
        return _unlike!!
    }
private var _unlike: ImageVector? = null