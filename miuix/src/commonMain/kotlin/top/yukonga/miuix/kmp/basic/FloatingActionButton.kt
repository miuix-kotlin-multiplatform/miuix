// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * A [FloatingActionButton] component with Miuix style.
 *
 * @param onClick The callback when the [FloatingActionButton] is clicked.
 * @param modifier The modifier to be applied to the [FloatingActionButton].
 * @param shape The shape of the [FloatingActionButton].
 * @param containerColor The color of the [FloatingActionButton].
 * @param shadowElevation The shadow elevation of the [FloatingActionButton].
 * @param minWidth The minimum width of the [FloatingActionButton].
 * @param minHeight The minimum height of the [FloatingActionButton].
 * @param defaultWindowInsetsPadding Whether to apply default window insets padding to the [FloatingActionButton].
 * @param content The [Composable] content of the [FloatingActionButton].
 */
@Composable
fun FloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    containerColor: Color = MiuixTheme.colorScheme.primary,
    shadowElevation: Dp = 4.dp,
    minWidth: Dp = 60.dp,
    minHeight: Dp = 60.dp,
    defaultWindowInsetsPadding: Boolean = true,
    content: @Composable () -> Unit,
) {
    val currentOnClick by rememberUpdatedState(onClick)

    val displayCutoutInsets = WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal)
    val navigationBarsInsets = WindowInsets.navigationBars.only(WindowInsetsSides.Horizontal)

    val surfaceModifier = remember(modifier, defaultWindowInsetsPadding, displayCutoutInsets, navigationBarsInsets) {
        val baseModifier = if (defaultWindowInsetsPadding) {
            modifier
                .windowInsetsPadding(displayCutoutInsets)
                .windowInsetsPadding(navigationBarsInsets)
        } else {
            modifier
        }
        baseModifier.semantics { role = Role.Button }
    }

    Surface(
        onClick = currentOnClick,
        modifier = surfaceModifier,
        shape = shape,
        color = containerColor,
        shadowElevation = shadowElevation
    ) {
        val boxModifier = remember(minWidth, minHeight) {
            Modifier
                .defaultMinSize(
                    minWidth = minWidth,
                    minHeight = minHeight,
                )
        }
        Box(
            modifier = boxModifier,
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}
