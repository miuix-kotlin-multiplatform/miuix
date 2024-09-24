package top.yukonga.miuix.kmp.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

/**
 * The default color scheme for the Miuix components.
 *
 * @param primary The primary color.
 * @param onPrimary The color of the text on primary color.
 * @param primaryContainer The color of the primary container.
 * @param background The background color.
 * @param onBackground The color of the text on background.
 * @param subTextBase The base color of the sub text.
 * @param secondary The secondary color.
 * @param dropdownBackground The background color of the dropdown.
 * @param dropdownSelect The color of the selected item in the dropdown.
 * @param textFieldBg The background color of the text field.
 * @param textFieldSub The color of the sub text field.
 * @param disabledBg The background color of the disabled button.
 * @param submitDisabledBg The background color of the disabled submit button.
 * @param buttonDisableText The text color of the disabled button.
 * @param submitButtonDisabledText The text color of the disabled submit button.
 * @param smallTitle The color of the small title.
 */
@Stable
class MiuixColor(
    primary: Color,
    onPrimary: Color,
    primaryContainer: Color,
    background: Color,
    onBackground: Color,
    subTextBase: Color,
    secondary: Color,
    dropdownBackground: Color,
    dropdownSelect: Color,
    textFieldBg: Color,
    textFieldSub: Color,
    disabledBg: Color,
    submitDisabledBg: Color,
    buttonDisableText: Color,
    submitButtonDisabledText: Color,
    smallTitle: Color
) {
    val primary by mutableStateOf(primary, structuralEqualityPolicy())
    val onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
    val primaryContainer by mutableStateOf(primaryContainer, structuralEqualityPolicy())
    val background by mutableStateOf(background, structuralEqualityPolicy())
    val onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
    val subTextBase by mutableStateOf(subTextBase, structuralEqualityPolicy())
    val secondary by mutableStateOf(secondary, structuralEqualityPolicy())
    val dropdownBackground by mutableStateOf(dropdownBackground, structuralEqualityPolicy())
    val dropdownSelect by mutableStateOf(dropdownSelect, structuralEqualityPolicy())
    val textFieldBg by mutableStateOf(textFieldBg, structuralEqualityPolicy())
    val textFieldSub by mutableStateOf(textFieldSub, structuralEqualityPolicy())
    val disabledBg by mutableStateOf(submitDisabledBg, structuralEqualityPolicy())
    val submitDisabledBg by mutableStateOf(disabledBg, structuralEqualityPolicy())
    val buttonDisableText by mutableStateOf(buttonDisableText, structuralEqualityPolicy())
    val submitButtonDisabledText by mutableStateOf(submitButtonDisabledText, structuralEqualityPolicy())
    val smallTitle by mutableStateOf(smallTitle, structuralEqualityPolicy())
}

fun lightColorScheme() = MiuixColor(
    primary = Color(0xFF3482FF),
    onPrimary = Color.Black,
    primaryContainer = Color.White,
    background = Color(0xFFF5F5F5),
    onBackground = Color.Black,
    subTextBase = Color(0xFF999999),
    secondary = Color(0xFFE6E6E6),
    dropdownBackground = Color(0xFFFFFFFF),
    dropdownSelect = Color(0xFFEAF2FF),
    textFieldBg = Color(0xFFF0F0F0),
    textFieldSub = Color(0xFFA8A8A8),
    disabledBg = Color(0xFFC2D9FF),
    submitDisabledBg = Color(0xFFEEEEEC),
    buttonDisableText = Color.LightGray,
    submitButtonDisabledText = Color(0xFFFFFFFF),
    smallTitle = Color(0xFF8F96B2)
)

fun darkColorScheme() = MiuixColor(
    primary = Color(0xFF277AF7),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF1F1F1F),
    background = Color.Black,
    onBackground = Color.White,
    subTextBase = Color(0xFF666666),
    secondary = Color(0xFF333333),
    dropdownBackground = Color(0xFF242424),
    dropdownSelect = Color(0xFF23334E),
    textFieldBg = Color(0xFF363636),
    textFieldSub = Color(0xFF727272),
    disabledBg = Color(0xFF253E64),
    submitDisabledBg = Color(0xFF222223),
    buttonDisableText = Color(0xFF666666),
    submitButtonDisabledText = Color(0xFF677893),
    smallTitle = Color(0xFF787E96)
)