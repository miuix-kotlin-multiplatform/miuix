import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import kotlinx.browser.window
import kotlin.math.roundToInt

@Composable
actual fun getWindowSize(): WindowSize {
    val density = LocalDensity.current.density
    val width = window.innerWidth * density
    val height = window.innerHeight * density
    return WindowSize(width.roundToInt(), height.roundToInt())
}

actual fun platform(): Platform = Platform.WasmJs