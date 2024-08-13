package top.yukonga.miuix.kmp.basic

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.window.SecureFlagPolicy
import top.yukonga.miuix.kmp.R

@Composable
actual fun MiuixBasicDialog(
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

        content()
    }
}

@Composable
actual fun MiuixAnimatorDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
    content: @Composable () -> Unit
) {
    MiuixBasicDialog(
        onDismissRequest, properties
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider

        val window = dialogWindowProvider.window
        window.setWindowAnimations(R.style.MiuixDialogAnimation)

        val layoutParams = window.attributes
        layoutParams.dimAmount = 0.3f
        window.attributes = layoutParams

        content()
    }
}