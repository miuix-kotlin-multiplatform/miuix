package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape

/**
 * A [FloatingToolbar] that renders its content in a Card, arranged either horizontally or vertically.
 * The actual placement on screen is handled by the parent, typically Scaffold.
 *
 * @param items The list of [NavigationItem]s to display in the toolbar.
 * @param selected The index of the currently selected item.
 * @param onClick The callback invoked when an item is clicked.
 * @param modifier The modifier to be applied to the [FloatingToolbar].
 * @param targetState The orientation of the buttons inside the [FloatingToolbar] (Horizontal or Vertical).
 * @param cornerRadius Corner radius of the [FloatingToolbar] background Card.
 * @param color Background color of the [FloatingToolbar].
 * @param contentPadding Padding inside the [FloatingToolbar], around the buttons.
 * @param outSidePadding Padding outside the [FloatingToolbar].
 * @param defaultWindowInsetsPadding Whether to apply default window insets padding to the [FloatingToolbar].
 */
@Composable
fun FloatingToolbar(
    items: List<FloatingToolbarItem>,
    selected: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    targetState: FloatingToolbarOrientation = FloatingToolbarDefaults.Orientation,
    cornerRadius: Dp = FloatingToolbarDefaults.CornerRadius,
    color: Color = FloatingToolbarDefaults.DefaultColor(),
    contentPadding: PaddingValues = FloatingToolbarDefaults.ContentPadding,
    outSidePadding: PaddingValues = FloatingToolbarDefaults.OutSidePadding,
    showBorder: Boolean = true,
    defaultWindowInsetsPadding: Boolean = true,
) {
    Column(
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
                if (showBorder) {
                    Modifier
                        .background(color = MiuixTheme.colorScheme.dividerLine, shape = SmoothRoundedCornerShape(cornerRadius))
                        .padding(0.75.dp)
                } else Modifier
            )
            .clip(SmoothRoundedCornerShape(cornerRadius))
    ) {
        AnimatedContent(
            modifier = modifier,
            targetState = targetState,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "toolbar-animation"
        ) { targetOrientation ->
            val layoutModifier = Modifier.padding(contentPadding)
            val content = @Composable {
                items.forEachIndexed { index, item ->
                    IconButton(
                        modifier = Modifier.size(48.dp),
                        onClick = { onClick(index) }
                    ) {
                        Icon(
                            item.icon,
                            contentDescription = item.label,
                            tint = if (selected == index) {
                                MiuixTheme.colorScheme.onSurfaceContainer
                            } else {
                                MiuixTheme.colorScheme.onSurfaceContainerVariant
                            }
                        )
                    }
                }
            }
            when (targetOrientation) {
                FloatingToolbarOrientation.Horizontal -> {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = layoutModifier
                    ) { content() }
                }

                FloatingToolbarOrientation.Vertical -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = layoutModifier
                    ) { content() }
                }
            }
        }
    }
}

/**
 * Represents the orientation of the FloatingToolbar's content.
 */
enum class FloatingToolbarOrientation {
    Horizontal, Vertical
}

object FloatingToolbarDefaults {

    /**
     * Default orientation of the context of the [FloatingToolbar].
     */
    val Orientation = FloatingToolbarOrientation.Horizontal

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
     * Default padding inside the [FloatingToolbar].
     */
    val ContentPadding = PaddingValues(8.dp)

    /**
     * Default padding outside the [FloatingToolbar].
     */
    val OutSidePadding = PaddingValues(12.dp, 8.dp)
}


/**
 * The data class for [FloatingToolbar].
 *
 * @param label The label of the item.
 * @param icon The icon of the item.
 */
data class FloatingToolbarItem(
    val label: String,
    val icon: ImageVector
)
