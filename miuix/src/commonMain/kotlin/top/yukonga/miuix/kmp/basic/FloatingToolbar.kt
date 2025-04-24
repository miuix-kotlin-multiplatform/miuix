package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape

/**
 * A [FloatingToolbar] that renders its content in a Card, arranged either horizontally or vertically.
 * The actual placement on screen is handled by the parent, typically Scaffold.
 *
 * @param modifier The modifier to be applied to the [FloatingToolbar].
 * @param color Background color of the [FloatingToolbar].
 * @param cornerRadius Corner radius of the [FloatingToolbar].
 * @param outSidePadding Padding outside the [FloatingToolbar].
 * @param showDivider Whether to show the divider line around the [FloatingToolbar].
 * @param defaultWindowInsetsPadding Whether to apply default window insets padding to the [FloatingToolbar].
 * @param content The [Composable] content of the [FloatingToolbar].
 */
@Composable
fun FloatingToolbar(
    modifier: Modifier = Modifier,
    color: Color = FloatingToolbarDefaults.DefaultColor(),
    cornerRadius: Dp = FloatingToolbarDefaults.CornerRadius,
    outSidePadding: PaddingValues = FloatingToolbarDefaults.OutSidePadding,
    showDivider: Boolean = true,
    defaultWindowInsetsPadding: Boolean = true,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(outSidePadding)
            .then(
                if (defaultWindowInsetsPadding) {
                    Modifier
                        .windowInsetsPadding(WindowInsets.statusBars.only(WindowInsetsSides.Vertical))
                        .windowInsetsPadding(WindowInsets.captionBar.only(WindowInsetsSides.Vertical))
                        .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
                        .windowInsetsPadding(WindowInsets.navigationBars)
                } else Modifier
            )
            .background(color = color)
            .then(
                if (showDivider) {
                    Modifier
                        .background(
                            color = MiuixTheme.colorScheme.dividerLine,
                            shape = SmoothRoundedCornerShape(cornerRadius)
                        )
                        .padding(0.75.dp)
                } else Modifier
            )
            .clip(SmoothRoundedCornerShape(cornerRadius))
            .pointerInput(Unit) { detectTapGestures { /* Do nothing to consume the click */ } }
    ) {
        Box(
            modifier = modifier
        ) {
            content()
        }
    }
}

object FloatingToolbarDefaults {

    /**
     * Default corner radius of the [FloatingToolbar].
     */
    val CornerRadius = 50.dp

    /**
     * Default color of the [FloatingToolbar].
     */
    @Composable
    fun DefaultColor() = MiuixTheme.colorScheme.surfaceContainer

    /**
     * Default padding outside the [FloatingToolbar].
     */
    val OutSidePadding = PaddingValues(12.dp, 8.dp)
}
