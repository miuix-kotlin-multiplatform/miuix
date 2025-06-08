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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.extra.SpinnerEntry
import top.yukonga.miuix.kmp.extra.SpinnerMode
import top.yukonga.miuix.kmp.extra.SuperSpinner

@Composable
fun SuperSpinnerDemo() {
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
                val options1 = listOf(
                    SpinnerEntry(title = "Option 1"),
                    SpinnerEntry(title = "Option 2"),
                    SpinnerEntry(title = "Option 3"),
                )
                var selectedIndex2 by remember { mutableStateOf(0) }
                val options2 = listOf(
                    SpinnerEntry(
                        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFFFF5B29)) },
                        title = "Red Theme",
                        summary = "Vibrant red"
                    ),
                    SpinnerEntry(
                        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFF3482FF)) },
                        title = "Blue Theme",
                        summary = "Calm blue"
                    ),
                    SpinnerEntry(
                        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFF36D167)) },
                        title = "Green Theme",
                        summary = "Fresh green"
                    ),
                    SpinnerEntry(
                        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFFFFB21D)) },
                        title = "Yellow Theme",
                        summary = "Bright yellow"
                    )
                )
                var selectedIndex3 by remember { mutableStateOf(0) }
                val options3 = listOf(
                    SpinnerEntry(title = "Option 1"),
                    SpinnerEntry(title = "Option 2"),
                    SpinnerEntry(title = "Option 3")
                )

                Card {
                    SuperSpinner(
                        title = "Dropdown Selector",
                        items = options1,
                        selectedIndex = selectedIndex1,
                        onSelectedIndexChange = { selectedIndex1 = it }
                    )
                    SuperSpinner(
                        title = "Function Selection",
                        summary = "Choose the action you want to perform",
                        items = options2,
                        selectedIndex = selectedIndex2,
                        onSelectedIndexChange = { selectedIndex2 = it }
                    )
                    SuperSpinner(
                        title = "Always on Right Mode",
                        items = options3,
                        selectedIndex = selectedIndex3,
                        onSelectedIndexChange = { selectedIndex3 = it },
                        mode = SpinnerMode.AlwaysOnRight // Always on right mode
                    )
                    SuperSpinner(
                        title = "Disabled Selector",
                        summary = "This selector is currently unavailable",
                        items = listOf(SpinnerEntry(title = "Option 1")),
                        selectedIndex = 0,
                        onSelectedIndexChange = {},
                        enabled = false
                    )
                }
            }
        }
    }
}

// Create a rounded rectangle Painter
class RoundedRectanglePainter(
    private val cornerRadius: Dp = 6.dp
) : Painter() {
    override val intrinsicSize = Size.Unspecified

    override fun DrawScope.onDraw() {
        drawRoundRect(
            color = Color.White,
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        )
    }
}