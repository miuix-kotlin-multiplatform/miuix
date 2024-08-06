import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import top.yukonga.miuix.kmp.ui.MiuixTheme
import top.yukonga.miuix.kmp.ui.darkColorScheme
import top.yukonga.miuix.kmp.ui.lightColorScheme

@Composable
fun App() {
    MiuixTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        UITest()
    }
}



