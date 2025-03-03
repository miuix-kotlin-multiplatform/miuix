package component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.CircularProgressIndicator
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.InfiniteProgressIndicator
import top.yukonga.miuix.kmp.basic.LinearProgressIndicator
import top.yukonga.miuix.kmp.basic.Slider
import top.yukonga.miuix.kmp.basic.SliderDefaults.SliderHapticEffect
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.TabRow
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.basic.TextField
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.useful.AddSecret
import top.yukonga.miuix.kmp.icon.icons.useful.Back
import top.yukonga.miuix.kmp.icon.icons.useful.Blocklist
import top.yukonga.miuix.kmp.icon.icons.useful.Cancel
import top.yukonga.miuix.kmp.icon.icons.useful.Confirm
import top.yukonga.miuix.kmp.icon.icons.useful.Copy
import top.yukonga.miuix.kmp.icon.icons.useful.Cut
import top.yukonga.miuix.kmp.icon.icons.useful.Delete
import top.yukonga.miuix.kmp.icon.icons.useful.DeselectAll
import top.yukonga.miuix.kmp.icon.icons.useful.Edit
import top.yukonga.miuix.kmp.icon.icons.useful.ImmersionDelete
import top.yukonga.miuix.kmp.icon.icons.useful.ImmersionMore
import top.yukonga.miuix.kmp.icon.icons.useful.Info
import top.yukonga.miuix.kmp.icon.icons.useful.More
import top.yukonga.miuix.kmp.icon.icons.useful.Move
import top.yukonga.miuix.kmp.icon.icons.useful.NavigatorSwitch
import top.yukonga.miuix.kmp.icon.icons.useful.New
import top.yukonga.miuix.kmp.icon.icons.useful.Order
import top.yukonga.miuix.kmp.icon.icons.useful.Paste
import top.yukonga.miuix.kmp.icon.icons.useful.Pause
import top.yukonga.miuix.kmp.icon.icons.useful.Personal
import top.yukonga.miuix.kmp.icon.icons.useful.Play
import top.yukonga.miuix.kmp.icon.icons.useful.Reboot
import top.yukonga.miuix.kmp.icon.icons.useful.Redo
import top.yukonga.miuix.kmp.icon.icons.useful.Refresh
import top.yukonga.miuix.kmp.icon.icons.useful.RemoveBlocklist
import top.yukonga.miuix.kmp.icon.icons.useful.RemoveSecret
import top.yukonga.miuix.kmp.icon.icons.useful.Rename
import top.yukonga.miuix.kmp.icon.icons.useful.Restore
import top.yukonga.miuix.kmp.icon.icons.useful.Save
import top.yukonga.miuix.kmp.icon.icons.useful.Scan
import top.yukonga.miuix.kmp.icon.icons.useful.Search
import top.yukonga.miuix.kmp.icon.icons.useful.SelectAll
import top.yukonga.miuix.kmp.icon.icons.useful.Send
import top.yukonga.miuix.kmp.icon.icons.useful.Settings
import top.yukonga.miuix.kmp.icon.icons.useful.Share
import top.yukonga.miuix.kmp.icon.icons.useful.Stick
import top.yukonga.miuix.kmp.icon.icons.useful.Undo
import top.yukonga.miuix.kmp.icon.icons.useful.Unstick
import top.yukonga.miuix.kmp.icon.icons.useful.Update
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun OtherComponent(padding: PaddingValues) {
    var buttonText by remember { mutableStateOf("Cancel") }
    var submitButtonText by remember { mutableStateOf("Submit") }
    var clickCount by remember { mutableStateOf(0) }
    var submitClickCount by remember { mutableStateOf(0) }
    val focusManager = LocalFocusManager.current
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf(TextFieldValue("")) }
    var text3 by remember { mutableStateOf("") }
    var progress by remember { mutableStateOf(0.5f) }
    var progressHaptic by remember { mutableStateOf(0.5f) }
    val progressValues = remember { listOf(0.1f, 0.3f, progress, 0.7f, 0.9f, null) }
    val progressDisable by remember { mutableStateOf(0.5f) }
    val tabTexts = listOf("tab1", "tab2", "tab3", "tab4", "tab5", "tab6")
    var selectedTabIndex1 by remember { mutableStateOf(0) }
    val miuixIconsNormal = listOf(
        MiuixIcons.Useful.AddSecret,
        MiuixIcons.Useful.Back,
        MiuixIcons.Useful.Blocklist,
        MiuixIcons.Useful.Cancel,
        MiuixIcons.Useful.Confirm,
        MiuixIcons.Useful.Copy,
        MiuixIcons.Useful.Cut,
        MiuixIcons.Useful.Delete,
        MiuixIcons.Useful.DeselectAll,
        MiuixIcons.Useful.Edit,
        MiuixIcons.Useful.ImmersionDelete,
        MiuixIcons.Useful.ImmersionMore,
        MiuixIcons.Useful.Info,
        MiuixIcons.Useful.More,
        MiuixIcons.Useful.Move,
        MiuixIcons.Useful.NavigatorSwitch,
        MiuixIcons.Useful.New,
        MiuixIcons.Useful.Order,
        MiuixIcons.Useful.Paste,
        MiuixIcons.Useful.Pause,
        MiuixIcons.Useful.Personal,
        MiuixIcons.Useful.Play,
        MiuixIcons.Useful.Reboot,
        MiuixIcons.Useful.Redo,
        MiuixIcons.Useful.Refresh,
        MiuixIcons.Useful.RemoveBlocklist,
        MiuixIcons.Useful.RemoveSecret,
        MiuixIcons.Useful.Rename,
        MiuixIcons.Useful.Restore,
        MiuixIcons.Useful.Save,
        MiuixIcons.Useful.Scan,
        MiuixIcons.Useful.Search,
        MiuixIcons.Useful.SelectAll,
        MiuixIcons.Useful.Send,
        MiuixIcons.Useful.Settings,
        MiuixIcons.Useful.Share,
        MiuixIcons.Useful.Stick,
        MiuixIcons.Useful.Undo,
        MiuixIcons.Useful.Unstick,
        MiuixIcons.Useful.Update
    )

    SmallTitle(text = "Button")
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            text = buttonText,
            onClick = {
                clickCount++
                buttonText = "Click: $clickCount"
            },
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(12.dp))
        TextButton(
            text = submitButtonText,
            onClick = {
                submitClickCount++
                submitButtonText = "Click: $submitClickCount"
            },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.textButtonColorsPrimary()
        )
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            text = "Disabled",
            onClick = {},
            modifier = Modifier.weight(1f),
            enabled = false
        )
        Spacer(Modifier.width(12.dp))
        TextButton(
            text = "Disabled",
            onClick = {},
            enabled = false,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.textButtonColorsPrimary()
        )
    }

    SmallTitle(text = "ProgressIndicator")
    progressValues.forEach { progressValue ->
        LinearProgressIndicator(
            progress = progressValue,
            modifier = Modifier
                .padding(horizontal = 15.dp) // Increased from 12.dp because of StrokeCap.Round.
                .padding(bottom = 12.dp)
        )
    }

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        progressValues.forEach { progressValue ->
            CircularProgressIndicator(
                progress = progressValue
            )
        }
        InfiniteProgressIndicator(
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
        )
    }

    SmallTitle(text = "TextField")
    TextField(
        value = text1,
        onValueChange = { text1 = it },
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )

    TextField(
        value = text2,
        onValueChange = { text2 = it },
        backgroundColor = MiuixTheme.colorScheme.secondaryContainer,
        label = "Text Field",
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )

    TextField(
        value = text3,
        onValueChange = { text3 = it },
        backgroundColor = MiuixTheme.colorScheme.secondaryContainer,
        label = "Placeholder & SingleLine",
        useLabelAsPlaceholder = true,
        singleLine = true,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 6.dp),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )

    SmallTitle(text = "Slider")
    Slider(
        progress = progress,
        onProgressChange = { newProgress -> progress = newProgress },
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp)
    )

    Slider(
        progress = progressHaptic,
        onProgressChange = { newProgress -> progressHaptic = newProgress },
        hapticEffect = SliderHapticEffect.Step,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp)
    )

    Slider(
        progress = progressDisable,
        onProgressChange = {},
        enabled = false,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 6.dp)
    )

    SmallTitle(text = "TabRow")
    TabRow(
        tabs = tabTexts,
        selectedTabIndex = selectedTabIndex1,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 6.dp)
    ) {
        selectedTabIndex1 = it
    }

    SmallTitle(text = "Icon")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 6.dp),
        insideMargin = PaddingValues(16.dp)
    ) {
        FlowRow {
            miuixIconsNormal.forEach { icon ->
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }

    SmallTitle(text = "Card")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        color = MiuixTheme.colorScheme.primaryVariant,
        insideMargin = PaddingValues(16.dp)
    ) {
        Text(
            color = MiuixTheme.colorScheme.onPrimary,
            text = "Card",
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp + padding.calculateBottomPadding()),
        insideMargin = PaddingValues(16.dp)
    ) {
        Text(
            color = MiuixTheme.colorScheme.onSurface,
            text = "Card\nCardCard\nCardCardCard",
            style = MiuixTheme.textStyles.paragraph
        )
    }
}