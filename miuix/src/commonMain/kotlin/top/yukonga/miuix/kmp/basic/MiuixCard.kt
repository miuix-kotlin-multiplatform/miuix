package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.squircleshape.SquircleShape

/**
 *
 * A card component with Miuix style.
 * Card contain contain content and actions that relate information about a subject.
 *
 * This [MiuixCard] does not handle input events
 *
 * @param modifier The modifier to be applied to the [MiuixCard].
 * @param isSecondary Whether the [MiuixCard] is secondary.
 * @param insideMargin The margin inside the [MiuixCard].
 * @param cornerRadius The corner radius of the [MiuixCard].
 * @param content The [Composable] content of the [MiuixCard].
 */
@Composable
fun MiuixCard(
    modifier: Modifier = Modifier,
    isSecondary: Boolean = false,
    insideMargin: DpSize = DpSize(20.dp, 20.dp),
    cornerRadius: Dp = 18.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val color = if (isSecondary) MiuixTheme.colorScheme.secondaryContainer else MiuixTheme.colorScheme.primaryContainer
    MiuixSurface(
        modifier = modifier,
        shape = SquircleShape(cornerRadius),
        color = color,
    ) {
        Column(
            modifier = Modifier.padding(vertical = insideMargin.height, horizontal = insideMargin.width),
            content = content
        )
    }
}