// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.interfaces

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import top.yukonga.miuix.kmp.interfaces.HoldDownInteraction.HoldDown
import top.yukonga.miuix.kmp.interfaces.HoldDownInteraction.Release

/**
 * An interaction related to hold down events.
 *
 * @see HoldDown
 * @see Release
 */
interface HoldDownInteraction : Interaction {
    /**
     * An interaction representing a hold down event on a component.
     *
     * @see Release
     */
    class HoldDown : HoldDownInteraction

    /**
     * An interaction representing a [HoldDown] event being released on a component.
     *
     * @property holdDown the source [HoldDown] interaction that is being released
     *
     * @see HoldDown
     */
    class Release(
        val holdDown: HoldDown,
    ) : HoldDownInteraction
}

/**
 * Subscribes to this [MutableInteractionSource] and returns a [State] representing whether this
 * component is selected or not.
 *
 * @return [State] representing whether this component is being focused or not
 */
@Composable
fun InteractionSource.collectIsHeldDownAsState(): State<Boolean> {
    val isHeldDown = remember { mutableStateOf(false) }
    LaunchedEffect(this) {
        val holdInteraction = mutableListOf<HoldDown>()
        interactions.collect { interaction ->
            when (interaction) {
                is HoldDown -> holdInteraction.add(interaction)
                is Release -> holdInteraction.remove(interaction.holdDown)
            }
            isHeldDown.value = holdInteraction.isNotEmpty()
        }
    }
    return isHeldDown
}
