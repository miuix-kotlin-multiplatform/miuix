package top.yukonga.miuix.kmp

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.MiuixSurface
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun MiuixFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(50.dp),
    containerColor: Color = MiuixTheme.colorScheme.primary,
    shadowElevation: Float = 18f,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    MiuixSurface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        shape = shape,
        color = containerColor,
        shadowElevation = shadowElevation,
        interactionSource = interactionSource
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