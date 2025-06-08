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
import top.yukonga.miuix.kmp.extra.CheckboxLocation
import top.yukonga.miuix.kmp.extra.SuperCheckbox

@Composable
fun SuperCheckboxDemo() {
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
            var isChecked by remember { mutableStateOf(false) }
            var rightChecked by remember { mutableStateOf(false) }
            var notificationsEnabled by remember { mutableStateOf(false) }

            Card {
                SuperCheckbox(
                    title = "Checkbox Option",
                    checked = isChecked,
                    onCheckedChange = { isChecked = it }
                )
                SuperCheckbox(
                    title = "Notifications",
                    summary = "Receive push notifications from the app",
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
                SuperCheckbox(
                    title = "Right Checkbox",
                    summary = "Checkbox is on the right side",
                    checked = rightChecked,
                    onCheckedChange = { rightChecked = it },
                    checkboxLocation = CheckboxLocation.Right
                )
                SuperCheckbox(
                    title = "Disabled Checkbox",
                    summary = "This checkbox is currently unavailable",
                    checked = true,
                    onCheckedChange = {},
                    enabled = false
                )
            }
        }
    }
}
