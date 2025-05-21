// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.icon.icons.other

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.icon.MiuixIcons

val MiuixIcons.Other.GitHub: ImageVector
    get() {
        if (_github != null) return _github!!
        _github = ImageVector.Builder("GitHub", 24.dp, 24.dp, 1024f, 1024f).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(512f, 0f)
                curveTo(229.12f, 0f, 0f, 229.12f, 0f, 512f)
                curveTo(0f, 738.56f, 146.56f, 929.92f, 350.08f, 997.76f)
                curveTo(375.68f, 1002.24f, 385.28f, 986.88f, 385.28f, 973.44f)
                curveTo(385.28f, 961.28f, 384.64f, 920.96f, 384.64f, 878.08f)
                curveTo(256f, 901.76f, 222.72f, 846.72f, 212.48f, 817.92f)
                curveTo(206.72f, 803.2f, 181.76f, 757.76f, 160f, 745.6f)
                curveTo(142.08f, 736f, 116.48f, 712.32f, 159.36f, 711.68f)
                curveTo(199.68f, 711.04f, 228.48f, 748.8f, 238.08f, 764.16f)
                curveTo(284.16f, 841.6f, 357.76f, 819.84f, 387.2f, 806.4f)
                curveTo(391.68f, 773.12f, 405.12f, 750.72f, 419.84f, 737.92f)
                curveTo(305.92f, 725.12f, 186.88f, 680.96f, 186.88f, 485.12f)
                curveTo(186.88f, 429.44f, 206.72f, 383.36f, 239.36f, 347.52f)
                curveTo(234.24f, 334.72f, 216.32f, 282.24f, 244.48f, 211.84f)
                curveTo(244.48f, 211.84f, 287.36f, 198.4f, 385.28f, 264.32f)
                curveTo(426.24f, 252.8f, 469.76f, 247.04f, 513.28f, 247.04f)
                curveTo(556.8f, 247.04f, 600.32f, 252.8f, 641.28f, 264.32f)
                curveTo(739.2f, 197.76f, 782.08f, 211.84f, 782.08f, 211.84f)
                curveTo(810.24f, 282.24f, 792.32f, 334.72f, 787.2f, 347.52f)
                curveTo(819.84f, 383.36f, 839.68f, 428.8f, 839.68f, 485.12f)
                curveTo(839.68f, 681.6f, 720f, 725.12f, 606.08f, 737.92f)
                curveTo(624.64f, 753.92f, 640.64f, 784.64f, 640.64f, 832.64f)
                curveTo(640.64f, 901.12f, 640f, 956.16f, 640f, 973.44f)
                curveTo(640f, 986.88f, 649.6f, 1002.88f, 675.2f, 997.76f)
                curveTo(877.44f, 929.92f, 1024f, 737.92f, 1024f, 512f)
                curveTo(1024f, 229.12f, 794.88f, 0f, 512f, 0f)
                close()
            }
        }.build()
        return _github!!
    }

private var _github: ImageVector? = null