package top.yukonga.miuix.kmp.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import top.yukonga.miuix.kmp.anim.AccelerateEasing
import top.yukonga.miuix.kmp.anim.DecelerateEasing
import top.yukonga.miuix.kmp.basic.Box
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * A util class for show popup and dialog.
 */
class MiuixPopupUtil {

    companion object {
        private var isPopupShowing = mutableStateOf(false)
        private var isDialogShowing = mutableStateOf(false)
        private var popupContext = mutableStateOf<(@Composable () -> Unit)?>(null)
        private var dialogContext = mutableStateOf<(@Composable () -> Unit)?>(null)
        private var popupTransformOrigin = mutableStateOf({ TransformOrigin.Center })

        /**
         * Show a dialog.
         *
         * @param content The [Composable] content of the dialog.
         */
        @Composable
        fun showDialog(
            content: (@Composable () -> Unit)? = null,
        ) {
            if (isDialogShowing.value) return
            isDialogShowing.value = true
            dialogContext.value = content
        }

        /**
         * Dismiss the dialog.
         *
         * @param show The show state of the dialog.
         */
        fun dismissDialog(
            show: MutableState<Boolean>,
        ) {
            isDialogShowing.value = false
            show.value = false
        }

        /**
         * Show a popup.
         *
         * @param content The [Composable] content of the popup.
         * @param transformOrigin The pivot point in terms of fraction of the overall size, used for scale transformations. By default it's [TransformOrigin.Center].
         */
        @Composable
        fun showPopup(
            content: (@Composable () -> Unit)? = null,
            transformOrigin: (() -> TransformOrigin) = { TransformOrigin.Center }
        ) {
            if (isPopupShowing.value) return
            popupTransformOrigin.value = transformOrigin
            isPopupShowing.value = true
            popupContext.value = content
        }

        /**
         * Dismiss the popup.
         *
         * @param show The show state of the popup.
         */
        fun dismissPopup(
            show: MutableState<Boolean>,
        ) {
            isPopupShowing.value = false
            show.value = false
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
            val largeScreen by rememberUpdatedState { derivedStateOf { (windowHeight >= 480.dp && windowWidth >= 840.dp) } }
            var dimEnterDuration by remember { mutableIntStateOf(0) }
            var dimExitDuration by remember { mutableIntStateOf(0) }
            if (isDialogShowing.value) {
                dimEnterDuration = 300
                dimExitDuration = 250
            } else if (isPopupShowing.value) {
                dimEnterDuration = 150
                dimExitDuration = 150
            }
            AnimatedVisibility(
                visible = isDialogShowing.value || isPopupShowing.value,
                modifier = Modifier.zIndex(1f).fillMaxSize(),
                enter = fadeIn(animationSpec = tween(dimEnterDuration, easing = DecelerateEasing(1.5f))),
                exit = fadeOut(animationSpec = tween(dimExitDuration, easing = DecelerateEasing(1.5f)))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MiuixTheme.colorScheme.windowDimming)
                )
            }
            AnimatedVisibility(
                visible = isDialogShowing.value,
                modifier = Modifier.zIndex(2f).fillMaxSize(),
                enter = if (largeScreen.invoke().value) {
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
                },
                exit = if (largeScreen.invoke().value) {
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
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    dialogContext.value?.invoke()
                }
                AnimatedVisibility(
                    visible = isPopupShowing.value && isDialogShowing.value,
                    modifier = Modifier.zIndex(1f).fillMaxSize(),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MiuixTheme.colorScheme.windowDimming)
                    )
                }
            }
            AnimatedVisibility(
                visible = isPopupShowing.value,
                modifier = Modifier.zIndex(2f).fillMaxSize(),
                enter = fadeIn(
                    animationSpec = tween(150, easing = AccelerateEasing(1.5f))
                ) + scaleIn(
                    initialScale = 0.4f,
                    animationSpec = tween(150, easing = DecelerateEasing(1.5f)),
                    transformOrigin = popupTransformOrigin.value.invoke()
                ),
                exit = fadeOut(
                    animationSpec = tween(150, easing = AccelerateEasing(3.0f))
                ) + scaleOut(
                    targetScale = 0.8f,
                    animationSpec = tween(150, easing = AccelerateEasing(3.0f)),
                    transformOrigin = popupTransformOrigin.value.invoke()
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    popupContext.value?.invoke()
                }
            }
        }
    }
}