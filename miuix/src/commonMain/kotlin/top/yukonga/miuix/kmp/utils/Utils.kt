package top.yukonga.miuix.kmp.utils

import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun createRipple() = ripple(color = MiuixTheme.colorScheme.onBackground.copy(alpha = 0.8f))
