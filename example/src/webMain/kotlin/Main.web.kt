// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    hideLoading()
    ComposeViewport(viewportContainerId = "composeApp") {
        App()
    }
}

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun(
    """
        function hideLoading() {
            document.getElementById('loading').style.display = 'none';
            document.getElementById('composeApp').style.display = 'block';
        }
    """
)
external fun hideLoading()
