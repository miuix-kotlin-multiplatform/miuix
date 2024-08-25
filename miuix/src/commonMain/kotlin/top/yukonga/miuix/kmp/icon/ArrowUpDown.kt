package top.yukonga.miuix.kmp.icon

import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

val MiuixIcons.ArrowUpDown: ImageVector
    get() {
        if (_arrowUpDown != null) return _arrowUpDown!!
        _arrowUpDown = ImageVector.Builder("ArrowUpDown", 13.dp, 21.dp, 26f, 42f).apply {
            materialPath(pathFillType = PathFillType.EvenOdd) {
                moveTo(5.8643f, 12.6223f)
                lineTo(11.5708f, 6.9158f)
                lineTo(12.7234f, 5.7632f)
                lineTo(13.8245f, 6.8643f)
                lineTo(19.531f, 12.5708f)
                lineTo(21.9811f, 15.0208f)
                curveTo(22.7621f, 15.8019f, 24.0284f, 15.8019f, 24.8095f, 15.0208f)
                curveTo(25.5905f, 14.2398f, 25.5905f, 12.9734f, 24.8095f, 12.1924f)
                lineTo(22.3595f, 9.7423f)
                lineTo(16.6529f, 4.0358f)
                lineTo(14.2029f, 1.5858f)
                curveTo(13.6481f, 1.031f, 12.8484f, 0.8703f, 12.1517f, 1.1037f)
                curveTo(11.8001f, 1.1854f, 11.4664f, 1.3633f, 11.1924f, 1.6373f)
                lineTo(8.7424f, 4.0874f)
                lineTo(3.0358f, 9.7939f)
                lineTo(0.5858f, 12.2439f)
                curveTo(-0.1953f, 13.025f, -0.1953f, 14.2913f, 0.5858f, 15.0724f)
                curveTo(1.3668f, 15.8534f, 2.6332f, 15.8534f, 3.4142f, 15.0724f)
                lineTo(5.8643f, 12.6223f)
                moveTo(5.8643f, 29.7496f)
                lineTo(11.5708f, 35.4562f)
                lineTo(12.7234f, 36.6088f)
                lineTo(13.8245f, 35.5077f)
                lineTo(19.531f, 29.8012f)
                lineTo(21.9811f, 27.3511f)
                curveTo(22.7621f, 26.5701f, 24.0284f, 26.5701f, 24.8095f, 27.3511f)
                curveTo(25.5905f, 28.1322f, 25.5905f, 29.3985f, 24.8095f, 30.1796f)
                lineTo(22.3595f, 32.6296f)
                lineTo(16.6529f, 38.3361f)
                lineTo(14.2029f, 40.7862f)
                curveTo(13.6481f, 41.341f, 12.8484f, 41.5017f, 12.1517f, 41.2683f)
                curveTo(11.8001f, 41.1865f, 11.4664f, 41.0086f, 11.1924f, 40.7346f)
                lineTo(8.7424f, 38.2846f)
                lineTo(3.0358f, 32.5781f)
                lineTo(0.5858f, 30.128f)
                curveTo(-0.1953f, 29.347f, -0.1953f, 28.0806f, 0.5858f, 27.2996f)
                curveTo(1.3668f, 26.5185f, 2.6332f, 26.5185f, 3.4142f, 27.2996f)
                lineTo(5.8643f, 29.7496f)
                close()
            }
        }.build()
        return _arrowUpDown!!
    }

private var _arrowUpDown: ImageVector? = null