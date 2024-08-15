import androidx.compose.runtime.Composable

data class WindowSize(val width: Int, val height: Int)

@Composable
expect fun getWindowSize(): WindowSize


@Composable
expect fun platform(): String


