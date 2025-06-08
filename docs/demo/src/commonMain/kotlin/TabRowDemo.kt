// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.TabRow
import top.yukonga.miuix.kmp.basic.TabRowWithContour

@Composable
fun TabRowDemo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(Color(0xfff77062), Color(0xfffe5196)))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .padding(16.dp)
                .widthIn(max = 600.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val tabs1 = listOf("Recommended", "Following", "Popular", "Featured")
            var selectedTabIndex1 by remember { mutableStateOf(0) }

            TabRow(
                tabs = tabs1,
                selectedTabIndex = selectedTabIndex1,
                onTabSelected = { selectedTabIndex1 = it }
            )
            val tabs2 = listOf("All", "Photos", "Videos", "Documents")
            var selectedTabIndex2 by remember { mutableStateOf(0) }

            TabRowWithContour(
                tabs = tabs2,
                selectedTabIndex = selectedTabIndex2,
                onTabSelected = { selectedTabIndex2 = it }
            )
        }
    }
}
