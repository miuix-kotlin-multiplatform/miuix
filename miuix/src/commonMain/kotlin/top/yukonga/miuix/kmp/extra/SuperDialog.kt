package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Box
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.BackHandler
import top.yukonga.miuix.kmp.utils.MiuixPopupUtil.Companion.showDialog
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape
import top.yukonga.miuix.kmp.utils.getRoundedCorner
import top.yukonga.miuix.kmp.utils.getWindowSize

/**
 * A dialog with a title, a summary, and other contents.
 *
 * @param show The show state of the [SuperDialog].
 * @param modifier The modifier to be applied to the [SuperDialog].
 * @param title The title of the [SuperDialog].
 * @param titleColor The color of the title.
 * @param summary The summary of the [SuperDialog].
 * @param summaryColor The color of the summary.
 * @param backgroundColor The background color of the [SuperDialog].
 * @param onDismissRequest The callback when the [SuperDialog] is dismissed.
 * @param outsideMargin The margin outside the [SuperDialog].
 * @param insideMargin The margin inside the [SuperDialog].
 * @param defaultWindowInsetsPadding Whether to apply default window insets padding to the [SuperDialog].
 * @param content The [Composable] content of the [SuperDialog].
 */
@Composable
fun SuperDialog(
    show: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    title: String? = null,
    titleColor: Color = SuperDialogDefaults.titleColor(),
    summary: String? = null,
    summaryColor: Color = SuperDialogDefaults.summaryColor(),
    backgroundColor: Color = SuperDialogDefaults.backgroundColor(),
    onDismissRequest: (() -> Unit)? = null,
    outsideMargin: DpSize = SuperDialogDefaults.outsideMargin,
    insideMargin: DpSize = SuperDialogDefaults.insideMargin,
    defaultWindowInsetsPadding: Boolean = true,
    content: @Composable () -> Unit
) {
    if (show.value) {
        if (!dialogStates.contains(show)) dialogStates.add(show)
        LaunchedEffect(show.value) {
            if (show.value) {
                dialogStates.forEach { state -> if (state != show) state.value = false }
            }
        }

        val density = LocalDensity.current
        val getWindowSize by rememberUpdatedState(getWindowSize())
        val windowWidth by rememberUpdatedState(getWindowSize.width.dp / density.density)
        val windowHeight by rememberUpdatedState(getWindowSize.height.dp / density.density)
        val paddingModifier = remember(outsideMargin) { Modifier.padding(horizontal = outsideMargin.width).padding(bottom = outsideMargin.height) }
        val roundedCorner by rememberUpdatedState(getRoundedCorner())
        val bottomCornerRadius by remember { derivedStateOf { if (roundedCorner != 0.dp) roundedCorner - outsideMargin.width else 32.dp } }
        val contentAlignment by rememberUpdatedState { derivedStateOf { if (windowHeight >= 480.dp && windowWidth >= 840.dp) Alignment.Center else Alignment.BottomCenter } }

        BackHandler(enabled = show.value) {
            onDismissRequest?.invoke()
        }

        showDialog(
            content = {
                Box(
                    modifier = if (defaultWindowInsetsPadding) {
                        Modifier
                            .imePadding()
                            .navigationBarsPadding()
                    } else {
                        Modifier
                    }
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    onDismissRequest?.invoke()
                                }
                            )
                        }
                        .then(paddingModifier)
                ) {
                    Column(
                        modifier = modifier
                            .widthIn(max = 420.dp)
                            .pointerInput(Unit) {
                                detectTapGestures { /* Do nothing to consume the click */ }
                            }
                            .align(contentAlignment.invoke().value)
                            .graphicsLayer(
                                shape = SmoothRoundedCornerShape(bottomCornerRadius),
                                clip = false
                            )
                            .background(
                                color = backgroundColor,
                                shape = SmoothRoundedCornerShape(bottomCornerRadius)
                            )
                            .padding(
                                horizontal = insideMargin.width,
                                vertical = insideMargin.height
                            ),
                    ) {
                        title?.let {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp),
                                text = it,
                                fontSize = MiuixTheme.textStyles.title4.fontSize,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                color = titleColor
                            )
                        }
                        summary?.let {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp),
                                text = it,
                                fontSize = MiuixTheme.textStyles.body1.fontSize,
                                textAlign = TextAlign.Center,
                                color = summaryColor
                            )
                        }
                        content()
                    }
                }
            }
        )
    }
}

object SuperDialogDefaults {

    /**
     * The default color of the title.
     */
    @Composable
    fun titleColor() = MiuixTheme.colorScheme.onSurface

    /**
     * The default color of the summary.
     */
    @Composable
    fun summaryColor() = MiuixTheme.colorScheme.onSurfaceSecondary

    /**
     * The default background color of the [SuperDialog].
     */
    @Composable
    fun backgroundColor() = MiuixTheme.colorScheme.surfaceVariant

    /**
     * The default margin outside the [SuperDialog].
     */
    val outsideMargin = DpSize(12.dp, 12.dp)

    /**
     * The default margin inside the [SuperDialog].
     */
    val insideMargin = DpSize(24.dp, 24.dp)
}

/**
 * Only one dialog is allowed to be displayed at a time.
 */
val dialogStates = mutableStateListOf<MutableState<Boolean>>()
