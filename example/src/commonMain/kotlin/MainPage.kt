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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
                    .height(getWindowSize().height.dp),
                contentPadding = PaddingValues(top = padding.calculateTopPadding()),
                overscrollEffect = null
            ) {
                item {
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
                if (!expanded) {
                    item {
                        textComponent()
                    }
                    otherComponent(focusManager, padding)
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
                    item {
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
                    if (!expanded) {
                        otherComponent(focusManager, padding)
                        item {
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
                    item {
                        textComponent()
                        Spacer(modifier = Modifier.height(padding.calculateBottomPadding()))
                    }
                }
            }
        }
    }
}