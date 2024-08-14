import androidx.compose.runtime.Composable
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.platform.LocalDensity

object WindowSizeProvider {
    private var composeWindow: ComposeWindow? = null

    fun init(window: ComposeWindow) {
        composeWindow = window
    }

    fun getWindowSize(): WindowSize {
        return composeWindow?.let { window ->
            WindowSize(
                width = window.bounds.width,
                height = window.bounds.height - window.insets.top
            )
        } ?: WindowSize(0, 0)
    }
}

@Composable
actual fun getWindowSize(): WindowSize {
    val density = LocalDensity.current.density
    val windowSize = WindowSizeProvider.getWindowSize()
    return WindowSize(
        width = (windowSize.width * density).toInt(),
        height = (windowSize.height * density).toInt()
    )
}