import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.basic.FloatingActionButton
import top.yukonga.miuix.kmp.basic.FloatingToolbar
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.IconButton
import top.yukonga.miuix.kmp.basic.ListPopup
import top.yukonga.miuix.kmp.basic.ListPopupColumn
import top.yukonga.miuix.kmp.basic.ListPopupDefaults
import top.yukonga.miuix.kmp.basic.FabPosition
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationItem
import top.yukonga.miuix.kmp.basic.PopupPositionProvider
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.SmallTopAppBar
import top.yukonga.miuix.kmp.basic.ToolbarPosition
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.extra.DropdownImpl
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.basic.Check
import top.yukonga.miuix.kmp.icon.icons.other.GitHub
import top.yukonga.miuix.kmp.icon.icons.useful.ImmersionMore
import top.yukonga.miuix.kmp.icon.icons.useful.NavigatorSwitch
import top.yukonga.miuix.kmp.icon.icons.useful.Order
import top.yukonga.miuix.kmp.icon.icons.useful.Settings
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.MiuixPopupUtils.Companion.dismissPopup
import utils.FPSMonitor

data class UIState(
    val showFPSMonitor: Boolean = false,
    val showTopAppBar: Boolean = true,
    val showBottomBar: Boolean = true,
    val useFloatingToolbar: Boolean = false,
    val floatingToolbarPosition: Int = 7,
    val showFloatingActionButton: Boolean = true,
    val floatingActionButtonPosition: Int = 2,
    val enablePageUserScroll: Boolean = false,
    val isTopPopupExpanded: Boolean = false
)

@Composable
fun UITest(
    colorMode: MutableState<Int>,
) {
    val topAppBarScrollBehaviorList = List(3) { MiuixScrollBehavior() }
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()
    val selectedPage by remember { derivedStateOf { pagerState.currentPage } }
    val currentScrollBehavior = topAppBarScrollBehaviorList[selectedPage]

    val items = remember {
        listOf(
            NavigationItem("HomePage", MiuixIcons.Useful.NavigatorSwitch),
            NavigationItem("DropDown", MiuixIcons.Useful.Order),
            NavigationItem("Settings", MiuixIcons.Useful.Settings)
        )
    }

    var uiState by remember { mutableStateOf(UIState()) }
    val showTopPopup = remember { mutableStateOf(false) }

    val hazeState = remember { HazeState() }
    val hazeStyle = HazeStyle(
        backgroundColor = MiuixTheme.colorScheme.background,
        tint = HazeTint(MiuixTheme.colorScheme.background.copy(0.67f))
    )

    val uriHandler = LocalUriHandler.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AnimatedVisibility(
                visible = uiState.showTopAppBar,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                AppTopBar(
                    selectedPage = selectedPage,
                    hazeState = hazeState,
                    hazeStyle = hazeStyle,
                    currentScrollBehavior = currentScrollBehavior,
                    items = items,
                    isTopPopupExpanded = uiState.isTopPopupExpanded,
                    showTopPopup = showTopPopup,
                    onPopupExpandedChange = { uiState = uiState.copy(isTopPopupExpanded = it) },
                    onPageSelected = { page ->
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    }
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = uiState.showBottomBar && !uiState.useFloatingToolbar,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                NavigationBar(
                    modifier = Modifier
                        .hazeEffect(hazeState) {
                            style = hazeStyle
                            blurRadius = 25.dp
                            noiseFactor = 0f
                        },
                    items = items,
                    selected = selectedPage,
                    onClick = { index ->
                        if (index in 0..items.lastIndex) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            if (uiState.showFloatingActionButton) {
                FloatingActionButton(
                    onClick = {
                        uriHandler.openUri("https://github.com/miuix-kotlin-multiplatform/miuix")
                    }
                ) {
                    Icon(
                        imageVector = MiuixIcons.Other.GitHub,
                        tint = Color.White,
                        contentDescription = "GitHub"
                    )
                }
            }
        },
        floatingActionButtonPosition = when (uiState.floatingActionButtonPosition) {
            0 -> FabPosition.Start
            1 -> FabPosition.Center
            2 -> FabPosition.End
            else -> FabPosition.EndOverlay
        }
    ) { padding ->
        AppHorizontalPager(
            modifier = Modifier
                .imePadding()
                .hazeSource(state = hazeState)
                .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
                .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Horizontal)),
            pagerState = pagerState,
            topAppBarScrollBehaviorList = topAppBarScrollBehaviorList,
            padding = padding,
            uiState = uiState,
            onUiStateChange = { uiState = it },
            colorMode = colorMode,
        )
    }

    AnimatedVisibility(
        visible = uiState.useFloatingToolbar,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + expandVertically(),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { it }) + shrinkVertically()
    ) {
        FloatingToolbar(
            position = when (uiState.floatingToolbarPosition) {
                0 -> ToolbarPosition.LeftTop
                1 -> ToolbarPosition.LeftCenter
                2 -> ToolbarPosition.LeftBottom
                3 -> ToolbarPosition.RightTop
                4 -> ToolbarPosition.RightCenter
                5 -> ToolbarPosition.RightBottom
                6 -> ToolbarPosition.BottomLeft
                7 -> ToolbarPosition.BottomCenter
                8 -> ToolbarPosition.BottomRight
                else -> ToolbarPosition.BottomCenter
            },
            modifier = Modifier.hazeEffect(hazeState) {
                style = hazeStyle
                blurRadius = 25.dp
                noiseFactor = 0f
            },
            color = MiuixTheme.colorScheme.background.copy(0.67f)
        ) {
            listOf(
                MiuixIcons.Useful.NavigatorSwitch to 0,
                MiuixIcons.Useful.Order to 1,
                MiuixIcons.Useful.Settings to 2
            ).forEach { (icon, pageIndex) ->
                IconButton(
                    modifier = Modifier.size(48.dp),
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pageIndex)
                        }
                    }
                ) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = if (selectedPage == pageIndex) MiuixTheme.colorScheme.onSurfaceContainer else MiuixTheme.colorScheme.onSurfaceContainerVariant
                    )
                }
            }
        }
    }

    AnimatedVisibility(
        visible = uiState.showFPSMonitor,
        enter = fadeIn() + expandHorizontally(),
        exit = fadeOut() + shrinkHorizontally()
    ) {
        FPSMonitor(
            modifier = Modifier
                .statusBarsPadding()
                .captionBarPadding()
                .padding(horizontal = 4.dp)
        )
    }
}

@Composable
private fun AppTopBar(
    selectedPage: Int,
    hazeState: HazeState,
    hazeStyle: HazeStyle,
    currentScrollBehavior: ScrollBehavior,
    items: List<NavigationItem>,
    isTopPopupExpanded: Boolean,
    showTopPopup: MutableState<Boolean>,
    onPopupExpandedChange: (Boolean) -> Unit,
    onPageSelected: (Int) -> Unit
) {
    LocalHapticFeedback.current

    BoxWithConstraints {
        if (maxWidth > 840.dp) {
            SmallTopAppBar(
                title = "Miuix",
                modifier = Modifier.hazeEffect(state = hazeState) {
                    style = hazeStyle
                    blurRadius = 25.dp
                    noiseFactor = 0f
                },
                scrollBehavior = currentScrollBehavior,
                color = Color.Transparent,
                actions = {
                    TopAppBarActions(
                        selectedPage = selectedPage,
                        items = items,
                        isTopPopupExpanded = isTopPopupExpanded,
                        showTopPopup = showTopPopup,
                        onPopupExpandedChange = onPopupExpandedChange,
                        onPageSelected = onPageSelected
                    )
                }
            )
        } else {
            TopAppBar(
                title = "Miuix",
                modifier = Modifier
                    .hazeEffect(state = hazeState) {
                        style = hazeStyle
                        blurRadius = 25.dp
                        noiseFactor = 0f
                    },
                scrollBehavior = currentScrollBehavior,
                color = Color.Transparent,
                actions = {
                    TopAppBarActions(
                        selectedPage = selectedPage,
                        items = items,
                        isTopPopupExpanded = isTopPopupExpanded,
                        showTopPopup = showTopPopup,
                        onPopupExpandedChange = onPopupExpandedChange,
                        onPageSelected = onPageSelected
                    )
                }
            )
        }
    }
}

@Composable
private fun TopAppBarActions(
    selectedPage: Int,
    items: List<NavigationItem>,
    isTopPopupExpanded: Boolean,
    showTopPopup: MutableState<Boolean>,
    onPopupExpandedChange: (Boolean) -> Unit,
    onPageSelected: (Int) -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current

    if (isTopPopupExpanded) {
        ListPopup(
            show = showTopPopup,
            popupPositionProvider = ListPopupDefaults.ContextMenuPositionProvider,
            alignment = PopupPositionProvider.Align.TopRight,
            onDismissRequest = {
                onPopupExpandedChange(false)
            }
        ) {
            ListPopupColumn {
                items.take(3).forEachIndexed { index, navigationItem ->
                    DropdownImpl(
                        text = navigationItem.label,
                        optionSize = items.take(3).size,
                        isSelected = index == selectedPage,
                        onSelectedIndexChange = {
                            onPageSelected(index)
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                            dismissPopup(showTopPopup)
                            onPopupExpandedChange(false)
                        },
                        index = index
                    )
                }
            }
        }
        showTopPopup.value = true
    }

    IconButton(
        modifier = Modifier.padding(end = 20.dp),
        onClick = {
            onPopupExpandedChange(true)
        },
        holdDownState = isTopPopupExpanded
    ) {
        Icon(
            imageVector = MiuixIcons.Useful.ImmersionMore,
            tint = MiuixTheme.colorScheme.onBackground,
            contentDescription = "More"
        )
    }
}

@Composable
fun AppHorizontalPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    topAppBarScrollBehaviorList: List<ScrollBehavior>,
    padding: PaddingValues,
    uiState: UIState,
    onUiStateChange: (UIState) -> Unit,
    colorMode: MutableState<Int>
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        userScrollEnabled = uiState.enablePageUserScroll,
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
                    showFPSMonitor = uiState.showFPSMonitor,
                    onShowFPSMonitorChange = { onUiStateChange(uiState.copy(showFPSMonitor = it)) },
                    showTopAppBar = uiState.showTopAppBar,
                    onShowTopAppBarChange = { onUiStateChange(uiState.copy(showTopAppBar = it)) },
                    showBottomBar = uiState.showBottomBar,
                    onShowBottomBarChange = { onUiStateChange(uiState.copy(showBottomBar = it)) },
                    useFloatingToolbar = uiState.useFloatingToolbar,
                    onUseFloatingToolbarChange = { onUiStateChange(uiState.copy(useFloatingToolbar = it)) },
                    floatingToolbarPosition = uiState.floatingToolbarPosition,
                    onFloatingToolbarPositionChange = { onUiStateChange(uiState.copy(floatingToolbarPosition = it)) },
                    showFloatingActionButton = uiState.showFloatingActionButton,
                    onShowFloatingActionButtonChange = { onUiStateChange(uiState.copy(showFloatingActionButton = it)) },
                    fabPosition = uiState.floatingActionButtonPosition,
                    onFabPositionChange = { onUiStateChange(uiState.copy(floatingActionButtonPosition = it)) },
                    enablePageUserScroll = uiState.enablePageUserScroll,
                    onEnablePageUserScrollChange = { onUiStateChange(uiState.copy(enablePageUserScroll = it)) },
                    colorMode = colorMode
                )
            }
        }
    )
}
