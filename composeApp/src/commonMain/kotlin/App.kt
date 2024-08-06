import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.ui.MiuixTheme
import top.yukonga.miuix.kmp.ui.darkColorScheme
import top.yukonga.miuix.kmp.ui.lightColorScheme

@Composable
fun App() {
    MiuixTheme(
        colors = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Box(
            modifier = Modifier
                .background(color = MiuixTheme.colors.background)
                .displayCutoutPadding()
                .systemBarsPadding()
                .imePadding()
                .fillMaxSize()
                .padding(horizontal = 18.dp)
        ) {
            LazyColumn {
                item {
                    Test()
                }
            }
        }
    }
}