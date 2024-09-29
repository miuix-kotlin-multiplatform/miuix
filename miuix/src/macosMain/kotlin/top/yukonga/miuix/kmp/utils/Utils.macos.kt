package top.yukonga.miuix.kmp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.AppKit.NSApplication
import platform.AppKit.NSEvent
import platform.AppKit.NSEventMaskKeyDown
import platform.AppKit.NSWindow
import kotlin.math.roundToInt

actual fun platform(): Platform = Platform.MacOS

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun getWindowSize(): WindowSize {
    val window: NSWindow? = NSApplication.sharedApplication.mainWindow
    val contentLayoutRect = window?.contentLayoutRect ?: return WindowSize(0, 0)
    val density = LocalDensity.current.density
    val width = contentLayoutRect.useContents { size.width } * density
    val height = contentLayoutRect.useContents { size.height } * density
    return WindowSize(width.roundToInt(), height.roundToInt())
}

@Composable
actual fun getRoundedCorner(): Dp = 0.dp

@Composable
actual fun BackHandler(
    enabled: Boolean,
    onBack: () -> Unit
) {
    if (!enabled) return
    DisposableEffect(Unit) {
        val monitor = NSEvent.addLocalMonitorForEventsMatchingMask(NSEventMaskKeyDown) { event ->
            if (event?.keyCode == 53.toUShort()) {
                onBack()
                null
            } else {
                event
            }
        }

        onDispose {
            if (monitor != null) {
                NSEvent.removeMonitor(monitor)
            }
        }
    }
}