package top.yukonga.miuix.kmp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.MiuixSurface
import top.yukonga.miuix.kmp.basic.MiuixText
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun MiuixNavigationBar(
    items: List<NavigationItem>,
    selectedItem: MutableState<Int>,
    modifier: Modifier = Modifier,
    color: Color = MiuixTheme.colorScheme.background,
    onClick: (Int) -> Unit
) {
    require(items.size in 2..5) { "BottomBar must have between 2 and 5 items" }
    MiuixSurface(
        modifier = modifier,
        color = color
    ) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
                .background(Color.Transparent)
        ) {
            HorizontalDivider(
                thickness = 0.3.dp,
                color = MiuixTheme.colorScheme.subDropdown
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    val isSelected = selectedItem.value == index
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f / items.size)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onClick(index)
                            }
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp).padding(top = 6.dp),
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (isSelected) MiuixTheme.colorScheme.onBackground else MiuixTheme.colorScheme.subDropdown
                        )
                        MiuixText(
                            modifier = Modifier.padding(bottom = 14.dp),
                            text = item.label,
                            color = if (isSelected) MiuixTheme.colorScheme.onBackground else MiuixTheme.colorScheme.subDropdown,
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                        )
                    }
                }
            }
        }
    }
}

data class NavigationItem(
    val label: String,
    val icon: ImageVector
)