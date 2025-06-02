// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.PressFeedbackType
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape
import top.yukonga.miuix.kmp.utils.pressSink
import top.yukonga.miuix.kmp.utils.pressTilt

/**
 * A [Card] component with Miuix style.
 * Card contain contain content and actions that relate information about a subject.
 *
 * This [Card] does not handle input events
 *
 * @param modifier The modifier to be applied to the [Card].
 * @param cornerRadius The corner radius of the [Card].
 * @param insideMargin The margin inside the [Card].
 * @param color The color of the [Card].
 * @param content The [Composable] content of the [Card].
 */
@Composable
fun Card(
    modifier: Modifier = Modifier,
    color: Color = CardDefaults.DefaultColor(),
    cornerRadius: Dp = CardDefaults.CornerRadius,
    insideMargin: PaddingValues = CardDefaults.InsideMargin,
    content: @Composable ColumnScope.() -> Unit
) {
    BasicCard(
        modifier = modifier,
        cornerRadius = cornerRadius,
        color = color
    ) {
        val columnModifier = remember(insideMargin) {
            Modifier.padding(insideMargin)
        }
        Column(
            modifier = columnModifier,
            content = content
        )
    }
}

/**
 * A [Card] component with Miuix style.
 * Card contain contain content and actions that relate information about a subject.
 *
 * This [Card] handles input events
 *
 * @param modifier The modifier to be applied to the [Card].
 * @param cornerRadius The corner radius of the [Card].
 * @param insideMargin The margin inside the [Card].
 * @param color The color of the [Card].
 * @param pressFeedbackType The press feedback type of the [Card].
 * @param showIndication Whether to show indication of the [Card].
 * @param onClick The callback to be invoked when the [Card] is clicked.
 * @param onLongPress The callback to be invoked when the [Card] is long pressed.
 * @param content The [Composable] content of the [Card].
 */
@Composable
fun Card(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = CardDefaults.CornerRadius,
    insideMargin: PaddingValues = CardDefaults.InsideMargin,
    color: Color = CardDefaults.DefaultColor(),
    pressFeedbackType: PressFeedbackType = PressFeedbackType.None,
    showIndication: Boolean? = false,
    onClick: (() -> Unit)? = null,
    onLongPress: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val currentOnClick by rememberUpdatedState(onClick)
    val currentOnLongPress by rememberUpdatedState(onLongPress)

    val pressFeedbackModifier = remember(pressFeedbackType, interactionSource) {
        when (pressFeedbackType) {
            PressFeedbackType.None -> Modifier
            PressFeedbackType.Sink -> Modifier.pressSink(interactionSource)
            PressFeedbackType.Tilt -> Modifier.pressTilt(interactionSource)
        }
    }

    BasicCard(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { currentOnClick?.invoke() },
                    onLongPress = { currentOnLongPress?.invoke() }
                )
            }
            .pointerInput(interactionSource) {
                awaitEachGesture {
                    val pressInteraction: PressInteraction.Press
                    awaitFirstDown().also {
                        pressInteraction = PressInteraction.Press(it.position)
                        interactionSource.tryEmit(pressInteraction)
                    }
                    if (waitForUpOrCancellation() == null) {
                        interactionSource.tryEmit(PressInteraction.Cancel(pressInteraction))
                    } else {
                        interactionSource.tryEmit(PressInteraction.Release(pressInteraction))
                    }
                }
            }
            .hoverable(interactionSource)
            .then(pressFeedbackModifier),
        cornerRadius = cornerRadius,
        color = color
    ) {
        val currentIndication = LocalIndication.current
        val columnModifier = remember(interactionSource, showIndication, insideMargin, currentIndication) {
            Modifier
                .indication(
                    interactionSource = interactionSource,
                    indication = if (showIndication == true) currentIndication else null
                )
                .padding(insideMargin)
        }
        Column(
            modifier = columnModifier,
            content = content
        )
    }
}

/**
 * A [BasicCard] component.
 *
 * @param modifier The modifier to be applied to the [BasicCard].
 * @param color The color of the [BasicCard].
 * @param cornerRadius The corner radius of the [BasicCard].
 * @param content The [Composable] content of the [BasicCard].
 */
@Composable
private fun BasicCard(
    modifier: Modifier = Modifier,
    color: Color = CardDefaults.DefaultColor(),
    cornerRadius: Dp = CardDefaults.CornerRadius,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = remember(cornerRadius) { SmoothRoundedCornerShape(cornerRadius) }
    val clipShape = remember(cornerRadius) { RoundedCornerShape(cornerRadius) }

    val boxModifier = remember(modifier, color, shape, clipShape) {
        modifier
            .semantics(mergeDescendants = false) {
                isTraversalGroup = true
            }
            .background(color = color, shape = shape)
            .clip(clipShape) // For touch feedback, there is a problem when using SmoothRoundedCornerShape.
    }
    Box(
        modifier = boxModifier,
        propagateMinConstraints = true,
        content = content
    )
}

object CardDefaults {

    /**
     * The default corner radius of the [Card].
     */
    val CornerRadius = 16.dp

    /**
     * The default margin inside the [Card].
     */
    val InsideMargin = PaddingValues(0.dp)

    /**
     * The default color width of the [Card].
     */
    @Composable
    fun DefaultColor() = MiuixTheme.colorScheme.surface
}