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
import top.yukonga.miuix.kmp.MiuixSuperSwitch
import top.yukonga.miuix.kmp.basic.MiuixButton
import top.yukonga.miuix.kmp.basic.MiuixSwitch

@Composable
fun SecondComponent() {
    var switch by remember { mutableStateOf(false) }
    var switchTrue by remember { mutableStateOf(true) }
    var buttonText by remember { mutableStateOf("Button") }
    var submitButtonText by remember { mutableStateOf("Submit") }
    var textWithSwitch by remember { mutableStateOf("State: true") }
    var textWishSwitchTrue by remember { mutableStateOf(true) }
    var clickCount by remember { mutableStateOf(0) }
    var submitClickCount by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MiuixSwitch(
            checked = switchTrue,
            onCheckedChange = { switchTrue = it }
        )
        MiuixSwitch(
            modifier = Modifier.padding(start = 8.dp),
            checked = switch,
            onCheckedChange = { switch = it }
        )
        MiuixSwitch(
            modifier = Modifier.padding(start = 8.dp),
            enabled = false,
            checked = true,
            onCheckedChange = { }
        )
        MiuixSwitch(
            modifier = Modifier.padding(start = 8.dp),
            enabled = false,
            checked = false,
            onCheckedChange = { }
        )
    }

    MiuixSuperSwitch(
        title = "TextWithSwitch",
        summary = textWithSwitch,
        checked = textWishSwitchTrue,
        onCheckedChange = {
            textWishSwitchTrue = it
            textWithSwitch = "State: $it"
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