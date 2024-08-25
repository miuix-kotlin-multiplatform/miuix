package top.yukonga.miuix.kmp.icon

import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

val MiuixIcons.ArrowBack: ImageVector
    get() {
        if (_arrowback != null) return _arrowback!!
        _arrowback = ImageVector.Builder("ArrowBack", 100.dp, 100.dp, 100f, 100f).apply {
            materialPath(
                fillAlpha = 0.8f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(22.689f, 48.654f)
                curveTo(22.228f, 49.115f, 21.998f, 49.719f, 22.0f, 50.323f)
                curveTo(21.993f, 50.933f, 22.223f, 51.546f, 22.688f, 52.011f)
                lineTo(39.654f, 68.977f)
                curveTo(40.572f, 69.895f, 42.06f, 69.895f, 42.978f, 68.977f)
                curveTo(43.896f, 68.059f, 43.896f, 66.571f, 42.978f, 65.653f)
                lineTo(30.351f, 53.025f)
                horizontalLineTo(76.526f)
                curveTo(77.907f, 53.025f, 79.026f, 51.906f, 79.026f, 50.525f)
                curveTo(79.026f, 49.145f, 77.907f, 48.025f, 76.526f, 48.025f)
                horizontalLineTo(29.965f)
                lineTo(42.978f, 35.013f)
                curveTo(43.896f, 34.095f, 43.896f, 32.606f, 42.978f, 31.688f)
                curveTo(42.06f, 30.771f, 40.572f, 30.771f, 39.654f, 31.688f)
                lineTo(22.689f, 48.654f)
                close()
            }
        }.build()
        return _arrowback!!
    }

private var _arrowback: ImageVector? = null