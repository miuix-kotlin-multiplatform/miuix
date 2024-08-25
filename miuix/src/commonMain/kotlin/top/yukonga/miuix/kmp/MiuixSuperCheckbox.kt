package top.yukonga.miuix.kmp

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.MiuixBasicComponent
import top.yukonga.miuix.kmp.basic.MiuixCheckbox

@Composable
fun MiuixSuperCheckbox(
    title: String,
    summary: String? = null,
    rightActions: @Composable RowScope.() -> Unit = {},
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    insideMargin: DpSize = DpSize(28.dp, 14.dp),
    enabled: Boolean = true
) {
    var isChecked by remember { mutableStateOf(checked) }
    val interactionSource = remember { MutableInteractionSource() }

    if (isChecked != checked) {
        isChecked = checked
    }

    MiuixBasicComponent(
        modifier = modifier,
        insideMargin = insideMargin,
        title = title,
        summary = summary,
        leftAction = {
            MiuixCheckbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled
            )
        },
        rightActions = rightActions,
        interactionSource = interactionSource,
        onClick = {
            if (enabled) {
                isChecked = !isChecked
                onCheckedChange?.invoke(isChecked)
            }
        }
    )
}