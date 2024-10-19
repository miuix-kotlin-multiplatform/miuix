import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.LazyColumn
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.extra.SuperDropdown

@Composable
fun SecondPage(
    topAppBarScrollBehavior: ScrollBehavior,
    padding: PaddingValues
) {
    val dropdownOptions = listOf("Option 1", "Option 2", "Option 3", "Option 4")
    val dropdownSelectedOption = remember { mutableStateOf(0) }

    LazyColumn(
        contentPadding = PaddingValues(top = padding.calculateTopPadding()),
        topAppBarScrollBehavior = topAppBarScrollBehavior
    ) {
        item {
            Card(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(top = 12.dp, bottom = 12.dp + padding.calculateBottomPadding())
            ) {
                for (i in 0 until 20) {
                    SuperDropdown(
                        title = "Dropdown",
                        items = dropdownOptions,
                        selectedIndex = dropdownSelectedOption.value,
                        onSelectedIndexChange = { newOption -> dropdownSelectedOption.value = newOption }
                    )
                }
            }
        }
    }
}