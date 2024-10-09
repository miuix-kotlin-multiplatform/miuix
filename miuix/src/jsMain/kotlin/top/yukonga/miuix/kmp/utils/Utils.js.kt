package top.yukonga.miuix.kmp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.browser.window
import kotlin.math.roundToInt

@Composable
actual fun getWindowSize(): WindowSize {
    val density = LocalDensity.current.density
    val width = window.innerWidth * density
    val height = window.innerHeight * density
    return WindowSize(width.roundToInt(), height.roundToInt())
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