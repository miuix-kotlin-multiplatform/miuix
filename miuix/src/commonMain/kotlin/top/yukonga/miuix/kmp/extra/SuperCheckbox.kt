// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
    val updatedOnCheckedChange by rememberUpdatedState(onCheckedChange)
    val updatedOnClick by rememberUpdatedState(onClick)

    val rememberedOnClick: (() -> Unit)? =
        remember(enabled, onCheckedChange, checked, updatedOnClick, updatedOnCheckedChange) {
            if (enabled && onCheckedChange != null) {
                {
                    updatedOnClick?.invoke()
                    updatedOnCheckedChange?.invoke(!checked)
                }
            } else {
                null
            }
        }

    val rememberedLeftAction: (@Composable () -> Unit)? =
        remember(checkboxLocation, insideMargin, checked, updatedOnCheckedChange, enabled, checkboxColors) {
            if (checkboxLocation == CheckboxLocation.Left) {
                @Composable {
                    val leftCheckboxModifier = remember(insideMargin) {
                        Modifier.padding(end = insideMargin.calculateLeftPadding(LayoutDirection.Ltr))
                    }
                    Checkbox(
                        modifier = leftCheckboxModifier,
                        checked = checked,
                        onCheckedChange = updatedOnCheckedChange,
                        enabled = enabled,
                        colors = checkboxColors
                    )
                }
            } else {
                null
            }
        }

    val rememberedRightActions: @Composable RowScope.() -> Unit =
        remember(rightActions, checkboxLocation, checked, updatedOnCheckedChange, enabled, checkboxColors) {
            @Composable {
                rightActions()
                if (checkboxLocation == CheckboxLocation.Right) {
                    Checkbox(
                        modifier = Modifier,
                        checked = checked,
                        onCheckedChange = updatedOnCheckedChange,
                        enabled = enabled,
                        colors = checkboxColors
                    )
                }
            }
        }

    BasicComponent(
        modifier = modifier,
        insideMargin = insideMargin,
        title = title,
        titleColor = titleColor,
        summary = summary,
        summaryColor = summaryColor,
        leftAction = rememberedLeftAction,
        rightActions = rememberedRightActions,
        onClick = rememberedOnClick,
        enabled = enabled
    )
}

enum class CheckboxLocation {
    Left,
    Right,
}