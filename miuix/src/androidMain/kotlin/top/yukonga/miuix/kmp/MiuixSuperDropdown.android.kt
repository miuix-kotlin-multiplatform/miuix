package top.yukonga.miuix.kmp

import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

actual fun modifierPlatform(modifier: Modifier, isHovered: MutableState<Boolean>): Modifier = modifier