import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.basic.Check
import top.yukonga.miuix.kmp.icon.icons.useful.Settings
import ui.AppTheme

@Composable
fun App(
    colorMode: MutableState<Int> = remember { mutableStateOf(0) }
) {
    AppTheme(colorMode = colorMode.value) {
        // 使用 Useful 分类下的 Settings 图标
        Icon(
            imageVector = MiuixIcons.Useful.Settings,
            contentDescription = "Settings",
            modifier = Modifier.size(24.dp)
        )

        // 使用 Basic 分类下的 Check 图标
        Icon(
            imageVector = MiuixIcons.Basic.Check,
            contentDescription = "Check",
            modifier = Modifier.size(24.dp)
        )
        //UITest(colorMode = colorMode)
    }
}
