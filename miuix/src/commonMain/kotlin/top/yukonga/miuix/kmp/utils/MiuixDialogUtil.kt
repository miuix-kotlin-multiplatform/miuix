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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import top.yukonga.miuix.kmp.basic.MiuixBox

class MiuixDialogUtil {

    companion object {
        private var isPopupVisible by mutableStateOf(false)
        private var isDialogVisible by mutableStateOf(false)
        private var popupContext: (@Composable () -> Unit)? = null
        private var dialogContext: (@Composable () -> Unit)? = null

        @Composable
        fun showDialog(
            visible: MutableState<Boolean>,
            content: (@Composable () -> Unit)? = null,
        ) {
            isDialogVisible = visible.value
            dialogContext = content
        }

        @Composable
        fun showPopup(
            visible: MutableState<Boolean>,
            content: (@Composable () -> Unit)? = null,
        ) {
            isPopupVisible = visible.value
            popupContext = content
        }

        @Composable
        fun MiuixDialogHost() {
            AnimatedVisibility(
                visible = isDialogVisible || isPopupVisible,
                modifier = Modifier.zIndex(1f).fillMaxSize(),
            ) {
                MiuixBox(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                )
            }
            AnimatedVisibility(
                visible = isDialogVisible,
                modifier = Modifier.zIndex(2f).fillMaxSize(),
                enter = slideInVertically(animationSpec = tween(300)) { fullHeight -> fullHeight },
                exit = slideOutVertically(animationSpec = tween(300)) { fullHeight -> fullHeight }
            ) {
                MiuixBox(
                    modifier = Modifier.fillMaxSize().navigationBarsPadding()
                ) {
                    dialogContext?.invoke()
                }
            }
            AnimatedVisibility(
                visible = isPopupVisible,
                modifier = Modifier.zIndex(2f).fillMaxSize(),
                enter = fadeIn(animationSpec = tween(100)) + scaleIn(animationSpec = tween(100), initialScale = 0.9f),
                exit = fadeOut(animationSpec = tween(100)) + scaleOut(animationSpec = tween(100), targetScale = 0.9f)
            ) {
                MiuixBox(
                    modifier = Modifier.fillMaxSize()
                ) {
                    popupContext?.invoke()
                }
            }
        }
    }
}


