// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.BackHandler
import top.yukonga.miuix.kmp.utils.MiuixPopupUtils.Companion.DialogLayout
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
    enableWindowDim: Boolean = true,
    onDismissRequest: (() -> Unit)? = null,
    outsideMargin: DpSize = SuperDialogDefaults.outsideMargin,
    insideMargin: DpSize = SuperDialogDefaults.insideMargin,
    defaultWindowInsetsPadding: Boolean = true,
    content: @Composable () -> Unit
) {
    if (!show.value) return

    DialogLayout(
        visible = show,
        enableWindowDim = enableWindowDim,
    ) {
        SuperDialogContent(
            modifier = modifier,
            title = title,
            titleColor = titleColor,
            summary = summary,
            summaryColor = summaryColor,
            backgroundColor = backgroundColor,
            outsideMargin = outsideMargin,
            insideMargin = insideMargin,
            defaultWindowInsetsPadding = defaultWindowInsetsPadding,
            onDismissRequest = onDismissRequest,
            content = content
        )
    }

    BackHandler(enabled = show.value) {
        onDismissRequest?.invoke()
    }
}

@Composable
private fun SuperDialogContent(
    modifier: Modifier,
    title: String?,
    titleColor: Color,
    summary: String?,
    summaryColor: Color,
    backgroundColor: Color,
    outsideMargin: DpSize,
    insideMargin: DpSize,
    defaultWindowInsetsPadding: Boolean,
    onDismissRequest: (() -> Unit)?,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current

    val roundedCorner by rememberUpdatedState(getRoundedCorner())

    val windowSize by rememberUpdatedState(getWindowSize())
    val windowWidth by remember(windowSize, density) {
        derivedStateOf { windowSize.width.dp / density.density }
    }
    val windowHeight by remember(windowSize, density) {
        derivedStateOf { windowSize.height.dp / density.density }
    }

    val bottomCornerRadius by remember(roundedCorner, outsideMargin.width) {
        derivedStateOf {
            if (roundedCorner != 0.dp) roundedCorner - outsideMargin.width else 32.dp
        }
    }

    val contentAlignment by remember(windowHeight, windowWidth) {
        derivedStateOf {
            if (windowHeight >= 480.dp && windowWidth >= 840.dp)
                Alignment.Center
            else
                Alignment.BottomCenter
        }
    }

    val rootBoxModifier = Modifier
        .then(
            if (defaultWindowInsetsPadding)
                Modifier.imePadding().navigationBarsPadding()
            else
                Modifier
        )
        .fillMaxSize()
        .pointerInput(onDismissRequest) {
            detectTapGestures(
                onTap = { onDismissRequest?.invoke() }
            )
        }
        .padding(horizontal = outsideMargin.width)
        .padding(bottom = outsideMargin.height)

    val columnModifier = modifier
        .widthIn(max = 420.dp)
        .pointerInput(Unit) {
            detectTapGestures { /* Consume click to prevent dismissal */ }
        }
        .clip(SmoothRoundedCornerShape(bottomCornerRadius))
        .background(backgroundColor)
        .padding(horizontal = insideMargin.width, vertical = insideMargin.height)

    Box(modifier = rootBoxModifier) {
        Column(modifier = columnModifier.align(contentAlignment)) {
            title?.let {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    text = it,
                    fontSize = MiuixTheme.textStyles.title4.fontSize,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = titleColor
                )
            }
            summary?.let {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
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
