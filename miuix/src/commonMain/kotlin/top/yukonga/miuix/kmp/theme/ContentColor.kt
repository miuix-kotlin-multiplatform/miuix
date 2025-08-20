// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * CompositionLocal containing the preferred content color for a given position in the hierarchy.
 * This typically represents the `on` color for a color in [Colors]. For example, if the
 * background color is [Colors.surface], this color is typically set to
 * [Colors.onSurface].
 *
 * This color should be used for any typography / iconography, to ensure that the color of these
 * adjusts when the background color changes. For example, on a dark background, text should be
 * light, and on a light background, text should be dark.
 *
 * Defaults to [Color.Black] if no color has been explicitly set.
 */
val LocalContentColor = compositionLocalOf { Color.Black }