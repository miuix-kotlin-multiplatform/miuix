package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
@NonRestartableComposable
fun MiuixSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = MiuixTheme.colorScheme.background,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider {
        MiuixBox(
            modifier = modifier
                .background(color = color, shape = shape)
                .border(
                    border = border ?: BorderStroke(0.dp, Color.Transparent),
                    shape = shape
                ),
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}

@Composable
@NonRestartableComposable
fun MiuixSurface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = MiuixTheme.colorScheme.background,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val ripple = ripple(color = MiuixTheme.colorScheme.onBackground.copy(alpha = 0.8f))
    CompositionLocalProvider {
        MiuixBox(
            modifier = modifier
                .minimumInteractiveComponentSize()
                .background(
                    shape = shape,
                    color = color
                )
                .border(
                    border = border ?: BorderStroke(0.dp, Color.Transparent),
                    shape = shape
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = ripple,
                    enabled = enabled,
                    onClick = onClick
                ),
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}