package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape

/**
 * A [FloatingActionButton] component with Miuix style.
 *
 * @param modifier The modifier to be applied to the [FloatingActionButton].
 * @param onClick The callback when the [FloatingActionButton] is clicked.
 * @param shape The shape of the [FloatingActionButton].
 * @param containerColor The color of the [FloatingActionButton].
 * @param shadowElevation The shadow elevation of the [FloatingActionButton].
 * @param content The [Composable] content of the [FloatingActionButton].
 */
@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(60.dp),
    containerColor: Color = MiuixTheme.colorScheme.primary,
    shadowElevation: Float = 18f,
    content: @Composable () -> Unit,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        shape = shape,
        color = containerColor,
        shadowElevation = shadowElevation
    ) {
        Box(
            modifier =
            Modifier.defaultMinSize(
                minWidth = 60.dp,
                minHeight = 60.dp,
            ),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}