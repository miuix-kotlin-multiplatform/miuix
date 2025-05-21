// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.icon.icons.useful

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.icon.MiuixIcons

val MiuixIcons.Useful.Cancel: ImageVector
    get() {
        if (_cancel != null) return _cancel!!
        _cancel = ImageVector.Builder("Cancel", 26.0.dp, 26.0.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(5.314f, 20.686f)
                curveTo(5.471f, 20.843f, 5.549f, 20.921f, 5.634f, 20.962f)
                curveTo(5.789f, 21.038f, 5.97f, 21.038f, 6.126f, 20.962f)
                curveTo(6.211f, 20.921f, 6.289f, 20.843f, 6.445f, 20.686f)
                lineTo(13.0f, 14.131f)
                lineTo(19.545f, 20.677f)
                curveTo(19.702f, 20.833f, 19.78f, 20.912f, 19.865f, 20.953f)
                curveTo(20.02f, 21.028f, 20.202f, 21.028f, 20.357f, 20.953f)
                curveTo(20.442f, 20.912f, 20.52f, 20.833f, 20.677f, 20.677f)
                curveTo(20.833f, 20.52f, 20.912f, 20.442f, 20.953f, 20.357f)
                curveTo(21.028f, 20.202f, 21.028f, 20.02f, 20.953f, 19.865f)
                curveTo(20.912f, 19.78f, 20.833f, 19.702f, 20.677f, 19.545f)
                lineTo(14.131f, 13.0f)
                lineTo(20.686f, 6.445f)
                curveTo(20.843f, 6.289f, 20.921f, 6.211f, 20.962f, 6.126f)
                curveTo(21.038f, 5.97f, 21.038f, 5.789f, 20.962f, 5.634f)
                curveTo(20.921f, 5.549f, 20.843f, 5.471f, 20.686f, 5.314f)
                curveTo(20.529f, 5.157f, 20.451f, 5.079f, 20.366f, 5.038f)
                curveTo(20.211f, 4.962f, 20.03f, 4.962f, 19.874f, 5.038f)
                curveTo(19.789f, 5.079f, 19.711f, 5.157f, 19.555f, 5.314f)
                lineTo(13.0f, 11.869f)
                lineTo(6.455f, 5.323f)
                curveTo(6.298f, 5.167f, 6.22f, 5.089f, 6.135f, 5.047f)
                curveTo(5.98f, 4.972f, 5.798f, 4.972f, 5.643f, 5.047f)
                curveTo(5.558f, 5.089f, 5.48f, 5.167f, 5.323f, 5.323f)
                curveTo(5.167f, 5.48f, 5.089f, 5.558f, 5.047f, 5.643f)
                curveTo(4.972f, 5.798f, 4.972f, 5.98f, 5.047f, 6.135f)
                curveTo(5.089f, 6.22f, 5.167f, 6.298f, 5.323f, 6.455f)
                lineTo(11.869f, 13.0f)
                lineTo(5.314f, 19.555f)
                curveTo(5.157f, 19.711f, 5.079f, 19.789f, 5.038f, 19.874f)
                curveTo(4.962f, 20.03f, 4.962f, 20.211f, 5.038f, 20.366f)
                curveTo(5.079f, 20.451f, 5.157f, 20.529f, 5.314f, 20.686f)
                close()
            }
        }.build()
        return _cancel!!
    }

private var _cancel: ImageVector? = null