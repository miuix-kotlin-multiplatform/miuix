import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIScreen
import kotlin.math.roundToInt

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun getWindowSize(): WindowSize {
    val screenBounds = UIScreen.mainScreen.bounds
    val density = LocalDensity.current.density
    val width = screenBounds.useContents { size.width } * density
    val height = screenBounds.useContents { size.height } * density
    return WindowSize(width.roundToInt(), height.roundToInt())
}

actual fun platform(): Platform = Platform.IOS