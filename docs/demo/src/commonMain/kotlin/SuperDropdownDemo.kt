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
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.extra.DropDownMode
import top.yukonga.miuix.kmp.extra.SuperDropdown

@Composable
fun SuperDropdownDemo() {
    Scaffold {
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
                var selectedIndex1 by remember { mutableStateOf(0) }
                val options1 = listOf("Option 1", "Option 2", "Option 3")
                var selectedIndex2 by remember { mutableStateOf(0) }
                val options2 = listOf("Chinese", "English", "Japanese")
                var selectedIndex3 by remember { mutableStateOf(0) }
                val options3 = listOf("Option 1", "Option 2", "Option 3")

                Card {
                    SuperDropdown(
                        title = "Dropdown Menu",
                        items = options1,
                        selectedIndex = selectedIndex1,
                        onSelectedIndexChange = { selectedIndex1 = it }
                    )
                    SuperDropdown(
                        title = "Language Settings",
                        summary = "Choose your preferred language",
                        items = options2,
                        selectedIndex = selectedIndex2,
                        onSelectedIndexChange = { selectedIndex2 = it }
                    )
                    SuperDropdown(
                        title = "Always on Right Mode",
                        items = options3,
                        selectedIndex = selectedIndex3,
                        onSelectedIndexChange = { selectedIndex3 = it },
                        mode = DropDownMode.AlwaysOnRight
                    )
                    SuperDropdown(
                        title = "Disabled Dropdown",
                        summary = "This dropdown menu is currently unavailable",
                        items = listOf("Option 1"),
                        selectedIndex = 0,
                        onSelectedIndexChange = {},
                        enabled = false
                    )
                }
            }
        }
    }
}
