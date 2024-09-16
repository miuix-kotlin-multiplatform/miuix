package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.Checkbox

/**
 * A checkbox with a title and a summary.
 *
 * @param title The title of the [SuperCheckbox].
 * @param summary The summary of the [SuperCheckbox].
 * @param rightActions The [Composable] content that on the right side of the [SuperCheckbox].
 * @param checked The checked state of the [SuperCheckbox].
 * @param onCheckedChange The callback when the checked state of the [SuperCheckbox] is changed.
 * @param modifier The modifier to be applied to the [SuperCheckbox].
 * @param insideMargin The margin inside the [SuperCheckbox].
 * @param enabled Whether the [SuperCheckbox] is clickable.
 */
@Composable
fun SuperCheckbox(
    title: String,
    summary: String? = null,
    rightActions: @Composable RowScope.() -> Unit = {},
    checked: Boolean,
    checkboxLocation: CheckboxLocation = CheckboxLocation.Left,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    insideMargin: DpSize = DpSize(24.dp, 14.dp),
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
        summary = summary,
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
        }
    )
}

enum class CheckboxLocation {
    Left,
    Right,
}