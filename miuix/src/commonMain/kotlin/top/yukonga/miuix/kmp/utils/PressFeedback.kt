// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

/** Default sink amount for the press sink effect. */
internal const val SinkAmount: Float = 0.94f

/** Default tilt amount for the press tilt effect. */
internal const val TiltAmount: Float = 8f

/** Default damping ratio for the press feedback spring animations. */
internal const val DampingRatio: Float = 0.6f

/** Default stiffness for the press feedback spring animations. */
internal const val Stiffness: Float = 400f

/**
 * Applies a "sink" visual feedback when the component is pressed,
 * by scaling the component down smoothly using animation.
 *
 * @param interactionSource An external [MutableInteractionSource] to observe press state.
 * @param sinkAmount The target scale when pressed (less than 1f).
 * @param dampingRatio The damping ratio for the spring animation.
 * @param stiffness The stiffness of the spring animation.
 */
fun Modifier.pressSink(
    interactionSource: MutableInteractionSource,
    sinkAmount: Float = SinkAmount,
    dampingRatio: Float = DampingRatio,
    stiffness: Float = Stiffness
): Modifier = composed {
    val isPressed by interactionSource.collectIsPressedAsState()

    val animationSpec = spring<Float>(dampingRatio = dampingRatio, stiffness = stiffness)

    val scale by animateFloatAsState(
        targetValue = if (isPressed) sinkAmount else 1f,
        animationSpec = animationSpec,
        label = "pressSinkScale"
    )

    this.scale(scale)
}

/**
 * Applies a "tilt" effect based on the position where the user pressed the component.
 * The tilt direction is determined by touch offset,
 * giving the effect that one corner "sinks" while the other "static".
 *
 * @param interactionSource An external [MutableInteractionSource] to observe press state.
 * @param tiltAmount Maximum rotation (in degrees) to apply along X and Y axes.
 * @param dampingRatio The damping ratio for the tilt spring animation.
 * @param stiffness The stiffness of the tilt spring animation.
 */
fun Modifier.pressTilt(
    interactionSource: MutableInteractionSource,
    tiltAmount: Float = TiltAmount,
    dampingRatio: Float = DampingRatio,
    stiffness: Float = Stiffness
): Modifier = composed {
    val isPressed by interactionSource.collectIsPressedAsState()

    val animationSpec = spring<Float>(dampingRatio = dampingRatio, stiffness = stiffness)

    var targetX by remember { mutableStateOf(0f) }
    var targetY by remember { mutableStateOf(0f) }

    val tiltX by animateFloatAsState(
        targetValue = if (isPressed) targetX else 0f,
        animationSpec = animationSpec,
        label = "pressTiltX"
    )
    val tiltY by animateFloatAsState(
        targetValue = if (isPressed) targetY else 0f,
        animationSpec = animationSpec,
        label = "pressTiltY"
    )
    var transformOrigin by remember { mutableStateOf(TransformOrigin.Center) }

    this
        .graphicsLayer {
            rotationX = tiltX
            rotationY = tiltY
            cameraDistance = 12 * density
            this.transformOrigin = transformOrigin
        }
        .pointerInput(tiltAmount) {
            awaitEachGesture {
                val down = awaitFirstDown()
                val w = size.width
                val h = size.height
                val offset = down.position

                transformOrigin = TransformOrigin(
                    pivotFractionX = if (offset.x < w / 2) 1f else 0f,
                    pivotFractionY = if (offset.y < h / 2) 1f else 0f
                )

                targetX = if (offset.y < h / 2) tiltAmount else -tiltAmount
                targetY = if (offset.x < w / 2) -tiltAmount else tiltAmount
            }
        }
}

/**
 * The type of visual feedback to apply when the component is pressed.
 */
enum class PressFeedbackType {
    /** No feedback effect. */
    None,

    /** Sinks slightly when pressed. */
    Sink,

    /** Tilts based on touch position when pressed. */
    Tilt
}
