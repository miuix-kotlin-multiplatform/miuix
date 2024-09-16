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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.yukonga.miuix.kmp.basic.Text

// This is a simple FPS monitor that displays the current frames per second.
@Composable
fun FPSMonitor(modifier: Modifier = Modifier) {
    var fps by remember { mutableStateOf(0) }
    var lastFrameTime by remember { mutableStateOf(0L) }
    var frameCount by remember { mutableStateOf(0) }
    var totalFrameTime by remember { mutableStateOf(0L) }

    Text(
        text = "FPS: $fps",
        modifier = modifier,
        color = if (fps < 57) Color.Red else Color.Green
    )

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
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