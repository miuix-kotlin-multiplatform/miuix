// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIScreen
import kotlin.math.roundToInt

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun getWindowSize(): WindowSize {
    val screenBounds = UIScreen.mainScreen.bounds
    val density = LocalDensity.current.density
    val width = screenBounds.useContents { size.width } * density
    val height = screenBounds.useContents { size.height } * density
    return WindowSize(width.roundToInt(), height.roundToInt())
}

actual fun platform(): Platform = Platform.IOS

@Composable
actual fun getRoundedCorner(): Dp = 0.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun BackHandler(
    enabled: Boolean,
    onBack: () -> Unit
) {
    androidx.compose.ui.backhandler.BackHandler(enabled, onBack)
}