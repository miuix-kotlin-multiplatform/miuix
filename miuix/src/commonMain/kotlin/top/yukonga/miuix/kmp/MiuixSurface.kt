package top.yukonga.miuix.kmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import top.yukonga.miuix.kmp.ui.MiuixTheme

@Composable
@NonRestartableComposable
fun MiuixSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = MiuixTheme.colorScheme.background,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider {
        Box(
            modifier = modifier
                .background(color = color, shape = shape)
                .pointerInput(Unit) {},
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}
