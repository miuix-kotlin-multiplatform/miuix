package top.yukonga.miuix.kmp.icon.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.icon.MiuixIcons

val MiuixIcons.ImmersionMore: ImageVector
    get() {
        if (_immersionMore != null) return _immersionMore!!
        _immersionMore = ImageVector.Builder("ImmersionMore", 26.dp, 26.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = NonZero
            ) {
                moveTo(12.9999f, 14.4054f)
                curveTo(12.2237f, 14.4054f, 11.5945f, 13.7762f, 11.5945f, 13.0f)
                curveTo(11.5945f, 12.2238f, 12.2237f, 11.5946f, 12.9999f, 11.5946f)
                curveTo(13.7761f, 11.5946f, 14.4053f, 12.2238f, 14.4053f, 13.0f)
                curveTo(14.4053f, 13.7762f, 13.7761f, 14.4054f, 12.9999f, 14.4054f)
                close()
            }
            path(
                fill = SolidColor(Color.Black),
                pathFillType = NonZero
            ) {
                moveTo(12.9999f, 21.4324f)
                curveTo(12.2237f, 21.4324f, 11.5945f, 20.8032f, 11.5945f, 20.027f)
                curveTo(11.5945f, 19.2508f, 12.2237f, 18.6216f, 12.9999f, 18.6216f)
                curveTo(13.7761f, 18.6216f, 14.4053f, 19.2508f, 14.4053f, 20.027f)
                curveTo(14.4053f, 20.8032f, 13.7761f, 21.4324f, 12.9999f, 21.4324f)
                close()
            }
            path(
                fill = SolidColor(Color.Black),
                pathFillType = NonZero
            ) {
                moveTo(12.9999f, 7.3784f)
                curveTo(12.2237f, 7.3784f, 11.5945f, 6.7492f, 11.5945f, 5.973f)
                curveTo(11.5945f, 5.1968f, 12.2237f, 4.5676f, 12.9999f, 4.5676f)
                curveTo(13.7761f, 4.5676f, 14.4053f, 5.1968f, 14.4053f, 5.973f)
                curveTo(14.4053f, 6.7492f, 13.7761f, 7.3784f, 12.9999f, 7.3784f)
                close()
            }
        }.build()
        return _immersionMore!!
    }

private var _immersionMore: ImageVector? = null