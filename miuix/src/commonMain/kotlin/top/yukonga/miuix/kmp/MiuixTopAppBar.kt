package top.yukonga.miuix.kmp

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun MiuixTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: @Composable RowScope.() -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollOffset: Int = 0
) {
    val density = LocalDensity.current.density
    val maxPadding = 48.dp
    val minPadding = 12.dp
    val verticalPadding by animateDpAsState(
        targetValue = max(minPadding, maxPadding - (scrollOffset).dp / density)
    )
    val alpha by animateFloatAsState(
        targetValue = if (maxPadding - verticalPadding < minPadding) 0f else 1f,
        animationSpec = tween(durationMillis = 100)
    )
    val offsetY by animateDpAsState(
        targetValue = if (maxPadding - verticalPadding < minPadding) 10.dp else 0.dp,
        animationSpec = tween(durationMillis = 100)
    )
    val largeTextAlpha by animateFloatAsState(
        targetValue = if (maxPadding - verticalPadding < minPadding) 1f else 0f,
        animationSpec = tween(durationMillis = 500)
    )
    MiuixBox {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            MiuixText(
                text = title,
                fontSize = 28.sp,
                modifier = Modifier
                    .alpha(largeTextAlpha)
                    .padding(top = verticalPadding * 2 - 24.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
    MiuixBox {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(MiuixTheme.colorScheme.background)
                .padding(horizontal = 24.dp, vertical = minPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
            ) {
                navigationIcon()
            }
            MiuixText(
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .weight(1f)
                    .offset(y = offsetY)
                    .alpha(alpha)
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                actions()
            }
        }
    }
}