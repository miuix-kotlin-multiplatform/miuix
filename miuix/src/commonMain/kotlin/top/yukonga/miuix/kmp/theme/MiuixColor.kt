package top.yukonga.miuix.kmp.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

/**
 * The default color scheme for the Miuix components.
 *
 * @param primary The primary color. Cases: Switch, Button, Slider.
 * @param onPrimary The color of the text on primary color. Cases: Switch, Button, Slider.
 * @param primaryVariant The variant color of the primary color.Cases:Card
 * @param onPrimaryVariant The color of the text on primary variant color.
 * @param disabledPrimary The disabled primary color of the switch.
 * @param disabledOnPrimary The color of the switch on disabled primary color.
 * @param disabledPrimaryButton The disabled primary color of the button.
 * @param disabledOnPrimaryButton The color of the button on disabled primary color.
 * @param disabledPrimarySlider The disabled primary color of the slider.
 * @param primaryContainer The container color of the primary color.
 * @param onPrimaryContainer The color of the text on primary container color.
 * @param secondary The secondary color.
 * @param onSecondary The color of the text on secondary color.
 * @param secondaryVariant The variant color of the secondary color.
 * @param onSecondaryVariant The color of the text on secondary variant color.
 * @param disabledSecondary The disabled secondary color.
 * @param disabledOnSecondary The color of the text on disabled secondary color.
 * @param disabledSecondaryVariant The disabled secondary color.
 * @param disabledOnSecondaryVariant The color of the text on disabled secondary variant color.
 * @param secondaryContainer The container color of the secondary color.
 * @param onSecondaryContainer The color of the text on secondary container color.
 * @param secondaryContainerVariant The variant color of the secondary container color.
 * @param onSecondaryContainerVariant The color of the text on secondary container variant color.
 * @param tertiaryContainer The container color of the tertiary color.
 * @param onTertiaryContainer The color of the text on tertiary container color.
 * @param tertiaryContainerVariant The variant color of the tertiary container color.
 * @param background The background color.
 * @param onBackground The color of the text on background color.
 * @param onBackgroundVariant The color of the text on background variant color.
 * @param surface The surface color.
 * @param onSurface The color of the text on surface color.
 * @param surfaceVariant The variant color of the surface color.
 * @param onSurfaceVariantDialog The color of the dialog summary on surface variant color.
 * @param onSurfaceVariantSummary The color of the summary on surface variant color.
 * @param onSurfaceVariantActions The color of the actions on surface variant color.
 * @param disabledOnSurface The color of the text on disabled surface color.
 * @param outline The outline color.
 * @param dividerLine The divider line color.
 * @param surfaceContainer The container color of the surface color.
 * @param onSurfaceContainer The color of the text on surface container color.
 * @param onSurfaceContainerVariant The color of the text on surface container variant color.
 * @param surfaceContainerHigh The container color of the surface color.
 * @param onSurfaceContainerHigh The color of the text on surface container high color.
 * @param surfaceContainerHighest The container color of the surface color.
 * @param onSurfaceContainerHighest The color of the text on surface container highest color.
 * @param windowDimming The color of the window dimming. Cases: Dialog, Dropdown.
 */
@Stable
class MiuixColor(
    primary: Color,
    onPrimary: Color,
    primaryVariant: Color,
    onPrimaryVariant: Color,
    disabledPrimary: Color,
    disabledOnPrimary: Color,
    disabledPrimaryButton: Color,
    disabledOnPrimaryButton: Color,
    disabledPrimarySlider: Color,
    primaryContainer: Color,
    onPrimaryContainer: Color,
    secondary: Color,
    onSecondary: Color,
    secondaryVariant: Color,
    onSecondaryVariant: Color,
    disabledSecondary: Color,
    disabledOnSecondary: Color,
    disabledSecondaryVariant: Color,
    disabledOnSecondaryVariant: Color,
    secondaryContainer: Color,
    onSecondaryContainer: Color,
    secondaryContainerVariant: Color,
    onSecondaryContainerVariant: Color,
    tertiaryContainer: Color,
    onTertiaryContainer: Color,
    tertiaryContainerVariant: Color,
    background: Color,
    onBackground: Color,
    onBackgroundVariant: Color,
    surface: Color,
    onSurface: Color,
    surfaceVariant: Color,
    onSurfaceVariantDialog: Color,
    onSurfaceVariantSummary: Color,
    onSurfaceVariantActions: Color,
    disabledOnSurface: Color,
    outline: Color,
    dividerLine: Color,
    surfaceContainer: Color,
    onSurfaceContainer: Color,
    onSurfaceContainerVariant: Color,
    surfaceContainerHigh: Color,
    onSurfaceContainerHigh: Color,
    surfaceContainerHighest: Color,
    onSurfaceContainerHighest: Color,
    windowDimming: Color
) {
    val primary by mutableStateOf(primary, structuralEqualityPolicy())
    val onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
    val primaryVariant by mutableStateOf(primaryVariant, structuralEqualityPolicy())
    val onPrimaryVariant by mutableStateOf(onPrimaryVariant, structuralEqualityPolicy())
    val disabledPrimary by mutableStateOf(disabledPrimary, structuralEqualityPolicy())
    val disabledOnPrimary by mutableStateOf(disabledOnPrimary, structuralEqualityPolicy())
    val disabledPrimaryButton by mutableStateOf(disabledPrimaryButton, structuralEqualityPolicy())
    val disabledOnPrimaryButton by mutableStateOf(disabledOnPrimaryButton, structuralEqualityPolicy())
    val disabledPrimarySlider by mutableStateOf(disabledPrimarySlider, structuralEqualityPolicy())
    val primaryContainer by mutableStateOf(primaryContainer, structuralEqualityPolicy())
    val onPrimaryContainer by mutableStateOf(onPrimaryContainer, structuralEqualityPolicy())
    val secondary by mutableStateOf(secondary, structuralEqualityPolicy())
    val onSecondary by mutableStateOf(onSecondary, structuralEqualityPolicy())
    val secondaryVariant by mutableStateOf(secondaryVariant, structuralEqualityPolicy())
    val onSecondaryVariant by mutableStateOf(onSecondaryVariant, structuralEqualityPolicy())
    val disabledSecondary by mutableStateOf(disabledSecondary, structuralEqualityPolicy())
    val disabledOnSecondary by mutableStateOf(disabledOnSecondary, structuralEqualityPolicy())
    val disabledSecondaryVariant by mutableStateOf(disabledSecondaryVariant, structuralEqualityPolicy())
    val disabledOnSecondaryVariant by mutableStateOf(disabledOnSecondaryVariant, structuralEqualityPolicy())
    val secondaryContainer by mutableStateOf(secondaryContainer, structuralEqualityPolicy())
    val onSecondaryContainer by mutableStateOf(onSecondaryContainer, structuralEqualityPolicy())
    val secondaryContainerVariant by mutableStateOf(secondaryContainerVariant, structuralEqualityPolicy())
    val onSecondaryContainerVariant by mutableStateOf(onSecondaryContainerVariant, structuralEqualityPolicy())
    val tertiaryContainer by mutableStateOf(tertiaryContainer, structuralEqualityPolicy())
    val onTertiaryContainer by mutableStateOf(onTertiaryContainer, structuralEqualityPolicy())
    val tertiaryContainerVariant by mutableStateOf(tertiaryContainerVariant, structuralEqualityPolicy())
    val background by mutableStateOf(background, structuralEqualityPolicy())
    val onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
    val onBackgroundVariant by mutableStateOf(onBackgroundVariant, structuralEqualityPolicy())
    val surface by mutableStateOf(surface, structuralEqualityPolicy())
    val onSurface by mutableStateOf(onSurface, structuralEqualityPolicy())
    val surfaceVariant by mutableStateOf(surfaceVariant, structuralEqualityPolicy())
    val onSurfaceVariantDialog by mutableStateOf(onSurfaceVariantDialog, structuralEqualityPolicy())
    val onSurfaceVariantSummary by mutableStateOf(onSurfaceVariantSummary, structuralEqualityPolicy())
    val onSurfaceVariantActions by mutableStateOf(onSurfaceVariantActions, structuralEqualityPolicy())
    val disabledOnSurface by mutableStateOf(disabledOnSurface, structuralEqualityPolicy())
    val outline by mutableStateOf(outline, structuralEqualityPolicy())
    val dividerLine by mutableStateOf(dividerLine, structuralEqualityPolicy())
    val surfaceContainer by mutableStateOf(surfaceContainer, structuralEqualityPolicy())
    val onSurfaceContainer by mutableStateOf(onSurfaceContainer, structuralEqualityPolicy())
    val onSurfaceContainerVariant by mutableStateOf(onSurfaceContainerVariant, structuralEqualityPolicy())
    val surfaceContainerHigh by mutableStateOf(surfaceContainerHigh, structuralEqualityPolicy())
    val onSurfaceContainerHigh by mutableStateOf(onSurfaceContainerHigh, structuralEqualityPolicy())
    val surfaceContainerHighest by mutableStateOf(surfaceContainerHighest, structuralEqualityPolicy())
    val onSurfaceContainerHighest by mutableStateOf(onSurfaceContainerHighest, structuralEqualityPolicy())
    val windowDimming by mutableStateOf(windowDimming, structuralEqualityPolicy())
}

fun lightColorScheme() = MiuixColor(
    primary = Color(0xFF3482FF),
    onPrimary = Color.White,
    primaryVariant = Color(0xFF3482FF),
    onPrimaryVariant = Color(0xFFAECDFF),
    disabledPrimary = Color(0xFFC2D9FF),
    disabledOnPrimary = Color(0xFFEDF4FF),
    disabledPrimaryButton = Color(0xFFEBF1FD),
    disabledOnPrimaryButton = Color(0xFFFAFAFF),
    disabledPrimarySlider = Color(0xFFB4D1FF),
    primaryContainer = Color(0xFF5D9BFF),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFE6E6E6),
    onSecondary = Color.White,
    secondaryVariant = Color(0xFFF0F0F0),
    onSecondaryVariant = Color(0xFF303030),
    disabledSecondary = Color(0xFFF2F2F2),
    disabledOnSecondary = Color(0xFFFCFCFC),
    disabledSecondaryVariant = Color(0xFFF5F5F5),
    disabledOnSecondaryVariant = Color(0xFFB2B2B2),
    secondaryContainer = Color(0xFFF0F0F0),
    onSecondaryContainer = Color(0xFFA9A9A9),
    secondaryContainerVariant = Color(0xFFF0F0F0),
    onSecondaryContainerVariant = Color(0xFFA9A9A9),
    tertiaryContainer = Color(0xFFEBF3FF),
    onTertiaryContainer = Color(0xFF3482FF),
    tertiaryContainerVariant = Color(0xFFEBF3FF),
    background = Color(0xFFF7F7F7),
    onBackground = Color.Black,
    onBackgroundVariant = Color(0xFF8C93B0),
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color.White,
    onSurfaceVariantDialog = Color(0xFF333333),
    onSurfaceVariantSummary = Color(0xFF666666),
    onSurfaceVariantActions = Color(0xFF999999),
    disabledOnSurface = Color(0xFFB2B2B2),
    outline = Color(0xFFD9D9D9),
    dividerLine = Color(0xFFE0E0E0),
    surfaceContainer = Color(0xFFF9F9F9),
    onSurfaceContainer = Color(0xFF323232),
    onSurfaceContainerVariant = Color(0xFF959595),
    surfaceContainerHigh = Color(0xFFEDEDED),
    onSurfaceContainerHigh = Color(0xFFA5A5A5),
    surfaceContainerHighest = Color(0xFFE8E8E8),
    onSurfaceContainerHighest = Color.Black,
    windowDimming = Color.Black.copy(alpha = 0.3f)
)

fun darkColorScheme() = MiuixColor(
    primary = Color(0xFF277Af7),
    onPrimary = Color.White,
    primaryVariant = Color(0xFF0073DD),
    onPrimaryVariant = Color(0xFF99C7F1),
    disabledPrimary = Color(0xFF253E64),
    disabledOnPrimary = Color(0xFF677993),
    disabledPrimaryButton = Color(0xFF1E2B43),
    disabledOnPrimaryButton = Color(0xFF505A6E),
    disabledPrimarySlider = Color(0xFF435C81),
    primaryContainer = Color(0xFF338FE4),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF4F4F4F),
    onSecondary = Color.White,
    secondaryVariant = Color(0xFF3A3A3A),
    onSecondaryVariant = Color(0xFFAAAAAA),
    disabledSecondary = Color(0xFF3F3F3F),
    disabledOnSecondary = Color(0xFF707070),
    disabledSecondaryVariant = Color(0xFF434343),
    disabledOnSecondaryVariant = Color(0xFF646464),
    secondaryContainer = Color(0xFF2C2C2C),
    onSecondaryContainer = Color(0xFF5D5D5D),
    secondaryContainerVariant = Color(0xFF4F4F4F),
    onSecondaryContainerVariant = Color(0xFF959595),
    tertiaryContainer = Color(0xFF2B3B54),
    onTertiaryContainer = Color(0xFF277AF7),
    tertiaryContainerVariant = Color(0xFF4F4F4F),
    background = Color(0xFF101010),
    onBackground = Color(0xFFE8E8E8),
    onBackgroundVariant = Color(0xFF787E96),
    surface = Color(0xFF242424),
    onSurface = Color(0xFFE9E9E9),
    surfaceVariant = Color(0xFF181818),
    onSurfaceVariantDialog = Color(0xFFD2D2D2),
    onSurfaceVariantSummary = Color(0xFF929292),
    onSurfaceVariantActions = Color(0xFF7B7B7B),
    disabledOnSurface = Color(0xFF666666),
    outline = Color(0xFF404040),
    dividerLine = Color(0xFF393939),
    surfaceContainer = Color(0xFF161616),
    onSurfaceContainer = Color(0xFFD0D0D0),
    onSurfaceContainerVariant = Color(0xFF737373),
    surfaceContainerHigh = Color(0xFF2D2D2D),
    onSurfaceContainerHigh = Color(0xFF6C6C6C),
    surfaceContainerHighest = Color(0xFF2D2D2D),
    onSurfaceContainerHighest = Color(0xFFE9E9E9),
    windowDimming = Color.Black.copy(alpha = 0.6f)
)