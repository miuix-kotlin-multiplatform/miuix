package top.yukonga.miuix.kmp.extra

import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent

/**
 * Returns modifier to be used for the current platform.
 */
@OptIn(ExperimentalComposeUiApi::class)
actual fun modifierPlatform(modifier: Modifier, isHovered: MutableState<Boolean>): Modifier = modifier
    .onPointerEvent(PointerEventType.Move) {
        isHovered.value = true
    }
    .onPointerEvent(PointerEventType.Enter) {
        isHovered.value = false
    }
    .onPointerEvent(PointerEventType.Exit) {
        isHovered.value = false
    }