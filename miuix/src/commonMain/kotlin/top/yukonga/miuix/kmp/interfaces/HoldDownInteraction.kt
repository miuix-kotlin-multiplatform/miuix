package top.yukonga.miuix.kmp.interfaces

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import top.yukonga.miuix.kmp.interfaces.HoldDownInteraction.Hold
import top.yukonga.miuix.kmp.interfaces.HoldDownInteraction.Release

/**
 * An interaction related to hold down events.
 *
 * @see Hold
 * @see Release
 */
interface HoldDownInteraction : Interaction {
    /**
     * An interaction representing a hold down event on a component.
     *
     * @see Release
     */
    class Hold : HoldDownInteraction

    /**
     * An interaction representing a [Hold] event being released on a component.
     *
     * @property hold the source [Hold] interaction that is being released
     *
     * @see Hold
     */
    class Release(val hold: Hold) : HoldDownInteraction
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
        val holdInteraction = mutableListOf<Hold>()
        interactions.collect { interaction ->
            when (interaction) {
                is Hold -> holdInteraction.add(interaction)
                is Release -> holdInteraction.remove(interaction.hold)
            }
            isHeldDown.value = holdInteraction.isNotEmpty()
        }
    }
    return isHeldDown
}