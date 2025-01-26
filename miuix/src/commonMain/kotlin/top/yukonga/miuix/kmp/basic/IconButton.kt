package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape

/**
 * A [IconButton] component with Miuix style.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * @param onClick The callback when the [IconButton] is clicked.
 * @param modifier The modifier to be applied to the [IconButton]
 * @param enabled Whether the [IconButton] is enabled.
 * @param backgroundColor The background color of of the [IconButton].
 * @param cornerRadius The corner radius of of the [IconButton].
 * @param minHeight The minimum height of of the [IconButton].
 * @param minWidth The minimum width of the [IconButton].
 * @param content The content of this icon button, typically an [Icon].
 */
@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = Color.Unspecified,
    cornerRadius: Dp = IconButtonDefaults.ConorRadius,
    minHeight: Dp = IconButtonDefaults.MinHeight,
    minWidth: Dp = IconButtonDefaults.MinWidth,
    content: @Composable () -> Unit
) {
    val shape = remember { derivedStateOf { SmoothRoundedCornerShape(cornerRadius) } }
    Box(
        modifier = modifier
            .defaultMinSize(minWidth = minWidth, minHeight = minHeight)
            .clip(shape.value)
            .background(color = backgroundColor)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

object IconButtonDefaults {

    /**
     * The default minimum width of the [IconButton].
     */
    val MinWidth = 40.dp

    /**
     * The default minimum height of the [IconButton].
     */
    val MinHeight = 40.dp

    /**
     * The default corner radius of the [IconButton].
     */
    val ConorRadius = 40.dp
}
