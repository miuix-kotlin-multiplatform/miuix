import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import java.awt.Toolkit

@Composable
actual fun getWindowSize(): WindowSize {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val density = LocalDensity.current.density
    val width = screenSize.width * density
    val height = screenSize.height * density
    return WindowSize(width.toInt(), height.toInt())
}