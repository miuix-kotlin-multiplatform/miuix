package top.yukonga.miuix.kmp.icon.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.icon.MiuixIcons

val MiuixIcons.More: ImageVector
    get() {
        if (_more != null) return _more!!
        _more = ImageVector.Builder("More", 26.dp, 26.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = EvenOdd
            ) {
                moveTo(6.9265f, 19.0778f)
                curveTo(10.2808f, 22.4321f, 15.7192f, 22.4321f, 19.0735f, 19.0778f)
                curveTo(22.4277f, 15.7235f, 22.4277f, 10.2851f, 19.0735f, 6.9308f)
                curveTo(15.7192f, 3.5766f, 10.2808f, 3.5766f, 6.9265f, 6.9308f)
                curveTo(3.5722f, 10.2851f, 3.5722f, 15.7235f, 6.9265f, 19.0778f)
                close()
                moveTo(5.7951f, 20.2092f)
                curveTo(9.7743f, 24.1883f, 16.2257f, 24.1883f, 20.2048f, 20.2092f)
                curveTo(24.184f, 16.23f, 24.184f, 9.7786f, 20.2048f, 5.7995f)
                curveTo(16.2257f, 1.8203f, 9.7743f, 1.8203f, 5.7951f, 5.7995f)
                curveTo(1.816f, 9.7786f, 1.816f, 16.23f, 5.7951f, 20.2092f)
                close()
            }
            path(
                fill = SolidColor(Color.Black),
                pathFillType = NonZero
            ) {
                moveTo(14.0999f, 13.0f)
                curveTo(14.0999f, 13.6075f, 13.6074f, 14.1f, 12.9999f, 14.1f)
                curveTo(12.3924f, 14.1f, 11.8999f, 13.6075f, 11.8999f, 13.0f)
                curveTo(11.8999f, 12.3925f, 12.3924f, 11.9f, 12.9999f, 11.9f)
                curveTo(13.6074f, 11.9f, 14.0999f, 12.3925f, 14.0999f, 13.0f)
                close()
            }
            path(
                fill = SolidColor(Color.Black),
                pathFillType = NonZero
            ) {
                moveTo(18.3162f, 13.0f)
                curveTo(18.3162f, 13.6075f, 17.8237f, 14.1f, 17.2162f, 14.1f)
                curveTo(16.6087f, 14.1f, 16.1162f, 13.6075f, 16.1162f, 13.0f)
                curveTo(16.1162f, 12.3925f, 16.6087f, 11.9f, 17.2162f, 11.9f)
                curveTo(17.8237f, 11.9f, 18.3162f, 12.3925f, 18.3162f, 13.0f)
                close()
            }
            path(
                fill = SolidColor(Color.Black),
                pathFillType = NonZero
            ) {
                moveTo(9.8837f, 13.0f)
                curveTo(9.8837f, 13.6075f, 9.3912f, 14.1f, 8.7837f, 14.1f)
                curveTo(8.1762f, 14.1f, 7.6837f, 13.6075f, 7.6837f, 13.0f)
                curveTo(7.6837f, 12.3925f, 8.1762f, 11.9f, 8.7837f, 11.9f)
                curveTo(9.3912f, 11.9f, 9.8837f, 12.3925f, 9.8837f, 13.0f)
                close()
            }
        }.build()
        return _more!!
    }

private var _more: ImageVector? = null