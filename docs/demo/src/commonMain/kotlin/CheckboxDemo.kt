// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import top.yukonga.miuix.kmp.basic.Checkbox

@Composable
fun CheckboxDemo() {
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
            var checkbox1 by remember { mutableStateOf(false) }
            var checkbox2 by remember { mutableStateOf(true) }
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                Checkbox(
                    checked = checkbox1,
                    onCheckedChange = { checkbox1 = it }
                )
                Checkbox(
                    checked = checkbox2,
                    onCheckedChange = { checkbox2 = it }
                )
                Checkbox(
                    checked = false,
                    onCheckedChange = { },
                    enabled = false
                )
                Checkbox(
                    checked = true,
                    onCheckedChange = { },
                    enabled = false
                )
            }
        }
    }
}
