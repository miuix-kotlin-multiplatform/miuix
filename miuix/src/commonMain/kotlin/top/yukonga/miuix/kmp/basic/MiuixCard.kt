package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 *
 * A card component with Miuix style.
 * Card contain contain content and actions that relate information about a subject.
 *
 * This [MiuixCard] does not handle input events
 *
 * @param modifier The modifier to be applied to the [MiuixCard].
 * @param shape defines the shape of this card's container, border (when [border] is not null).
 * @param insideMargin The margin inside the [MiuixCard].
 * @param border the border to draw around the container of this card.
 */
@Composable
fun MiuixCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    insideMargin: DpSize = DpSize(20.dp, 20.dp),
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    MiuixSurface(
        modifier = modifier,
        shape = shape,
        color = MiuixTheme.colorScheme.primaryContainer,
        border = border,
    ) {
        Column(
            modifier = Modifier.padding(vertical = insideMargin.height, horizontal = insideMargin.width),
            content = content
        )
    }
}