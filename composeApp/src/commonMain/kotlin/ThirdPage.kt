import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.MiuixScrollBehavior
import top.yukonga.miuix.kmp.MiuixSuperDropdown
import top.yukonga.miuix.kmp.MiuixSuperSwitch
import top.yukonga.miuix.kmp.basic.MiuixLazyColumn
import top.yukonga.miuix.kmp.utils.enableOverscroll

@Composable
fun ThirdPage(
    topAppBarScrollBehavior: MiuixScrollBehavior,
    padding: PaddingValues,
    showFPSMonitor: Boolean,
    onShowFPSMonitorChange: (Boolean) -> Unit,
    showTopAppBar: Boolean,
    onShowTopAppBarChange: (Boolean) -> Unit,
    showBottomBar: Boolean,
    onShowBottomBarChange: (Boolean) -> Unit,
    enablePageUserScroll: Boolean,
    onEnablePageUserScrollChange: (Boolean) -> Unit,
    enableTopBarBlur: Boolean,
    onEnableTopBarBlurChange: (Boolean) -> Unit,
    enableBottomBarBlur: Boolean,
    onEnableBottomBarBlurChange: (Boolean) -> Unit,
    enableOverScroll: Boolean,
    onEnableOverScrollChange: (Boolean) -> Unit,
    colorMode: MutableState<Int>
) {
    MiuixLazyColumn(
        modifier = Modifier.height(getWindowSize().height.dp),
        enableOverScroll = enableOverScroll,
        contentPadding = PaddingValues(top = padding.calculateTopPadding()),
        topAppBarScrollBehavior = topAppBarScrollBehavior
    ) {
        item {
            MiuixSuperSwitch(
                title = "Show FPS Monitor",
                checked = showFPSMonitor,
                onCheckedChange = onShowFPSMonitorChange
            )
            MiuixSuperSwitch(
                title = "Show Top App Bar",
                checked = showTopAppBar,
                onCheckedChange = onShowTopAppBarChange
            )
            MiuixSuperSwitch(
                title = "Show Bottom Bar",
                checked = showBottomBar,
                onCheckedChange = onShowBottomBarChange
            )
            MiuixSuperSwitch(
                title = "Enable Page User Scroll",
                checked = enablePageUserScroll,
                onCheckedChange = onEnablePageUserScrollChange,
                enabled = platform() != Platform.WasmJs,
            )
            MiuixSuperSwitch(
                title = "Enable Top Bar Blur",
                checked = enableTopBarBlur,
                onCheckedChange = onEnableTopBarBlurChange
            )
            MiuixSuperSwitch(
                title = "Enable Bottom Bar Blur",
                checked = enableBottomBarBlur,
                onCheckedChange = onEnableBottomBarBlurChange
            )
            MiuixSuperSwitch(
                title = "Enable Over Scroll",
                checked = if (enableOverscroll()) enableOverScroll else false,
                onCheckedChange = onEnableOverScrollChange,
                enabled = enableOverscroll(),
            )
            MiuixSuperDropdown(
                title = "Color Mode",
                items = listOf("System", "Light", "Dark"),
                selectedIndex = colorMode.value,
                onSelectedIndexChange = { colorMode.value = it }
            )
        }
    }
}