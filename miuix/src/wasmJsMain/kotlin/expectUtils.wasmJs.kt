import androidx.compose.runtime.Composable
import kotlinx.browser.window

@Composable
actual fun getWindowSize(): WindowSize {
    return WindowSize(window.innerWidth, window.innerHeight)
}

@Composable
actual fun platform(): String  = "WasmJs"