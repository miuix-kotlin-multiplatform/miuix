// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.BasicComponentColors
import top.yukonga.miuix.kmp.basic.BasicComponentDefaults
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.basic.ArrowRight
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * A arrow with a title and a summary.
 *
 * @param title The title of the [SuperArrow].
 * @param titleColor The color of the title.
 * @param summary The summary of the [SuperArrow].
 * @param summaryColor The color of the summary.
 * @param leftAction The [Composable] content that on the left side of the [SuperArrow].
 * @param rightText The text on the right side of the [SuperArrow].
 * @param rightActionColor The color of the right action.
 * @param modifier The modifier to be applied to the [SuperArrow].
 * @param insideMargin The margin inside the [SuperArrow].
 * @param onClick The callback when the [SuperArrow] is clicked.
 * @param holdDownState Used to determine whether it is in the pressed state.
 * @param enabled Whether the [SuperArrow] is clickable.
 */
@Composable
fun SuperArrow(
    title: String,
    titleColor: BasicComponentColors = BasicComponentDefaults.titleColor(),
    summary: String? = null,
    summaryColor: BasicComponentColors = BasicComponentDefaults.summaryColor(),
    leftAction: @Composable (() -> Unit)? = null,
    rightText: String? = null,
    rightActionColor: RightActionColors = SuperArrowDefaults.rightActionColors(),
    modifier: Modifier = Modifier,
    insideMargin: PaddingValues = BasicComponentDefaults.InsideMargin,
    onClick: (() -> Unit)? = null,
    holdDownState: Boolean = false,
    enabled: Boolean = true
) {
    val rightActionsContent: @Composable (RowScope.() -> Unit) =
        remember(rightText, rightActionColor, enabled) {
            {
                SuperArrowRightActions(
                    rightText = rightText,
                    tintColor = rightActionColor.color(enabled)
                )
            }
        }

    BasicComponent(
        modifier = modifier,
        insideMargin = insideMargin,
        title = title,
        titleColor = titleColor,
        summary = summary,
        summaryColor = summaryColor,
        leftAction = leftAction,
        rightActions = rightActionsContent,
        onClick = onClick?.takeIf { enabled },
        holdDownState = holdDownState,
        enabled = enabled
    )
}

@Composable
private fun SuperArrowRightActions(
    rightText: String?,
    tintColor: Color
) {
    val tintFilter = remember(tintColor) { ColorFilter.tint(tintColor) }

    if (rightText != null) {
        Text(
            modifier = Modifier.widthIn(max = 130.dp),
            text = rightText,
            fontSize = MiuixTheme.textStyles.body2.fontSize,
            color = tintColor,
            textAlign = TextAlign.End,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
    Image(
        modifier = Modifier
            .padding(start = 8.dp)
            .size(width = 10.dp, height = 16.dp),
        imageVector = MiuixIcons.Basic.ArrowRight,
        contentDescription = null,
        colorFilter = tintFilter,
    )
}

object SuperArrowDefaults {
    /**
     * The default color of the arrow.
     */
    @Composable
    fun rightActionColors() = RightActionColors(
        color = MiuixTheme.colorScheme.onSurfaceVariantActions,
        disabledColor = MiuixTheme.colorScheme.disabledOnSecondaryVariant
    )
}


@Immutable
class RightActionColors(
    private val color: Color,
    private val disabledColor: Color
) {
    @Stable
    internal fun color(enabled: Boolean): Color = if (enabled) color else disabledColor
}
