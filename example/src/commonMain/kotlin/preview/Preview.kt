// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package preview

import App
import FourthPage
import MainPage
import SecondPage
import ThirdPage
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.ui.tooling.preview.Preview
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.Scaffold
import ui.AppTheme

@Composable
@Preview
fun UITestPreview() {
    AppTheme {
        Scaffold {
            App()
        }
    }
}

@Composable
@Preview
fun MainPagePreview() {
    AppTheme {
        Scaffold {
            MainPage(MiuixScrollBehavior(), PaddingValues(), true)
        }
    }
}

@Composable
@Preview
fun SecondPagePreview() {
    AppTheme {
        Scaffold {
            SecondPage(
                MiuixScrollBehavior(),
                PaddingValues(),
                true
            )
        }
    }
}

@Composable
@Preview
fun ThirdPagePreview() {
    AppTheme {
        Scaffold {
            ThirdPage(
                MiuixScrollBehavior(),
                PaddingValues(),
                true
            )
        }
    }
}

@Composable
@Preview
fun FourthPagePreview() {
    AppTheme {
        Scaffold {
            FourthPage(
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
                false,
                remember { mutableIntStateOf(0) }
            )
        }
    }
}
