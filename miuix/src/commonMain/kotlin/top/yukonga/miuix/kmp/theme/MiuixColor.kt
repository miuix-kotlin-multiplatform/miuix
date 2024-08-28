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
 * @param onPrimaryContainer The color of the text on primary container.
 * @param background The background color.
 * @param onBackground The color of the text on background.
 * @param subTextBase The base color of the sub text.
 * @param subTextField The color of the sub text field.
 * @param subDropdown The color of the sub dropdown.
 * @param secondary The secondary color.
 * @param dropdownBackground The background color of the dropdown.
 * @param dropdownSelect The color of the selected item in the dropdown.
 * @param disabledBg The background color of the disabled button.
 * @param buttonDisableText The text color of the disabled button.
 * @param submitButtonDisabledText The text color of the disabled submit button.
 */
@Stable
class MiuixColor(
    primary: Color,
    onPrimary: Color,
    primaryContainer: Color,
    background: Color,
    onBackground: Color,
    subTextBase: Color,
    subTextField: Color,
    subDropdown: Color,
    secondary: Color,
    dropdownBackground: Color,
    dropdownSelect: Color,
    disabledBg: Color,
    submitDisabledBg: Color,
    buttonDisableText: Color,
    submitButtonDisabledText: Color
) {
    val primary by mutableStateOf(primary, structuralEqualityPolicy())
    val onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
    val primaryContainer by mutableStateOf(primaryContainer, structuralEqualityPolicy())
    val background by mutableStateOf(background, structuralEqualityPolicy())
    val onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
    val subTextBase by mutableStateOf(subTextBase, structuralEqualityPolicy())
    val subTextField by mutableStateOf(subTextField, structuralEqualityPolicy())
    val subDropdown by mutableStateOf(subDropdown, structuralEqualityPolicy())
    val secondary by mutableStateOf(secondary, structuralEqualityPolicy())
    val dropdownBackground by mutableStateOf(dropdownBackground, structuralEqualityPolicy())
    val dropdownSelect by mutableStateOf(dropdownSelect, structuralEqualityPolicy())
    val submitDisabledBg by mutableStateOf(disabledBg, structuralEqualityPolicy())
    val disabledBg by mutableStateOf(submitDisabledBg, structuralEqualityPolicy())
    val buttonDisableText by mutableStateOf(buttonDisableText, structuralEqualityPolicy())
    val submitButtonDisabledText by mutableStateOf(submitButtonDisabledText, structuralEqualityPolicy())
}

fun lightColorScheme() = MiuixColor(
    primary = Color(0xFF3482FF),
    onPrimary = Color.Black,
    primaryContainer = Color.White,
    background = Color(0xFFF5F5F5),
    onBackground = Color.Black,
    subTextBase = Color(0xFF666666),
    subTextField = Color(0xFFA8A8A8),
    subDropdown = Color(0xFF999999),
    secondary = Color(0xFFE6E6E6),
    dropdownBackground = Color(0xFFFFFFFF),
    dropdownSelect = Color(0xFFEAF2FF),
    disabledBg = Color(0xFFC2D9FF),
    submitDisabledBg = Color(0xFFEEEEEC),
    buttonDisableText = Color.LightGray,
    submitButtonDisabledText = Color(0xFFFFFFFF)
)

fun darkColorScheme() = MiuixColor(
    primary = Color(0xFF277AF7),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF1F1F1F),
    background = Color.Black,
    onBackground = Color.White,
    subTextBase = Color(0xFF808080),
    subTextField = Color(0xFF727272),
    subDropdown = Color(0xFF666666),
    secondary = Color(0xFF333333),
    dropdownBackground = Color(0xFF242424),
    dropdownSelect = Color(0xFF23334E),
    disabledBg = Color(0xFF253E64),
    submitDisabledBg = Color(0xFF222223),
    buttonDisableText = Color(0xFF666666),
    submitButtonDisabledText = Color(0xFF677893)
)