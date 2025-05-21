// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import misc.VersionInfo
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.extra.SuperArrow
import top.yukonga.miuix.kmp.extra.SuperDialog
import top.yukonga.miuix.kmp.extra.SuperDropdown
import top.yukonga.miuix.kmp.extra.SuperSwitch
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.getWindowSize
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuix.kmp.utils.scrollEndHaptic

@Composable
fun ThirdPage(
    topAppBarScrollBehavior: ScrollBehavior,
    padding: PaddingValues,
    showFPSMonitor: Boolean,
    onShowFPSMonitorChange: (Boolean) -> Unit,
    showTopAppBar: Boolean,
    onShowTopAppBarChange: (Boolean) -> Unit,
    showNavigationBar: Boolean,
    onShowNavigationBarChange: (Boolean) -> Unit,
    useFloatingNavigationBar: Boolean,
    onUseFloatingNavigationBarChange: (Boolean) -> Unit,
    floatingNavigationBarMode: Int,
    onFloatingNavigationBarModeChange: (Int) -> Unit,
    floatingNavigationBarPosition: Int,
    onFloatingNavigationBarPositionChange: (Int) -> Unit,
    showFloatingToolbar: Boolean,
    onShowFloatingToolbarChange: (Boolean) -> Unit,
    floatingToolbarPosition: Int,
    onFloatingToolbarPositionChange: (Int) -> Unit,
    floatingToolbarOrientation: Int,
    onFloatingToolbarOrientationChange: (Int) -> Unit,
    showFloatingActionButton: Boolean,
    onShowFloatingActionButtonChange: (Boolean) -> Unit,
    fabPosition: Int,
    onFabPositionChange: (Int) -> Unit,
    enablePageUserScroll: Boolean,
    onEnablePageUserScrollChange: (Boolean) -> Unit,
    scrollEndHaptic: Boolean,
    onScrollEndHapticChange: (Boolean) -> Unit,
    colorMode: MutableState<Int>
) {
    val showDialog = remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier
            .then(
                if (scrollEndHaptic) Modifier.scrollEndHaptic() else Modifier
            )
            .overScrollVertical()
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
            .height(getWindowSize().height.dp),
        contentPadding = PaddingValues(top = padding.calculateTopPadding()),
        overscrollEffect = null
    ) {
        item {
            Card(
                modifier = Modifier.padding(12.dp)
            ) {
                SuperSwitch(
                    title = "Show FPS Monitor",
                    checked = showFPSMonitor,
                    onCheckedChange = onShowFPSMonitorChange
                )
                SuperSwitch(
                    title = "Show TopAppBar",
                    checked = showTopAppBar,
                    onCheckedChange = onShowTopAppBarChange
                )
                SuperSwitch(
                    title = "Show NavigationBar",
                    checked = showNavigationBar,
                    onCheckedChange = onShowNavigationBarChange
                )
                AnimatedVisibility(
                    visible = showNavigationBar
                ) {
                    Column {
                        SuperSwitch(
                            title = "Use FloatingNavigationBar",
                            checked = useFloatingNavigationBar,
                            onCheckedChange = onUseFloatingNavigationBarChange
                        )
                        AnimatedVisibility(
                            visible = useFloatingNavigationBar
                        ) {
                            Column {
                                SuperDropdown(
                                    title = "FloatingNavigationBar Mode",
                                    items = listOf("IconOnly", "IconAndText", "TextOnly"),
                                    selectedIndex = floatingNavigationBarMode,
                                    onSelectedIndexChange = onFloatingNavigationBarModeChange
                                )
                                SuperDropdown(
                                    title = "FloatingNavigationBar Position",
                                    items = listOf("Center", "Start", "End"),
                                    selectedIndex = floatingNavigationBarPosition,
                                    onSelectedIndexChange = onFloatingNavigationBarPositionChange
                                )
                            }
                        }
                    }
                }
                SuperSwitch(
                    title = "Show FloatingToolbar",
                    checked = showFloatingToolbar,
                    onCheckedChange = onShowFloatingToolbarChange
                )
                AnimatedVisibility(
                    visible = showFloatingToolbar
                ) {
                    Column {
                        SuperDropdown(
                            title = "FloatingToolbar Position",
                            items = listOf(
                                "TopStart",
                                "CenterStart",
                                "BottomStart",
                                "TopEnd",
                                "CenterEnd",
                                "BottomEnd",
                                "TopCenter",
                                "BottomCenter"
                            ),
                            selectedIndex = floatingToolbarPosition,
                            onSelectedIndexChange = onFloatingToolbarPositionChange
                        )
                        SuperDropdown(
                            title = "FloatingToolbar Orientation",
                            items = listOf("Horizontal", "Vertical"),
                            selectedIndex = floatingToolbarOrientation,
                            onSelectedIndexChange = onFloatingToolbarOrientationChange
                        )
                    }
                }
                SuperSwitch(
                    title = "Show FloatingActionButton",
                    checked = showFloatingActionButton,
                    onCheckedChange = onShowFloatingActionButtonChange
                )
                AnimatedVisibility(
                    visible = showFloatingActionButton
                ) {
                    SuperDropdown(
                        title = "FloatingActionButton Position",
                        items = listOf(
                            "Start",
                            "Center",
                            "End",
                            "EndOverlay",
                        ),
                        selectedIndex = fabPosition,
                        onSelectedIndexChange = { fabPosition ->
                            onFabPositionChange(fabPosition)
                        }
                    )
                }
                SuperSwitch(
                    title = "Enable Scroll End Haptic",
                    checked = scrollEndHaptic,
                    onCheckedChange = onScrollEndHapticChange
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
                    .padding(bottom = 12.dp)
            ) {
                SuperArrow(
                    title = "About",
                    summary = "About this app",
                    onClick = {
                        showDialog.value = true
                    }
                )
            }
            Spacer(modifier = Modifier.height(padding.calculateBottomPadding()))
        }
    }
    dialog(showDialog)
}

@Composable
fun dialog(showDialog: MutableState<Boolean>) {
    SuperDialog(
        title = "About",
        show = showDialog,
        onDismissRequest = {
            showDialog.value = false
        },
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
