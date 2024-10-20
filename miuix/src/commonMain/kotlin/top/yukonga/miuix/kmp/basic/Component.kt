package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * A basic component with Miuix style. Widely used in other extension components.
 *
 * @param modifier The modifier to be applied to the [BasicComponent].
 * @param insideMargin The margin inside the [BasicComponent].
 * @param title The title of the [BasicComponent].
 * @param titleColor The color of the title.
 * @param summary The summary of the [BasicComponent].
 * @param summaryColor The color of the summary.
 * @param leftAction The [Composable] content that on the left side of the [BasicComponent].
 * @param rightActions The [Composable] content on the right side of the [BasicComponent].
 * @param onClick The callback when the [BasicComponent] is clicked.
 * @param enabled Whether the [BasicComponent] is enabled.
 * @param interactionSource The [MutableInteractionSource] for the [BasicComponent].
 */
@Composable
@Suppress("NAME_SHADOWING")
fun BasicComponent(
    modifier: Modifier = Modifier,
    insideMargin: DpSize? = null,
    title: String? = null,
    titleColor: Color = MiuixTheme.colorScheme.onSurface,
    summary: String? = null,
    summaryColor: Color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
    leftAction: @Composable (() -> Unit?)? = null,
    rightActions: @Composable RowScope.() -> Unit = {},
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    var pointerPressed by remember { mutableStateOf(false) }
    val insideMargin = remember { insideMargin } ?: remember { DpSize(16.dp, 16.dp) }
    val paddingModifier = remember(insideMargin) {
        Modifier.padding(horizontal = insideMargin.width, vertical = insideMargin.height)
    }
    val titleColor = if (enabled) titleColor else MiuixTheme.colorScheme.disabledOnSecondaryVariant
    val summaryColor = if (enabled) summaryColor else MiuixTheme.colorScheme.disabledOnSecondaryVariant
    Row(
        modifier = if (onClick != null && enabled) {
            modifier
                .clickable(
                    indication = LocalIndication.current,
                    interactionSource = interactionSource
                ) {
                    onClick.invoke()
                }
        } else {
            modifier
        }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (enabled) {
                        val event = awaitPointerEvent()
                        pointerPressed = event.type == PointerEventType.Press
                    }
                }
            }
            .fillMaxWidth()
            .then(paddingModifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        leftAction?.let {
            Box(
                modifier = Modifier.padding(end = 16.dp)
            ) {
                it()
            }
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            title?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Medium,
                    color = titleColor
                )
            }
            summary?.let {
                Text(
                    text = it,
                    fontSize = MiuixTheme.textStyles.title.fontSize,
                    color = summaryColor
                )
            }
        }
        Box(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = rightActions
            )
        }
    }
}