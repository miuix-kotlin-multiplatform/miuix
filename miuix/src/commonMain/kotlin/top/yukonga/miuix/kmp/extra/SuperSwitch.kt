// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.BasicComponentColors
import top.yukonga.miuix.kmp.basic.BasicComponentDefaults
import top.yukonga.miuix.kmp.basic.Switch
import top.yukonga.miuix.kmp.basic.SwitchColors
import top.yukonga.miuix.kmp.basic.SwitchDefaults

/**
 * A switch with a title and a summary.
 *
 * @param checked The checked state of the [SuperSwitch].
 * @param onCheckedChange The callback when the checked state of the [SuperSwitch] is changed.
 * @param title The title of the [SuperSwitch].
 * @param titleColor The color of the title.
 * @param summary The summary of the [SuperSwitch].
 * @param summaryColor The color of the summary.
 * @param leftAction The [Composable] content that on the left side of the [SuperSwitch].
 * @param rightActions The [Composable] content on the right side of the [SuperSwitch].
 * @param switchColors The [SwitchColors] of the [SuperSwitch].
 * @param modifier The modifier to be applied to the [SuperSwitch].
 * @param insideMargin The margin inside the [SuperSwitch].
 * @param onClick The callback when the [SuperSwitch] is clicked.
 * @param holdDownState Used to determine whether it is in the pressed state.
 * @param enabled Whether the [SuperSwitch] is clickable.
 */
@Composable
fun SuperSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    title: String,
    titleColor: BasicComponentColors = BasicComponentDefaults.titleColor(),
    summary: String? = null,
    summaryColor: BasicComponentColors = BasicComponentDefaults.summaryColor(),
    leftAction: @Composable (() -> Unit)? = null,
    rightActions: @Composable RowScope.() -> Unit = {},
    switchColors: SwitchColors = SwitchDefaults.switchColors(),
    modifier: Modifier = Modifier,
    insideMargin: PaddingValues = BasicComponentDefaults.InsideMargin,
    onClick: (() -> Unit)? = null,
    holdDownState: Boolean = false,
    enabled: Boolean = true
) {
    val updatedOnCheckedChange by rememberUpdatedState(onCheckedChange)
    val currentOnClick by rememberUpdatedState(onClick)

    val rememberedRightActions: @Composable RowScope.() -> Unit =
        remember(rightActions, checked, updatedOnCheckedChange, enabled, switchColors) {
            {
                rightActions()
                Switch(
                    checked = checked,
                    onCheckedChange = updatedOnCheckedChange,
                    enabled = enabled,
                    colors = switchColors
                )
            }
        }

    val rememberedOnClick: (() -> Unit)? = remember(enabled, currentOnClick, updatedOnCheckedChange, checked) {
        if (enabled) {
            {
                currentOnClick?.invoke()
                updatedOnCheckedChange?.invoke(!checked)
            }
        } else {
            null
        }
    }

    BasicComponent(
        modifier = modifier,
        insideMargin = insideMargin,
        title = title,
        titleColor = titleColor,
        summary = summary,
        summaryColor = summaryColor,
        leftAction = leftAction,
        rightActions = rememberedRightActions,
        onClick = rememberedOnClick,
        holdDownState = holdDownState,
        enabled = enabled
    )
}