// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import ui.AppTheme

@Composable
fun App(
    colorMode: MutableState<Int> = remember { mutableStateOf(0) }
) {
    AppTheme(colorMode = colorMode.value) {
        UITest(colorMode = colorMode)
    }
}
