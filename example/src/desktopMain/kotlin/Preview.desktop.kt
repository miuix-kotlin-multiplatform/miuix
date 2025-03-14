import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.rememberTopAppBarState
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
            MainPage(MiuixScrollBehavior(rememberTopAppBarState()), PaddingValues())
        }
    }
}


@Composable
@Preview
fun SecondPagePreview() {
    AppTheme {
        Scaffold {
            SecondPage(MiuixScrollBehavior(rememberTopAppBarState()), PaddingValues())
        }
    }
}


@Composable
@Preview
fun ThirdPagePreview() {
    AppTheme {
        Scaffold {
            ThirdPage(
                MiuixScrollBehavior(rememberTopAppBarState()),
                PaddingValues(),
                false,
                {},
                false,
                {},
                false,
                {},
                false,
                {},
                false,
                {},
                remember { mutableStateOf(0) }
            )
        }
    }
}
