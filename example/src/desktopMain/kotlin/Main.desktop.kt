// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.painterResource
import top.yukonga.miuix.kmp.example.generated.resources.Res
import top.yukonga.miuix.kmp.example.generated.resources.icon
import java.awt.Dimension

fun main() =
    application {
        val state =
            rememberWindowState(
                size = DpSize(420.dp, 840.dp),
                position = WindowPosition.Aligned(Alignment.Center),
            )
        Window(
            state = state,
            onCloseRequest = ::exitApplication,
            title = "Miuix",
            icon = painterResource(Res.drawable.icon),
        ) {
            window.minimumSize = Dimension(300, 600)
            App()
        }
    }