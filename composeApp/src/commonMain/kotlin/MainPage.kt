import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import component.OtherComponent
import component.TextComponent
import top.yukonga.miuix.kmp.MiuixScrollBehavior
import top.yukonga.miuix.kmp.MiuixSearchBar
import top.yukonga.miuix.kmp.basic.MiuixLazyColumn

@Composable
fun MainPage(
    topAppBarScrollBehavior: MiuixScrollBehavior,
    padding: PaddingValues
) {
    val miuixSearchValue = remember { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    MiuixLazyColumn(
        contentPadding = PaddingValues(top = padding.calculateTopPadding()),
        topAppBarScrollBehavior = topAppBarScrollBehavior,
    ) {
        item {
            MiuixSearchBar(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                searchValue = miuixSearchValue,
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    repeat(4) { idx ->
                        val resultText = "Suggestion $idx"
                        ListItem(
                            headlineContent = { Text(resultText) },
                            leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    miuixSearchValue.value = resultText
                                    expanded = false
                                }
                        )
                    }
                }
            }
        }
        if (!expanded) {
            item {
                TextComponent()
            }
            item {
                OtherComponent(padding)
            }
        }
    }
}