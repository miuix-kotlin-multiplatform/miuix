// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.TextComponent
import component.otherComponent
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.InputField
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.SearchBar
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.useful.AddSecret
import top.yukonga.miuix.kmp.icon.icons.useful.Back
import top.yukonga.miuix.kmp.icon.icons.useful.Blocklist
import top.yukonga.miuix.kmp.icon.icons.useful.Cancel
import top.yukonga.miuix.kmp.icon.icons.useful.Confirm
import top.yukonga.miuix.kmp.icon.icons.useful.Copy
import top.yukonga.miuix.kmp.icon.icons.useful.Cut
import top.yukonga.miuix.kmp.icon.icons.useful.Delete
import top.yukonga.miuix.kmp.icon.icons.useful.Edit
import top.yukonga.miuix.kmp.icon.icons.useful.ImmersionMore
import top.yukonga.miuix.kmp.icon.icons.useful.Info
import top.yukonga.miuix.kmp.icon.icons.useful.Like
import top.yukonga.miuix.kmp.icon.icons.useful.More
import top.yukonga.miuix.kmp.icon.icons.useful.Move
import top.yukonga.miuix.kmp.icon.icons.useful.NavigatorSwitch
import top.yukonga.miuix.kmp.icon.icons.useful.New
import top.yukonga.miuix.kmp.icon.icons.useful.Order
import top.yukonga.miuix.kmp.icon.icons.useful.Paste
import top.yukonga.miuix.kmp.icon.icons.useful.Pause
import top.yukonga.miuix.kmp.icon.icons.useful.Personal
import top.yukonga.miuix.kmp.icon.icons.useful.Play
import top.yukonga.miuix.kmp.icon.icons.useful.Reboot
import top.yukonga.miuix.kmp.icon.icons.useful.Redo
import top.yukonga.miuix.kmp.icon.icons.useful.Refresh
import top.yukonga.miuix.kmp.icon.icons.useful.Remove
import top.yukonga.miuix.kmp.icon.icons.useful.RemoveBlocklist
import top.yukonga.miuix.kmp.icon.icons.useful.RemoveSecret
import top.yukonga.miuix.kmp.icon.icons.useful.Rename
import top.yukonga.miuix.kmp.icon.icons.useful.Restore
import top.yukonga.miuix.kmp.icon.icons.useful.Save
import top.yukonga.miuix.kmp.icon.icons.useful.Scan
import top.yukonga.miuix.kmp.icon.icons.useful.Search
import top.yukonga.miuix.kmp.icon.icons.useful.SelectAll
import top.yukonga.miuix.kmp.icon.icons.useful.Settings
import top.yukonga.miuix.kmp.icon.icons.useful.Share
import top.yukonga.miuix.kmp.icon.icons.useful.Stick
import top.yukonga.miuix.kmp.icon.icons.useful.Undo
import top.yukonga.miuix.kmp.icon.icons.useful.Unlike
import top.yukonga.miuix.kmp.icon.icons.useful.Unstick
import top.yukonga.miuix.kmp.icon.icons.useful.Update
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.getWindowSize
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuix.kmp.utils.scrollEndHaptic

@Composable
fun MainPage(
    topAppBarScrollBehavior: ScrollBehavior,
    padding: PaddingValues,
    scrollEndHaptic: Boolean,
) {
    var searchValue by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    val dialogTextFieldValue = remember { mutableStateOf("") }
    val showDialog2 = remember { mutableStateOf(false) }
    val dialog2dropdownSelectedOption = remember { mutableStateOf(0) }
    val dialog2SuperSwitchState = remember { mutableStateOf(false) }
    val checkbox = remember { mutableStateOf(false) }
    val checkboxTrue = remember { mutableStateOf(true) }
    val switch = remember { mutableStateOf(false) }
    val switchTrue = remember { mutableStateOf(true) }
    val dropdownOptionSelected = remember { mutableStateOf(0) }
    val dropdownOptionSelectedRight = remember { mutableStateOf(1) }
    val spinnerOptionSelected = remember { mutableStateOf(0) }
    val spinnerOptionSelectedRight = remember { mutableStateOf(1) }
    val spinnerOptionSelectedDialog = remember { mutableStateOf(2) }
    val superCheckbox = remember { mutableStateOf("State: false") }
    val superCheckboxState = remember { mutableStateOf(false) }
    val superRightCheckbox = remember { mutableStateOf("false") }
    val superRightCheckboxState = remember { mutableStateOf(false) }
    val superSwitch = remember { mutableStateOf("false") }
    val superSwitchState = remember { mutableStateOf(false) }
    val superSwitchAnimState = remember { mutableStateOf(false) }

    val notExpanded by remember { derivedStateOf { !expanded } }

    val textComponent = @Composable {
        TextComponent(
            showDialog,
            dialogTextFieldValue,
            showDialog2,
            dialog2dropdownSelectedOption,
            dialog2SuperSwitchState,
            checkbox,
            checkboxTrue,
            switch,
            switchTrue,
            dropdownOptionSelected,
            dropdownOptionSelectedRight,
            spinnerOptionSelected,
            spinnerOptionSelectedRight,
            spinnerOptionSelectedDialog,
            superCheckbox,
            superCheckboxState,
            superRightCheckbox,
            superRightCheckboxState,
            superSwitch,
            superSwitchState,
            superSwitchAnimState
        )
    }

    val miuixIcons = remember {
        listOf(
            MiuixIcons.Useful.AddSecret,
            MiuixIcons.Useful.Back,
            MiuixIcons.Useful.Blocklist,
            MiuixIcons.Useful.Cancel,
            MiuixIcons.Useful.Confirm,
            MiuixIcons.Useful.Copy,
            MiuixIcons.Useful.Cut,
            MiuixIcons.Useful.Delete,
            MiuixIcons.Useful.Edit,
            MiuixIcons.Useful.ImmersionMore,
            MiuixIcons.Useful.Info,
            MiuixIcons.Useful.Like,
            MiuixIcons.Useful.More,
            MiuixIcons.Useful.Move,
            MiuixIcons.Useful.NavigatorSwitch,
            MiuixIcons.Useful.New,
            MiuixIcons.Useful.Order,
            MiuixIcons.Useful.Paste,
            MiuixIcons.Useful.Pause,
            MiuixIcons.Useful.Personal,
            MiuixIcons.Useful.Play,
            MiuixIcons.Useful.Reboot,
            MiuixIcons.Useful.Redo,
            MiuixIcons.Useful.Refresh,
            MiuixIcons.Useful.Remove,
            MiuixIcons.Useful.RemoveBlocklist,
            MiuixIcons.Useful.RemoveSecret,
            MiuixIcons.Useful.Rename,
            MiuixIcons.Useful.Restore,
            MiuixIcons.Useful.Save,
            MiuixIcons.Useful.Scan,
            MiuixIcons.Useful.Search,
            MiuixIcons.Useful.SelectAll,
            MiuixIcons.Useful.Settings,
            MiuixIcons.Useful.Share,
            MiuixIcons.Useful.Stick,
            MiuixIcons.Useful.Undo,
            MiuixIcons.Useful.Unlike,
            MiuixIcons.Useful.Unstick,
            MiuixIcons.Useful.Update
        )
    }

    val windowSize by rememberUpdatedState(getWindowSize())

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val focusManager = LocalFocusManager.current
        if (maxWidth < 840.dp) {
            LazyColumn(
                modifier = Modifier
                    .then(
                        if (scrollEndHaptic) Modifier.scrollEndHaptic() else Modifier
                    )
                    .overScrollVertical()
                    .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                    .height(windowSize.height.dp),
                contentPadding = PaddingValues(top = padding.calculateTopPadding()),
                overscrollEffect = null,
            ) {
                item(key = "searchbar") {
                    SmallTitle(text = "SearchBar")
                    SearchBar(
                        inputField = {
                            InputField(
                                query = searchValue,
                                onQueryChange = { searchValue = it },
                                onSearch = { expanded = false },
                                expanded = expanded,
                                onExpandedChange = { expanded = it },
                                label = "Search"
                            )
                        },
                        outsideRightAction = {
                            Text(
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .clickable(
                                        interactionSource = null,
                                        indication = null
                                    ) {
                                        expanded = false
                                        searchValue = ""
                                    },
                                text = "Cancel",
                                style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
                                color = MiuixTheme.colorScheme.primary
                            )
                        },
                        expanded = expanded,
                        onExpandedChange = { expanded = it }
                    ) {
                        Column {
                            repeat(4) { idx ->
                                val resultText = "Suggestion $idx"
                                BasicComponent(
                                    title = resultText,
                                    onClick = {
                                        searchValue = resultText
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                if (notExpanded) {
                    item(key = "textComponent") {
                        textComponent()
                    }
                    otherComponent(miuixIcons, focusManager, padding)
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
                    .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Horizontal))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .overScrollVertical()
                        .scrollEndHaptic()
                        .weight(0.5f),
                    contentPadding = PaddingValues(top = padding.calculateTopPadding())
                ) {
                    item(key = "searchbar-wide") {
                        SmallTitle(text = "SearchBar")
                        SearchBar(
                            inputField = {
                                InputField(
                                    query = searchValue,
                                    onQueryChange = { searchValue = it },
                                    onSearch = { expanded = false },
                                    expanded = expanded,
                                    onExpandedChange = { expanded = it },
                                    label = "Search"
                                )
                            },
                            outsideRightAction = {
                                Text(
                                    modifier = Modifier
                                        .clickable(
                                            interactionSource = null,
                                            indication = null
                                        ) {
                                            expanded = false
                                            searchValue = ""
                                        },
                                    text = "Cancel",
                                    color = MiuixTheme.colorScheme.primary
                                )
                            },
                            expanded = expanded,
                            onExpandedChange = { expanded = it }
                        ) {
                            Column(
                                Modifier.fillMaxSize()
                            ) {
                                repeat(4) { idx ->
                                    val resultText = "Suggestion $idx"
                                    BasicComponent(
                                        title = resultText,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        onClick = {
                                            searchValue = resultText
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                    if (notExpanded) {
                        otherComponent(miuixIcons, focusManager, padding)
                        item(key = "spacer-wide") {
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .overScrollVertical()
                        .scrollEndHaptic()
                        .padding(end = 12.dp, bottom = 12.dp)
                        .weight(0.5f),
                    contentPadding = PaddingValues(top = padding.calculateTopPadding())
                ) {
                    item(key = "textComponent-wide") {
                        textComponent()
                        Spacer(modifier = Modifier.height(padding.calculateBottomPadding()))
                    }
                }
            }
        }
    }
}