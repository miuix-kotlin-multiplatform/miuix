import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.unit.dp
import component.OtherComponent
import component.TextComponent
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.InputField
import top.yukonga.miuix.kmp.basic.LazyColumn
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.SearchBar
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.Search
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun MainPage(
    topAppBarScrollBehavior: ScrollBehavior,
    padding: PaddingValues
) {
    var miuixSearchValue by remember { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        contentPadding = PaddingValues(top = padding.calculateTopPadding()),
        topAppBarScrollBehavior = topAppBarScrollBehavior,
    ) {
        item {
            SearchBar(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                inputField = {
                    InputField(
                        query = miuixSearchValue,
                        onQueryChange = { miuixSearchValue = it },
                        onSearch = { expanded = false },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        label = "Search",
                        leadingIcon = {
                            Image(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                imageVector = MiuixIcons.Search,
                                colorFilter = BlendModeColorFilter(
                                    MiuixTheme.colorScheme.onSurfaceContainer,
                                    BlendMode.SrcIn
                                ),
                                contentDescription = "Search"
                            )
                        },
                    )
                },
                outsideRightAction = {
                    Text(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .clickable(
                                interactionSource = null,
                                indication = null
                            ) {
                                expanded = false
                                miuixSearchValue = ""
                            },
                        text = "Cancel",
                        color = MiuixTheme.colorScheme.primary
                    )
                },
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    repeat(4) { idx ->
                        val resultText = "Suggestion $idx"
                        BasicComponent(
                            title = resultText,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                                miuixSearchValue = resultText
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