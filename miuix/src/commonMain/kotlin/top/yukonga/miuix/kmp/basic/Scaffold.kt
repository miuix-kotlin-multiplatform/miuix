// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.onConsumedWindowInsetsChanged
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMapNotNull
import androidx.compose.ui.util.fastMaxBy
import top.yukonga.miuix.kmp.extra.SuperDialog
import top.yukonga.miuix.kmp.extra.SuperDropdown
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.MiuixPopupUtils.Companion.MiuixPopupHost

/**
 * A [Scaffold] component with Miuix style.
 *
 * This implements the basic miuix design visual layout structure.
 *
 * To show a [Snackbar], use [SnackbarHostState.showSnackbar].
 *
 * @param modifier the [Modifier] to be applied to this scaffold.
 * @param topBar top app bar of the screen.
 * @param bottomBar bottom bar of the screen.
 * @param floatingActionButton floating action button of the screen.
 * @param floatingActionButtonPosition position of the floating action button.
 * @param floatingToolbar floating toolbar of the screen.
 * @param floatingToolbarPosition position of the floating toolbar.
 * @param snackbarHost component to host [Snackbar]s that are pushed to be shown via
 *   [SnackbarHostState.showSnackbar], typically a [SnackbarHost].
 * @param popupHost component to host [SuperDropdown]s & [SuperDialog]s that are pushed to be shown via
 *   [MiuixPopupUtils.showPopup] & [MiuixPopupUtils.showDialog], typically a [MiuixPopupHost].
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
fun Scaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    floatingToolbar: @Composable () -> Unit = {},
    floatingToolbarPosition: ToolbarPosition = ToolbarPosition.BottomCenter,
    snackbarHost: @Composable () -> Unit = {},
    popupHost: @Composable () -> Unit = { MiuixPopupHost() },
    containerColor: Color = MiuixTheme.colorScheme.background,
    contentWindowInsets: WindowInsets = WindowInsets.statusBars,
    content: @Composable (PaddingValues) -> Unit
) {
    val safeInsets = remember(contentWindowInsets) { MutableWindowInsets(contentWindowInsets) }

    Surface(
        modifier = modifier.onConsumedWindowInsetsChanged { consumedWindowInsets ->
            // Exclude currently consumed window insets from user provided contentWindowInsets
            safeInsets.insets = contentWindowInsets.exclude(consumedWindowInsets)
        },
        color = containerColor
    ) {
        ScaffoldLayout(
            topBar = {
                topBar()
            },
            bottomBar = {
                bottomBar()
            },
            content = {
                content(it)
            },
            snackbar = snackbarHost,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            floatingToolbar = floatingToolbar,
            floatingToolbarPosition = floatingToolbarPosition,
            popup = popupHost,
            contentWindowInsets = safeInsets,
        )
    }
}

/**
 * Layout for a [Scaffold]'s content.
 *
 * @param topBar the content to place at the top of the [Scaffold], typically a [TopAppBar]
 * @param snackbar the [Snackbar] displayed on top of the [content].
 * @param bottomBar the content to place at the bottom of the [Scaffold], on top of the [content],
 *   typically a [NavigationBar].
 * @param floatingActionButton the [FloatingActionButton] displayed on top of the [content], below the [snackbar] and
 *   above the [NavigationBar]
 * @param floatingActionButtonPosition [FabPosition] for the FAB (if present).
 * @param floatingToolbar the [FloatingToolbar] displayed on top of the [content].
 * @param floatingToolbarPosition [ToolbarPosition] for the floating toolbar (if present).
 * @param popup the [MiuixPopupHost] displayed on top of the [content].
 * @param content the main 'body' of the [Scaffold].
 * @param contentWindowInsets the [WindowInsets] to apply to the [content].
 */
@Composable
private fun ScaffoldLayout(
    topBar: @Composable () -> Unit,
    snackbar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    floatingActionButton: @Composable () -> Unit,
    floatingActionButtonPosition: FabPosition,
    floatingToolbar: @Composable () -> Unit,
    floatingToolbarPosition: ToolbarPosition,
    popup: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    contentWindowInsets: WindowInsets
) {
    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        // Measure Popups first (highest z-index)
        val popupPlaceables =
            subcompose(ScaffoldLayoutContent.Popup, popup).fastMap {
                it.measure(looseConstraints)
            }

        // Measure TopBar
        val topBarPlaceables =
            subcompose(ScaffoldLayoutContent.TopBar, topBar).fastMap {
                it.measure(looseConstraints)
            }
        val topBarHeight = topBarPlaceables.fastMaxBy { it.height }?.height ?: 0

        // Measure Snackbar
        val snackbarPlaceables =
            subcompose(ScaffoldLayoutContent.Snackbar, snackbar).fastMap {
                val leftInset = contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection)
                val rightInset = contentWindowInsets.getRight(this@SubcomposeLayout, layoutDirection)
                val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)
                it.measure(looseConstraints.offset(-leftInset - rightInset, -bottomInset))
            }
        val snackbarHeight = snackbarPlaceables.fastMaxBy { it.height }?.height ?: 0
        val snackbarWidth = snackbarPlaceables.fastMaxBy { it.width }?.width ?: 0

        // Measure FAB
        val fabPlaceables =
            subcompose(ScaffoldLayoutContent.Fab, floatingActionButton).fastMapNotNull { measurable ->
                val leftInset = contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection)
                val rightInset = contentWindowInsets.getRight(this@SubcomposeLayout, layoutDirection)
                val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)
                measurable
                    .measure(looseConstraints.offset(-leftInset - rightInset, -bottomInset))
                    .takeIf { it.height != 0 && it.width != 0 }
            }
        val fabPlacement = if (fabPlaceables.isNotEmpty()) {
            val fabWidth = fabPlaceables.fastMaxBy { it.width }!!.width
            val fabHeight = fabPlaceables.fastMaxBy { it.height }!!.height
            // FAB distance from the left of the layout, taking into account LTR / RTL
            val fabLeftOffset =
                when (floatingActionButtonPosition) {
                    FabPosition.Start -> {
                        if (layoutDirection == LayoutDirection.Ltr) {
                            FabSpacing.roundToPx()
                        } else {
                            layoutWidth - FabSpacing.roundToPx() - fabWidth
                        }
                    }

                    FabPosition.End,
                    FabPosition.EndOverlay -> {
                        if (layoutDirection == LayoutDirection.Ltr) {
                            layoutWidth - FabSpacing.roundToPx() - fabWidth
                        } else {
                            FabSpacing.roundToPx()
                        }
                    }

                    else -> (layoutWidth - fabWidth) / 2
                }

            FabPlacement(left = fabLeftOffset, width = fabWidth, height = fabHeight)
        } else {
            null
        }

        // Measure BottomBar
        val bottomBarPlaceables =
            subcompose(ScaffoldLayoutContent.BottomBar) { bottomBar() }
                .fastMap { it.measure(looseConstraints) }
        val bottomBarHeight = bottomBarPlaceables.fastMaxBy { it.height }?.height

        // Calculate FAB offset from bottom
        val fabOffsetFromBottom = fabPlacement?.let {
            if (bottomBarHeight == null || floatingActionButtonPosition == FabPosition.EndOverlay) {
                it.height + FabSpacing.roundToPx() + contentWindowInsets.getBottom(this@SubcomposeLayout)
            } else {
                // Total height is the bottom bar height + the FAB height + the padding
                // between the FAB and bottom bar
                bottomBarHeight + it.height + FabSpacing.roundToPx()
            }
        }

        // Calculate Snackbar offset from bottom
        val snackbarOffsetFromBottom = if (snackbarHeight != 0) {
            snackbarHeight +
                    (fabOffsetFromBottom
                        ?: bottomBarHeight
                        ?: contentWindowInsets.getBottom(this@SubcomposeLayout))
        } else {
            0
        }

        // Measure FloatingToolbar
        val floatingToolbarPlaceables =
            subcompose(ScaffoldLayoutContent.FloatingToolbar, floatingToolbar).fastMapNotNull { measurable ->
                measurable.measure(looseConstraints)
                    .takeIf { it.height != 0 && it.width != 0 }
            }
        val floatingToolbarWidth = floatingToolbarPlaceables.fastMaxBy { it.width }?.width ?: 0
        val floatingToolbarHeight = floatingToolbarPlaceables.fastMaxBy { it.height }?.height ?: 0

        // Measure Main Content
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
            // Place Popups first
            popupPlaceables.fastForEach { it.place(0, 0) }

            // Place Content
            bodyContentPlaceables.fastForEach { it.place(0, 0) }

            // Place TopBar
            topBarPlaceables.fastForEach { it.place(0, 0) }

            // Place Snackbar
            snackbarPlaceables.fastForEach {
                val left = (layoutWidth - snackbarWidth) / 2 + contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection)
                val top = layoutHeight - snackbarOffsetFromBottom
                it.place(left, top)
            }

            // Place BottomBar
            bottomBarPlaceables.fastForEach { it.place(0, layoutHeight - (bottomBarHeight ?: 0)) }

            // Place FloatingToolbar
            if (floatingToolbarPlaceables.isNotEmpty()) {
                val alignment = floatingToolbarPosition.toAlignment() // Use internal extension
                val topInset = contentWindowInsets.getTop(this@SubcomposeLayout)
                val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)
                val startInset = contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection)
                val endInset = contentWindowInsets.getRight(this@SubcomposeLayout, layoutDirection)

                // Adjust available space for insets more accurately
                val availableWidth = layoutWidth - startInset - endInset
                // Available height considers top bar height and bottom insets
                val availableHeight = layoutHeight - topInset - bottomInset

                val position = alignment.align(
                    IntSize(floatingToolbarWidth, floatingToolbarHeight),
                    // Use the calculated available space for alignment
                    IntSize(availableWidth, availableHeight),
                    layoutDirection
                )

                // Calculate final coordinates, adding back insets based on alignment
                // Start with the aligned position within the available space
                var x = position.x + startInset
                var y = position.y

                // Specific adjustments based on vertical alignment
                if (alignment.vertical == Alignment.Bottom) {
                    // If bottom aligned, position relative to the bottom edge of available space
                    y = layoutHeight - bottomInset - floatingToolbarHeight
                }

                // Specific adjustments based on horizontal alignment (already handled by align function + startInset)

                floatingToolbarPlaceables.fastForEach { placeable ->
                    placeable.place(x, y)
                }
            }

            // Place FAB
            fabPlacement?.let { placement ->
                fabPlaceables.fastForEach {
                    it.place(placement.left, layoutHeight - fabOffsetFromBottom!!)
                }
            }
        }
    }
}

private enum class ScaffoldLayoutContent {
    TopBar,
    BottomBar,
    Snackbar,
    FloatingToolbar,
    Fab,
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

@kotlin.jvm.JvmInline
value class FabPosition internal constructor(@Suppress("unused") private val value: Int) {
    companion object {
        /**
         * Position FAB at the bottom of the screen at the start, above the [NavigationBar] (if it
         * exists)
         */
        val Start = FabPosition(0)

        /**
         * Position FAB at the bottom of the screen in the center, above the [NavigationBar] (if it
         * exists)
         */
        val Center = FabPosition(1)

        /**
         * Position FAB at the bottom of the screen at the end, above the [NavigationBar] (if it
         * exists)
         */
        val End = FabPosition(2)

        /**
         * Position FAB at the bottom of the screen at the end, overlaying the [NavigationBar] (if
         * it exists)
         */
        val EndOverlay = FabPosition(3)
    }

    override fun toString(): String {
        return when (this) {
            Start -> "FabPosition.Start"
            Center -> "FabPosition.Center"
            End -> "FabPosition.End"
            else -> "FabPosition.EndOverlay"
        }
    }
}

/**
 * Placement information for a [FloatingActionButton] inside a [Scaffold].
 *
 * @property left the FAB's offset from the left edge of the bottom bar, already adjusted for RTL
 *   support
 * @property width the width of the FAB
 * @property height the height of the FAB
 */
@Immutable
internal class FabPlacement(val left: Int, val width: Int, val height: Int)

// FAB spacing above the bottom bar / bottom of the Scaffold
private val FabSpacing = 12.dp

// Add Alignment.vertical property helper if not available
internal val Alignment.vertical: Alignment.Vertical
    get() = when (this) {
        Alignment.TopStart, Alignment.TopCenter, Alignment.TopEnd -> Alignment.Top
        Alignment.CenterStart, Alignment.Center, Alignment.CenterEnd -> Alignment.CenterVertically
        Alignment.BottomStart, Alignment.BottomCenter, Alignment.BottomEnd -> Alignment.Bottom
        else -> Alignment.CenterVertically // Default or throw error
    }

// Add Alignment.horizontal property helper if not available
internal val Alignment.horizontal: Alignment.Horizontal
    get() = when (this) {
        Alignment.TopStart, Alignment.CenterStart, Alignment.BottomStart -> Alignment.Start
        Alignment.TopCenter, Alignment.Center, Alignment.BottomCenter -> Alignment.CenterHorizontally
        Alignment.TopEnd, Alignment.CenterEnd, Alignment.BottomEnd -> Alignment.End
        else -> Alignment.CenterHorizontally // Default or throw error
    }

// Keep the internal toAlignment function for ToolbarPosition here
internal fun ToolbarPosition.toAlignment(): Alignment {
    return when (this) {
        ToolbarPosition.TopStart -> Alignment.TopStart
        ToolbarPosition.CenterStart -> Alignment.CenterStart
        ToolbarPosition.BottomStart -> Alignment.BottomStart
        ToolbarPosition.TopEnd -> Alignment.TopEnd
        ToolbarPosition.CenterEnd -> Alignment.CenterEnd
        ToolbarPosition.BottomEnd -> Alignment.BottomEnd
        ToolbarPosition.TopCenter -> Alignment.TopCenter // Added
        ToolbarPosition.BottomCenter -> Alignment.BottomCenter
        else -> Alignment.BottomCenter // Default or throw error
    }
}

/**
 * Represents the position of a floating toolbar within the Scaffold.
 * Used by Scaffold for placement calculations.
 */
@kotlin.jvm.JvmInline
value class ToolbarPosition internal constructor(@Suppress("unused") private val value: Int) {
    companion object {
        /** Position Toolbar at the top start corner. */
        val TopStart = ToolbarPosition(0)

        /** Position Toolbar vertically centered on the start edge. */
        val CenterStart = ToolbarPosition(1)

        /** Position Toolbar at the bottom start corner. */
        val BottomStart = ToolbarPosition(2)

        /** Position Toolbar at the top end corner. */
        val TopEnd = ToolbarPosition(3)

        /** Position Toolbar vertically centered on the end edge. */
        val CenterEnd = ToolbarPosition(4)

        /** Position Toolbar at the bottom end corner. */
        val BottomEnd = ToolbarPosition(5)

        /** Position Toolbar horizontally centered along the top edge. */ // Added KDoc
        val TopCenter = ToolbarPosition(6)

        /** Position Toolbar horizontally centered along the bottom edge. */
        val BottomCenter = ToolbarPosition(7)
    }

    override fun toString(): String {
        return when (this) {
            TopStart -> "ToolbarPosition.TopStart"
            CenterStart -> "ToolbarPosition.CenterStart"
            BottomStart -> "ToolbarPosition.BottomStart"
            TopEnd -> "ToolbarPosition.TopEnd"
            CenterEnd -> "ToolbarPosition.CenterEnd"
            BottomEnd -> "ToolbarPosition.BottomEnd"
            TopCenter -> "ToolbarPosition.TopCenter"
            else -> "ToolbarPosition.BottomCenter"
        }
    }
}