package component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.MiuixSuperCheckbox
import top.yukonga.miuix.kmp.MiuixSuperSwitch
import top.yukonga.miuix.kmp.basic.MiuixButton
import top.yukonga.miuix.kmp.basic.MiuixCheckbox
import top.yukonga.miuix.kmp.basic.MiuixSwitch

@Composable
fun SecondComponent() {
    var checkbox by remember { mutableStateOf(false) }
    var checkboxTrue by remember { mutableStateOf(true) }
    var switch by remember { mutableStateOf(false) }
    var switchTrue by remember { mutableStateOf(true) }
    var buttonText by remember { mutableStateOf("Button") }
    var submitButtonText by remember { mutableStateOf("Submit") }
    var miuixSuperCheckbox by remember { mutableStateOf("State: false") }
    var miuixSuperCheckboxState by remember { mutableStateOf(false) }
    var miuixSuperSwitch by remember { mutableStateOf("State: false") }
    var miuixSuperSwitchState by remember { mutableStateOf(false) }
    var clickCount by remember { mutableStateOf(0) }
    var submitClickCount by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 20.dp),
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


    MiuixSuperCheckbox(
        title = "Checkbox",
        summary = miuixSuperCheckbox,
        checked = miuixSuperCheckboxState,
        onCheckedChange = {
            miuixSuperCheckboxState = it
            miuixSuperCheckbox = "State: $it"
        },
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 20.dp),
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

    MiuixSuperSwitch(
        title = "Switch",
        summary = miuixSuperSwitch,
        checked = miuixSuperSwitchState,
        onCheckedChange = {
            miuixSuperSwitchState = it
            miuixSuperSwitch = "State: $it"
        },
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MiuixButton(
            modifier = Modifier.weight(1f),
            text = buttonText,
            onClick = {
                clickCount++
                buttonText = "Click: $clickCount"
            }
        )
        Spacer(Modifier.width(20.dp))
        MiuixButton(
            modifier = Modifier.weight(1f),
            text = submitButtonText,
            submit = true,
            onClick = {
                submitClickCount++
                submitButtonText = "Click: $submitClickCount"
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MiuixButton(
            modifier = Modifier.weight(1f),
            text = "Disabled",
            submit = true,
            enabled = false,
            onClick = {}
        )
        Spacer(Modifier.width(20.dp))
        MiuixButton(
            modifier = Modifier.weight(1f),
            text = "Disabled",
            submit = false,
            enabled = false,
            onClick = {}
        )
    }

}