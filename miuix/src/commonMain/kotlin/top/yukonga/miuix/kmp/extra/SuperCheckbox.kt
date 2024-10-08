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
import top.yukonga.miuix.kmp.basic.Checkbox
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * A checkbox with a title and a summary.
 *
 * @param modifier The modifier to be applied to the [SuperCheckbox].
 * @param title The title of the [SuperCheckbox].
 * @param titleColor The color of the title.
 * @param summary The summary of the [SuperCheckbox].
 * @param summaryColor The color of the summary.
 * @param rightActions The [Composable] content that on the right side of the [SuperCheckbox].
 * @param checked The checked state of the [SuperCheckbox].
 * @param onCheckedChange The callback when the checked state of the [SuperCheckbox] is changed.
 * @param insideMargin The margin inside the [SuperCheckbox].
 * @param enabled Whether the [SuperCheckbox] is clickable.
 */
@Composable
fun SuperCheckbox(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = MiuixTheme.colorScheme.onSurface,
    summary: String? = null,
    summaryColor: Color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
    rightActions: @Composable RowScope.() -> Unit = {},
    checked: Boolean,
    checkboxLocation: CheckboxLocation = CheckboxLocation.Left,
    onCheckedChange: ((Boolean) -> Unit)?,
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
        leftAction = if (checkboxLocation == CheckboxLocation.Left) {
            {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = updatedOnCheckedChange,
                    enabled = enabled
                )
            }
        } else null,
        rightActions = {
            rightActions()
            if (checkboxLocation == CheckboxLocation.Right) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = updatedOnCheckedChange,
                    enabled = enabled
                )
            }
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

enum class CheckboxLocation {
    Left,
    Right,
}