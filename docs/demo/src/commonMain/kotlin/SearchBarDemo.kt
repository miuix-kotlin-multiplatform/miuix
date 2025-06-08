// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.BasicComponentDefaults
import top.yukonga.miuix.kmp.basic.InputField
import top.yukonga.miuix.kmp.basic.SearchBar
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun SearchBarDemo() {
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
            var searchValue by remember { mutableStateOf("") }
            var expanded by remember { mutableStateOf(false) }
            SearchBar(
                inputField = {
                    InputField(
                        query = searchValue,
                        onQueryChange = { searchValue = it },
                        onSearch = { expanded = false },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        label = "Search"
                    )
                },
                outsideRightAction = {
                    Text(
                        modifier = Modifier
                            .clickable(
                                interactionSource = null,
                                indication = null
                            ) {
                                expanded = false
                                searchValue = ""
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
                            titleColor = BasicComponentDefaults.titleColor(Color.White),
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                                searchValue = resultText
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}
