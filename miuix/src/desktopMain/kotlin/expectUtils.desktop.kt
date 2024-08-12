import androidx.compose.runtime.Composable
import androidx.compose.ui.window.WindowState

object WindowSizeProvider {
    private var windowState: WindowState? = null

    fun init(currentWindowState: WindowState) {
        windowState = currentWindowState
    }

    fun getWindowSize(): WindowSize {
        return WindowSize(windowState?.size?.width?.value?.toInt() ?: 0, windowState?.size?.height?.value?.toInt() ?: 0)
    }
}

@Composable
actual fun getWindowSize(): WindowSize {
    return WindowSizeProvider.getWindowSize()
}