import android.os.Build
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity

@Composable
actual fun getWindowSize(): WindowSize {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp

    val density = LocalDensity.current
    val statusBarPx = with(density) { WindowInsets.statusBars.asPaddingValues().calculateTopPadding().toPx() }.toInt()
    val navigationBarPx = with(density) { WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().toPx() }.toInt()

    val width = screenWidthDp * density.density
    var height = screenHeightDp * density.density

    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        height += statusBarPx + navigationBarPx // 如果应用 targetSDK >= Android15，则 Configuration 不再排除系统栏
    }

    return WindowSize(width.toInt(), height.toInt())
}