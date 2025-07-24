// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.BasicComponentColors
import top.yukonga.miuix.kmp.basic.BasicComponentDefaults
import top.yukonga.miuix.kmp.basic.Checkbox
import top.yukonga.miuix.kmp.basic.CheckboxColors
import top.yukonga.miuix.kmp.basic.CheckboxDefaults

/**
 * A checkbox with a title and a summary.
 *
 * @param title The title of the [SuperCheckbox].
 * @param checked The checked state of the [SuperCheckbox].
 * @param onCheckedChange The callback when the checked state of the [SuperCheckbox] is changed.
 * @param modifier The modifier to be applied to the [SuperCheckbox].
 * @param titleColor The color of the title.
 * @param summary The summary of the [SuperCheckbox].
 * @param summaryColor The color of the summary.
 * @param checkboxColors The [CheckboxColors] of the [SuperCheckbox].
 * @param rightActions The [Composable] content that on the right side of the [SuperCheckbox].
 * @param checkboxLocation The location of checkbox, [CheckboxLocation.Left] or [CheckboxLocation.Right].
 * @param onClick Optional callback when the component is clicked before checkbox is toggled.
 * @param insideMargin The margin inside the [SuperCheckbox].
 * @param enabled Whether the [SuperCheckbox] is clickable.
 */
@Composable
fun SuperCheckbox(
    title: String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    titleColor: BasicComponentColors = BasicComponentDefaults.titleColor(),
    summary: String? = null,
    summaryColor: BasicComponentColors = BasicComponentDefaults.summaryColor(),
    checkboxColors: CheckboxColors = CheckboxDefaults.checkboxColors(),
    rightActions: @Composable RowScope.() -> Unit = {},
    checkboxLocation: CheckboxLocation = CheckboxLocation.Left,
    onClick: (() -> Unit)? = null,
    insideMargin: PaddingValues = BasicComponentDefaults.InsideMargin,
    enabled: Boolean = true
) {
    val handleClick: (() -> Unit)? = if (enabled && onCheckedChange != null) {
        {
            onClick?.invoke()
            onCheckedChange.invoke(!checked)
        }
    } else null

    BasicComponent(
        modifier = modifier,
        insideMargin = insideMargin,
        title = title,
        titleColor = titleColor,
        summary = summary,
        summaryColor = summaryColor,
        leftAction = if (checkboxLocation == CheckboxLocation.Left) {
            {
                SuperCheckboxLeftAction(
                    checked = checked,
                    onCheckedChange = onCheckedChange,
                    enabled = enabled,
                    checkboxColors = checkboxColors,
                    insideMargin = insideMargin
                )
            }
        } else null,
        rightActions = {
            SuperCheckboxRightActions(
                rightActions = rightActions,
                checkboxLocation = checkboxLocation,
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
                checkboxColors = checkboxColors
            )
        },
        onClick = handleClick,
        enabled = enabled
    )
}

@Composable
private fun SuperCheckboxLeftAction(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    enabled: Boolean,
    checkboxColors: CheckboxColors,
    insideMargin: PaddingValues
) {
    Checkbox(
        modifier = Modifier.padding(end = insideMargin.calculateLeftPadding(LayoutDirection.Ltr)),
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        colors = checkboxColors
    )
}

@Composable
private fun RowScope.SuperCheckboxRightActions(
    rightActions: @Composable RowScope.() -> Unit,
    checkboxLocation: CheckboxLocation,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    enabled: Boolean,
    checkboxColors: CheckboxColors
) {
    rightActions()
    if (checkboxLocation == CheckboxLocation.Right) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = checkboxColors
        )
    }
}

enum class CheckboxLocation {
    Left,
    Right,
}