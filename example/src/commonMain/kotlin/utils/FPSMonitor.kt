// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.coroutineScope
import top.yukonga.miuix.kmp.basic.Text

/**
 * A simple FPS monitor that displays the current frames per second.
 */
@Composable
fun FPSMonitor(modifier: Modifier = Modifier) {
    var fps by remember { mutableStateOf(0) }
    var maxFps by remember { mutableStateOf(0) }
    var lastFrameTime by remember { mutableStateOf(0L) }
    var frameCount by remember { mutableStateOf(0) }
    var totalFrameTime by remember { mutableStateOf(0L) }

    if (fps > maxFps) {
        maxFps = fps
    }

    val color = when {
        fps >= maxFps - 2 -> Color.Green
        fps >= maxFps - 6 -> Color.Blue
        fps >= maxFps - 15 -> Color(0xFFFFD700)
        else -> Color.Red
    }

    Text(
        modifier = modifier,
        text = "FPS: $fps",
        color = color
    )

    LaunchedEffect(Unit) {
        coroutineScope {
            while (true) {
                withFrameMillis { frameTimeMillis ->
                    if (lastFrameTime != 0L) {
                        val frameDuration = frameTimeMillis - lastFrameTime
                        totalFrameTime += frameDuration
                        frameCount++
                        if (totalFrameTime >= 1000L) {
                            fps = frameCount
                            frameCount = 0
                            totalFrameTime = 0L
                        }
                    }
                    lastFrameTime = frameTimeMillis
                }
            }
        }
    }
}