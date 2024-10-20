package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * A surface component with Miuix style.
 *
 * @param modifier The modifier to be applied to the [Surface].
 * @param shape The shape of the [Surface].
 * @param color The color of the [Surface].
 * @param border The border of the [Surface].
 * @param shadowElevation The shadow elevation of the [Surface].
 * @param content The [Composable] content of the [Surface].
 */
@Composable
@NonRestartableComposable
fun Surface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = MiuixTheme.colorScheme.background,
    border: BorderStroke? = null,
    shadowElevation: Float = 0f,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .surface(
                shape = shape,
                backgroundColor = color,
                border = border,
                shadowElevation = shadowElevation
            ),
        propagateMinConstraints = true
    ) {
        content()
    }
}

/**
 * A surface component with Miuix style.
 *
 * @param modifier The modifier to be applied to the [Surface].
 * @param onClick The callback when the [Surface] is clicked.
 * @param enabled Whether the [Surface] is enabled.
 * @param shape The shape of the [Surface].
 * @param color The color of the [Surface].
 * @param border The border of the [Surface].
 * @param shadowElevation The shadow elevation of the [Surface].
 * @param content The [Composable] content of the [Surface].
 */
@Composable
@NonRestartableComposable
fun Surface(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = MiuixTheme.colorScheme.background,
    border: BorderStroke? = null,
    shadowElevation: Float = 0f,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .surface(
                shape = shape,
                backgroundColor = color,
                border = border,
                shadowElevation = shadowElevation
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        propagateMinConstraints = true
    ) {
        content()
    }
}

@Stable
private fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
    shadowElevation: Float,
) =
    this.then(
        if (shadowElevation > 0f) {
            Modifier.graphicsLayer(
                shadowElevation = shadowElevation,
                shape = shape,
                clip = false
            )
        } else {
            Modifier
        }
    )
        .then(if (border != null) Modifier.border(border, shape) else Modifier)
        .background(color = backgroundColor, shape = shape)
        .clip(shape)