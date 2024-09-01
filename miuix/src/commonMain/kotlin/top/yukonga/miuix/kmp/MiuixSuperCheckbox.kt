package top.yukonga.miuix.kmp

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
import top.yukonga.miuix.kmp.basic.MiuixBasicComponent
import top.yukonga.miuix.kmp.basic.MiuixCheckbox

/**
 * A checkbox with a title and a summary.
 *
 * @param title The title of the [MiuixSuperCheckbox].
 * @param summary The summary of the [MiuixSuperCheckbox].
 * @param rightActions The [Composable] content that on the right side of the [MiuixSuperCheckbox].
 * @param checked The checked state of the [MiuixSuperCheckbox].
 * @param onCheckedChange The callback when the checked state of the [MiuixSuperCheckbox] is changed.
 * @param modifier The modifier to be applied to the [MiuixSuperCheckbox].
 * @param insideMargin The margin inside the [MiuixSuperCheckbox].
 * @param enabled Whether the [MiuixSuperCheckbox] is clickable.
 */
@Composable
fun MiuixSuperCheckbox(
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

    MiuixBasicComponent(
        modifier = modifier,
        insideMargin = insideMargin,
        title = title,
        summary = summary,
        leftAction = if (checkboxLocation == CheckboxLocation.Left) {
            {
                MiuixCheckbox(
                    checked = isChecked,
                    onCheckedChange = updatedOnCheckedChange,
                    enabled = enabled
                )
            }
        } else null,
        rightActions = {
            rightActions()
            if (checkboxLocation == CheckboxLocation.Right) {
                MiuixCheckbox(
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