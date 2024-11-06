package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.DpSize
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.BasicComponentColors
import top.yukonga.miuix.kmp.basic.BasicComponentDefaults
import top.yukonga.miuix.kmp.basic.Switch
import top.yukonga.miuix.kmp.basic.SwitchColors
import top.yukonga.miuix.kmp.basic.SwitchDefaults

/**
 * A switch with a title and a summary.
 *
 * @param title The title of the [SuperSwitch].
 * @param checked The checked state of the [SuperSwitch].
 * @param onCheckedChange The callback when the checked state of the [SuperSwitch] is changed.
 * @param modifier The modifier to be applied to the [SuperSwitch].
 * @param titleColor The color of the title.
 * @param summary The summary of the [SuperSwitch].
 * @param summaryColor The color of the summary.
 * @param switchColors The [SwitchColors] of the [SuperSwitch].
 * @param leftAction The [Composable] content that on the left side of the [SuperSwitch].
 * @param rightActions The [Composable] content on the right side of the [SuperSwitch].
 * @param insideMargin The margin inside the [SuperSwitch].
 * @param enabled Whether the [SuperSwitch] is clickable.
 */
@Composable
fun SuperSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    titleColor: BasicComponentColors = BasicComponentDefaults.titleColor(),
    summary: String? = null,
    summaryColor: BasicComponentColors = BasicComponentDefaults.summaryColor(),
    switchColors: SwitchColors = SwitchDefaults.switchColors(),
    leftAction: @Composable (() -> Unit)? = null,
    rightActions: @Composable RowScope.() -> Unit = {},
    insideMargin: DpSize = BasicComponentDefaults.InsideMargin,
    enabled: Boolean = true
) {
    var isChecked by remember { mutableStateOf(checked) }
    val updatedOnCheckedChange by rememberUpdatedState(onCheckedChange)
    val localHapticFeedback = LocalHapticFeedback.current

    if (isChecked != checked) isChecked = checked

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
                enabled = enabled,
                colors = switchColors
            )
        },
        onClick = {
            if (enabled) {
                isChecked = !isChecked
                updatedOnCheckedChange?.invoke(isChecked)
                localHapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            }
        },
        enabled = enabled
    )
}