// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.Checkbox
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.Switch
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.basic.TextField
import top.yukonga.miuix.kmp.extra.CheckboxLocation
import top.yukonga.miuix.kmp.extra.DropDownMode
import top.yukonga.miuix.kmp.extra.SpinnerEntry
import top.yukonga.miuix.kmp.extra.SpinnerMode
import top.yukonga.miuix.kmp.extra.SuperArrow
import top.yukonga.miuix.kmp.extra.SuperCheckbox
import top.yukonga.miuix.kmp.extra.SuperDialog
import top.yukonga.miuix.kmp.extra.SuperDropdown
import top.yukonga.miuix.kmp.extra.SuperSpinner
import top.yukonga.miuix.kmp.extra.SuperSwitch
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.useful.Personal
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun TextComponent(
    showDialog: MutableState<Boolean>,
    dialogTextFieldValue: MutableState<String>,
    showDialog2: MutableState<Boolean>,
    dialog2dropdownSelectedOption: MutableState<Int>,
    dialog2SuperSwitchState: MutableState<Boolean>,
    checkbox: MutableState<Boolean>,
    checkboxTrue: MutableState<Boolean>,
    switch: MutableState<Boolean>,
    switchTrue: MutableState<Boolean>,
    dropdownOptionSelected: MutableState<Int>,
    dropdownOptionSelectedRight: MutableState<Int>,
    spinnerOptionSelected: MutableState<Int>,
    spinnerOptionSelectedRight: MutableState<Int>,
    spinnerOptionSelectedDialog: MutableState<Int>,
    miuixSuperCheckbox: MutableState<String>,
    miuixSuperCheckboxState: MutableState<Boolean>,
    miuixSuperRightCheckbox: MutableState<String>,
    miuixSuperRightCheckboxState: MutableState<Boolean>,
    miuixSuperSwitch: MutableState<String>,
    miuixSuperSwitchState: MutableState<Boolean>,
    miuixSuperSwitchAnimState: MutableState<Boolean>,
) {
    val dropdownOptions = listOf("Option 1", "Option 2", "Option 3", "Option 4")
    val dropdownLongOptions = listOf(
        "Option 1", "Option 2 (long)", "Option 3 (long long)", "Option 4 (long long long)",
        "Option 5 (long long long long)", "Option 6 (long long long long long)"
    )
    val spinnerOptions = listOf(
        SpinnerEntry(icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFFFF5B29)) }, "Option 1", "Red"),
        SpinnerEntry(icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFF36D167)) }, "Option 2", "Green"),
        SpinnerEntry(icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFF3482FF)) }, "Option 3", "Blue"),
        SpinnerEntry(icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFFFFB21D)) }, "Option 4", "Yellow"),
    )

    SmallTitle(text = "Basic Component")
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 6.dp)
    ) {
        BasicComponent(
            title = "Title",
            summary = "Summary",
            leftAction = {
                Text(
                    text = "Left",
                    modifier = Modifier.padding(end = 16.dp)
                )
            },
            rightActions = {
                Text(text = "Right1")
                Spacer(Modifier.width(10.dp))
                Text(text = "Right2")
            },
            onClick = {},
            enabled = true
        )
        BasicComponent(
            title = "Title",
            summary = "Summary",
            leftAction = {
                Text(
                    text = "Left",
                    modifier = Modifier.padding(end = 16.dp),
                    color = MiuixTheme.colorScheme.disabledOnSecondaryVariant
                )
            },
            rightActions = {
                Text(
                    text = "Right1",
                    color = MiuixTheme.colorScheme.disabledOnSecondaryVariant
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "Right2",
                    color = MiuixTheme.colorScheme.disabledOnSecondaryVariant
                )
            },
            enabled = false
        )
    }

    SmallTitle(text = "Arrow & Dialog")
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 6.dp)
    ) {
        SuperArrow(
            leftAction = {
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Icon(
                        imageVector = MiuixIcons.Useful.Personal,
                        contentDescription = "Personal",
                        tint = MiuixTheme.colorScheme.onBackground
                    )
                }
            },
            title = "Arrow",
            summary = "Click to show Dialog 1",
            onClick = {
                showDialog.value = true
            },
            holdDownState = showDialog.value
        )

        SuperArrow(
            title = "Arrow",
            summary = "Click to show Dialog 2",
            onClick = {
                showDialog2.value = true
            },
            holdDownState = showDialog2.value
        )

        SuperArrow(
            title = "Disabled Arrow",
            onClick = {},
            enabled = false
        )
    }

    SmallTitle(text = "Checkbox")
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Checkbox(
                checked = checkbox.value,
                onCheckedChange = { checkbox.value = it }
            )
            Checkbox(
                checked = checkboxTrue.value,
                onCheckedChange = { checkboxTrue.value = it },
                modifier = Modifier.padding(start = 8.dp)
            )
            Checkbox(
                checked = false,
                onCheckedChange = { },
                modifier = Modifier.padding(start = 8.dp),
                enabled = false
            )
            Checkbox(
                checked = true,
                onCheckedChange = { },
                modifier = Modifier.padding(start = 8.dp),
                enabled = false
            )
        }

        SuperCheckbox(
            checkboxLocation = CheckboxLocation.Right,
            title = "Checkbox",
            checked = miuixSuperRightCheckboxState.value,
            rightActions = {
                Text(
                    modifier = Modifier.padding(end = 6.dp),
                    text = miuixSuperRightCheckbox.value,
                    color = MiuixTheme.colorScheme.onSurfaceVariantActions
                )
            },
            onCheckedChange = {
                miuixSuperRightCheckboxState.value = it
                miuixSuperRightCheckbox.value = "$it"
            },
        )

        SuperCheckbox(
            title = "Checkbox",
            summary = miuixSuperCheckbox.value,
            checked = miuixSuperCheckboxState.value,
            onCheckedChange = {
                miuixSuperCheckboxState.value = it
                miuixSuperCheckbox.value = "State: $it"
            },
        )

        SuperCheckbox(
            title = "Disabled Checkbox",
            checked = true,
            enabled = false,
            onCheckedChange = {},
        )
    }

    SmallTitle(text = "Switch")
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Switch(
                checked = switch.value,
                onCheckedChange = { switch.value = it }
            )
            Switch(
                checked = switchTrue.value,
                onCheckedChange = { switchTrue.value = it },
                modifier = Modifier.padding(start = 6.dp)
            )
            Switch(
                checked = false,
                onCheckedChange = { },
                modifier = Modifier.padding(start = 6.dp),
                enabled = false
            )
            Switch(
                checked = true,
                onCheckedChange = { },
                modifier = Modifier.padding(start = 6.dp),
                enabled = false
            )
        }

        SuperSwitch(
            title = "Switch",
            summary = "Click to expand a Switch",
            checked = miuixSuperSwitchAnimState.value,
            onCheckedChange = {
                miuixSuperSwitchAnimState.value = it
            },
        )

        AnimatedVisibility(
            visible = miuixSuperSwitchAnimState.value,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            SuperSwitch(
                title = "Switch",
                checked = miuixSuperSwitchState.value,
                rightActions = {
                    Text(
                        modifier = Modifier.padding(end = 6.dp),
                        text = miuixSuperSwitch.value,
                        color = MiuixTheme.colorScheme.onSurfaceVariantActions
                    )
                },
                onCheckedChange = {
                    miuixSuperSwitchState.value = it
                    miuixSuperSwitch.value = "$it"
                },
            )
        }

        SuperSwitch(
            title = "Disabled Switch",
            checked = true,
            enabled = false,
            onCheckedChange = {},
        )
    }

    SmallTitle(text = "Dropdown")
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 6.dp)
    ) {
        SuperDropdown(
            title = "Dropdown",
            summary = "Popup near click",
            items = dropdownOptions,
            selectedIndex = dropdownOptionSelected.value,
            onSelectedIndexChange = { newOption -> dropdownOptionSelected.value = newOption },
        )

        SuperDropdown(
            title = "Dropdown",
            summary = "Popup always on right",
            items = dropdownLongOptions,
            selectedIndex = dropdownOptionSelectedRight.value,
            onSelectedIndexChange = { newOption -> dropdownOptionSelectedRight.value = newOption },
            mode = DropDownMode.AlwaysOnRight,
            maxHeight = 300.dp
        )

        SuperDropdown(
            title = "Disabled Dropdown",
            items = listOf("Option 3"),
            selectedIndex = 0,
            onSelectedIndexChange = {},
            enabled = false
        )
    }

    SmallTitle(text = "Spinner")
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 6.dp)
    ) {
        SuperSpinner(
            title = "Spinner",
            summary = "Spinner near click",
            items = spinnerOptions,
            selectedIndex = spinnerOptionSelected.value,
            onSelectedIndexChange = { newOption -> spinnerOptionSelected.value = newOption },
        )

        SuperSpinner(
            title = "Spinner",
            summary = "Spinner always on right",
            items = spinnerOptions,
            selectedIndex = spinnerOptionSelectedRight.value,
            onSelectedIndexChange = { newOption -> spinnerOptionSelectedRight.value = newOption },
            mode = SpinnerMode.AlwaysOnRight,
        )

        SuperSpinner(
            title = "Spinner",
            summary = "Spinner as Dialog",
            dialogButtonString = "Cancel",
            items = spinnerOptions,
            selectedIndex = spinnerOptionSelectedDialog.value,
            onSelectedIndexChange = { newOption -> spinnerOptionSelectedDialog.value = newOption },
        )

        SuperSpinner(
            title = "Disabled Spinner",
            items = listOf(SpinnerEntry(icon = null, title = "Option 4")),
            selectedIndex = 0,
            onSelectedIndexChange = {},
            enabled = false
        )
    }

    dialog(showDialog, dialogTextFieldValue)
    dialog2(showDialog2, dialog2dropdownSelectedOption, dialog2SuperSwitchState)
}

@Composable
fun dialog(
    showDialog: MutableState<Boolean>,
    dialogTextFieldValue: MutableState<String>
) {
    SuperDialog(
        title = "Dialog 1",
        summary = "Summary",
        show = showDialog,
        onDismissRequest = {
            showDialog.value = false
        }
    ) {
        TextField(
            modifier = Modifier.padding(bottom = 16.dp),
            value = dialogTextFieldValue.value,
            maxLines = 1,
            onValueChange = { dialogTextFieldValue.value = it }
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                text = "Cancel",
                onClick = {
                    showDialog.value = false
                },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(20.dp))
            TextButton(
                text = "Confirm",
                onClick = {
                    showDialog.value = false
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColorsPrimary()
            )
        }
    }
}

@Composable
fun dialog2(
    showDialog: MutableState<Boolean>,
    dialog2dropdownSelectedOption: MutableState<Int>,
    dialog2SuperSwitchState: MutableState<Boolean>
) {
    val dropdownOptions = listOf("Option 1", "Option 2")
    SuperDialog(
        title = "Dialog 2",
        show = showDialog,
        onDismissRequest = {
            showDialog.value = false
        }
    ) {
        Card(
            color = MiuixTheme.colorScheme.secondaryContainer,
        ) {
            SuperDropdown(
                title = "Dropdown",
                items = dropdownOptions,
                selectedIndex = dialog2dropdownSelectedOption.value,
                onSelectedIndexChange = { newOption -> dialog2dropdownSelectedOption.value = newOption }
            )
            SuperSwitch(
                title = "Switch",
                checked = dialog2SuperSwitchState.value,
                onCheckedChange = {
                    dialog2SuperSwitchState.value = it
                }
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                text = "Cancel",
                onClick = {
                    showDialog.value = false
                },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(20.dp))
            TextButton(
                text = "Confirm",
                onClick = {
                    showDialog.value = false
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColorsPrimary()
            )
        }
    }
}

class RoundedRectanglePainter(
    private val cornerRadius: Dp = 6.dp
) : Painter() {
    override val intrinsicSize = Size.Unspecified

    override fun DrawScope.onDraw() {
        drawRoundRect(
            color = Color.White,
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        )
    }
}