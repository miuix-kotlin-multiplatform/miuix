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
        val content: @Composable () -> Unit,
        val transformOrigin: () -> TransformOrigin,
        val windowDimming: Boolean,
        val zIndex: Float
    )

    @Immutable
    private data class DialogState(
        val content: @Composable () -> Unit,
        val zIndex: Float,
        val enterTransition: EnterTransition?,
        val exitTransition: ExitTransition?,
        val dimEnterTransition: EnterTransition?,
        val dimExitTransition: ExitTransition?
    )

    companion object {
        private val popupStates = mutableStateMapOf<MutableState<Boolean>, PopupState>()
        private val dialogStates = mutableStateMapOf<MutableState<Boolean>, DialogState>()
        private var nextZIndex = 1f

        private val isAnyPopupShowing by derivedStateOf {
            popupStates.isNotEmpty() && popupStates.keys.any { it.value }
        }
        private val isAnyDialogShowing by derivedStateOf {
            dialogStates.isNotEmpty() && dialogStates.keys.any { it.value }
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

        /**
         * Create a dialog layout.
         *
         * @param visible The show state controller for this specific dialog.
         * @param enterTransition Optional, custom enter animation for dialog content
         * @param exitTransition Optional, custom exit animation for dialog content
         * @param dimEnterTransition Optional, custom enter animation for dim layer
         * @param dimExitTransition Optional, custom exit animation for dim layer
         * @param content The [Composable] content of the dialog.
         */
        @Composable
        @Suppress("FunctionName")
        fun DialogLayout(
            visible: MutableState<Boolean> = mutableStateOf(true),
            enterTransition: EnterTransition? = null,
            exitTransition: ExitTransition? = null,
            dimEnterTransition: EnterTransition? = null,
            dimExitTransition: ExitTransition? = null,
            content: (@Composable () -> Unit)? = null,
        ) {
            if (visible.value == false) return
            if (content == null) {
                if (visible.value) visible.value = false
                return
            }
            val currentZIndex = if (!visible.value) {
                nextZIndex++
            } else {
                dialogStates[visible]?.zIndex ?: nextZIndex++
            }
            dialogStates[visible] = DialogState({
                content() },
                currentZIndex,
                enterTransition,
                exitTransition,
                dimEnterTransition,
                dimExitTransition
            )
        }

        /**
         * Create a popup layout.
         *
         * @param visible The show state controller for this specific popup.
         * @param transformOrigin The pivot point in terms of fraction of the overall size,
         *   used for scale transformations. By default it's [TransformOrigin.Center].
         * @param windowDimming Whether to dim the window when the popup is showing.
         * @param content The [Composable] content of the popup.
         */
        @Suppress("FunctionName")
        fun PopupLayout(
            visible: MutableState<Boolean>,
            transformOrigin: (() -> TransformOrigin) = { TransformOrigin.Center },
            windowDimming: Boolean = true,
            content: (@Composable () -> Unit)? = null,
        ) {
            if (content == null) {
                if (visible.value) visible.value = false
                return
            }
            val currentZIndex = if (!visible.value) {
                nextZIndex++
            } else {
                popupStates[visible]?.zIndex ?: nextZIndex++
            }
            popupStates[visible] = PopupState(content, transformOrigin, windowDimming, currentZIndex)
            if (!visible.value) visible.value = true
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

            val dimEnterDuration = remember(isAnyDialogShowing, isAnyPopupShowing) {
                when {
                    isAnyDialogShowing -> 300
                    isAnyPopupShowing -> 150
                    else -> 150
                }
            }
            val dimExitDuration = remember(isAnyDialogShowing, isAnyPopupShowing) {
                when {
                    isAnyDialogShowing -> 250
                    isAnyPopupShowing -> 150
                    else -> 150
                }
            }

            dialogStates.entries.forEach { (showState, dialogState) ->
                key(showState) {
                    var internalVisible by remember { mutableStateOf(false) }

                    LaunchedEffect(showState.value) {
                        internalVisible = showState.value
                    }

                    // Dimming layer for the dialog
                    AnimatedVisibility(
                        visible = internalVisible,
                        modifier = Modifier.zIndex(dialogState.zIndex).fillMaxSize(),
                        enter = dialogState.dimEnterTransition?:fadeIn(animationSpec = tween(dimEnterDuration, easing = DecelerateEasing(1.5f))),
                        exit = dialogState.dimExitTransition?:fadeOut(animationSpec = tween(dimExitDuration, easing = DecelerateEasing(1.5f)))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MiuixTheme.colorScheme.windowDimming)
                        )
                    }

                    // Content layer for the dialog
                    AnimatedVisibility(
                        visible = internalVisible,
                        modifier = Modifier.zIndex(dialogState.zIndex).fillMaxSize(),
                        enter = dialogState.enterTransition?:defaultMiuixDialogEnterTransition(largeScreen),
                        exit = dialogState.exitTransition?:defaultMiuixDialogExitTransition(largeScreen)
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
                    if (popupState.windowDimming) {
                        AnimatedVisibility(
                            visible = internalVisible,
                            modifier = Modifier.zIndex(popupState.zIndex).fillMaxSize(),
                            enter = fadeIn(
                                animationSpec = tween(
                                    durationMillis = dimEnterDuration,
                                    easing = DecelerateEasing(1.5f)
                                )
                            ),
                            exit = fadeOut(animationSpec = tween(dimExitDuration, easing = DecelerateEasing(1.5f)))
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
                        enter = fadeIn(
                            animationSpec = tween(150, easing = DecelerateEasing(1.5f))
                        ) + scaleIn(
                            initialScale = 0.8f,
                            animationSpec = tween(150, easing = DecelerateEasing(1.5f)),
                            transformOrigin = popupState.transformOrigin()
                        ),
                        exit = fadeOut(
                            animationSpec = tween(150, easing = DecelerateEasing(1.5f))
                        ) + scaleOut(
                            targetScale = 0.8f,
                            animationSpec = tween(150, easing = DecelerateEasing(1.5f)),
                            transformOrigin = popupState.transformOrigin()
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            popupState.content()
                        }

                        DisposableEffect(showState.value) {
                            onDispose {
                                if (!showState.value) {
                                    dialogStates.remove(showState)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}