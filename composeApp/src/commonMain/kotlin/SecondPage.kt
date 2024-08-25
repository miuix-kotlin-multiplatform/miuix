import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import top.yukonga.miuix.kmp.MiuixScrollBehavior
import top.yukonga.miuix.kmp.MiuixSuperDropdown
import top.yukonga.miuix.kmp.basic.MiuixLazyColumn

@Composable
fun SecondPage(
    topAppBarScrollBehavior: MiuixScrollBehavior,
    padding: PaddingValues,
    enableOverScroll: Boolean
) {
    val dropdownOptions = listOf("Option 1", "Option 2", "Option 3", "Option 4")
    val dropdownSelectedOption = remember { mutableStateOf("Option 1") }

    MiuixLazyColumn(
        enableOverScroll = enableOverScroll,
        contentPadding = PaddingValues(top = padding.calculateTopPadding()),
        topAppBarScrollBehavior = topAppBarScrollBehavior
    ) {
        items(25) {
            MiuixSuperDropdown(
                title = "Dropdown",
                summary = "Popup near click",
                options = dropdownOptions,
                selectedOption = dropdownSelectedOption,
                onOptionSelected = { newOption -> dropdownSelectedOption.value = newOption },
            )
        }
        item {
            Spacer(modifier = Modifier.height(padding.calculateBottomPadding()))
        }
    }
}