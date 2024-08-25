package top.yukonga.miuix.kmp.icon

import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

val MiuixIcons.ArrowRight: ImageVector
    get() {
        if (_arrowRight != null) return _arrowRight!!
        _arrowRight = ImageVector.Builder("ArrowRight", 26.dp, 42.dp, 26f, 42f).apply {
            materialPath(pathFillType = PathFillType.EvenOdd) {
                moveTo(18.9982f, 20.3635f)
                lineTo(6.5388f, 7.9041f)
                lineTo(4.4899f, 5.8552f)
                curveTo(3.8367f, 5.202f, 3.8367f, 4.143f, 4.4899f, 3.4899f)
                curveTo(5.143f, 2.8367f, 6.202f, 2.8367f, 6.8552f, 3.4899f)
                lineTo(8.9041f, 5.5388f)
                lineTo(21.3635f, 17.9982f)
                lineTo(23.4124f, 20.0471f)
                curveTo(23.7596f, 20.3943f, 23.9223f, 20.8562f, 23.9003f, 21.3109f)
                curveTo(23.9238f, 21.7673f, 23.7612f, 22.2315f, 23.4126f, 22.5801f)
                lineTo(21.3638f, 24.629f)
                lineTo(8.9043f, 37.0884f)
                lineTo(6.8555f, 39.1373f)
                curveTo(6.2023f, 39.7904f, 5.1433f, 39.7904f, 4.4901f, 39.1373f)
                curveTo(3.837f, 38.4841f, 3.837f, 37.4251f, 4.4901f, 36.772f)
                lineTo(6.539f, 34.7231f)
                lineTo(18.9984f, 22.2636f)
                lineTo(19.9484f, 21.3137f)
                lineTo(18.9982f, 20.3635f)
                close()
            }
        }.build()
        return _arrowRight!!
    }

private var _arrowRight: ImageVector? = null