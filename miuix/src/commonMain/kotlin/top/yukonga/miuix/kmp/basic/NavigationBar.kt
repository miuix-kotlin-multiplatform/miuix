package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.HorizontalDivider
import top.yukonga.miuix.kmp.utils.Platform
import top.yukonga.miuix.kmp.utils.platform

/**
 * A [NavigationBar] that with 2 to 5 items.
 *
 * @param modifier The modifier to be applied to the [NavigationBar].
 * @param items The items of the [NavigationBar].
 * @param selected The selected index of the [NavigationBar].
 * @param color The color of the [NavigationBar].
 * @param onClick The callback when the item of the [NavigationBar] is clicked.
 * @param defaultWindowInsetsPadding whether to apply default window insets padding to the [NavigationBar].
 */
@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    items: List<NavigationItem>,
    selected: Int,
    color: Color = MiuixTheme.colorScheme.surfaceContainer,
    onClick: (Int) -> Unit,
    defaultWindowInsetsPadding: Boolean = true
) {
    require(items.size in 2..5) { "BottomBar must have between 2 and 5 items" }
    val captionBarBottomPadding by rememberUpdatedState(
        WindowInsets.captionBar.only(WindowInsetsSides.Bottom).asPaddingValues().calculateBottomPadding()
    )
    val animatedCaptionBarHeight by animateDpAsState(
        targetValue = if (captionBarBottomPadding > 0.dp) captionBarBottomPadding else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )
    Surface(color = color) {
        Column(
            modifier = if (defaultWindowInsetsPadding) {
                modifier
                    .navigationBarsPadding()
                    .padding(bottom = animatedCaptionBarHeight)
            } else {
                modifier
            }
                .fillMaxWidth()
                .background(Color.Transparent)
        ) {
            HorizontalDivider(
                thickness = 0.75.dp,
                color = MiuixTheme.colorScheme.dividerLine
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    val isSelected = selected == index
                    var isPressed by remember { mutableStateOf(false) }
                    val tint by animateColorAsState(
                        targetValue = when {
                            isPressed -> if (isSelected) {
                                MiuixTheme.colorScheme.onSurfaceContainer.copy(alpha = 0.6f)
                            } else {
                                MiuixTheme.colorScheme.onSurfaceContainerVariant.copy(alpha = 0.6f)
                            }

                            isSelected -> MiuixTheme.colorScheme.onSurfaceContainer
                            else -> MiuixTheme.colorScheme.onSurfaceContainerVariant
                        }
                    )
                    val fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f / items.size)
                            .height(NavigationBarHeight)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        isPressed = true
                                        tryAwaitRelease()
                                        isPressed = false
                                    },
                                    onTap = { onClick(index) }
                                )
                            }
                    ) {
                        Image(
                            modifier = Modifier.size(32.dp).padding(top = 6.dp),
                            imageVector = item.icon,
                            contentDescription = item.label,
                            colorFilter = ColorFilter.tint(tint)
                        )
                        Text(
                            modifier = Modifier.padding(bottom = if (platform() != Platform.IOS) 12.dp else 0.dp),
                            text = item.label,
                            color = tint,
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            fontWeight = fontWeight
                        )
                    }
                }
            }
        }
    }
}

/** The default expanded height of a [NavigationBar]. */
val NavigationBarHeight: Dp = if (platform() != Platform.IOS) 64.dp else 48.dp


/**
 * The data class for [NavigationBar].
 *
 * @param label The label of the item.
 * @param icon The icon of the item.
 */
data class NavigationItem(
    val label: String,
    val icon: ImageVector
)
