import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.MiuixNavigationBar
import top.yukonga.miuix.kmp.MiuixScrollBehavior
import top.yukonga.miuix.kmp.MiuixTopAppBar
import top.yukonga.miuix.kmp.NavigationItem
import top.yukonga.miuix.kmp.basic.MiuixHorizontalPager
import top.yukonga.miuix.kmp.basic.MiuixScaffold
import top.yukonga.miuix.kmp.basic.MiuixSurface
import top.yukonga.miuix.kmp.rememberMiuixTopAppBarState
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.MiuixDialogUtil.Companion.MiuixDialogHost
import utils.FPSMonitor

@OptIn(FlowPreview::class)
@Composable
fun UITest() {
    val topAppBarScrollBehavior0 = MiuixScrollBehavior(rememberMiuixTopAppBarState())
    val topAppBarScrollBehavior1 = MiuixScrollBehavior(rememberMiuixTopAppBarState())
    val topAppBarScrollBehavior2 = MiuixScrollBehavior(rememberMiuixTopAppBarState())

    val topAppBarScrollBehaviorList = listOf(
        topAppBarScrollBehavior0, topAppBarScrollBehavior1, topAppBarScrollBehavior2
    )

    val pagerState = rememberPagerState(pageCount = { 3 })
    var targetPage by remember { mutableStateOf(pagerState.currentPage) }
    val coroutineScope = rememberCoroutineScope()

    val currentScrollBehavior = when (pagerState.currentPage) {
        0 -> topAppBarScrollBehaviorList[0]
        1 -> topAppBarScrollBehaviorList[1]
        2 -> topAppBarScrollBehaviorList[2]
        else -> throw IllegalStateException("Unsupported page")
    }

    val items = listOf(
        NavigationItem("Main", Icons.Default.Phone),
        NavigationItem("Second", Icons.Default.Email),
        NavigationItem("Settings", Icons.Default.Settings)
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.debounce(150).collectLatest {
            targetPage = pagerState.currentPage
        }
    }

    val showFPSMonitor = remember { mutableStateOf(false) }
    val showTopAppBar = remember { mutableStateOf(true) }
    val showBottomBar = remember { mutableStateOf(true) }
    val enablePageUserScroll = remember { mutableStateOf(true) }
    val enableTopBarBlur = remember { mutableStateOf(true) }
    val enableBottomBarBlur = remember { mutableStateOf(true) }
    val enableOverScroll = remember { mutableStateOf(true) }

    MiuixSurface {
        MiuixScaffold(
            modifier = Modifier.fillMaxSize(),
            enableTopBarBlur = enableTopBarBlur.value,
            enableBottomBarBlur = enableBottomBarBlur.value,
            topBar = {
                if (showTopAppBar.value) {
                    MiuixTopAppBar(
                        color = if (enableTopBarBlur.value) Color.Transparent else MiuixTheme.colorScheme.background,
                        title = "Miuix",
                        scrollBehavior = currentScrollBehavior
                    )
                }
            },
            bottomBar = {
                if (showBottomBar.value) {
                    MiuixNavigationBar(
                        color = if (enableBottomBarBlur.value) Color.Transparent else MiuixTheme.colorScheme.background,
                        items = items,
                        selected = targetPage,
                        onClick = { index ->
                            targetPage = index
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            },
            dialogHost = { MiuixDialogHost() }
        ) { padding ->
            AppHorizontalPager(
                pagerState = pagerState,
                topAppBarScrollBehaviorList = topAppBarScrollBehaviorList,
                padding = padding,
                showFPSMonitor = showFPSMonitor.value,
                onShowFPSMonitorChange = { showFPSMonitor.value = it },
                showTopAppBar = showTopAppBar.value,
                onShowTopAppBarChange = { showTopAppBar.value = it },
                showBottomBar = showBottomBar.value,
                onShowBottomBarChange = { showBottomBar.value = it },
                enablePageUserScroll = enablePageUserScroll.value,
                onEnablePageUserScrollChange = { enablePageUserScroll.value = it },
                enableTopBarBlur = enableTopBarBlur.value,
                onEnableTopBarBlurChange = { enableTopBarBlur.value = it },
                enableBottomBarBlur = enableBottomBarBlur.value,
                onEnableBottomBarBlurChange = { enableBottomBarBlur.value = it },
                enableOverScroll = enableOverScroll.value,
                onEnableOverScrollChange = { enableOverScroll.value = it }
            )
        }
    }

    if (showFPSMonitor.value) {
        FPSMonitor(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 28.dp)
        )
    }
}

@Composable
fun AppHorizontalPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    topAppBarScrollBehaviorList: List<MiuixScrollBehavior>,
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
    MiuixHorizontalPager(
        modifier = modifier,
        pagerState = pagerState,
        userScrollEnabled = enablePageUserScroll,
        pageContent = { page ->
            when (page) {
                0 -> MainPage(
                    topAppBarScrollBehavior = topAppBarScrollBehaviorList[0],
                    padding = padding,
                    enableOverScroll = enableOverScroll,
                )
                1 -> SecondPage(
                    topAppBarScrollBehavior = topAppBarScrollBehaviorList[1],
                    padding = padding,
                    enableOverScroll = enableOverScroll,
                )
                2 -> ThirdPage(
                    topAppBarScrollBehavior = topAppBarScrollBehaviorList[2],
                    padding = padding,
                    showFPSMonitor = showFPSMonitor,
                    onShowFPSMonitorChange = onShowFPSMonitorChange,
                    showTopAppBar = showTopAppBar,
                    onShowTopAppBarChange = onShowTopAppBarChange,
                    showBottomBar = showBottomBar,
                    onShowBottomBarChange = onShowBottomBarChange,
                    enablePageUserScroll = enablePageUserScroll,
                    onEnablePageUserScrollChange = onEnablePageUserScrollChange,
                    enableTopBarBlur = enableTopBarBlur,
                    onEnableTopBarBlurChange = onEnableTopBarBlurChange,
                    enableBottomBarBlur = enableBottomBarBlur,
                    onEnableBottomBarBlurChange = onEnableBottomBarBlurChange,
                    enableOverScroll = enableOverScroll,
                    onEnableOverScrollChange = onEnableOverScrollChange
                )

                else -> throw IllegalStateException("Unsupported page")
            }
        }
    )
}