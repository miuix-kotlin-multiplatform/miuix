import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.LazyColumn
import top.yukonga.miuix.kmp.basic.PullToRefresh
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.rememberPullToRefreshState
import top.yukonga.miuix.kmp.extra.SuperDropdown

@Composable
fun SecondPage(
    topAppBarScrollBehavior: ScrollBehavior,
    padding: PaddingValues
) {
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    val dropdownOptions = listOf("Option 1", "Option 2", "Option 3", "Option 4")
    val dropdownSelectedOption = remember { mutableStateOf(0) }
    var ii = remember { mutableStateOf(10) }

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            isRefreshing = true
            delay(300)
            pullToRefreshState.completeRefreshing {
                isRefreshing = false
            }
        }
    }

    PullToRefresh(
        modifier = Modifier.padding(
            top = padding.calculateTopPadding()
        ),
        pullToRefreshState = pullToRefreshState,
        onRefresh = { ii.value++ }
    ) {
        LazyColumn(
            modifier = Modifier
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                .fillMaxSize(),
        ) {
            item {
                Card(
                    modifier = Modifier
                        .padding(12.dp)
                ) {
                    for (i in 0 until ii.value) {
                        SuperDropdown(
                            title = "Dropdown ${i + 1}",
                            items = dropdownOptions,
                            selectedIndex = dropdownSelectedOption.value,
                            onSelectedIndexChange = { newOption -> dropdownSelectedOption.value = newOption }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(padding.calculateBottomPadding()))
            }
        }
    }
}