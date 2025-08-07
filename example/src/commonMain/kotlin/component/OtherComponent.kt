// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.CardDefaults
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
import top.yukonga.miuix.kmp.icon.icons.useful.Like
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.ColorUtils.colorToHsv
import top.yukonga.miuix.kmp.utils.ColorUtils.colorToOkLab
import top.yukonga.miuix.kmp.utils.PressFeedbackType
import kotlin.math.round

fun LazyListScope.otherComponent(
    miuixIcons: List<ImageVector>,
    focusManager: FocusManager,
    padding: PaddingValues
) {
    item(key = "button") {
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

    item(key = "progressIndicator") {
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

    item(key = "textField") {
        var text1 by remember { mutableStateOf("") }
        var text2 by remember { mutableStateOf(TextFieldValue("")) }
        val text3 = rememberTextFieldState(initialText = "")
        var text4 by remember { mutableStateOf("") }

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
        TextField(
            value = text2,
            onValueChange = { text2 = it },
            label = "With title",
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        TextField(
            state = text3,
            label = "State-based",
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
            onKeyboardAction = { focusManager.clearFocus() },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        )
        TextField(
            value = text4,
            onValueChange = { text4 = it },
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

    item(key = "slider") {
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
        var progressHaptic by remember { mutableStateOf(0.5f) }
        Slider(
            progress = progressHaptic,
            onProgressChange = { newProgress -> progressHaptic = newProgress },
            hapticEffect = SliderDefaults.SliderHapticEffect.Step,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
        )
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

    item(key = "tabRow") {
        SmallTitle(text = "TabRow")
        val tabTexts = remember { listOf("Tab 1", "Tab 2", "Tab 3") }
        val tabTexts1 = remember { listOf("Tab 1", "Tab 2", "Tab 3", "Tab 4", "Tab 5", "Tab 6") }
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
                tabs = tabTexts1,
                selectedTabIndex = selectedTabIndex1,
            ) {
                selectedTabIndex1 = it
            }
            val selectedTabText by remember(selectedTabIndex1) {
                derivedStateOf { tabTexts1[selectedTabIndex1] }
            }
            Text(
                text = "Selected Tab: $selectedTabText",
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }

    item(key = "icon") {
        SmallTitle(text = "Icon")
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 6.dp),
            insideMargin = PaddingValues(16.dp)
        ) {
            FlowRow {
                miuixIcons.forEach { icon ->
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

    item(key = "colorPicker-hsva") {
        SmallTitle(text = "ColorPicker (HSVA)")
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
                val hsv = colorToHsv(selectedColor)
                Text(
                    text = "HEX: #${selectedColor.toArgb().toHexString(HexFormat.UpperCase)}" +
                            "\nRGBA: ${(selectedColor.red * 255).toInt()}, " +
                            "${(selectedColor.green * 255).toInt()}, " +
                            "${(selectedColor.blue * 255).toInt()}, " +
                            "${(round(selectedColor.alpha * 100) / 100.0)}" +
                            "\nHSVA: ${(hsv[0]).toInt()}, " +
                            "${(hsv[1] * 100).toInt()}%, " +
                            "${(hsv[2] * 100).toInt()}%, " +
                            "${(round(selectedColor.alpha * 100) / 100.0)}",
                    modifier = Modifier.weight(1f)
                )
            }
            ColorPicker(
                initialColor = selectedColor,
                onColorChanged = { selectedColor = it },
                hapticEffect = SliderDefaults.SliderHapticEffect.Step
            )
        }
    }

    item(key = "colorPicker-okLab") {
        SmallTitle(text = "ColorPicker (OkLab)")
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
                val okLab = colorToOkLab(selectedColor)
                Text(
                    text = "HEX: #${selectedColor.toArgb().toHexString(HexFormat.UpperCase)}" +
                            "\nRGBA: ${(selectedColor.red * 255).toInt()}, " +
                            "${(selectedColor.green * 255).toInt()}, " +
                            "${(selectedColor.blue * 255).toInt()}, " +
                            "${(round(selectedColor.alpha * 100) / 100.0)}" +
                            "\nOkLab: ${(okLab[0] * 1000).toInt() / 1000f}, " +
                            "${(okLab[1] * 1000).toInt() / 1000f}, " +
                            "${(okLab[2] * 1000).toInt() / 1000f} / " +
                            "${(round(selectedColor.alpha * 100) / 100.0)}",
                    modifier = Modifier.weight(1f)
                )
            }
            ColorPicker(
                initialColor = selectedColor,
                onColorChanged = { selectedColor = it },
                hapticEffect = SliderDefaults.SliderHapticEffect.Step,
                useOkLab = true
            )
        }
    }

    item(key = "card") {
        SmallTitle(text = "Card")
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
            colors = CardDefaults.defaultColors(
                color = MiuixTheme.colorScheme.primaryVariant
            ),
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