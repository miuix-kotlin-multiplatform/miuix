package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.Switch
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * A switch with a title and a summary.
 *
 * @param title The title of the [SuperSwitch].
 * @param titleColor The color of the title.
 * @param summary The summary of the [SuperSwitch].
 * @param summaryColor The color of the summary.
 * @param leftAction The [Composable] content that on the left side of the [SuperSwitch].
 * @param rightActions The [Composable] content on the right side of the [SuperSwitch].
 * @param checked The checked state of the [SuperSwitch].
 * @param onCheckedChange The callback when the checked state of the [SuperSwitch] is changed.
 * @param modifier The modifier to be applied to the [SuperSwitch].
 * @param insideMargin The margin inside the [SuperSwitch].
 * @param enabled Whether the [SuperSwitch] is clickable.
 */
@Composable
fun SuperSwitch(
    title: String,
    titleColor: Color = MiuixTheme.colorScheme.onSurface,
    summary: String? = null,
    summaryColor: Color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
    leftAction: @Composable (() -> Unit)? = null,
    rightActions: @Composable RowScope.() -> Unit = {},
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    insideMargin: DpSize = DpSize(16.dp, 16.dp),
    enabled: Boolean = true
) {
    var isChecked by remember { mutableStateOf(checked) }
    val updatedOnCheckedChange by rememberUpdatedState(onCheckedChange)

    if (isChecked != checked) {
        isChecked = checked
    }

    BasicComponent(
        modifier = modifier,
        insideMargin = insideMargin,
        title = title,
        titleColor = titleColor,
        summary = summary,
        summaryColor = summaryColor,
        leftAction = leftAction,
        rightActions = {
            rightActions()
            Switch(
                checked = isChecked,
                onCheckedChange = updatedOnCheckedChange,
                enabled = enabled
            )
        },
        onClick = {
            if (enabled) {
                isChecked = !isChecked
                updatedOnCheckedChange?.invoke(isChecked)
            }
        },
        enabled = enabled
    )
}