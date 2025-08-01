// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.browser.window

@Composable
@OptIn(ExperimentalComposeUiApi::class)
actual fun getWindowSize(): WindowSize {
    val window = LocalWindowInfo.current
    val windowSize by remember(window) {
        derivedStateOf {
            WindowSize(
                width = window.containerSize.width,
                height = window.containerSize.height
            )
        }
    }
    return windowSize
}

actual fun platform(): Platform = Platform.Js

@Composable
actual fun getRoundedCorner(): Dp = 0.dp

@Composable
actual fun BackHandler(
    enabled: Boolean,
    onBack: () -> Unit
) {
    if (!enabled) return
    LaunchedEffect(Unit) {
        val onKeyDown: (dynamic) -> Unit = { event ->
            if (event.key == "Escape") {
                onBack()
            }
        }
        window.addEventListener("keydown", onKeyDown)
    }
}