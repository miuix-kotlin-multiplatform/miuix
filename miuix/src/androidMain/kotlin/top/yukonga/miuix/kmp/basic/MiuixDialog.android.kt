package top.yukonga.miuix.kmp.basic

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.window.SecureFlagPolicy

@Composable
actual fun MiuixDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            decorFitsSystemWindows = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            securePolicy = SecureFlagPolicy.SecureOff
        )
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        val wdw = dialogWindowProvider.window
        wdw.setDimAmount(0.3f)
        //wdw.setWindowAnimations()

        content()
    }
}
