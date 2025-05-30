// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import top.yukonga.miuix.kmp.basic.FabPosition
import top.yukonga.miuix.kmp.basic.FloatingActionButton
import top.yukonga.miuix.kmp.basic.FloatingNavigationBar
import top.yukonga.miuix.kmp.basic.FloatingNavigationBarMode
import top.yukonga.miuix.kmp.basic.FloatingToolbar
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.IconButton
import top.yukonga.miuix.kmp.basic.ListPopup
import top.yukonga.miuix.kmp.basic.ListPopupColumn
import top.yukonga.miuix.kmp.basic.ListPopupDefaults
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
import top.yukonga.miuix.kmp.icon.icons.other.GitHub
import top.yukonga.miuix.kmp.icon.icons.useful.Delete
import top.yukonga.miuix.kmp.icon.icons.useful.Edit
import top.yukonga.miuix.kmp.icon.icons.useful.ImmersionMore
import top.yukonga.miuix.kmp.icon.icons.useful.NavigatorSwitch
import top.yukonga.miuix.kmp.icon.icons.useful.Order
import top.yukonga.miuix.kmp.icon.icons.useful.Settings
import top.yukonga.miuix.kmp.theme.MiuixTheme
import utils.FPSMonitor

data class UIState(
    val showFPSMonitor: Boolean = false,
    val showTopAppBar: Boolean = true,
    val showNavigationBar: Boolean = true,
    val useFloatingNavigationBar: Boolean = false,
    val floatingNavigationBarMode: Int = 0,
    val floatingNavigationBarPosition: Int = 0,
    val showFloatingToolbar: Boolean = false,
    val floatingToolbarPosition: Int = 1,
    val floatingToolbarOrientation: Int = 1,
    val showFloatingActionButton: Boolean = false,
    val floatingActionButtonPosition: Int = 2,
    val enablePageUserScroll: Boolean = false,
    val scrollEndHaptic: Boolean = true
)

@Composable
fun UITest(
    colorMode: MutableState<Int>,
) {
    val topAppBarScrollBehaviorList = List(3) { MiuixScrollBehavior() }
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()
    var selectedPage by remember { mutableIntStateOf(pagerState.currentPage) }
    val currentScrollBehavior = topAppBarScrollBehaviorList[selectedPage]

    LaunchedEffect(pagerState.settledPage) {
        if (selectedPage != pagerState.settledPage) selectedPage = pagerState.settledPage
    }

    val navigationItem = remember {
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
                    items = navigationItem,
                    showTopPopup = showTopPopup,
                    onPageSelected = { page ->
                        selectedPage = page
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    }
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = uiState.showNavigationBar,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                AnimatedVisibility(
                    visible = !uiState.useFloatingNavigationBar,
                    enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
                    exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top)
                ) {
                    NavigationBar(
                        modifier = Modifier
                            .hazeEffect(hazeState) {
                                style = hazeStyle
                                blurRadius = 25.dp
                                noiseFactor = 0f
                            },
                        color = Color.Transparent,
                        items = navigationItem,
                        selected = selectedPage,
                        onClick = { index ->
                            selectedPage = index
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
                AnimatedVisibility(
                    visible = uiState.useFloatingNavigationBar,
                    enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
                    exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top)
                ) {
                    FloatingNavigationBar(
                        modifier = Modifier
                            .hazeEffect(hazeState) {
                                style = hazeStyle
                                blurRadius = 25.dp
                                noiseFactor = 0f
                            },
                        color = Color.Transparent,
                        items = navigationItem,
                        selected = selectedPage,
                        mode = when (uiState.floatingNavigationBarMode) {
                            0 -> FloatingNavigationBarMode.IconOnly
                            1 -> FloatingNavigationBarMode.IconAndText
                            else -> FloatingNavigationBarMode.TextOnly
                        },
                        horizontalAlignment = when (uiState.floatingNavigationBarPosition) {
                            0 -> CenterHorizontally
                            1 -> Alignment.Start
                            else -> Alignment.End
                        },
                        onClick = { index ->
                            selectedPage = index
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
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
        },
        floatingToolbar = {
            AnimatedVisibility(
                visible = uiState.showFloatingToolbar,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                val floatingToolbarBgColor = MiuixTheme.colorScheme.primary
                FloatingToolbar(
                    modifier = Modifier
                        .hazeEffect(hazeState) {
                            style = HazeStyle(
                                backgroundColor = floatingToolbarBgColor,
                                tint = HazeTint(floatingToolbarBgColor.copy(0.67f))
                            )
                            blurRadius = 25.dp
                            noiseFactor = 0f
                        },
                    color = Color.Transparent,
                    cornerRadius = 20.dp
                ) {
                    AnimatedContent(
                        targetState = uiState.floatingToolbarOrientation,
                    ) { orientation ->
                        @Composable
                        fun floatingToolbarContent() {
                            IconButton(onClick = { /* Action 1 */ }) {
                                Icon(
                                    MiuixIcons.Useful.Edit,
                                    contentDescription = "Edit",
                                    tint = MiuixTheme.colorScheme.onPrimaryContainer
                                )
                            }
                            IconButton(onClick = { /* Action 2 */ }) {
                                Icon(
                                    MiuixIcons.Useful.Delete,
                                    contentDescription = "Delete",
                                    tint = MiuixTheme.colorScheme.onPrimaryContainer
                                )
                            }
                            IconButton(onClick = { /* Action 3 */ }) {
                                Icon(
                                    MiuixIcons.Useful.ImmersionMore,
                                    contentDescription = "More",
                                    tint = MiuixTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                        when (orientation) {
                            0 -> Row(
                                modifier = Modifier.padding(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) { floatingToolbarContent() }

                            else -> Column(
                                modifier = Modifier.padding(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) { floatingToolbarContent() }
                        }
                    }
                }
            }
        },
        floatingToolbarPosition = when (uiState.floatingToolbarPosition) {
            0 -> ToolbarPosition.TopStart
            1 -> ToolbarPosition.CenterStart
            2 -> ToolbarPosition.BottomStart
            3 -> ToolbarPosition.TopEnd
            4 -> ToolbarPosition.CenterEnd
            5 -> ToolbarPosition.BottomEnd
            6 -> ToolbarPosition.TopCenter
            else -> ToolbarPosition.BottomCenter
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
        visible = uiState.showFPSMonitor,
        enter = fadeIn() + expandHorizontally(),
        exit = fadeOut() + shrinkHorizontally()
    ) {
        FPSMonitor(
            modifier = Modifier
                .statusBarsPadding()
                .captionBarPadding()
                .padding(all = 12.dp)
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
    showTopPopup: MutableState<Boolean>,
    onPageSelected: (Int) -> Unit
) {
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
                        showTopPopup = showTopPopup,
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
                        showTopPopup = showTopPopup,
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
    showTopPopup: MutableState<Boolean>,
    onPageSelected: (Int) -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current

    ListPopup(
        show = showTopPopup,
        popupPositionProvider = ListPopupDefaults.ContextMenuPositionProvider,
        alignment = PopupPositionProvider.Align.TopRight,
        onDismissRequest = {
            showTopPopup.value = false
        },
        enableWindowDim = false
    ) {
        ListPopupColumn {
            items.forEachIndexed { index, navigationItem ->
                DropdownImpl(
                    text = navigationItem.label,
                    optionSize = items.size,
                    isSelected = index == selectedPage,
                    onSelectedIndexChange = {
                        onPageSelected(index)
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                        showTopPopup.value = false
                    },
                    index = index
                )
            }
        }
    }

    IconButton(
        modifier = Modifier.padding(end = 20.dp),
        onClick = {
            showTopPopup.value = true
        },
        holdDownState = showTopPopup.value
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
                    padding = padding,
                    scrollEndHaptic = uiState.scrollEndHaptic,
                )

                1 -> SecondPage(
                    topAppBarScrollBehavior = topAppBarScrollBehaviorList[1],
                    padding = padding,
                    scrollEndHaptic = uiState.scrollEndHaptic,
                )

                else -> ThirdPage(
                    topAppBarScrollBehavior = topAppBarScrollBehaviorList[2],
                    padding = padding,
                    showFPSMonitor = uiState.showFPSMonitor,
                    onShowFPSMonitorChange = { onUiStateChange(uiState.copy(showFPSMonitor = it)) },
                    showTopAppBar = uiState.showTopAppBar,
                    onShowTopAppBarChange = { onUiStateChange(uiState.copy(showTopAppBar = it)) },
                    showNavigationBar = uiState.showNavigationBar,
                    onShowNavigationBarChange = { onUiStateChange(uiState.copy(showNavigationBar = it)) },
                    showFloatingToolbar = uiState.showFloatingToolbar,
                    onShowFloatingToolbarChange = { onUiStateChange(uiState.copy(showFloatingToolbar = it)) },
                    useFloatingNavigationBar = uiState.useFloatingNavigationBar,
                    onUseFloatingNavigationBarChange = { onUiStateChange(uiState.copy(useFloatingNavigationBar = it)) },
                    floatingNavigationBarMode = uiState.floatingNavigationBarMode,
                    onFloatingNavigationBarModeChange = { onUiStateChange(uiState.copy(floatingNavigationBarMode = it)) },
                    floatingNavigationBarPosition = uiState.floatingNavigationBarPosition,
                    onFloatingNavigationBarPositionChange = { onUiStateChange(uiState.copy(floatingNavigationBarPosition = it)) },
                    floatingToolbarPosition = uiState.floatingToolbarPosition,
                    onFloatingToolbarPositionChange = { onUiStateChange(uiState.copy(floatingToolbarPosition = it)) },
                    floatingToolbarOrientation = uiState.floatingToolbarOrientation,
                    onFloatingToolbarOrientationChange = { onUiStateChange(uiState.copy(floatingToolbarOrientation = it)) },
                    showFloatingActionButton = uiState.showFloatingActionButton,
                    onShowFloatingActionButtonChange = { onUiStateChange(uiState.copy(showFloatingActionButton = it)) },
                    fabPosition = uiState.floatingActionButtonPosition,
                    onFabPositionChange = { onUiStateChange(uiState.copy(floatingActionButtonPosition = it)) },
                    enablePageUserScroll = uiState.enablePageUserScroll,
                    onEnablePageUserScrollChange = { onUiStateChange(uiState.copy(enablePageUserScroll = it)) },
                    scrollEndHaptic = uiState.scrollEndHaptic,
                    onScrollEndHapticChange = { onUiStateChange(uiState.copy(scrollEndHaptic = it)) },
                    colorMode = colorMode
                )
            }
        }
    )
}