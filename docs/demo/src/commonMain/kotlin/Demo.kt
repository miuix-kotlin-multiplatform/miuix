// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.theme.darkColorScheme
import top.yukonga.miuix.kmp.theme.lightColorScheme

@Composable
fun Demo(demoId: String? = null) {
    MiuixTheme(
        colors = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        if (demoId == null) {
            DemoSelection()
        } else {
            availableComponents.first { it.id == demoId }.demo()
        }
    }
}

private data class AvailableComponent(val name: String, val id: String, val demo: @Composable () -> Unit)

private val availableComponents = listOf(
    AvailableComponent("Scaffold", "scaffold") { ScaffoldDemo() },
    AvailableComponent("Surface", "surface") { SurfaceDemo() },
    AvailableComponent("TopAppBar", "topAppBar") { TopAppBarDemo() },
    AvailableComponent("NavigationBar", "navigationBar") { NavigationBarDemo() },
    AvailableComponent("TabRow", "tabRow") { TabRowDemo() },
    AvailableComponent("Card", "card") { CardDemo() },
    AvailableComponent("BasicComponent", "basicComponent") { BasicComponentDemo() },
    AvailableComponent("Button", "button") { ButtonDemo() },
    AvailableComponent("IconButton", "iconButton") { IconButtonDemo() },
    AvailableComponent("Text", "text") { TextDemo() },
    AvailableComponent("SmallTitle", "smallTitle") { SmallTitleDemo() },
    AvailableComponent("TextField", "textField") { TextFieldDemo() },
    AvailableComponent("Switch", "switch") { SwitchDemo() },
    AvailableComponent("Checkbox", "checkbox") { CheckboxDemo() },
    AvailableComponent("Slider", "slider") { SliderDemo() },
    AvailableComponent("ProgressIndicator", "progressIndicator") { ProgressIndicatorDemo() },
    AvailableComponent("Icon", "icon") { IconDemo() },
    AvailableComponent("FloatingActionButton", "floatingActionButton") { FloatingActionButtonDemo() },
    AvailableComponent("FloatingToolbar", "floatingToolbar") { FloatingToolbarDemo() },
    AvailableComponent("Divider", "divider") { DividerDemo() },
    AvailableComponent("PullToRefresh", "pullToRefresh") { PullToRefreshDemo() },
    AvailableComponent("SearchBar", "searchBar") { SearchBarDemo() },
    AvailableComponent("ColorPicker", "colorPicker") { ColorPickerDemo() },
    AvailableComponent("ListPopup", "listPopup") { ListPopupDemo() },
    AvailableComponent("SuperArrow", "superArrow") { SuperArrowDemo() },
    AvailableComponent("SuperSwitch", "superSwitch") { SuperSwitchDemo() },
    AvailableComponent("SuperCheckbox", "superCheckbox") { SuperCheckboxDemo() },
    AvailableComponent("SuperDropdown", "superDropdown") { SuperDropdownDemo() },
    AvailableComponent("SuperSpinner", "superSpinner") { SuperSpinnerDemo() },
    AvailableComponent("SuperDialog", "superDialog") { SuperDialogDemo() },
)

@Composable
private fun DemoSelection() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MiuixTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                        .widthIn(max = 600.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    availableComponents.forEach { demo ->
                        TextButton(
                            text = demo.name,
                            onClick = { navController.navigate(demo.id) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        }

        availableComponents.forEach { component ->
            composable(component.id) {
                Column {
                    component.demo()
                }
            }
        }
    }
}
