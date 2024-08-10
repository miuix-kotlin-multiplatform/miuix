package top.yukonga.miuix.kmp.basic

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties

@Composable
expect fun MiuixDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
)
