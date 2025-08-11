// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.CardDefaults
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.Surface
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.Colors
import top.yukonga.miuix.kmp.theme.darkColorScheme
import top.yukonga.miuix.kmp.theme.lightColorScheme
import top.yukonga.miuix.kmp.utils.getWindowSize
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuix.kmp.utils.scrollEndHaptic

@Composable
fun ThirdPage(
    topAppBarScrollBehavior: ScrollBehavior,
    padding: PaddingValues,
    scrollEndHaptic: Boolean
) {
    val windowSize by rememberUpdatedState(getWindowSize())

    LazyColumn(
        modifier = Modifier
            .then(
                if (scrollEndHaptic) Modifier.scrollEndHaptic() else Modifier
            )
            .overScrollVertical()
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
            .height(windowSize.height.dp),
        contentPadding = PaddingValues(top = padding.calculateTopPadding()),
        overscrollEffect = null
    ) {
        item(key = "light") {
            SmallTitle("Light Theme Colors")
            Card(
                modifier = Modifier.padding(horizontal = 12.dp).padding(bottom = 6.dp),
                colors = CardDefaults.defaultColors(
                    color = lightColorScheme().surface
                ),
                cornerRadius = 16.dp,
                insideMargin = PaddingValues(horizontal = 16.dp)
            ) {
                ColorsPreview(lightColorScheme())
            }
        }
        item(key = "dark") {
            SmallTitle("Dark Theme Colors")
            Card(
                modifier = Modifier.padding(horizontal = 12.dp),
                colors = CardDefaults.defaultColors(
                    color = darkColorScheme().surface
                ),
                cornerRadius = 16.dp,
                insideMargin = PaddingValues(horizontal = 16.dp)
            ) {
                ColorsPreview(darkColorScheme())
            }
            Spacer(modifier = Modifier.height(12.dp + padding.calculateBottomPadding()))
        }
    }
}

@Composable
fun ColorsPreview(colors: Colors) {
    val colorList = listOf(
        "primary" to colors.primary,
        "onPrimary" to colors.onPrimary,
        "primaryVariant" to colors.primaryVariant,
        "onPrimaryVariant" to colors.onPrimaryVariant,
        "disabledPrimary" to colors.disabledPrimary,
        "disabledOnPrimary" to colors.disabledOnPrimary,
        "disabledPrimaryButton" to colors.disabledPrimaryButton,
        "disabledOnPrimaryButton" to colors.disabledOnPrimaryButton,
        "disabledPrimarySlider" to colors.disabledPrimarySlider,
        "primaryContainer" to colors.primaryContainer,
        "onPrimaryContainer" to colors.onPrimaryContainer,
        "secondary" to colors.secondary,
        "onSecondary" to colors.onSecondary,
        "secondaryVariant" to colors.secondaryVariant,
        "onSecondaryVariant" to colors.onSecondaryVariant,
        "disabledSecondary" to colors.disabledSecondary,
        "disabledOnSecondary" to colors.disabledOnSecondary,
        "disabledSecondaryVariant" to colors.disabledSecondaryVariant,
        "disabledOnSecondaryVariant" to colors.disabledOnSecondaryVariant,
        "secondaryContainer" to colors.secondaryContainer,
        "onSecondaryContainer" to colors.onSecondaryContainer,
        "secondaryContainerVariant" to colors.secondaryContainerVariant,
        "onSecondaryContainerVariant" to colors.onSecondaryContainerVariant,
        "tertiaryContainer" to colors.tertiaryContainer,
        "onTertiaryContainer" to colors.onTertiaryContainer,
        "tertiaryContainerVariant" to colors.tertiaryContainerVariant,
        "background" to colors.background,
        "onBackground" to colors.onBackground,
        "onBackgroundVariant" to colors.onBackgroundVariant,
        "surface" to colors.surface,
        "onSurface" to colors.onSurface,
        "surfaceVariant" to colors.surfaceVariant,
        "onSurfaceSecondary" to colors.onSurfaceSecondary,
        "onSurfaceVariantSummary" to colors.onSurfaceVariantSummary,
        "onSurfaceVariantActions" to colors.onSurfaceVariantActions,
        "disabledOnSurface" to colors.disabledOnSurface,
        "outline" to colors.outline,
        "dividerLine" to colors.dividerLine,
        "surfaceContainer" to colors.surfaceContainer,
        "onSurfaceContainer" to colors.onSurfaceContainer,
        "onSurfaceContainerVariant" to colors.onSurfaceContainerVariant,
        "surfaceContainerHigh" to colors.surfaceContainerHigh,
        "onSurfaceContainerHigh" to colors.onSurfaceContainerHigh,
        "surfaceContainerHighest" to colors.surfaceContainerHighest,
        "onSurfaceContainerHighest" to colors.onSurfaceContainerHighest,
        "windowDimming" to colors.windowDimming,
    )
    val colorMap = colorList.toMap()

    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        colorList.chunked(2).forEach { rowColors ->
            Row(
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                rowColors.forEachIndexed { idx, (name, color) ->
                    val (surfaceColor, textColor) = when {
                        name.startsWith("on") && colorMap.containsKey(name.removePrefix("on").replaceFirstChar { it.lowercase() }) -> {
                            color to colorMap[name.removePrefix("on").replaceFirstChar { it.lowercase() }]!!
                        }

                        !name.startsWith("on") && colorMap.containsKey("on" + name.replaceFirstChar { it.uppercase() }) -> {
                            color to colorMap["on" + name.replaceFirstChar { it.uppercase() }]!!
                        }

                        else -> color to (if (color.luminance() > 0.5) Color.Black else Color.White)
                    }
                    ColorBlock(
                        name = name,
                        surfaceColor = surfaceColor,
                        textColor = textColor,
                        modifier = Modifier
                            .weight(1f)
                            .then(if (idx < rowColors.lastIndex) Modifier.padding(end = 16.dp) else Modifier)
                    )
                }
            }
        }
    }
}

@Composable
private fun ColorBlock(name: String, surfaceColor: Color, textColor: Color, modifier: Modifier = Modifier) {
    Surface(
        color = surfaceColor,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, textColor),
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.replace(Regex("([A-Z])"), " $1").trim().replaceFirstChar { it.uppercase() },
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

