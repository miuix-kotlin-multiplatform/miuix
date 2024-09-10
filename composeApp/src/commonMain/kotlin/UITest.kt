import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.MiuixFloatingActionButton
import top.yukonga.miuix.kmp.MiuixNavigationBar
import top.yukonga.miuix.kmp.MiuixScrollBehavior
import top.yukonga.miuix.kmp.MiuixTopAppBar
import top.yukonga.miuix.kmp.NavigationItem
import top.yukonga.miuix.kmp.basic.MiuixHorizontalPager
import top.yukonga.miuix.kmp.basic.MiuixScaffold
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.GitHub
import top.yukonga.miuix.kmp.rememberMiuixTopAppBarState
import top.yukonga.miuix.kmp.theme.MiuixTheme
import utils.FPSMonitor

@OptIn(FlowPreview::class)
@Composable
fun UITest(
    colorMode: MutableState<Int>,
) {
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
        else -> topAppBarScrollBehaviorList[2]
    }

    val items = listOf(
        NavigationItem("HomePage", Icons.Rounded.Home),
        NavigationItem("DropDown", Icons.Rounded.Star),
        NavigationItem("Settings", Icons.Rounded.Settings)
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.debounce(150).collectLatest {
            targetPage = pagerState.currentPage
        }
    }

    val showFPSMonitor = remember { mutableStateOf(false) }
    val showTopAppBar = remember { mutableStateOf(true) }
    val showBottomBar = remember { mutableStateOf(true) }
    val showFloatingActionButton = remember { mutableStateOf(true) }
    val enablePageUserScroll = remember { mutableStateOf(false) }
    val enableTopBarBlur = remember { mutableStateOf(true) }
    val enableBottomBarBlur = remember { mutableStateOf(true) }

    val uriHandler = LocalUriHandler.current

    MiuixScaffold(
        modifier = Modifier.fillMaxSize(),
        enableTopBarBlur = enableTopBarBlur.value,
        enableBottomBarBlur = enableBottomBarBlur.value,
        topBar = {
            AnimatedVisibility(
                visible = showTopAppBar.value,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                MiuixTopAppBar(
                    color = if (enableTopBarBlur.value) Color.Transparent else MiuixTheme.colorScheme.background,
                    title = "Miuix",
                    scrollBehavior = currentScrollBehavior
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar.value,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
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
        floatingActionButton = {
            AnimatedVisibility(
                visible = showFloatingActionButton.value,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally()
            ) {
                MiuixFloatingActionButton(
                    onClick = {
                        uriHandler.openUri("https://github.com/miuix-kotlin-multiplatform/miuix")
                    }
                ) {
                    Icon(
                        imageVector = MiuixIcons.GitHub,
                        tint = Color.White,
                        contentDescription = "GitHub"
                    )
                }
            }
        }
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
            showFloatingActionButton = showFloatingActionButton.value,
            onShowFloatingActionButtonChange = { showFloatingActionButton.value = it },
            enablePageUserScroll = enablePageUserScroll.value,
            onEnablePageUserScrollChange = { enablePageUserScroll.value = it },
            enableTopBarBlur = enableTopBarBlur.value,
            onEnableTopBarBlurChange = { enableTopBarBlur.value = it },
            enableBottomBarBlur = enableBottomBarBlur.value,
            onEnableBottomBarBlurChange = { enableBottomBarBlur.value = it },
            colorMode = colorMode,
        )
    }

    AnimatedVisibility(
        visible = showFPSMonitor.value,
        enter = fadeIn() + expandHorizontally(),
        exit = fadeOut() + shrinkHorizontally()
    ) {
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
    showFloatingActionButton: Boolean,
    onShowFloatingActionButtonChange: (Boolean) -> Unit,
    enablePageUserScroll: Boolean,
    onEnablePageUserScrollChange: (Boolean) -> Unit,
    enableTopBarBlur: Boolean,
    onEnableTopBarBlurChange: (Boolean) -> Unit,
    enableBottomBarBlur: Boolean,
    onEnableBottomBarBlurChange: (Boolean) -> Unit,
    colorMode: MutableState<Int>
) {
    MiuixHorizontalPager(
        modifier = modifier,
        pagerState = pagerState,
        userScrollEnabled = enablePageUserScroll,
        pageContent = { page ->
            when (page) {
                0 -> MainPage(
                    topAppBarScrollBehavior = topAppBarScrollBehaviorList[0],
                    padding = padding
                )

                1 -> SecondPage(
                    topAppBarScrollBehavior = topAppBarScrollBehaviorList[1],
                    padding = padding
                )

                else -> ThirdPage(
                    topAppBarScrollBehavior = topAppBarScrollBehaviorList[2],
                    padding = padding,
                    showFPSMonitor = showFPSMonitor,
                    onShowFPSMonitorChange = onShowFPSMonitorChange,
                    showTopAppBar = showTopAppBar,
                    onShowTopAppBarChange = onShowTopAppBarChange,
                    showBottomBar = showBottomBar,
                    onShowBottomBarChange = onShowBottomBarChange,
                    showFloatingActionButton = showFloatingActionButton,
                    onShowFloatingActionButtonChange = onShowFloatingActionButtonChange,
                    enablePageUserScroll = enablePageUserScroll,
                    onEnablePageUserScrollChange = onEnablePageUserScrollChange,
                    enableTopBarBlur = enableTopBarBlur,
                    onEnableTopBarBlurChange = onEnableTopBarBlurChange,
                    enableBottomBarBlur = enableBottomBarBlur,
                    onEnableBottomBarBlurChange = onEnableBottomBarBlurChange,
                    colorMode = colorMode
                )
            }
        }
    )
}