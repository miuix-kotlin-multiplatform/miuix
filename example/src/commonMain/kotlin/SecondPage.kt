// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.PullToRefresh
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.rememberPullToRefreshState
import top.yukonga.miuix.kmp.extra.SuperDropdown
import top.yukonga.miuix.kmp.utils.getWindowSize
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuix.kmp.utils.scrollEndHaptic

@Composable
fun SecondPage(
    topAppBarScrollBehavior: ScrollBehavior,
    padding: PaddingValues,
    scrollEndHaptic: Boolean
) {
    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()

    val dropdownOptions = remember { listOf("Option 1", "Option 2", "Option 3", "Option 4") }
    var dropdownSelectedOption by remember { mutableStateOf(0) }
    var ii by remember { mutableStateOf(6) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(500)
            ii += 6
            isRefreshing = false
        }
    }

    val windowSize by rememberUpdatedState(getWindowSize())

    PullToRefresh(
        isRefreshing = isRefreshing,
        onRefresh = { isRefreshing = true },
        pullToRefreshState = pullToRefreshState,
        topAppBarScrollBehavior = topAppBarScrollBehavior,
        contentPadding = PaddingValues(top = padding.calculateTopPadding() + 12.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .height(windowSize.height.dp)
                .overScrollVertical()
                .then(
                    if (scrollEndHaptic) Modifier.scrollEndHaptic() else Modifier
                ),
            contentPadding = PaddingValues(top = padding.calculateTopPadding() + 12.dp),
            overscrollEffect = null
        ) {
            item {
                Card(
                    modifier = Modifier.padding(horizontal = 12.dp).padding(bottom = 12.dp),
                ) {
                    for (i in 0 until ii) {
                        key(i) {
                            SuperDropdown(
                                title = "Dropdown ${i + 1}",
                                items = dropdownOptions,
                                selectedIndex = dropdownSelectedOption,
                                onSelectedIndexChange = { newOption ->
                                    dropdownSelectedOption = newOption
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(padding.calculateBottomPadding()))
            }
        }
    }
}