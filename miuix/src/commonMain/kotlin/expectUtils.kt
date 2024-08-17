import androidx.compose.runtime.Composable

data class WindowSize(val width: Int, val height: Int)


/** Returns the current window size */
@Composable
expect fun getWindowSize(): WindowSize


/** Returns the current platform name */
expect fun platform(): Platform

enum class Platform {
    Android,
    IOS,
    Desktop,
    WasmJs
}