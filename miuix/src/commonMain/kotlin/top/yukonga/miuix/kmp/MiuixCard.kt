package top.yukonga.miuix.kmp

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
 * MiuixCard contain contain content and actions that relate information about a subject.
 *
 * This Card does not handle input events - see the other Card overloads if you want a clickable or
 * selectable Card.
 *
 * @param modifier the [Modifier] to be applied to this card
 * @param shape defines the shape of this card's container, border (when [border] is not null)
 * @param insideMargin Card inside margins
 * @param border the border to draw around the container of this card
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