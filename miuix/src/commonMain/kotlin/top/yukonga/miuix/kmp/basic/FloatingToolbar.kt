package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.ToolbarPosition.BottomCenter
import top.yukonga.miuix.kmp.basic.ToolbarPosition.BottomLeft
import top.yukonga.miuix.kmp.basic.ToolbarPosition.BottomRight
import top.yukonga.miuix.kmp.basic.ToolbarPosition.LeftBottom
import top.yukonga.miuix.kmp.basic.ToolbarPosition.LeftCenter
import top.yukonga.miuix.kmp.basic.ToolbarPosition.LeftTop
import top.yukonga.miuix.kmp.basic.ToolbarPosition.RightBottom
import top.yukonga.miuix.kmp.basic.ToolbarPosition.RightCenter
import top.yukonga.miuix.kmp.basic.ToolbarPosition.RightTop
import top.yukonga.miuix.kmp.theme.MiuixTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
        /**
         * A floating toolbar that can be positioned at various screen edges.
         * Supports animated transitions between positions and customizable content.
         *
         * @param modifier Modifier applied to the toolbar content.
         * @param cornerRadius Corner radius of the toolbar background.
         * @param position ToolbarPosition specifying where the toolbar is aligned.
         * @param color Background color of the toolbar.
         * @param insideMargin Padding between the toolbar and screen edges.
         * @param buttons Composable content of the toolbar (e.g., buttons).
         */
fun FloatingToolbar(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = CardDefaults.CornerRadius,
    position: ToolbarPosition = BottomCenter,
    color: Color = MiuixTheme.colorScheme.background,
    insideMargin: PaddingValues = PaddingValues(12.dp, 8.dp),
    buttons: @Composable () -> Unit
) {
    val alignment = position.toAlignment()
    val isHorizontal = position.isBottomRow()
    val bottomPadding = if (position.needsBottomInset())
        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    else 0.dp

    Box(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(alignment)
                .padding(insideMargin)
                .padding(bottom = bottomPadding)
        ) {
            Card(
                cornerRadius = cornerRadius,
                color = color
            ) {
                AnimatedContent(
                    targetState = position,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "toolbar-animation"
                ) {
                    if (isHorizontal) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                        ) { buttons() }
                    } else {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                        ) { buttons() }
                    }
                }
            }
        }
    }
}

/**
 * Returns true if the position is one of the bottom row options.
 */
fun ToolbarPosition.isBottomRow(): Boolean = this in listOf(
    BottomLeft,
    BottomCenter,
    BottomRight
)

/**
 * Returns true if the position should include bottom system inset padding.
 */
fun ToolbarPosition.needsBottomInset(): Boolean = this in listOf(
    LeftBottom,
    RightBottom,
    BottomLeft,
    BottomCenter,
    BottomRight
)

/**
 * Represents the position of a toolbar in a layout.
 *
 * @property LeftTop Top left corner of the container
 * @property LeftCenter Center left of the container
 * @property LeftBottom Bottom left corner of the container
 * @property RightTop Top right corner of the container
 * @property RightCenter Center right of the container
 * @property RightBottom Bottom right corner of the container
 * @property BottomLeft Left side of the bottom row
 * @property BottomCenter Center of the bottom row
 * @property BottomRight Right side of the bottom row
 */
enum class ToolbarPosition {
    LeftTop,
    LeftCenter,
    LeftBottom,
    RightTop,
    RightCenter,
    RightBottom,
    BottomLeft,
    BottomCenter,
    BottomRight;

    fun toAlignment(): Alignment {
        return when (this) {
            LeftTop -> Alignment.TopStart
            LeftCenter -> Alignment.CenterStart
            LeftBottom -> Alignment.BottomStart
            RightTop -> Alignment.TopEnd
            RightCenter -> Alignment.CenterEnd
            RightBottom -> Alignment.BottomEnd
            BottomLeft -> Alignment.BottomStart
            BottomCenter -> Alignment.BottomCenter
            BottomRight -> Alignment.BottomEnd
        }
    }
}
