package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.createRipple

/**
 * A basic component with Miuix style. Widely used in other extension components.
 *
 * @param modifier The modifier to be applied to the [MiuixBasicComponent].
 * @param insideMargin The margin inside the [MiuixBasicComponent].
 * @param title The title of the [MiuixBasicComponent].
 * @param summary The summary of the [MiuixBasicComponent].
 * @param leftAction The [Composable] content that on the left side of the [MiuixBasicComponent].
 * @param rightActions The [Composable] content on the right side of the [MiuixBasicComponent].
 * @param enabledClick Whether the [MiuixBasicComponent] is clickable.
 * @param onClick The callback when the [MiuixBasicComponent] is clicked.
 * @param interactionSource The interaction source to be applied to the [MiuixBasicComponent].
 * @param indication The indication to be applied to the [MiuixBasicComponent].
 */
@Composable
fun MiuixBasicComponent(
    modifier: Modifier = Modifier,
    insideMargin: DpSize = DpSize(28.dp, 16.dp),
    title: String? = null,
    summary: String? = null,
    leftAction: @Composable (() -> Unit?)? = null,
    rightActions: @Composable RowScope.() -> Unit = {},
    enabledClick: Boolean = true,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
    indication: Indication? = null
) {
    Row(
        modifier = if (enabledClick) {
            modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = indication ?: createRipple()
                ) {
                    onClick?.invoke()
                }
        } else {
            modifier
        }
            .fillMaxWidth()
            .padding(horizontal = insideMargin.width, vertical = insideMargin.height),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        leftAction?.let {
            MiuixBox(
                modifier = Modifier.padding(end = 16.dp)
            ) {
                it()
            }
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            title?.let {
                MiuixText(
                    text = it,
                    fontWeight = FontWeight.Medium
                )
            }
            summary?.let {
                MiuixText(
                    text = it,
                    fontSize = MiuixTheme.textStyles.title.fontSize,
                    color = MiuixTheme.colorScheme.subTextBase
                )
            }
        }
        MiuixBox(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = rightActions
            )
        }
    }
}