import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.LazyColumn
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.extra.SuperArrow
import top.yukonga.miuix.kmp.extra.SuperDialog
import top.yukonga.miuix.kmp.extra.SuperDropdown
import top.yukonga.miuix.kmp.extra.SuperSwitch
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.MiuixPopupUtils.Companion.dismissDialog
import top.yukonga.miuix.kmp.utils.getWindowSize
import utils.VersionInfo

@Composable
fun ThirdPage(
    topAppBarScrollBehavior: ScrollBehavior,
    padding: PaddingValues,
    showFPSMonitor: Boolean,
    onShowFPSMonitorChange: (Boolean) -> Unit,
    showTopAppBar: Boolean,
    onShowTopAppBarChange: (Boolean) -> Unit,
    showBottomBar: Boolean,
    onShowBottomBarChange: (Boolean) -> Unit,
    showFloatingActionButton: Boolean,
    onShowFloatingActionButtonChange: (Boolean) -> Unit,
    enablePageUserScroll: Boolean,
    onEnablePageUserScrollChange: (Boolean) -> Unit,
    colorMode: MutableState<Int>
) {
    val showDialog = remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier.height(getWindowSize().height.dp),
        contentPadding = PaddingValues(top = padding.calculateTopPadding()),
        topAppBarScrollBehavior = topAppBarScrollBehavior
    ) {
        item {
            Card(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                SuperSwitch(
                    title = "Show FPS Monitor",
                    checked = showFPSMonitor,
                    onCheckedChange = onShowFPSMonitorChange
                )
                SuperSwitch(
                    title = "Show Top App Bar",
                    checked = showTopAppBar,
                    onCheckedChange = onShowTopAppBarChange
                )
                SuperSwitch(
                    title = "Show Bottom Bar",
                    checked = showBottomBar,
                    onCheckedChange = onShowBottomBarChange
                )
                SuperSwitch(
                    title = "Show Floating Action Button",
                    checked = showFloatingActionButton,
                    onCheckedChange = onShowFloatingActionButtonChange
                )
                SuperSwitch(
                    title = "Enable Page User Scroll",
                    checked = enablePageUserScroll,
                    onCheckedChange = onEnablePageUserScrollChange
                )
                SuperDropdown(
                    title = "Color Mode",
                    items = listOf("System", "Light", "Dark"),
                    selectedIndex = colorMode.value,
                    onSelectedIndexChange = { colorMode.value = it }
                )
            }
            Card(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            ) {
                SuperArrow(
                    title = "About",
                    summary = "About this app",
                    onClick = {
                        showDialog.value = true
                    }
                )
            }
        }
    }
    dialog(showDialog)
}

@Composable
fun dialog(showDialog: MutableState<Boolean>) {
    SuperDialog(
        title = "About",
        show = showDialog,
        onDismissRequest = { dismissDialog(showDialog) },
        content = {
            val uriHandler = LocalUriHandler.current
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
                text = "APP Version: " + VersionInfo.VERSION_NAME + " (" + VersionInfo.VERSION_CODE + ")"
                        + "\nJDK Version: " + VersionInfo.JDK_VERSION
            )
            Card(
                color = MiuixTheme.colorScheme.secondaryContainer,
            ) {
                SuperArrow(
                    title = "View Source",
                    rightText = "GitHub",
                    onClick = {
                        uriHandler.openUri("https://github.com/miuix-kotlin-multiplatform/miuix")
                    }

                )
                SuperArrow(
                    title = "Join Group",
                    rightText = "Telegram",
                    onClick = {
                        uriHandler.openUri("https://t.me/YuKongA13579")
                    }
                )
            }
        }
    )
}