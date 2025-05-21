// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.anim

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable
import kotlin.math.pow

/**
 * This is equivalent to the Android [AccelerateInterpolator](https://cs.android.com/search?q=file:androidx/core/animation/AccelerateInterpolator.java+class:androidx.core.animation.AccelerateInterpolator)
 *
 * @param factor Degree to which the animation should be eased. Setting
 * factor to 1.0f produces a y=x^2 parabola. Increasing factor above
 * 1.0f  exaggerates the ease-in effect (i.e., it starts even
 * slower and ends evens faster)
 */
@Immutable
class AccelerateEasing(
    private val factor: Float = 1.0f,
) : Easing {
    private val doubleFactor: Float = 2 * factor

    override fun transform(fraction: Float): Float =
        if (factor == 1.0f) {
            fraction * fraction
        } else {
            fraction.pow(doubleFactor)
        }
}
