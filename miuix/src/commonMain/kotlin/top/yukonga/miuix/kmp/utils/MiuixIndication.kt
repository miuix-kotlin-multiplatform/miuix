// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.interfaces.HoldDownInteraction

private const val ANIM_DURATION_MS = 120
private const val HOVER_ALPHA_DELTA = 0.06f
private const val FOCUS_ALPHA_DELTA = 0.08f
private const val PRESS_ALPHA_DELTA = 0.10f
private const val HOLD_DOWN_ALPHA_DELTA = 0.10f

/**
 * Miuix default [Indication] that draws a rectangular overlay when pressed.
 */
@Immutable
class MiuixIndication(
    private val color: Color = Color.Black,
) : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode =
        MiuixIndicationInstance(interactionSource, color)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MiuixIndication) return false
        return color == other.color
    }

    override fun hashCode(): Int {
        return color.hashCode()
    }

    private class MiuixIndicationInstance(
        private val interactionSource: InteractionSource,
        private val color: Color,
    ) : Modifier.Node(),
        DrawModifierNode {
        private var isPressed = false
        private var isHovered = false
        private var isFocused = false
        private var isHoldDown = false
        private val animatedAlpha = Animatable(0f)
        private var pressedAnimation: Job? = null
        private var restingAnimation: Job? = null

        private fun updateStates() {
            var targetAlpha = 0.0f
            if (isHovered) targetAlpha += HOVER_ALPHA_DELTA
            if (isFocused) targetAlpha += FOCUS_ALPHA_DELTA
            if (isPressed) targetAlpha += PRESS_ALPHA_DELTA
            if (isHoldDown) targetAlpha += HOLD_DOWN_ALPHA_DELTA

            if (targetAlpha == 0.0f) {
                restingAnimation =
                    coroutineScope.launch {
                        if (coroutineContext.isActive) {
                            pressedAnimation?.join()
                            animatedAlpha.animateTo(
                                targetValue = targetAlpha.coerceIn(0f, 0.10f),
                                animationSpec = tween(durationMillis = ANIM_DURATION_MS, easing = LinearEasing)
                            )
                        }
                    }
            } else {
                pressedAnimation?.cancel()
                restingAnimation?.cancel()
                pressedAnimation =
                    coroutineScope.launch {
                        animatedAlpha.animateTo(
                            targetValue = targetAlpha.coerceIn(0f, 0.10f),
                            animationSpec = tween(durationMillis = ANIM_DURATION_MS, easing = LinearEasing)
                        )
                    }
            }
        }

        override fun onAttach() {
            coroutineScope.launch {
                interactionSource.interactions.collect { interaction ->
                    val previousPressed = isPressed
                    val previousHovered = isHovered
                    val previousFocused = isFocused
                    val previousHoldDown = isHoldDown

                    when (interaction) {
                        is PressInteraction.Press -> isPressed = true
                        is PressInteraction.Release, is PressInteraction.Cancel -> isPressed = false
                        is HoverInteraction.Enter -> isHovered = true
                        is HoverInteraction.Exit -> isHovered = false
                        is FocusInteraction.Focus -> isFocused = true
                        is FocusInteraction.Unfocus -> isFocused = false
                        is HoldDownInteraction.HoldDown -> isHoldDown = true
                        is HoldDownInteraction.Release -> isHoldDown = false
                        else -> return@collect
                    }

                    val stateChanged = previousPressed != isPressed ||
                            previousHovered != isHovered ||
                            previousFocused != isFocused ||
                            previousHoldDown != isHoldDown

                    if (stateChanged) {
                        updateStates()
                    }
                }
            }
        }

        override fun ContentDrawScope.draw() {
            // Draw content
            drawContent()
            // Draw foreground
            drawRect(color = color.copy(alpha = animatedAlpha.value), size = size)
        }
    }
}
