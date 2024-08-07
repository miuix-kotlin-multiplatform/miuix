import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity

@Composable
actual fun getWindowSize(): WindowSize {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp
    val density = LocalDensity.current.density
    val width = screenWidthDp * density
    val height = screenHeightDp * density
    return WindowSize(width.toInt(), height.toInt())
}