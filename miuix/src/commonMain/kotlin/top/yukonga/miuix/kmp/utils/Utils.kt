package top.yukonga.miuix.kmp.utils

import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import top.yukonga.miuix.kmp.theme.MiuixTheme


val Dp.toPx: Float
    @Composable get() {
        val density = LocalDensity.current.density
        return density * value
    }

@Composable
fun createRipple() = ripple(color = MiuixTheme.colorScheme.onBackground.copy(alpha = 0.8f))
