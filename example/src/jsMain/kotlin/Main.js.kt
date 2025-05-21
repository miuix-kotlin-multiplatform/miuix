// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.jetbrains.skiko.wasm.onWasmReady
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array

private const val MiSanVF = "./MiSans VF.woff2"

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        ComposeViewport(
            viewportContainerId = "composeApplication"
        ) {
            val fontFamilyResolver = LocalFontFamilyResolver.current
            val fontsLoaded = remember { mutableStateOf(false) }

            if (fontsLoaded.value) {
                hideLoading()
                App()
            }

            LaunchedEffect(Unit) {
                val miSanVFBytes = loadRes(MiSanVF).toByteArray()
                val fontFamily = FontFamily(Font("MiSans VF", miSanVFBytes))
                fontFamilyResolver.preload(fontFamily)
                fontsLoaded.value = true
            }
        }
    }
}


suspend fun loadRes(url: String): ArrayBuffer {
    return window.fetch(url).await().arrayBuffer().await()
}

fun ArrayBuffer.toByteArray(): ByteArray {
    val source = Int8Array(this, 0, byteLength)
    return jsInt8ArrayToKotlinByteArray(source)
}

external fun hideLoading()

external fun jsInt8ArrayToKotlinByteArray(x: Int8Array): ByteArray