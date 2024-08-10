package top.yukonga.miuix.kmp

import androidx.compose.foundation.clickable
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
import top.yukonga.miuix.kmp.utils.createRipple

@Composable
fun MiuixSuperSwitch(
    title: String,
    summary: String? = null,
    leftAction: @Composable (() -> Unit)? = null,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    insideMargin: DpSize = DpSize(28.dp, 7.dp),
    enabled: Boolean = true
) {
    var isChecked by remember { mutableStateOf(checked) }
    val interactionSource = remember { MutableInteractionSource() }

    if (isChecked != checked) {
        isChecked = checked
    }

    MiuixBasicComponent(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = createRipple(),
            enabled = enabled
        ) {
            isChecked = !isChecked
            onCheckedChange?.invoke(isChecked)
        },
        insideMargin = insideMargin,
        title = title,
        summary = summary,
        leftAction = leftAction,
        rightActions = { createRightActions(checked, onCheckedChange, enabled) }
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