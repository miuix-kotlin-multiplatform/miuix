package top.yukonga.miuix.uitest

import App
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val displayMetrics = Resources.getSystem().displayMetrics
        val widthPixels = displayMetrics.widthPixels
        val heightPixels = displayMetrics.heightPixels
        val widthInches = widthPixels / displayMetrics.xdpi
        val heightInches = heightPixels / displayMetrics.ydpi
        val diagonalPixel = sqrt(widthPixels.toDouble().pow(2.0) + heightPixels.toDouble().pow(2.0))
        val screenInches = sqrt(widthInches.toDouble().pow(2.0) + heightInches.toDouble().pow(2.0))
        val actualDensityDpi = diagonalPixel / screenInches
        val actualDensity = actualDensityDpi / DisplayMetrics.DENSITY_DEFAULT

        val metrics = resources.displayMetrics
        metrics.density = actualDensity.toFloat()
        metrics.densityDpi = actualDensityDpi.toInt()
        resources.displayMetrics.setTo(metrics)

        setContent {
            val colorMode = remember { mutableIntStateOf(0) }
            val darkMode = colorMode.intValue == 2 || (isSystemInDarkTheme() && colorMode.intValue == 0)

            DisposableEffect(darkMode) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { darkMode },
                    navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { darkMode },
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    window.isNavigationBarContrastEnforced = false // Xiaomi moment, this code must be here
                }

                onDispose {}
            }

            App(colorMode)
        }
    }

}