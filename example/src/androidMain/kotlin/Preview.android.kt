// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.uitest

import App
import MainPage
import SecondPage
import ThirdPage
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.Scaffold
import ui.AppTheme

@Composable
@Preview(device = "spec:width=1200px,height=2670px,dpi=480")
fun UITestPreview() {
    AppTheme {
        Scaffold {
            App()
        }
    }
}

@Composable
@Preview(device = "spec:width=1200px,height=2670px,dpi=480")
fun MainPagePreview() {
    AppTheme {
        Scaffold {
            MainPage(MiuixScrollBehavior(), PaddingValues(), true)
        }
    }
}

@Composable
@Preview(device = "spec:width=1200px,height=2670px,dpi=480")
fun SecondPagePreview() {
    AppTheme {
        Scaffold {
            SecondPage(MiuixScrollBehavior(), PaddingValues(), true)
        }
    }
}

@Composable
@Preview(device = "spec:width=1200px,height=2670px,dpi=480")
fun ThirdPagePreview() {
    AppTheme {
        Scaffold {
            ThirdPage(
                MiuixScrollBehavior(),
                PaddingValues(),
                false,
                {},
                false,
                {},
                false,
                {},
                false,
                {},
                0,
                {},
                0,
                {},
                false,
                {},
                1,
                {},
                1,
                {},
                false,
                {},
                2,
                {},
                false,
                {},
                true,
                {},
                remember { mutableIntStateOf(0) }
            )
        }
    }
}
