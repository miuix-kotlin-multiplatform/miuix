package top.yukonga.miuix.kmp

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.MiuixBasicComponent
import top.yukonga.miuix.kmp.basic.MiuixSwitch

@Composable
fun MiuixSuperSwitch(
    title: String,
    summary: String? = null,
    leftAction: @Composable (() -> Unit)? = null,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    insideMargin: DpSize = DpSize(28.dp, 7.dp),
    enabledClick: Boolean = true
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
        leftAction = leftAction,
        rightActions = { createRightActions(checked, onCheckedChange, enabledClick) },
        enabledClick = enabledClick,
        interactionSource = interactionSource,
        onClick = {
            isChecked = !isChecked
            onCheckedChange?.invoke(isChecked)
        }
    )
}

@Composable
private fun createRightActions(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    enabled: Boolean
) {
    MiuixSwitch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled
    )
}