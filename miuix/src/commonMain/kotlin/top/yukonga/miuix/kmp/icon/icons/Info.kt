package top.yukonga.miuix.kmp.icon.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.icon.MiuixIcons

val MiuixIcons.Info: ImageVector
    get() {
        if (_info != null) return _info!!
        _info = ImageVector.Builder("Info", 26.dp, 26.dp, 26.0f, 26.0f).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = EvenOdd
            ) {
                moveTo(13.0f, 21.5935f)
                curveTo(17.7437f, 21.5935f, 21.5892f, 17.748f, 21.5892f, 13.0043f)
                curveTo(21.5892f, 8.2606f, 17.7437f, 4.4151f, 13.0f, 4.4151f)
                curveTo(8.2563f, 4.4151f, 4.4108f, 8.2606f, 4.4108f, 13.0043f)
                curveTo(4.4108f, 17.748f, 8.2563f, 21.5935f, 13.0f, 21.5935f)
                close()
                moveTo(13.0f, 23.1935f)
                curveTo(18.6273f, 23.1935f, 23.1892f, 18.6316f, 23.1892f, 13.0043f)
                curveTo(23.1892f, 7.377f, 18.6273f, 2.8151f, 13.0f, 2.8151f)
                curveTo(7.3727f, 2.8151f, 2.8108f, 7.377f, 2.8108f, 13.0043f)
                curveTo(2.8108f, 18.6316f, 7.3727f, 23.1935f, 13.0f, 23.1935f)
                close()
            }
            path(
                fill = SolidColor(Color.Black),
                pathFillType = NonZero
            ) {
                moveTo(14.1f, 8.8298f)
                curveTo(14.1f, 9.4373f, 13.6075f, 9.9298f, 13.0f, 9.9298f)
                curveTo(12.3925f, 9.9298f, 11.9f, 9.4373f, 11.9f, 8.8298f)
                curveTo(11.9f, 8.2222f, 12.3925f, 7.7298f, 13.0f, 7.7298f)
                curveTo(13.6075f, 7.7298f, 14.1f, 8.2222f, 14.1f, 8.8298f)
                close()
            }
            path(
                fill = SolidColor(Color.Black),
                pathFillType = NonZero
            ) {
                moveTo(13.0f, 11.2433f)
                curveTo(12.7786f, 11.2433f, 12.6679f, 11.2433f, 12.5788f, 11.274f)
                curveTo(12.4154f, 11.3304f, 12.2871f, 11.4587f, 12.2307f, 11.6221f)
                curveTo(12.2f, 11.7112f, 12.2f, 11.8219f, 12.2f, 12.0433f)
                verticalLineTo(17.4703f)
                curveTo(12.2f, 17.6917f, 12.2f, 17.8024f, 12.2307f, 17.8915f)
                curveTo(12.2871f, 18.0548f, 12.4154f, 18.1832f, 12.5788f, 18.2395f)
                curveTo(12.6679f, 18.2703f, 12.7786f, 18.2703f, 13.0f, 18.2703f)
                curveTo(13.2214f, 18.2703f, 13.3321f, 18.2703f, 13.4212f, 18.2395f)
                curveTo(13.5845f, 18.1832f, 13.7129f, 18.0548f, 13.7692f, 17.8915f)
                curveTo(13.8f, 17.8024f, 13.8f, 17.6917f, 13.8f, 17.4703f)
                lineTo(13.8f, 12.0433f)
                curveTo(13.8f, 11.8219f, 13.8f, 11.7112f, 13.7692f, 11.6221f)
                curveTo(13.7129f, 11.4587f, 13.5845f, 11.3304f, 13.4212f, 11.274f)
                curveTo(13.3321f, 11.2433f, 13.2214f, 11.2433f, 13.0f, 11.2433f)
                close()
            }
        }.build()
        return _info!!
    }

private var _info: ImageVector? = null