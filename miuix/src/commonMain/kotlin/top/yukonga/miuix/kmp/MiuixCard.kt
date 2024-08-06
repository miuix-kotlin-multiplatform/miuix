package top.yukonga.miuix.kmp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.ui.MiuixTheme

/**
 *
 * MiuixCard contain contain content and actions that relate information about a subject.
 *
 * This Card does not handle input events - see the other Card overloads if you want a clickable or
 * selectable Card.
 *
 * @param modifier the [Modifier] to be applied to this card
 * @param shape defines the shape of this card's container, border (when [border] is not null)
 * @param border the border to draw around the container of this card
 */
@Composable
fun MiuixCard(
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = MiuixTheme.colorScheme.primaryContainer,
        border = border,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 13.dp, horizontal = 16.dp),
            content = content
        )
    }
}

/**
 *
 * MiuixCard contain contain content and actions that relate information about a subject.
 *
 * This Card handles click events, calling its [onClick] lambda.
 *
 * @param onClick called when this card is clicked
 * @param modifier the [Modifier] to be applied to this card
 * @param enabled controls the enabled state of this card. When `false`, this component will not
 *   respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param shape defines the shape of this card's container, border (when [border] is not null).
 * @param border the border to draw around the container of this card
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this card. You can use this to change the card's appearance or
 *   preview the card in different states. Note that if `null` is provided, interactions will still
 *   happen internally.
 */
@Composable
fun MiuixCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.shape,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        color = MiuixTheme.colorScheme.primaryContainer,
        border = border,
        interactionSource = interactionSource,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 13.dp, horizontal = 16.dp),
            content = content
        )
    }
}
