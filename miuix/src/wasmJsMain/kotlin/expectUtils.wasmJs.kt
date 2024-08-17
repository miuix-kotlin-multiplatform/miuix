import androidx.compose.runtime.Composable
import kotlinx.browser.window

@Composable
actual fun getWindowSize(): WindowSize {
    return WindowSize(window.innerWidth, window.innerHeight)
}

actual fun platform(): Platform = Platform.WasmJs