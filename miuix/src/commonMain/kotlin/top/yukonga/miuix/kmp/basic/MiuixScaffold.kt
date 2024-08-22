package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.onConsumedWindowInsetsChanged
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import top.yukonga.miuix.kmp.MiuixTopAppBar
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.MiuixPopupUtil
import top.yukonga.miuix.kmp.utils.MiuixPopupUtil.Companion.MiuixPopupHost

/**
 * A scaffold component with Miuix style.
 * Scaffold implements the basic material design visual layout structure.
 *
 * This component provides API to put together several material components to construct your screen,
 * by ensuring proper layout strategy for them and collecting necessary data so these components
 * will work together correctly.
 *
 * To show a [Snackbar], use [SnackbarHostState.showSnackbar].
 * To show a [MiuixDialog], use [MiuixPopupUtil.showDialog].
 * To show a [MiuixPopup], use [MiuixPopupUtil.showPopup].
 *
 * @param modifier the [Modifier] to be applied to this scaffold
 * @param topBar top app bar of the screen
 * @param bottomBar bottom bar of the screen
 * @param snackbarHost component to host [Snackbar]s that are pushed to be shown via
 *   [SnackbarHostState.showSnackbar], typically a [SnackbarHost]
 * @param enableTopBarBlur whether to enable blur effect on the [MiuixTopAppBar].
 * @param alpha the alpha value of the blur effect. Default is 0.75f.
 * @param blurRadius the radius of the blur effect. Default is 25.dp.
 * @param noiseFactor the noise factor of the blur effect. Default is 0f.
 * @param containerColor the color used for the background of this scaffold. Use [Color.Transparent]
 *   to have no color.
 * @param contentWindowInsets window insets to be passed to [content] slot via [PaddingValues]
 *   params. Scaffold will take the insets into account from the top/bottom only if the [topBar]/
 *   [bottomBar] are not present, as the scaffold expect [topBar]/[bottomBar] to handle insets
 *   instead. Any insets consumed by other insets padding modifiers or [consumeWindowInsets] on a
 *   parent layout will be excluded from [contentWindowInsets].
 * @param content content of the screen. The lambda receives a [PaddingValues] that should be
 *   applied to the content root via [Modifier.padding] and [Modifier.consumeWindowInsets] to
 *   properly offset top and bottom bars. If using [Modifier.verticalScroll], apply this modifier to
 *   the child of the scroll, and not on the scroll itself.
 */
@Composable
fun MiuixScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    enableTopBarBlur: Boolean = true,
    enableBottomBarBlur: Boolean = true,
    alpha: Float = 0.75f,
    blurRadius: Dp = 25.dp,
    noiseFactor: Float = 0f,
    containerColor: Color = MiuixTheme.colorScheme.background,
    contentWindowInsets: WindowInsets = WindowInsets.statusBars,
    content: @Composable (PaddingValues) -> Unit
) {
    val safeInsets = remember(contentWindowInsets) { MutableWindowInsets(contentWindowInsets) }
    MiuixSurface(
        modifier = modifier.onConsumedWindowInsetsChanged { consumedWindowInsets ->
            // Exclude currently consumed window insets from user provided contentWindowInsets
            safeInsets.insets = contentWindowInsets.exclude(consumedWindowInsets)
        },
        color = containerColor,
    ) {
        val hazeState = remember { HazeState() }

        ScaffoldLayout(
            topBar = {
                if (enableTopBarBlur) {
                    MiuixBox(Modifier.hazeChild(hazeState)) {
                        topBar()
                    }
                } else {
                    topBar()
                }
            },
            bottomBar = {
                if (enableBottomBarBlur) {
                    MiuixBox(Modifier.hazeChild(hazeState)) {
                        bottomBar()
                    }
                } else {
                    bottomBar()
                }
            },
            content = {
                MiuixBox(
                    Modifier.haze(
                        state = hazeState, style = HazeStyle(
                            backgroundColor = containerColor,
                            tint = HazeTint.Color(containerColor.copy(alpha)),
                            blurRadius = blurRadius,
                            noiseFactor = noiseFactor
                        )
                    )
                ) {
                    content(it)
                }
            },
            snackbar = snackbarHost,
            popup = { MiuixPopupHost() },
            contentWindowInsets = safeInsets,
        )
    }
}

@Composable
private fun ScaffoldLayout(
    topBar: @Composable () -> Unit,
    snackbar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    popup: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    contentWindowInsets: WindowInsets,
) {
    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val popupPlaceables =
            subcompose(ScaffoldLayoutContent.Popup, popup).fastMap {
                it.measure(looseConstraints)
            }

        val topBarPlaceables =
            subcompose(ScaffoldLayoutContent.TopBar, topBar).fastMap {
                it.measure(looseConstraints)
            }

        val topBarHeight = topBarPlaceables.fastMaxBy { it.height }?.height ?: 0

        val snackbarPlaceables =
            subcompose(ScaffoldLayoutContent.Snackbar, snackbar).fastMap {
                // respect only bottom and horizontal for snackbar and fab
                val leftInset = contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection)
                val rightInset =
                    contentWindowInsets.getRight(this@SubcomposeLayout, layoutDirection)
                val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)
                // offset the snackbar constraints by the insets values
                it.measure(looseConstraints.offset(-leftInset - rightInset, -bottomInset))
            }

        val snackbarHeight = snackbarPlaceables.fastMaxBy { it.height }?.height ?: 0
        val snackbarWidth = snackbarPlaceables.fastMaxBy { it.width }?.width ?: 0


        val bottomBarPlaceables =
            subcompose(ScaffoldLayoutContent.BottomBar) { bottomBar() }
                .fastMap { it.measure(looseConstraints) }

        val bottomBarHeight = bottomBarPlaceables.fastMaxBy { it.height }?.height

        val snackbarOffsetFromBottom =
            if (snackbarHeight != 0) {
                snackbarHeight + (bottomBarHeight ?: contentWindowInsets.getBottom(this@SubcomposeLayout))
            } else {
                0
            }

        val bodyContentPlaceables =
            subcompose(ScaffoldLayoutContent.MainContent) {
                val insets = contentWindowInsets.asPaddingValues(this@SubcomposeLayout)
                val innerPadding =
                    PaddingValues(
                        top =
                        if (topBarPlaceables.isEmpty()) {
                            insets.calculateTopPadding()
                        } else {
                            topBarHeight.toDp()
                        },
                        bottom =
                        if (bottomBarPlaceables.isEmpty() || bottomBarHeight == null) {
                            insets.calculateBottomPadding()
                        } else {
                            bottomBarHeight.toDp()
                        },
                        start =
                        insets.calculateStartPadding(
                            (this@SubcomposeLayout).layoutDirection
                        ),
                        end =
                        insets.calculateEndPadding((this@SubcomposeLayout).layoutDirection)
                    )
                content(innerPadding)
            }
                .fastMap { it.measure(looseConstraints) }

        layout(layoutWidth, layoutHeight) {
            // Placing to control drawing order to match default elevation of each placeable
            popupPlaceables.fastForEach { it.place(0, 0) }
            bodyContentPlaceables.fastForEach { it.place(0, 0) }
            topBarPlaceables.fastForEach { it.place(0, 0) }
            snackbarPlaceables.fastForEach {
                it.place(
                    (layoutWidth - snackbarWidth) / 2 +
                            contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection),
                    layoutHeight - snackbarOffsetFromBottom
                )
            }
            // The bottom bar is always at the bottom of the layout
            bottomBarPlaceables.fastForEach { it.place(0, layoutHeight - (bottomBarHeight ?: 0)) }
        }
    }
}

private enum class ScaffoldLayoutContent {
    TopBar,
    BottomBar,
    Snackbar,
    Popup,
    MainContent
}

internal class MutableWindowInsets(initialInsets: WindowInsets = WindowInsets(0, 0, 0, 0)) :
    WindowInsets {
    /**
     * The [WindowInsets] that are used for [left][getLeft], [top][getTop], [right][getRight], and
     * [bottom][getBottom] values.
     */
    var insets by mutableStateOf(initialInsets)

    override fun getLeft(density: Density, layoutDirection: LayoutDirection): Int =
        insets.getLeft(density, layoutDirection)

    override fun getTop(density: Density): Int = insets.getTop(density)

    override fun getRight(density: Density, layoutDirection: LayoutDirection): Int =
        insets.getRight(density, layoutDirection)

    override fun getBottom(density: Density): Int = insets.getBottom(density)
}
