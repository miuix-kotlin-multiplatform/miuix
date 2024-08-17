import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.window.layout.WindowMetrics
import androidx.window.layout.WindowMetricsCalculator

@Composable
actual fun getWindowSize(): WindowSize {
    val context = LocalContext.current
    val windowMetrics: WindowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context)

    val widthPx = windowMetrics.bounds.width()
    val heightPx = windowMetrics.bounds.height()

    return WindowSize(widthPx, heightPx)
}

actual fun platform(): Platform = Platform.Android
