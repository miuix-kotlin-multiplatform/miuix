// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import top.yukonga.miuix.kmp.anim.DecelerateEasing
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * A util class for show popup and dialog.
 */
class MiuixPopupUtils {

    @Immutable
    private data class PopupState(
        val enterTransition: EnterTransition?,
        val exitTransition: ExitTransition?,
        val enableWindowDim: Boolean,
        val dimEnterTransition: EnterTransition?,
        val dimExitTransition: ExitTransition?,
        val transformOrigin: () -> TransformOrigin,
        val zIndex: Float,
        val content: @Composable () -> Unit
    )

    @Immutable
    private data class DialogState(
        val enterTransition: EnterTransition?,
        val exitTransition: ExitTransition?,
        val enableWindowDim: Boolean,
        val dimEnterTransition: EnterTransition?,
        val dimExitTransition: ExitTransition?,
        val zIndex: Float,
        val content: @Composable () -> Unit
    )

    companion object {
        private val popupStates = mutableStateMapOf<MutableState<Boolean>, PopupState>()
        private val dialogStates = mutableStateMapOf<MutableState<Boolean>, DialogState>()
        private var nextZIndex = 1f

        private fun defaultMiuixDialogDimmingEnterTransition(): EnterTransition {
            return fadeIn(animationSpec = tween(300, easing = DecelerateEasing(1.5f)))
        }

        private fun defaultMiuixDialogDimmingExitTransition(): ExitTransition {
            return fadeOut(animationSpec = tween(250, easing = DecelerateEasing(1.5f)))
        }

        private fun defaultMiuixPopupDimmingEnterTransition(): EnterTransition {
            return fadeIn(animationSpec = tween(150, easing = DecelerateEasing(1.5f)))
        }

        private fun defaultMiuixPopupDimmingExitTransition(): ExitTransition {
            return fadeOut(animationSpec = tween(150, easing = DecelerateEasing(1.5f)))
        }

        private fun defaultMiuixDialogEnterTransition(largeScreen: Boolean = true): EnterTransition {
            return if (largeScreen) {
                fadeIn(
                    animationSpec = spring(0.9f, 900f)
                ) + scaleIn(
                    initialScale = 0.8f,
                    animationSpec = spring(0.73f, 900f)
                )
            } else {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = spring(0.92f, 400f)
                )
            }
        }

        private fun defaultMiuixDialogExitTransition(largeScreen: Boolean = true): ExitTransition {
            return if (largeScreen) {
                fadeOut(
                    animationSpec = tween(200, easing = DecelerateEasing(1.5f))
                ) + scaleOut(
                    targetScale = 0.8f,
                    animationSpec = tween(200, easing = DecelerateEasing(1.5f))
                )
            } else {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(200, easing = DecelerateEasing(1.5f))
                )
            }
        }

        private fun defaultMiuixPopupEnterTransition(transformOrigin: () -> TransformOrigin): EnterTransition {
            return fadeIn(
                animationSpec = tween(150, easing = DecelerateEasing(1.5f))
            ) + scaleIn(
                initialScale = 0.8f,
                animationSpec = tween(150, easing = DecelerateEasing(1.5f)),
                transformOrigin = transformOrigin()
            )
        }

        private fun defaultMiuixPopupExitTransition(transformOrigin: () -> TransformOrigin): ExitTransition {
            return fadeOut(
                animationSpec = tween(150, easing = DecelerateEasing(1.5f))
            ) + scaleOut(
                targetScale = 0.8f,
                animationSpec = tween(150, easing = DecelerateEasing(1.5f)),
                transformOrigin = transformOrigin()
            )
        }

        /**
         * Create a dialog layout.
         *
         * @param visible The show state controller for this specific dialog.
         * @param enterTransition Optional, custom enter animation for dialog content.
         * @param exitTransition Optional, custom exit animation for dialog content.
         * @param enableWindowDim Whether to dim the window behind the dialog.
         * @param dimEnterTransition Optional, custom enter animation for dim layer.
         * @param dimExitTransition Optional, custom exit animation for dim layer.
         * @param content The [Composable] content of the dialog.
         */
        @Composable
        @Suppress("FunctionName")
        fun DialogLayout(
            visible: MutableState<Boolean> = mutableStateOf(true),
            enterTransition: EnterTransition? = null,
            exitTransition: ExitTransition? = null,
            enableWindowDim: Boolean = true,
            dimEnterTransition: EnterTransition? = null,
            dimExitTransition: ExitTransition? = null,
            content: (@Composable () -> Unit)? = null,
        ) {
            if (!visible.value) return
            if (content == null) {
                if (visible.value) visible.value = false
                return
            }
            val currentZIndex = if (!visible.value) {
                nextZIndex++
            } else {
                dialogStates[visible]?.zIndex ?: nextZIndex++
            }
            dialogStates[visible] = DialogState(
                enterTransition,
                exitTransition,
                enableWindowDim,
                dimEnterTransition,
                dimExitTransition,
                currentZIndex
            ) {
                content()
            }
        }

        /**
         * Create a popup layout.
         *
         * @param visible The show state controller for this specific popup.
         * @param enterTransition Optional, custom enter animation for popup content.
         * @param exitTransition Optional, custom exit animation for popup content.
         * @param enableWindowDim Whether to dim the window behind the popup.
         * @param dimEnterTransition Optional, custom enter animation for dim layer.
         * @param dimExitTransition Optional, custom exit animation for dim layer.
         * @param transformOrigin The pivot point in terms of fraction of the overall size,
         *   used for scale transformations. By default it's [TransformOrigin.Center].
         * @param content The [Composable] content of the popup.
         */
        @Suppress("FunctionName")
        fun PopupLayout(
            visible: MutableState<Boolean>,
            enterTransition: EnterTransition? = null,
            exitTransition: ExitTransition? = null,
            enableWindowDim: Boolean = true,
            dimEnterTransition: EnterTransition? = null,
            dimExitTransition: ExitTransition? = null,
            transformOrigin: (() -> TransformOrigin) = { TransformOrigin.Center },
            content: (@Composable () -> Unit)? = null,
        ) {
            if (!visible.value) return
            if (content == null) {
                if (visible.value) visible.value = false
                return
            }
            val currentZIndex = if (!visible.value) {
                nextZIndex++
            } else {
                popupStates[visible]?.zIndex ?: nextZIndex++
            }
            popupStates[visible] = PopupState(
                enterTransition,
                exitTransition,
                enableWindowDim,
                dimEnterTransition,
                dimExitTransition,
                transformOrigin,
                currentZIndex,
            ) {
                content()
            }
        }

        /**
         * A host for show popup and dialog. Already added to the [Scaffold] by default.
         */
        @Composable
        fun MiuixPopupHost() {
            val density = LocalDensity.current
            val getWindowSize by rememberUpdatedState(getWindowSize())
            val windowWidth by rememberUpdatedState(getWindowSize.width.dp / density.density)
            val windowHeight by rememberUpdatedState(getWindowSize.height.dp / density.density)
            val largeScreen by remember { derivedStateOf { (windowHeight >= 480.dp && windowWidth >= 840.dp) } }

            dialogStates.entries.forEach { (showState, dialogState) ->
                key(showState) {
                    var internalVisible by remember { mutableStateOf(false) }

                    LaunchedEffect(showState.value) {
                        internalVisible = showState.value
                    }

                    // Dimming layer for the dialog
                    if (dialogState.enableWindowDim) {
                        AnimatedVisibility(
                            visible = internalVisible,
                            modifier = Modifier.zIndex(dialogState.zIndex).fillMaxSize(),
                            enter = dialogState.dimEnterTransition ?: defaultMiuixDialogDimmingEnterTransition(),
                            exit = dialogState.dimExitTransition ?: defaultMiuixDialogDimmingExitTransition()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MiuixTheme.colorScheme.windowDimming)
                            )
                        }
                    }

                    // Content layer for the dialog
                    AnimatedVisibility(
                        visible = internalVisible,
                        modifier = Modifier.zIndex(dialogState.zIndex).fillMaxSize(),
                        enter = dialogState.enterTransition ?: defaultMiuixDialogEnterTransition(largeScreen),
                        exit = dialogState.exitTransition ?: defaultMiuixDialogExitTransition(largeScreen)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            dialogState.content()
                        }
                        DisposableEffect(showState) {
                            onDispose {
                                if (!showState.value) {
                                    dialogStates.remove(showState)
                                }
                            }
                        }
                    }
                }
            }

            popupStates.entries.forEach { (showState, popupState) ->
                key(showState) {
                    var internalVisible by remember { mutableStateOf(false) }

                    LaunchedEffect(showState.value) {
                        internalVisible = showState.value
                    }

                    // Dimming layer for the popup
                    if (popupState.enableWindowDim) {
                        AnimatedVisibility(
                            visible = internalVisible,
                            modifier = Modifier.zIndex(popupState.zIndex).fillMaxSize(),
                            enter = popupState.dimEnterTransition ?: defaultMiuixPopupDimmingEnterTransition(),
                            exit = popupState.dimExitTransition ?: defaultMiuixPopupDimmingExitTransition()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MiuixTheme.colorScheme.windowDimming)
                            )
                        }
                    }

                    // Content layer for the popup
                    AnimatedVisibility(
                        visible = internalVisible,
                        modifier = Modifier.zIndex(popupState.zIndex).fillMaxSize(),
                        enter = popupState.enterTransition ?: defaultMiuixPopupEnterTransition(popupState.transformOrigin),
                        exit = popupState.exitTransition ?: defaultMiuixPopupExitTransition(popupState.transformOrigin)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            popupState.content()
                        }
                        DisposableEffect(showState) {
                            onDispose {
                                if (!showState.value) {
                                    popupStates.remove(showState)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}