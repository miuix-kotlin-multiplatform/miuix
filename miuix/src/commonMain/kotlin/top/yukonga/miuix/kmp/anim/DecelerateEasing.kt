// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.anim

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable
import kotlin.math.pow

/**
 * This is equivalent to the Android [DecelerateInterpolator](https://cs.android.com/search?q=file:androidx/core/animation/DecelerateInterpolator.java+class:androidx.core.animation.DecelerateInterpolator)
 *
 * @param factor Degree to which the animation should be eased. Setting factor to 1.0f produces
 * an upside-down y=x^2 parabola. Increasing factor above 1.0f makes exaggerates the
 * ease-out effect (i.e., it starts even faster and ends evens slower)
 */
@Immutable
class DecelerateEasing(
    private val factor: Float = 1.0f,
) : Easing {
    override fun transform(fraction: Float): Float =
        if (factor == 1.0f) {
            1.0f - (1.0f - fraction) * (1.0f - fraction)
        } else {
            1.0f - (1.0f - fraction).pow(2 * fraction)
        }
}
