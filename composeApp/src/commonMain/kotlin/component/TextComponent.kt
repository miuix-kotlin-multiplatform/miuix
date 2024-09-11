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
import top.yukonga.miuix.kmp.CheckboxLocation
import top.yukonga.miuix.kmp.MiuixSearchBar
import top.yukonga.miuix.kmp.MiuixSuperArrow
import top.yukonga.miuix.kmp.MiuixSuperCheckbox
import top.yukonga.miuix.kmp.MiuixSuperDialog
import top.yukonga.miuix.kmp.MiuixSuperDropdown
import top.yukonga.miuix.kmp.MiuixSuperSwitch
import top.yukonga.miuix.kmp.basic.MiuixBasicComponent
import top.yukonga.miuix.kmp.basic.MiuixButton
import top.yukonga.miuix.kmp.basic.MiuixCard
import top.yukonga.miuix.kmp.basic.MiuixCheckbox
import top.yukonga.miuix.kmp.basic.MiuixSmallTitle
import top.yukonga.miuix.kmp.basic.MiuixSwitch
import top.yukonga.miuix.kmp.basic.MiuixText
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
    val miuixSearchValue = remember { mutableStateOf("") }

    MiuixSearchBar(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
        searchValue = miuixSearchValue,
        expanded = miuixSearchValue.value.isNotEmpty(),
        onExpandedChange = {}
    ) {
        MiuixBasicComponent(
            title = miuixSearchValue.value,
            onClick = {
                miuixSearchValue.value = ""
            },
        )
    }

    MiuixBasicComponent(
        title = "Title",
        summary = "Summary",
        leftAction = {
            MiuixText(text = "Left")
        },
        rightActions = {
            MiuixText(text = "Right1")
            Spacer(Modifier.width(8.dp))
            MiuixText(text = "Right2")
        },
        onClick = {}
    )

    MiuixSuperArrow(
        title = "Arrow",
        summary = "With an arrow on right",
        onClick = {}
    )

    MiuixSuperArrow(
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
        MiuixCheckbox(
            modifier = Modifier,
            checked = checkbox,
            onCheckedChange = { checkbox = it }

        )
        MiuixCheckbox(
            modifier = Modifier.padding(start = 8.dp),
            checked = checkboxTrue,
            onCheckedChange = { checkboxTrue = it }
        )
        MiuixCheckbox(
            modifier = Modifier.padding(start = 8.dp),
            enabled = false,
            checked = false,
            onCheckedChange = { }

        )
        MiuixCheckbox(
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
        MiuixSwitch(
            checked = switch,
            onCheckedChange = { switch = it }
        )
        MiuixSwitch(
            modifier = Modifier.padding(start = 8.dp),
            checked = switchTrue,
            onCheckedChange = { switchTrue = it }
        )
        MiuixSwitch(
            modifier = Modifier.padding(start = 8.dp),
            enabled = false,
            checked = false,
            onCheckedChange = { }
        )
        MiuixSwitch(
            modifier = Modifier.padding(start = 8.dp),
            enabled = false,
            checked = true,
            onCheckedChange = { }
        )
    }

    MiuixSmallTitle("Title")

    MiuixCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        MiuixSuperCheckbox(
            insideMargin = DpSize(18.dp, 18.dp),
            title = "Checkbox",
            summary = miuixSuperCheckbox,
            checked = miuixSuperCheckboxState,
            onCheckedChange = {
                miuixSuperCheckboxState = it
                miuixSuperCheckbox = "State: $it"
            },
        )

        MiuixSuperCheckbox(
            insideMargin = DpSize(18.dp, 18.dp),
            checkboxLocation = CheckboxLocation.Right,
            title = "Checkbox",
            checked = miuixSuperRightCheckboxState,
            rightActions = {
                MiuixText(
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

        MiuixSuperSwitch(
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
            MiuixSuperSwitch(
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

        MiuixSuperArrow(
            insideMargin = DpSize(18.dp, 18.dp),
            title = "Dialog",
            summary = "Click to show a Dialog",
            onClick = {
                showDialog.value = true
            }
        )

        MiuixSuperDropdown(
            insideMargin = DpSize(18.dp, 18.dp),
            title = "Dropdown",
            summary = "Popup near click",
            items = dropdownOptions,
            selectedIndex = dropdownSelectedOption.value,
            onSelectedIndexChange = { newOption -> dropdownSelectedOption.value = newOption },
        )

        MiuixSuperDropdown(
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
            MiuixSuperDialog(
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
                    MiuixButton(
                        modifier = Modifier.weight(1f),
                        text = "Cancel",
                        onClick = {
                            dismissDialog()
                            showDialog.value = false
                        }
                    )
                    Spacer(Modifier.width(20.dp))
                    MiuixButton(
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
