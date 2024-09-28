package top.yukonga.miuix.kmp.extra

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

/**
 * Returns modifier to be used for the current platform.
 */
@SuppressLint("ModifierFactoryExtensionFunction")
actual fun modifierPlatform(modifier: Modifier, isHovered: MutableState<Boolean>): Modifier = modifier