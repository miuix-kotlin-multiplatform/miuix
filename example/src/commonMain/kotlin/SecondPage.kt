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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import top.yukonga.miuix.kmp.utils.scrollEndHaptic

@Composable
fun SecondPage(
    topAppBarScrollBehavior: ScrollBehavior,
    padding: PaddingValues,
    scrollEndHaptic: Boolean
) {
    val pullToRefreshState = rememberPullToRefreshState()

    val dropdownOptions = listOf("Option 1", "Option 2", "Option 3", "Option 4")
    val dropdownSelectedOption = remember { mutableStateOf(0) }
    var ii by remember { mutableStateOf(6) }

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            delay(350)
            pullToRefreshState.completeRefreshing { ii += 6 }
        }
    }

    PullToRefresh(
        pullToRefreshState = pullToRefreshState,
        topAppBarScrollBehavior = topAppBarScrollBehavior,
        contentPadding = PaddingValues(top = padding.calculateTopPadding() + 12.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .height(getWindowSize().height.dp)
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