import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import top.yukonga.miuix.kmp.MiuixScrollBehavior
import top.yukonga.miuix.kmp.MiuixSuperSwitch
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuix.kmp.utils.rememberOverscrollFlingBehavior

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
    onEnableOverScrollChange: (Boolean) -> Unit
) {
    val scrollLazyColumnState = rememberLazyListState()

    LazyColumn(
        state = scrollLazyColumnState,
        flingBehavior = rememberOverscrollFlingBehavior { scrollLazyColumnState },
        contentPadding = PaddingValues(top = padding.calculateTopPadding()),
        modifier = if (enableOverScroll) {
            Modifier
                .overScrollVertical(onOverscroll = { topAppBarScrollBehavior.isPinned = it })
        } else {
            Modifier
        }
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
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
                onCheckedChange = onEnablePageUserScrollChange
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
            if (platform() != "WasmJs" && platform() != "Desktop") MiuixSuperSwitch(
                title = "Enable Over Scroll",
                checked = enableOverScroll,
                onCheckedChange = onEnableOverScrollChange
            )
        }
    }
}