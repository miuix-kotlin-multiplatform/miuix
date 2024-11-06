package top.yukonga.miuix.kmp.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.interfaces.HoldDownInteraction

/**
 * Miuix default [Indication] that draws a rectangular overlay when pressed.
 */
class MiuixIndication(
    private val backgroundColor: Color = Color.Black
) : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode =
        MiuixIndicationInstance(interactionSource, backgroundColor)

    override fun hashCode(): Int = -1

    override fun equals(other: Any?) = other === this

    private class MiuixIndicationInstance(
        private val interactionSource: InteractionSource,
        private val backgroundColor: Color
    ) : Modifier.Node(), DrawModifierNode {
        private var isPressed = false
        private var isHovered = false
        private var isFocused = false
        private val animatedAlpha = Animatable(0f)
        private var pressedAnimation: Job? = null
        private var restingAnimation: Job? = null

        private fun updateStates() {
            var targetAlpha = 0.0f
            if (isHovered) targetAlpha += 0.06f
            if (isFocused) targetAlpha += 0.08f
            if (isPressed) targetAlpha += 0.1f
            if (targetAlpha == 0.0f) {
                restingAnimation = coroutineScope.launch {
                    pressedAnimation?.join()
                    animatedAlpha.animateTo(0f, tween(150))
                }
            } else {
                restingAnimation?.cancel()
                pressedAnimation?.cancel()
                pressedAnimation = coroutineScope.launch {
                    animatedAlpha.animateTo(targetAlpha, tween(150))
                }
            }
        }

        override fun onAttach() {
            coroutineScope.launch {
                var pressed = false
                var hovered = false
                var focused = false
                var held = false
                interactionSource.interactions.collect { interaction ->
                    when (interaction) {
                        is PressInteraction.Press -> pressed = true
                        is PressInteraction.Release, is PressInteraction.Cancel -> pressed = false
                        is HoverInteraction.Enter -> hovered = true
                        is HoverInteraction.Exit -> hovered = false
                        is FocusInteraction.Focus -> focused = true
                        is FocusInteraction.Unfocus -> focused = false
                        is HoldDownInteraction.Hold -> held = true
                        is HoldDownInteraction.Release -> held = false
                        else -> return@collect
                    }
                    var invalidateNeeded = false
                    if (isPressed != (pressed || held)) {
                        isPressed = (pressed || held)
                        invalidateNeeded = true
                    }
                    if (isHovered != hovered) {
                        isHovered = hovered
                        invalidateNeeded = true
                    }
                    if (isFocused != focused) {
                        isFocused = focused
                        invalidateNeeded = true
                    }
                    if (invalidateNeeded) {
                        updateStates()
                    }
                }
            }
        }

        override fun ContentDrawScope.draw() {
            // Draw content
            drawContent()
            // Draw foreground
            drawRect(color = backgroundColor.copy(alpha = animatedAlpha.value), size = size)
        }
    }
}
