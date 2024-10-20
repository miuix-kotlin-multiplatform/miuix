package top.yukonga.miuix.kmp.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.invalidateDraw
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

/**
 * Miuix default [Indication] that draws a rectangular overlay when pressed.
 */
class MiuixIndication(
    private val backgroundColor: Color = Color.Black
) : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode =
        DefaultIndicationInstance(interactionSource, backgroundColor)

    override fun hashCode(): Int = -1

    override fun equals(other: Any?) = other === this

    private class DefaultIndicationInstance(
        private val interactionSource: InteractionSource,
        private val backgroundColor: Color
    ) : Modifier.Node(), DrawModifierNode {
        private var isPressed = false
        private var isHovered = false
        private var isFocused = false
        private var isPopupExpanded = false
        private var lastClickTime = 0L
        private val clickThreshold = 300L
        private val animatedAlpha = Animatable(0f)

        override fun onAttach() {
            coroutineScope.launch {
                interactionSource.interactions.collect { interaction ->
                    when (interaction) {
                        is PopupInteraction.Open -> {
                            isPopupExpanded = true
                            animatedAlpha.animateTo(0.12f)
                        }

                        is PopupInteraction.Close -> {
                            isPopupExpanded = false
                            if (!isHovered && !isFocused && !isPressed) {
                                animatedAlpha.animateTo(0f)
                            }
                        }

                        is PressInteraction.Press -> {
                            isPressed = true
                            animatedAlpha.animateTo(0.12f)
                        }

                        is PressInteraction.Cancel, is PressInteraction.Release -> {
                            isPressed = false
                            val currentTime = Clock.System.now().toEpochMilliseconds()
                            if (currentTime - lastClickTime < clickThreshold) {
                                animatedAlpha.snapTo(0f)
                            }
                            lastClickTime = currentTime
                            if (!isHovered && !isFocused && !isPopupExpanded) {
                                animatedAlpha.animateTo(0f)
                            } else if (isPopupExpanded) {
                                animatedAlpha.animateTo(0.12f)
                            } else if (isHovered || isFocused) {
                                animatedAlpha.animateTo(0.06f)
                            }
                        }

                        is HoverInteraction.Enter -> {
                            isHovered = true
                            animatedAlpha.animateTo(0.06f)
                        }

                        is HoverInteraction.Exit -> {
                            isHovered = false
                            if (!isFocused && !isPressed && !isPopupExpanded) {
                                animatedAlpha.animateTo(0f)
                            }
                        }

                        is FocusInteraction.Focus -> {
                            isFocused = true
                            animatedAlpha.animateTo(0.06f)
                        }

                        is FocusInteraction.Unfocus -> {
                            isFocused = false
                            if (!isHovered && !isPressed && !isPopupExpanded) {
                                animatedAlpha.animateTo(0f)
                            }
                        }
                    }
                    invalidateDraw()
                }
            }
        }

        override fun ContentDrawScope.draw() {
            drawContent()
            drawRect(color = backgroundColor.copy(alpha = animatedAlpha.value), size = size)
        }
    }
}


open class PopupInteraction : Interaction {
    object Open : PopupInteraction()
    object Close : PopupInteraction()
}
