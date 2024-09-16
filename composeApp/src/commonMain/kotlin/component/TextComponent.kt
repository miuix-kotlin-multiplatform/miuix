package component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.extra.CheckboxLocation
import top.yukonga.miuix.kmp.extra.SuperArrow
import top.yukonga.miuix.kmp.extra.SuperCheckbox
import top.yukonga.miuix.kmp.extra.SuperDialog
import top.yukonga.miuix.kmp.extra.SuperDropdown
import top.yukonga.miuix.kmp.extra.SuperSwitch
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.Button
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.Checkbox
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.Switch
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.MiuixPopupUtil.Companion.dismissDialog
import top.yukonga.miuix.kmp.utils.MiuixPopupUtil.Companion.showDialog

@Composable
fun TextComponent() {
    val showDialog = remember { mutableStateOf(false) }
    var checkbox by remember { mutableStateOf(false) }
    var checkboxTrue by remember { mutableStateOf(true) }
    var switch by remember { mutableStateOf(false) }
    var switchTrue by remember { mutableStateOf(true) }
    val dropdownOptions = listOf("Option 1", "Option 2", "Option 3", "Option 4")
    val dropdownSelectedOption = remember { mutableStateOf(0) }
    val dropdownSelectedOptionRight = remember { mutableStateOf(1) }
    var miuixSuperCheckbox by remember { mutableStateOf("State: false") }
    var miuixSuperCheckboxState by remember { mutableStateOf(false) }
    var miuixSuperRightCheckbox by remember { mutableStateOf("false") }
    var miuixSuperRightCheckboxState by remember { mutableStateOf(false) }
    var miuixSuperSwitch by remember { mutableStateOf("State: false") }
    var miuixSuperSwitchState by remember { mutableStateOf(false) }
    var miuixSuperSwitchAnimState by remember { mutableStateOf(false) }

    BasicComponent(
        title = "Title",
        summary = "Summary",
        leftAction = {
            Text(text = "Left")
        },
        rightActions = {
            Text(text = "Right1")
            Spacer(Modifier.width(8.dp))
            Text(text = "Right2")
        },
        onClick = {}
    )

    SuperArrow(
        title = "Arrow",
        summary = "With an arrow on right",
        onClick = {}
    )

    SuperArrow(
        leftAction = {
            Image(
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onBackground),
                imageVector = Icons.Default.Person,
                contentDescription = "Person",
            )
        },
        title = "Person",
        summary = "An introduction",
        onClick = {}
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Checkbox(
            modifier = Modifier,
            checked = checkbox,
            onCheckedChange = { checkbox = it }

        )
        Checkbox(
            modifier = Modifier.padding(start = 8.dp),
            checked = checkboxTrue,
            onCheckedChange = { checkboxTrue = it }
        )
        Checkbox(
            modifier = Modifier.padding(start = 8.dp),
            enabled = false,
            checked = false,
            onCheckedChange = { }

        )
        Checkbox(
            modifier = Modifier.padding(start = 8.dp),
            enabled = false,
            checked = true,
            onCheckedChange = { }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Switch(
            checked = switch,
            onCheckedChange = { switch = it }
        )
        Switch(
            modifier = Modifier.padding(start = 8.dp),
            checked = switchTrue,
            onCheckedChange = { switchTrue = it }
        )
        Switch(
            modifier = Modifier.padding(start = 8.dp),
            enabled = false,
            checked = false,
            onCheckedChange = { }
        )
        Switch(
            modifier = Modifier.padding(start = 8.dp),
            enabled = false,
            checked = true,
            onCheckedChange = { }
        )
    }

    SmallTitle("Title")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SuperCheckbox(
            insideMargin = DpSize(18.dp, 18.dp),
            title = "Checkbox",
            summary = miuixSuperCheckbox,
            checked = miuixSuperCheckboxState,
            onCheckedChange = {
                miuixSuperCheckboxState = it
                miuixSuperCheckbox = "State: $it"
            },
        )

        SuperCheckbox(
            insideMargin = DpSize(18.dp, 18.dp),
            checkboxLocation = CheckboxLocation.Right,
            title = "Checkbox",
            checked = miuixSuperRightCheckboxState,
            rightActions = {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = miuixSuperRightCheckbox,
                    color = MiuixTheme.colorScheme.subTextBase
                )
            },
            onCheckedChange = {
                miuixSuperRightCheckboxState = it
                miuixSuperRightCheckbox = "$it"
            },
        )

        SuperSwitch(
            insideMargin = DpSize(18.dp, 18.dp),
            title = "Switch",
            summary = "Click to expand a Switch",
            checked = miuixSuperSwitchAnimState,
            onCheckedChange = {
                miuixSuperSwitchAnimState = it
            },
        )

        AnimatedVisibility(
            visible = miuixSuperSwitchAnimState,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            SuperSwitch(
                insideMargin = DpSize(18.dp, 18.dp),
                title = "Switch",
                summary = miuixSuperSwitch,
                checked = miuixSuperSwitchState,
                onCheckedChange = {
                    miuixSuperSwitchState = it
                    miuixSuperSwitch = "State: $it"
                },
            )
        }

        SuperArrow(
            insideMargin = DpSize(18.dp, 18.dp),
            title = "Dialog",
            summary = "Click to show a Dialog",
            onClick = {
                showDialog.value = true
            }
        )

        SuperDropdown(
            insideMargin = DpSize(18.dp, 18.dp),
            title = "Dropdown",
            summary = "Popup near click",
            items = dropdownOptions,
            selectedIndex = dropdownSelectedOption.value,
            onSelectedIndexChange = { newOption -> dropdownSelectedOption.value = newOption },
        )

        SuperDropdown(
            insideMargin = DpSize(18.dp, 18.dp),
            title = "Dropdown",
            summary = "Popup always on right",
            alwaysRight = true,
            items = dropdownOptions,
            selectedIndex = dropdownSelectedOptionRight.value,
            onSelectedIndexChange = { newOption -> dropdownSelectedOptionRight.value = newOption },
        )

    }

    dialog(showDialog)
}

@Composable
fun dialog(showDialog: MutableState<Boolean>) {
    showDialog(
        show = showDialog.value,
        content = {
            SuperDialog(
                title = "Title",
                summary = "Summary",
                show = showDialog,
                onDismissRequest = {
                    showDialog.value = false
                },
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        text = "Cancel",
                        onClick = {
                            dismissDialog()
                            showDialog.value = false
                        }
                    )
                    Spacer(Modifier.width(20.dp))
                    Button(
                        modifier = Modifier.weight(1f),
                        text = "Confirm",
                        submit = true,
                        onClick = {
                            dismissDialog()
                            showDialog.value = false
                        }
                    )
                }
            }
        }
    )
}
