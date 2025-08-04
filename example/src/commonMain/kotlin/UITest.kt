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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.Card
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
import top.yukonga.miuix.kmp.basic.VerticalDivider
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
import top.yukonga.miuix.kmp.utils.WindowSize
import top.yukonga.miuix.kmp.utils.getWindowSize
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuix.kmp.utils.scrollEndHaptic
import utils.FPSMonitor

private object UIConstants {
    val WIDE_SCREEN_THRESHOLD = 840.dp
    const val MAIN_PAGE_INDEX = 0
    const val DROPDOWN_PAGE_INDEX = 1
    const val PAGE_COUNT = 3
    const val GITHUB_URL = "https://github.com/miuix-kotlin-multiplatform/miuix"

    val PAGE_TITLES = listOf("HomePage", "DropDown", "Settings")
}

enum class FloatingNavigationBarAlignment(val value: Int) {
    Center(0), Start(1), End(2);

    companion object {
        fun fromInt(value: Int) = entries.find { it.value == value } ?: Center
    }
}

enum class FloatingNavigationBarDisplayMode(val value: Int) {
    IconOnly(0), IconAndText(1), TextOnly(2);

    companion object {
        fun fromInt(value: Int) = entries.find { it.value == value } ?: IconOnly
    }
}

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
    val scrollEndHaptic: Boolean = true,
    val isWideScreen: Boolean = false,
)

@Composable
fun UITest(
    colorMode: MutableState<Int>,
) {
    val topAppBarScrollBehaviorList = List(UIConstants.PAGE_COUNT) { MiuixScrollBehavior() }
    val pagerState = rememberPagerState(pageCount = { UIConstants.PAGE_COUNT })
    val coroutineScope = rememberCoroutineScope()
    var selectedPage by remember { mutableIntStateOf(pagerState.currentPage) }
    val currentScrollBehavior = topAppBarScrollBehaviorList[selectedPage]

    LaunchedEffect(pagerState.settledPage) {
        if (selectedPage != pagerState.settledPage) selectedPage = pagerState.settledPage
    }

    val navigationItems = remember {
        listOf(
            NavigationItem("HomePage", MiuixIcons.Useful.NavigatorSwitch),
            NavigationItem("DropDown", MiuixIcons.Useful.Order),
            NavigationItem("Settings", MiuixIcons.Useful.Settings)
        )
    }

    var uiState by remember { mutableStateOf(UIState()) }
    val showTopPopup = remember { mutableStateOf(false) }
    val windowSize by rememberUpdatedState(getWindowSize())

    val onPageSelected: (Int) -> Unit = remember {
        { page ->
            selectedPage = page
            coroutineScope.launch { pagerState.animateScrollToPage(page) }
        }
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val isWideScreen = maxWidth > UIConstants.WIDE_SCREEN_THRESHOLD
        uiState = uiState.copy(isWideScreen = isWideScreen)

        if (isWideScreen) {
            WideScreenLayout(
                selectedPage = selectedPage,
                navigationItems = navigationItems,
                uiState = uiState,
                onUiStateChange = { uiState = it },
                onPageSelected = onPageSelected,
                showTopPopup = showTopPopup,
                pagerState = pagerState,
                topAppBarScrollBehaviorList = topAppBarScrollBehaviorList,
                currentScrollBehavior = currentScrollBehavior,
                windowSize = windowSize,
                colorMode = colorMode
            )
        } else {
            CompactScreenLayout(
                selectedPage = selectedPage,
                navigationItems = navigationItems,
                uiState = uiState,
                onUiStateChange = { uiState = it },
                onPageSelected = onPageSelected,
                showTopPopup = showTopPopup,
                pagerState = pagerState,
                topAppBarScrollBehaviorList = topAppBarScrollBehaviorList,
                currentScrollBehavior = currentScrollBehavior,
                colorMode = colorMode
            )
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
                .padding(all = 12.dp)
        )
    }
}

@Composable
private fun WideScreenLayout(
    selectedPage: Int,
    navigationItems: List<NavigationItem>,
    uiState: UIState,
    onUiStateChange: (UIState) -> Unit,
    onPageSelected: (Int) -> Unit,
    showTopPopup: MutableState<Boolean>,
    pagerState: PagerState,
    topAppBarScrollBehaviorList: List<ScrollBehavior>,
    currentScrollBehavior: ScrollBehavior,
    windowSize: WindowSize,
    colorMode: MutableState<Int>
) {
    val layoutDirection = LocalLayoutDirection.current

    Scaffold {
        val barScrollBehavior = MiuixScrollBehavior()
        Row {
            Box(modifier = Modifier.weight(0.4f)) {
                WideScreenPanel(
                    selectedPage = selectedPage,
                    onPageSelected = onPageSelected,
                    barScrollBehavior = barScrollBehavior,
                    uiState = uiState,
                    windowSize = windowSize,
                    layoutDirection = layoutDirection
                )
            }
            VerticalDivider(
                modifier = Modifier.padding(horizontal = 6.dp)
            )
            Box(modifier = Modifier.weight(0.6f)) {
                WideScreenContent(
                    selectedPage = selectedPage,
                    navigationItems = navigationItems,
                    uiState = uiState,
                    onPageSelected = onPageSelected,
                    showTopPopup = showTopPopup,
                    pagerState = pagerState,
                    topAppBarScrollBehaviorList = topAppBarScrollBehaviorList,
                    currentScrollBehavior = currentScrollBehavior,
                    onUiStateChange = onUiStateChange,
                    colorMode = colorMode
                )
            }
        }
    }
}

@Composable
private fun WideScreenPanel(
    selectedPage: Int,
    onPageSelected: (Int) -> Unit,
    barScrollBehavior: ScrollBehavior,
    uiState: UIState,
    windowSize: WindowSize,
    layoutDirection: LayoutDirection
) {
    Scaffold(
        modifier = Modifier
            .padding(start = 18.dp, end = 12.dp)
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = "Miuix",
                horizontalPadding = 12.dp,
                scrollBehavior = barScrollBehavior
            )
        },
        popupHost = { null }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .then(
                    if (uiState.scrollEndHaptic) Modifier.scrollEndHaptic() else Modifier
                )
                .overScrollVertical()
                .nestedScroll(barScrollBehavior.nestedScrollConnection)
                .height(windowSize.let { getWindowSize().height.dp }),
        ) {
            item {
                Card(
                    modifier = Modifier
                        .padding(start = padding.calculateStartPadding(layoutDirection))
                        .padding(
                            top = 12.dp + padding.calculateTopPadding(),
                            bottom = padding.calculateBottomPadding()
                        ),
                ) {
                    UIConstants.PAGE_TITLES.forEachIndexed { index, title ->
                        BasicComponent(
                            title = title,
                            onClick = { onPageSelected(index) },
                            holdDownState = selectedPage == index,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WideScreenContent(
    selectedPage: Int,
    navigationItems: List<NavigationItem>,
    uiState: UIState,
    onPageSelected: (Int) -> Unit,
    showTopPopup: MutableState<Boolean>,
    pagerState: PagerState,
    topAppBarScrollBehaviorList: List<ScrollBehavior>,
    currentScrollBehavior: ScrollBehavior,
    onUiStateChange: (UIState) -> Unit,
    colorMode: MutableState<Int>
) {
    Scaffold(
        modifier = Modifier
            .padding(end = 6.dp)
            .fillMaxSize(),
        topBar = {
            AnimatedVisibility(
                visible = uiState.showTopAppBar,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                SmallTopAppBar(
                    title = UIConstants.PAGE_TITLES[selectedPage],
                    scrollBehavior = currentScrollBehavior,
                    actions = {
                        TopAppBarActions(
                            selectedPage = selectedPage,
                            items = navigationItems,
                            showTopPopup = showTopPopup,
                            onPageSelected = onPageSelected
                        )
                    },
                    defaultWindowInsetsPadding = false,
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.End))
                        .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.End)),
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(uiState.showFloatingActionButton)
        },
        floatingActionButtonPosition = uiState.floatingActionButtonPosition.toFabPosition(),
        floatingToolbar = {
            FloatingToolbar(
                showFloatingToolbar = uiState.showFloatingToolbar,
                floatingToolbarOrientation = uiState.floatingToolbarOrientation
            )
        },
        floatingToolbarPosition = uiState.floatingToolbarPosition.toToolbarPosition(),
        popupHost = { null }
    ) { padding ->
        AppPager(
            modifier = Modifier
                .imePadding()
                .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.End))
                .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.End)),
            pagerState = pagerState,
            topAppBarScrollBehaviorList = topAppBarScrollBehaviorList,
            padding = PaddingValues(
                end = padding.calculateEndPadding(LayoutDirection.Ltr),
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            ),
            uiState = uiState,
            onUiStateChange = onUiStateChange,
            colorMode = colorMode,
        )
    }
}

@Composable
private fun CompactScreenLayout(
    selectedPage: Int,
    navigationItems: List<NavigationItem>,
    uiState: UIState,
    onUiStateChange: (UIState) -> Unit,
    onPageSelected: (Int) -> Unit,
    showTopPopup: MutableState<Boolean>,
    pagerState: PagerState,
    topAppBarScrollBehaviorList: List<ScrollBehavior>,
    currentScrollBehavior: ScrollBehavior,
    colorMode: MutableState<Int>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AnimatedVisibility(
                visible = uiState.showTopAppBar,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                TopAppBar(
                    title = "Miuix",
                    scrollBehavior = currentScrollBehavior,
                    actions = {
                        TopAppBarActions(
                            selectedPage = selectedPage,
                            items = navigationItems,
                            showTopPopup = showTopPopup,
                            onPageSelected = onPageSelected
                        )
                    }
                )
            }
        },
        bottomBar = {
            NavigationBar(
                uiState = uiState,
                navigationItems = navigationItems,
                selectedPage = selectedPage,
                onPageSelected = onPageSelected
            )
        },
        floatingActionButton = {
            FloatingActionButton(uiState.showFloatingActionButton)
        },
        floatingActionButtonPosition = uiState.floatingActionButtonPosition.toFabPosition(),
        floatingToolbar = {
            FloatingToolbar(
                showFloatingToolbar = uiState.showFloatingToolbar,
                floatingToolbarOrientation = uiState.floatingToolbarOrientation
            )
        },
        floatingToolbarPosition = uiState.floatingToolbarPosition.toToolbarPosition()
    ) { padding ->
        AppPager(
            modifier = Modifier
                .imePadding()
                .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
                .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Horizontal)),
            pagerState = pagerState,
            topAppBarScrollBehaviorList = topAppBarScrollBehaviorList,
            padding = padding,
            uiState = uiState,
            onUiStateChange = onUiStateChange,
            colorMode = colorMode,
        )
    }
}

@Composable
private fun NavigationBar(
    uiState: UIState,
    navigationItems: List<NavigationItem>,
    selectedPage: Int,
    onPageSelected: (Int) -> Unit
) {
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
                items = navigationItems,
                selected = selectedPage,
                onClick = onPageSelected
            )
        }
        AnimatedVisibility(
            visible = uiState.useFloatingNavigationBar,
            enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
            exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            FloatingNavigationBar(
                items = navigationItems,
                selected = selectedPage,
                mode = FloatingNavigationBarDisplayMode.fromInt(uiState.floatingNavigationBarMode).toMode(),
                horizontalAlignment = FloatingNavigationBarAlignment.fromInt(uiState.floatingNavigationBarPosition).toAlignment(),
                onClick = onPageSelected
            )
        }
    }
}

@Composable
private fun FloatingActionButton(show: Boolean) {
    if (show) {
        val uriHandler = LocalUriHandler.current
        FloatingActionButton(
            onClick = {
                uriHandler.openUri(UIConstants.GITHUB_URL)
            }
        ) {
            Icon(
                imageVector = MiuixIcons.Other.GitHub,
                tint = Color.White,
                contentDescription = "GitHub"
            )
        }
    }
}

@Composable
private fun FloatingToolbar(
    showFloatingToolbar: Boolean,
    floatingToolbarOrientation: Int
) {
    AnimatedVisibility(
        visible = showFloatingToolbar,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        FloatingToolbar(
            color = MiuixTheme.colorScheme.primaryVariant,
            cornerRadius = 20.dp
        ) {
            AnimatedContent(
                targetState = floatingToolbarOrientation,
            ) { orientation ->
                val content = @Composable {
                    FloatingToolbarActions()
                }
                when (orientation) {
                    0 -> Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) { content() }

                    else -> Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) { content() }
                }
            }
        }
    }
}

@Composable
private fun FloatingToolbarActions() {
    val iconTint = MiuixTheme.colorScheme.onPrimaryContainer

    IconButton(onClick = { /* Action 1 */ }) {
        Icon(
            MiuixIcons.Useful.Edit,
            contentDescription = "Edit",
            tint = iconTint
        )
    }
    IconButton(onClick = { /* Action 2 */ }) {
        Icon(
            MiuixIcons.Useful.Delete,
            contentDescription = "Delete",
            tint = iconTint
        )
    }
    IconButton(onClick = { /* Action 3 */ }) {
        Icon(
            MiuixIcons.Useful.ImmersionMore,
            contentDescription = "More",
            tint = iconTint
        )
    }
}

private fun Int.toFabPosition(): FabPosition = when (this) {
    0 -> FabPosition.Start
    1 -> FabPosition.Center
    2 -> FabPosition.End
    else -> FabPosition.EndOverlay
}

private fun Int.toToolbarPosition(): ToolbarPosition = when (this) {
    0 -> ToolbarPosition.TopStart
    1 -> ToolbarPosition.CenterStart
    2 -> ToolbarPosition.BottomStart
    3 -> ToolbarPosition.TopEnd
    4 -> ToolbarPosition.CenterEnd
    5 -> ToolbarPosition.BottomEnd
    6 -> ToolbarPosition.TopCenter
    else -> ToolbarPosition.BottomCenter
}

private fun FloatingNavigationBarDisplayMode.toMode(): FloatingNavigationBarMode = when (this) {
    FloatingNavigationBarDisplayMode.IconOnly -> FloatingNavigationBarMode.IconOnly
    FloatingNavigationBarDisplayMode.IconAndText -> FloatingNavigationBarMode.IconAndText
    FloatingNavigationBarDisplayMode.TextOnly -> FloatingNavigationBarMode.TextOnly
}

private fun FloatingNavigationBarAlignment.toAlignment(): Alignment.Horizontal = when (this) {
    FloatingNavigationBarAlignment.Center -> CenterHorizontally
    FloatingNavigationBarAlignment.Start -> Alignment.Start
    FloatingNavigationBarAlignment.End -> Alignment.End
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
fun AppPager(
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
        beyondViewportPageCount = 1,
        overscrollEffect = null,
        pageContent = { page ->
            when (page) {
                UIConstants.MAIN_PAGE_INDEX -> MainPage(
                    topAppBarScrollBehavior = topAppBarScrollBehaviorList[0],
                    padding = padding,
                    scrollEndHaptic = uiState.scrollEndHaptic,
                )

                UIConstants.DROPDOWN_PAGE_INDEX -> SecondPage(
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
                    isWideScreen = uiState.isWideScreen,
                    colorMode = colorMode
                )
            }
        }
    )
}
