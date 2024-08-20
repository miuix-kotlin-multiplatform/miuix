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

/**
 * A switch with a title and a summary.
 *
 * @param title The title of the [MiuixSuperSwitch].
 * @param summary The summary of the [MiuixSuperSwitch].
 * @param leftAction The [Composable] content that on the left side of the [MiuixSuperSwitch].
 * @param checked The checked state of the [MiuixSuperSwitch].
 * @param onCheckedChange The callback when the checked state of the [MiuixSuperSwitch] is changed.
 * @param modifier The modifier to be applied to the [MiuixSuperSwitch].
 * @param insideMargin The margin inside the [MiuixSuperSwitch].
 * @param enabled Whether the [MiuixSuperSwitch] is clickable.
 */
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
        modifier = modifier,
        insideMargin = insideMargin,
        title = title,
        summary = summary,
        leftAction = leftAction,
        rightActions = {
            MiuixSwitch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled
            )
        },
        interactionSource = interactionSource,
        onClick = {
            if (enabled) {
                isChecked = !isChecked
                onCheckedChange?.invoke(isChecked)
            }
        }
    )
}