// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape

/**
 * A [FloatingToolbar] that renders its content in a Card, arranged either horizontally or vertically.
 * The actual placement on screen is handled by the parent, typically Scaffold.
 *
 * @param modifier The modifier to be applied to the [FloatingToolbar].
 * @param color Background color of the [FloatingToolbar].
 * @param cornerRadius Corner radius of the [FloatingToolbar].
 * @param outSidePadding Padding outside the [FloatingToolbar].
 * @param shadowElevation The shadow elevation of the [FloatingToolbar].
 * @param showDivider Whether to show the divider line around the [FloatingToolbar].
 * @param defaultWindowInsetsPadding Whether to apply default window insets padding to the [FloatingToolbar].
 * @param content The [Composable] content of the [FloatingToolbar].
 */
@Composable
fun FloatingToolbar(
    modifier: Modifier = Modifier,
    color: Color = FloatingToolbarDefaults.DefaultColor(),
    cornerRadius: Dp = FloatingToolbarDefaults.CornerRadius,
    outSidePadding: PaddingValues = FloatingToolbarDefaults.OutSidePadding,
    shadowElevation: Dp = 4.dp,
    showDivider: Boolean = false,
    defaultWindowInsetsPadding: Boolean = true,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val statusBarsInsets = WindowInsets.statusBars.only(WindowInsetsSides.Vertical)
    val captionBarInsets = WindowInsets.captionBar.only(WindowInsetsSides.Vertical)
    val displayCutoutInsets = WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal)
    val navigationBarsInsets = WindowInsets.navigationBars

    val roundedCornerShape = remember(cornerRadius) { SmoothRoundedCornerShape(cornerRadius) }
    val dividerColor = MiuixTheme.colorScheme.dividerLine

    val outerBoxModifier = remember(
        outSidePadding, defaultWindowInsetsPadding, statusBarsInsets, captionBarInsets,
        displayCutoutInsets, navigationBarsInsets, showDivider, dividerColor, roundedCornerShape,
        shadowElevation, density, cornerRadius, color
    ) {
        Modifier
            .padding(outSidePadding)
            .then(
                if (defaultWindowInsetsPadding) {
                    Modifier
                        .windowInsetsPadding(statusBarsInsets)
                        .windowInsetsPadding(captionBarInsets)
                        .windowInsetsPadding(displayCutoutInsets)
                        .windowInsetsPadding(navigationBarsInsets)
                } else Modifier
            )
            .then(
                if (showDivider) {
                    Modifier
                        .background(
                            color = dividerColor,
                            shape = roundedCornerShape
                        )
                        .padding(0.75.dp)
                } else Modifier
            )
            .then(
                if (shadowElevation > 0.dp) {
                    Modifier.graphicsLayer(
                        shadowElevation = with(density) { shadowElevation.toPx() },
                        shape = roundedCornerShape,
                        clip = cornerRadius > 0.dp
                    )
                } else if (cornerRadius > 0.dp) {
                    Modifier.clip(roundedCornerShape)
                } else {
                    Modifier
                }
            )
            .background(color = color)
            .pointerInput(Unit) { detectTapGestures { /* Do nothing to consume the click */ } }
    }

    Box(
        modifier = outerBoxModifier
    ) {
        Box(
            modifier = modifier
        ) {
            content()
        }
    }
}

object FloatingToolbarDefaults {

    /**
     * Default corner radius of the [FloatingToolbar].
     */
    val CornerRadius = 50.dp

    /**
     * Default color of the [FloatingToolbar].
     */
    @Composable
    fun DefaultColor() = MiuixTheme.colorScheme.surfaceContainer

    /**
     * Default padding outside the [FloatingToolbar].
     */
    val OutSidePadding = PaddingValues(12.dp, 8.dp)
}
