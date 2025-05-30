// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.CircularProgressIndicator
import top.yukonga.miuix.kmp.basic.ColorPicker
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.InfiniteProgressIndicator
import top.yukonga.miuix.kmp.basic.LinearProgressIndicator
import top.yukonga.miuix.kmp.basic.Slider
import top.yukonga.miuix.kmp.basic.SliderDefaults
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.TabRow
import top.yukonga.miuix.kmp.basic.TabRowWithContour
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.basic.TextField
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
import top.yukonga.miuix.kmp.utils.PressFeedbackType
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape
import kotlin.math.round

fun LazyListScope.otherComponent(focusManager: FocusManager, padding: PaddingValues) {
    val miuixIconsNormal = listOf(
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

    item {
        var buttonText by remember { mutableStateOf("Cancel") }
        var submitButtonText by remember { mutableStateOf("Submit") }
        var clickCount by remember { mutableStateOf(0) }
        var submitClickCount by remember { mutableStateOf(0) }

        SmallTitle(text = "Button")
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                text = buttonText,
                onClick = {
                    clickCount++
                    buttonText = "Click: $clickCount"
                },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(12.dp))
            TextButton(
                text = submitButtonText,
                onClick = {
                    submitClickCount++
                    submitButtonText = "Click: $submitClickCount"
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColorsPrimary()
            )
        }
    }

    item {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                text = "Disabled",
                onClick = {},
                modifier = Modifier.weight(1f),
                enabled = false
            )
            Spacer(Modifier.width(12.dp))
            TextButton(
                text = "Disabled",
                onClick = {},
                enabled = false,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColorsPrimary()
            )
        }
    }

    item {
        SmallTitle(text = "ProgressIndicator")
        val progressValues = listOf(0.0f, 0.25f, 0.5f, 0.75f, 1.0f, null)
        val animatedProgressValue by rememberInfiniteTransition().animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            )
        )

        LinearProgressIndicator(
            progress = animatedProgressValue,
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(bottom = 12.dp)
        )
        progressValues.forEach { progressValue ->
            LinearProgressIndicator(
                progress = progressValue,
                modifier = Modifier
                    .padding(horizontal = 15.dp) // Increased from 12.dp.
                    .padding(bottom = 12.dp)
            )
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            CircularProgressIndicator(
                progress = animatedProgressValue
            )
            progressValues.forEach { progressValue ->
                CircularProgressIndicator(
                    progress = progressValue
                )
            }
            InfiniteProgressIndicator(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
            )
        }
    }

    item {
        var text1 by remember { mutableStateOf("") }

        SmallTitle(text = "TextField")
        TextField(
            value = text1,
            onValueChange = { text1 = it },
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
    }
    item {
        var text2 by remember { mutableStateOf(TextFieldValue("")) }

        TextField(
            value = text2,
            onValueChange = { text2 = it },
            label = "Text Field",
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
    }

    item {
        var text3 by remember { mutableStateOf("") }

        TextField(
            value = text3,
            onValueChange = { text3 = it },
            label = "Placeholder & SingleLine",
            useLabelAsPlaceholder = true,
            singleLine = true,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 6.dp),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
    }

    item {
        SmallTitle(text = "Slider")
        var progress by remember { mutableStateOf(0.5f) }
        Slider(
            progress = progress,
            onProgressChange = { newProgress -> progress = newProgress },
            decimalPlaces = 3,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
        )
    }

    item {
        var progressHaptic by remember { mutableStateOf(0.5f) }
        Slider(
            progress = progressHaptic,
            onProgressChange = { newProgress -> progressHaptic = newProgress },
            hapticEffect = SliderDefaults.SliderHapticEffect.Step,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
        )
    }

    item {
        val progressDisable by remember { mutableStateOf(0.5f) }
        Slider(
            progress = progressDisable,
            onProgressChange = {},
            enabled = false,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 6.dp)
        )
    }

    item {
        SmallTitle(text = "TabRow")
        val tabTexts = listOf("Tab 1", "Tab 2", "Tab 3", "Tab 4", "Tab 5", "Tab 6")
        var selectedTabIndex by remember { mutableStateOf(0) }
        var selectedTabIndex1 by remember { mutableStateOf(0) }
        TabRow(
            tabs = tabTexts,
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
        ) {
            selectedTabIndex = it
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 6.dp),
            insideMargin = PaddingValues(16.dp)
        ) {
            TabRowWithContour(
                tabs = tabTexts,
                selectedTabIndex = selectedTabIndex1,
            ) {
                selectedTabIndex1 = it
            }
            Text(
                text = "Selected Tab: ${tabTexts[selectedTabIndex1]}",
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }

    item {
        SmallTitle(text = "Icon")
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 6.dp),
            insideMargin = PaddingValues(16.dp)
        ) {
            FlowRow {
                miuixIconsNormal.forEach { icon ->
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (icon != MiuixIcons.Useful.Like) MiuixTheme.colorScheme.onBackground else Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }

    item {
        SmallTitle(text = "ColorPicker")
        val miuixColor = MiuixTheme.colorScheme.primary
        var selectedColor by remember { mutableStateOf(miuixColor) }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 6.dp),
            insideMargin = PaddingValues(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Selected Color:\nRGBA: " +
                            "${(selectedColor.red * 255).toInt()}," +
                            "${(selectedColor.green * 255).toInt()}," +
                            "${(selectedColor.blue * 255).toInt()}," +
                            "${(round(selectedColor.alpha * 100) / 100.0)}" +
                            "\nHEX: #" +
                            (selectedColor.alpha * 255).toInt().toString(16).padStart(2, '0')
                                .uppercase() +
                            (selectedColor.red * 255).toInt().toString(16).padStart(2, '0')
                                .uppercase() +
                            (selectedColor.green * 255).toInt().toString(16).padStart(2, '0')
                                .uppercase() +
                            (selectedColor.blue * 255).toInt().toString(16).padStart(2, '0')
                                .uppercase(),
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .width(100.dp)
                        .align(Alignment.CenterVertically)
                        .clip(SmoothRoundedCornerShape(12.dp))
                        .background(selectedColor)
                )
            }

            ColorPicker(
                initialColor = selectedColor,
                onColorChanged = { selectedColor = it },
                showPreview = false,
                hapticEffect = SliderDefaults.SliderHapticEffect.Step
            )
        }
    }

    item {
        SmallTitle(text = "Card")
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
            color = MiuixTheme.colorScheme.primaryVariant,
            insideMargin = PaddingValues(16.dp),
            pressFeedbackType = PressFeedbackType.None,
            showIndication = true,
            onClick = { }
        ) {
            Text(
                color = MiuixTheme.colorScheme.onPrimary,
                text = "Card",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                color = MiuixTheme.colorScheme.onPrimaryVariant,
                text = "ShowIndication: true",
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }

    item {
        val hapticFeedback = LocalHapticFeedback.current
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp + padding.calculateBottomPadding()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                insideMargin = PaddingValues(16.dp),
                pressFeedbackType = PressFeedbackType.Sink,
                showIndication = true,
                onClick = { },
                content = {
                    Text(
                        color = MiuixTheme.colorScheme.onSurface,
                        text = "Card",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        text = "PressFeedback\nType: Sink",
                        style = MiuixTheme.textStyles.paragraph
                    )
                }
            )

            Card(
                modifier = Modifier.weight(1f),
                insideMargin = PaddingValues(16.dp),
                pressFeedbackType = PressFeedbackType.Tilt,
                onLongPress = { hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress) },
                content = {
                    Text(
                        color = MiuixTheme.colorScheme.onSurface,
                        text = "Card",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        text = "PressFeedback\nType: Tilt",
                        style = MiuixTheme.textStyles.paragraph
                    )
                }
            )
        }
    }
}