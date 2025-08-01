// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import platform.AppKit.NSEvent
import platform.AppKit.NSEventMaskKeyDown

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

actual fun platform(): Platform = Platform.MacOS

@Composable
actual fun getRoundedCorner(): Dp = 0.dp

@Composable
actual fun BackHandler(
    enabled: Boolean,
    onBack: () -> Unit
) {
    if (!enabled) return
    DisposableEffect(Unit) {
        val monitor = NSEvent.addLocalMonitorForEventsMatchingMask(NSEventMaskKeyDown) { event ->
            if (event?.keyCode == 53.toUShort()) {
                onBack()
                null
            } else {
                event
            }
        }

        onDispose {
            if (monitor != null) {
                NSEvent.removeMonitor(monitor)
            }
        }
    }
}