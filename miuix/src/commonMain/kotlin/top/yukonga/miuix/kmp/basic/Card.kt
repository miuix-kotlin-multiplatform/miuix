package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape

/**
 * A [Card] component with Miuix style.
 * Card contain contain content and actions that relate information about a subject.
 *
 * This [Card] does not handle input events
 *
 * @param modifier The modifier to be applied to the [Card].
 * @param cornerRadius The corner radius of the [Card].
 * @param insideMargin The margin inside the [Card].
 * @param color The color of the [Card].
 * @param content The [Composable] content of the [Card].
 */
@Composable
fun Card(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = CardDefaults.ConorRadius,
    insideMargin: DpSize = CardDefaults.InsideMargin,
    color: Color = CardDefaults.DefaultColor(),
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = remember { SmoothRoundedCornerShape(cornerRadius) }
    val paddingModifier = remember(insideMargin) {
        Modifier.padding(vertical = insideMargin.height, horizontal = insideMargin.width)
    }

    Surface(
        modifier = modifier,
        shape = shape,
        color = color,
    ) {
        Column(
            modifier = paddingModifier,
            content = content
        )
    }
}

object CardDefaults {

    /**
     * The default corner radius of the [Card].
     */
    val ConorRadius = 16.dp

    /**
     * The default margin inside the [Card].
     */
    val InsideMargin = DpSize(0.dp, 0.dp)

    /**
     * The default color width of the [Card].
     */
    @Composable
    fun DefaultColor() = MiuixTheme.colorScheme.surface
}