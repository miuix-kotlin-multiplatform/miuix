package top.yukonga.miuix.kmp.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import top.yukonga.miuix.kmp.basic.MiuixBox
import top.yukonga.miuix.kmp.basic.MiuixScaffold

/**
 * A util class for show popup and dialog.
 */
class MiuixPopupUtil {

    companion object {
        private var isPopupShowing = mutableStateOf(false)
        private var isDialogShowing = mutableStateOf(false)
        private var popupContext = mutableStateOf<(@Composable () -> Unit)?>(null)
        private var dialogContext = mutableStateOf<(@Composable () -> Unit)?>(null)

        /**
         * Show a dialog.
         *
         * @param content The [Composable] content of the dialog.
         */
        @Composable
        fun showDialog(
            show: Boolean,
            content: (@Composable () -> Unit)? = null,
        ) {
            isDialogShowing.value = show
            dialogContext.value = content
        }

        /**
         * Dismiss the dialog.
         */
        fun dismissDialog() {
            isDialogShowing.value = false
        }

        /**
         * Show a popup.
         *
         * @param content The [Composable] content of the popup.
         */
        @Composable
        fun showPopup(
            show: Boolean,
            content: (@Composable () -> Unit)? = null,
        ) {
            isPopupShowing.value = show
            popupContext.value = content
        }

        /**
         * Dismiss the dialog.
         */
        fun dismissPopup() {
            isPopupShowing.value = false
        }

        /**
         * A host for show popup and dialog. Already added to the [MiuixScaffold] by default.
         */
        @Composable
        fun MiuixPopupHost() {
            AnimatedVisibility(
                visible = isDialogShowing.value || isPopupShowing.value,
                modifier = Modifier.zIndex(1f).fillMaxSize(),
            ) {
                MiuixBox(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                )
            }
            AnimatedVisibility(
                visible = isDialogShowing.value,
                modifier = Modifier.zIndex(2f).fillMaxSize(),
                enter = slideInVertically(animationSpec = tween(300)) { fullHeight -> fullHeight },
                exit = slideOutVertically(animationSpec = tween(300)) { fullHeight -> fullHeight }
            ) {
                MiuixBox(
                    modifier = Modifier.fillMaxSize().navigationBarsPadding()
                ) {
                    dialogContext.value?.invoke()
                }
            }
            AnimatedVisibility(
                visible = isPopupShowing.value,
                modifier = Modifier.zIndex(2f).fillMaxSize(),
                enter = fadeIn(animationSpec = tween(100)) + scaleIn(animationSpec = tween(100), initialScale = 0.9f),
                exit = fadeOut(animationSpec = tween(100)) + scaleOut(animationSpec = tween(100), targetScale = 0.9f)
            ) {
                MiuixBox(
                    modifier = Modifier.fillMaxSize()
                ) {
                    popupContext.value?.invoke()
                }
            }
        }
    }
}