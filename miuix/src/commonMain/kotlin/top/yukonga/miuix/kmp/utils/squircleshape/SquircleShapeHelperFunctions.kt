/*
 * Copyright (c) 2023 Stoyan Vuchev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package top.yukonga.miuix.kmp.utils.squircleshape

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Size

/**
 *
 *  Clamps the corner radius from 0.0f to the size of the smallest axis.
 *
 *  @param cornerSize The corner radius in pixels.
 *  @param size The size of the shape.
 *
 **/
@Stable
internal fun clampedCornerRadius(
    cornerSize: Float,
    size: Size
): Float {
    val smallestAxis = size.minDimension / 2
    return cornerSize.coerceIn(0.0f, smallestAxis)
}

/**
 *
 *  Clamps the corner smoothing from 0.55f to 1f.
 *
 *  @param cornerSmoothing (0.55f - rounded corner shape, 1f - fully pronounced squircle).
 *
 **/
@Stable
internal fun clampedCornerSmoothing(cornerSmoothing: Float) = cornerSmoothing.coerceIn(0.55f, 1f)