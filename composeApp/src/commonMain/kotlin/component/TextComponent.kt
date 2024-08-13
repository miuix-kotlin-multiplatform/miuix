package component

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.MiuixDropdown
import top.yukonga.miuix.kmp.MiuixSuperArrow
import top.yukonga.miuix.kmp.MiuixSuperDialog
import top.yukonga.miuix.kmp.basic.MiuixBasicComponent
import top.yukonga.miuix.kmp.basic.MiuixButton
import top.yukonga.miuix.kmp.basic.MiuixText
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.MiuixDialogUtil

@Composable
fun TextComponent() {
    val showDialog = remember { mutableStateOf(false) }
    val dropdownOptions = listOf("Option 1", "Option 2", "Option 3", "Option 4")
    val dropdownSelectedOption = remember { mutableStateOf("Option 1") }
    val dropdownSelectedOptionRight = remember { mutableStateOf("Option 1") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MiuixText(
            text = "Text",
            fontWeight = FontWeight.Medium
        )
        MiuixText(
            text = "Text",
            modifier = Modifier.padding(start = 24.dp)
        )
        MiuixText(
            text = "Text",
            fontSize = 15.sp,
            color = MiuixTheme.colorScheme.subTextBase,
            modifier = Modifier.padding(start = 24.dp)
        )
        MiuixText(
            text = "Text",
            color = MiuixTheme.colorScheme.subTextField,
            modifier = Modifier.padding(start = 24.dp)
        )
        MiuixText(
            text = "Text",
            style = MiuixTheme.textStyles.title,
            color = MiuixTheme.colorScheme.subDropdown,
            modifier = Modifier.padding(start = 24.dp)
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
        }
    )

    MiuixSuperArrow(
        leftAction = {
            Image(
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onBackground),
                imageVector = Icons.Default.Person,
                contentDescription = "Person",
            )
        },
        title = "Title",
        summary = "Summary",
        onClick = {}
    )

    MiuixSuperArrow(
        title = "Title",
        summary = "Summary",
        rightText = "Right",
        onClick = {
            showDialog.value = true
        }
    )

    dialog(showDialog)

    MiuixDropdown(
        title = "Dropdown",
        summary = "Summary",
        options = dropdownOptions,
        selectedOption = dropdownSelectedOption,
        onOptionSelected = { newOption -> dropdownSelectedOption.value = newOption },
    )

    MiuixDropdown(
        title = "Dropdown",
        summary = "AlwaysRight",
        alwaysRight = true,
        options = dropdownOptions,
        selectedOption = dropdownSelectedOptionRight,
        onOptionSelected = { newOption -> dropdownSelectedOptionRight.value = newOption },
    )

}

@Composable
fun dialog(showDialog: MutableState<Boolean>) {
    MiuixDialogUtil.showDialog(
        visible = showDialog,
        content = {
            MiuixSuperDialog(
                title = "Dialog",
                summary = "Summary",
                onDismissRequest = { showDialog.value = false },
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MiuixButton(
                        modifier = Modifier.weight(1f),
                        text = "Cancel",
                        onClick = { showDialog.value = false }
                    )
                    Spacer(Modifier.width(20.dp))
                    MiuixButton(
                        modifier = Modifier.weight(1f),
                        text = "Confirm",
                        submit = true,
                        onClick = { showDialog.value = false }
                    )
                }
            }
        }
    )
}