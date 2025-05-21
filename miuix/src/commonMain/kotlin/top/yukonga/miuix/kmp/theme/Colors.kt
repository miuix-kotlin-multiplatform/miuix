// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
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
 * @param onSurfaceSecondary The color of the text on surface secondary color.
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
class Colors(
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
    onSurfaceSecondary: Color,
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
    windowDimming: Color,
) {
    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set
    var onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
        internal set
    var primaryVariant by mutableStateOf(primaryVariant, structuralEqualityPolicy())
        internal set
    var onPrimaryVariant by mutableStateOf(onPrimaryVariant, structuralEqualityPolicy())
        internal set
    var disabledPrimary by mutableStateOf(disabledPrimary, structuralEqualityPolicy())
        internal set
    var disabledOnPrimary by mutableStateOf(disabledOnPrimary, structuralEqualityPolicy())
        internal set
    var disabledPrimaryButton by mutableStateOf(disabledPrimaryButton, structuralEqualityPolicy())
        internal set
    var disabledOnPrimaryButton by mutableStateOf(disabledOnPrimaryButton, structuralEqualityPolicy())
        internal set
    var disabledPrimarySlider by mutableStateOf(disabledPrimarySlider, structuralEqualityPolicy())
        internal set
    var primaryContainer by mutableStateOf(primaryContainer, structuralEqualityPolicy())
        internal set
    var onPrimaryContainer by mutableStateOf(onPrimaryContainer, structuralEqualityPolicy())
        internal set
    var secondary by mutableStateOf(secondary, structuralEqualityPolicy())
        internal set
    var onSecondary by mutableStateOf(onSecondary, structuralEqualityPolicy())
        internal set
    var secondaryVariant by mutableStateOf(secondaryVariant, structuralEqualityPolicy())
        internal set
    var onSecondaryVariant by mutableStateOf(onSecondaryVariant, structuralEqualityPolicy())
        internal set
    var disabledSecondary by mutableStateOf(disabledSecondary, structuralEqualityPolicy())
        internal set
    var disabledOnSecondary by mutableStateOf(disabledOnSecondary, structuralEqualityPolicy())
        internal set
    var disabledSecondaryVariant by mutableStateOf(disabledSecondaryVariant, structuralEqualityPolicy())
        internal set
    var disabledOnSecondaryVariant by mutableStateOf(disabledOnSecondaryVariant, structuralEqualityPolicy())
        internal set
    var secondaryContainer by mutableStateOf(secondaryContainer, structuralEqualityPolicy())
        internal set
    var onSecondaryContainer by mutableStateOf(onSecondaryContainer, structuralEqualityPolicy())
        internal set
    var secondaryContainerVariant by mutableStateOf(secondaryContainerVariant, structuralEqualityPolicy())
        internal set
    var onSecondaryContainerVariant by mutableStateOf(onSecondaryContainerVariant, structuralEqualityPolicy())
        internal set
    var tertiaryContainer by mutableStateOf(tertiaryContainer, structuralEqualityPolicy())
        internal set
    var onTertiaryContainer by mutableStateOf(onTertiaryContainer, structuralEqualityPolicy())
        internal set
    var tertiaryContainerVariant by mutableStateOf(tertiaryContainerVariant, structuralEqualityPolicy())
        internal set
    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    var onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
        internal set
    var onBackgroundVariant by mutableStateOf(onBackgroundVariant, structuralEqualityPolicy())
        internal set
    var surface by mutableStateOf(surface, structuralEqualityPolicy())
        internal set
    var onSurface by mutableStateOf(onSurface, structuralEqualityPolicy())
        internal set
    var surfaceVariant by mutableStateOf(surfaceVariant, structuralEqualityPolicy())
        internal set
    var onSurfaceSecondary by mutableStateOf(onSurfaceSecondary, structuralEqualityPolicy())
        internal set
    var onSurfaceVariantSummary by mutableStateOf(onSurfaceVariantSummary, structuralEqualityPolicy())
        internal set
    var onSurfaceVariantActions by mutableStateOf(onSurfaceVariantActions, structuralEqualityPolicy())
        internal set
    var disabledOnSurface by mutableStateOf(disabledOnSurface, structuralEqualityPolicy())
        internal set
    var outline by mutableStateOf(outline, structuralEqualityPolicy())
        internal set
    var dividerLine by mutableStateOf(dividerLine, structuralEqualityPolicy())
        internal set
    var surfaceContainer by mutableStateOf(surfaceContainer, structuralEqualityPolicy())
        internal set
    var onSurfaceContainer by mutableStateOf(onSurfaceContainer, structuralEqualityPolicy())
        internal set
    var onSurfaceContainerVariant by mutableStateOf(onSurfaceContainerVariant, structuralEqualityPolicy())
        internal set
    var surfaceContainerHigh by mutableStateOf(surfaceContainerHigh, structuralEqualityPolicy())
        internal set
    var onSurfaceContainerHigh by mutableStateOf(onSurfaceContainerHigh, structuralEqualityPolicy())
        internal set
    var surfaceContainerHighest by mutableStateOf(surfaceContainerHighest, structuralEqualityPolicy())
        internal set
    var onSurfaceContainerHighest by mutableStateOf(onSurfaceContainerHighest, structuralEqualityPolicy())
        internal set
    var windowDimming by mutableStateOf(windowDimming, structuralEqualityPolicy())
        internal set

    fun copy(
        primary: Color = this.primary,
        onPrimary: Color = this.onPrimary,
        primaryVariant: Color = this.primaryVariant,
        onPrimaryVariant: Color = this.onPrimaryVariant,
        disabledPrimary: Color = this.disabledPrimary,
        disabledOnPrimary: Color = this.disabledOnPrimary,
        disabledPrimaryButton: Color = this.disabledPrimaryButton,
        disabledOnPrimaryButton: Color = this.disabledOnPrimaryButton,
        disabledPrimarySlider: Color = this.disabledPrimarySlider,
        primaryContainer: Color = this.primaryContainer,
        onPrimaryContainer: Color = this.onPrimaryContainer,
        secondary: Color = this.secondary,
        onSecondary: Color = this.onSecondary,
        secondaryVariant: Color = this.secondaryVariant,
        onSecondaryVariant: Color = this.onSecondaryVariant,
        disabledSecondary: Color = this.disabledSecondary,
        disabledOnSecondary: Color = this.disabledOnSecondary,
        disabledSecondaryVariant: Color = this.disabledSecondaryVariant,
        disabledOnSecondaryVariant: Color = this.disabledOnSecondaryVariant,
        secondaryContainer: Color = this.secondaryContainer,
        onSecondaryContainer: Color = this.onSecondaryContainer,
        secondaryContainerVariant: Color = this.secondaryContainerVariant,
        onSecondaryContainerVariant: Color = this.onSecondaryContainerVariant,
        tertiaryContainer: Color = this.tertiaryContainer,
        onTertiaryContainer: Color = this.onTertiaryContainer,
        tertiaryContainerVariant: Color = this.tertiaryContainerVariant,
        background: Color = this.background,
        onBackground: Color = this.onBackground,
        onBackgroundVariant: Color = this.onBackgroundVariant,
        surface: Color = this.surface,
        onSurface: Color = this.onSurface,
        surfaceVariant: Color = this.surfaceVariant,
        onSurfaceSecondary: Color = this.onSurfaceSecondary,
        onSurfaceVariantSummary: Color = this.onSurfaceVariantSummary,
        onSurfaceVariantActions: Color = this.onSurfaceVariantActions,
        disabledOnSurface: Color = this.disabledOnSurface,
        outline: Color = this.outline,
        dividerLine: Color = this.dividerLine,
        surfaceContainer: Color = this.surfaceContainer,
        onSurfaceContainer: Color = this.onSurfaceContainer,
        onSurfaceContainerVariant: Color = this.onSurfaceContainerVariant,
        surfaceContainerHigh: Color = this.surfaceContainerHigh,
        onSurfaceContainerHigh: Color = this.onSurfaceContainerHigh,
        surfaceContainerHighest: Color = this.surfaceContainerHighest,
        onSurfaceContainerHighest: Color = this.onSurfaceContainerHighest,
        windowDimming: Color = this.windowDimming,
    ): Colors =
        Colors(
            primary,
            onPrimary,
            primaryVariant,
            onPrimaryVariant,
            disabledPrimary,
            disabledOnPrimary,
            disabledPrimaryButton,
            disabledOnPrimaryButton,
            disabledPrimarySlider,
            primaryContainer,
            onPrimaryContainer,
            secondary,
            onSecondary,
            secondaryVariant,
            onSecondaryVariant,
            disabledSecondary,
            disabledOnSecondary,
            disabledSecondaryVariant,
            disabledOnSecondaryVariant,
            secondaryContainer,
            onSecondaryContainer,
            secondaryContainerVariant,
            onSecondaryContainerVariant,
            tertiaryContainer,
            onTertiaryContainer,
            tertiaryContainerVariant,
            background,
            onBackground,
            onBackgroundVariant,
            surface,
            onSurface,
            surfaceVariant,
            onSurfaceSecondary,
            onSurfaceVariantSummary,
            onSurfaceVariantActions,
            disabledOnSurface,
            outline,
            dividerLine,
            surfaceContainer,
            onSurfaceContainer,
            onSurfaceContainerVariant,
            surfaceContainerHigh,
            onSurfaceContainerHigh,
            surfaceContainerHighest,
            onSurfaceContainerHighest,
            windowDimming,
        )
}

fun lightColorScheme(
    primary: Color = Color(0xFF3482FF),
    onPrimary: Color = Color.White,
    primaryVariant: Color = Color(0xFF3482FF),
    onPrimaryVariant: Color = Color(0xFFAECDFF),
    disabledPrimary: Color = Color(0xFFC2D9FF),
    disabledOnPrimary: Color = Color(0xFFEDF4FF),
    disabledPrimaryButton: Color = Color(0xFFC2D9FF),
    disabledOnPrimaryButton: Color = Color(0xFFFFFFFF),
    disabledPrimarySlider: Color = Color(0xFFB3D0FF),
    primaryContainer: Color = Color(0xFF5D9BFF),
    onPrimaryContainer: Color = Color.White,
    secondary: Color = Color(0xFFE6E6E6),
    onSecondary: Color = Color.White,
    secondaryVariant: Color = Color(0xFFF0F0F0),
    onSecondaryVariant: Color = Color(0xFF303030),
    disabledSecondary: Color = Color(0xFFF2F2F2),
    disabledOnSecondary: Color = Color(0xFFFCFCFC),
    disabledSecondaryVariant: Color = Color(0xFFF2F2F2),
    disabledOnSecondaryVariant: Color = Color(0xFFB2B2B2),
    secondaryContainer: Color = Color(0xFFF0F0F0),
    onSecondaryContainer: Color = Color(0xFFA9A9A9),
    secondaryContainerVariant: Color = Color(0xFFF0F0F0),
    onSecondaryContainerVariant: Color = Color(0xFFA8A8A8),
    tertiaryContainer: Color = Color(0xFFEAF2FF),
    onTertiaryContainer: Color = Color(0xFF3482FF),
    tertiaryContainerVariant: Color = Color(0xFFEAF2FF),
    background: Color = Color(0xFFF7F7F7),
    onBackground: Color = Color.Black,
    onBackgroundVariant: Color = Color(0xFF8C93B0),
    surface: Color = Color.White,
    onSurface: Color = Color.Black,
    surfaceVariant: Color = Color.White,
    onSurfaceSecondary: Color = Color(0xCC000000),
    onSurfaceVariantSummary: Color = Color(0x99000000),
    onSurfaceVariantActions: Color = Color(0x66000000),
    disabledOnSurface: Color = Color(0xFFB2B2B2),
    outline: Color = Color(0xFFD9D9D9),
    dividerLine: Color = Color(0xFFE0E0E0),
    surfaceContainer: Color = Color(0xFFF9F9F9),
    onSurfaceContainer: Color = Color(0xFF323232),
    onSurfaceContainerVariant: Color = Color(0xFF959595),
    surfaceContainerHigh: Color = Color(0xFFE8E8E8),
    onSurfaceContainerHigh: Color = Color(0xFFA2A2A2),
    surfaceContainerHighest: Color = Color(0xFFE8E8E8),
    onSurfaceContainerHighest: Color = Color.Black,
    windowDimming: Color = Color.Black.copy(alpha = 0.3F),
): Colors =
    Colors(
        primary,
        onPrimary,
        primaryVariant,
        onPrimaryVariant,
        disabledPrimary,
        disabledOnPrimary,
        disabledPrimaryButton,
        disabledOnPrimaryButton,
        disabledPrimarySlider,
        primaryContainer,
        onPrimaryContainer,
        secondary,
        onSecondary,
        secondaryVariant,
        onSecondaryVariant,
        disabledSecondary,
        disabledOnSecondary,
        disabledSecondaryVariant,
        disabledOnSecondaryVariant,
        secondaryContainer,
        onSecondaryContainer,
        secondaryContainerVariant,
        onSecondaryContainerVariant,
        tertiaryContainer,
        onTertiaryContainer,
        tertiaryContainerVariant,
        background,
        onBackground,
        onBackgroundVariant,
        surface,
        onSurface,
        surfaceVariant,
        onSurfaceSecondary,
        onSurfaceVariantSummary,
        onSurfaceVariantActions,
        disabledOnSurface,
        outline,
        dividerLine,
        surfaceContainer,
        onSurfaceContainer,
        onSurfaceContainerVariant,
        surfaceContainerHigh,
        onSurfaceContainerHigh,
        surfaceContainerHighest,
        onSurfaceContainerHighest,
        windowDimming,
    )

fun darkColorScheme(
    primary: Color = Color(0xFF277AF7),
    onPrimary: Color = Color.White,
    primaryVariant: Color = Color(0xFF0073DD),
    onPrimaryVariant: Color = Color(0xFF99C7F1),
    disabledPrimary: Color = Color(0xFF253E64),
    disabledOnPrimary: Color = Color(0xFF677993),
    disabledPrimaryButton: Color = Color(0xFF253E64),
    disabledOnPrimaryButton: Color = Color(0xFF677893),
    disabledPrimarySlider: Color = Color(0xFF445D82),
    primaryContainer: Color = Color(0xFF338FE4),
    onPrimaryContainer: Color = Color.White,
    secondary: Color = Color(0xFF505050),
    onSecondary: Color = Color.White,
    secondaryVariant: Color = Color(0xFF434343),
    onSecondaryVariant: Color = Color(0xFFD9D9D9),
    disabledSecondary: Color = Color(0xFF404040),
    disabledOnSecondary: Color = Color(0xFF707070),
    disabledSecondaryVariant: Color = Color(0xFF404040),
    disabledOnSecondaryVariant: Color = Color(0xFF707170),
    secondaryContainer: Color = Color(0xFF434343),
    onSecondaryContainer: Color = Color(0xFF7C7C7C),
    secondaryContainerVariant: Color = Color(0xFF4F4F4F),
    onSecondaryContainerVariant: Color = Color(0xFF959595),
    tertiaryContainer: Color = Color(0xFF2B3B54),
    onTertiaryContainer: Color = Color(0xFF277AF7),
    tertiaryContainerVariant: Color = Color(0xFF505050),
    background: Color = Color.Black,
    onBackground: Color = Color(0xFFF2F2F2),
    onBackgroundVariant: Color = Color(0xFF787E96),
    surface: Color = Color(0xFF242424),
    onSurface: Color = Color(0xE6FFFFFF),
    surfaceVariant: Color = Color(0xFF242424),
    onSurfaceSecondary: Color = Color(0xCCFFFFFF),
    onSurfaceVariantSummary: Color = Color(0x80FFFFFF),
    onSurfaceVariantActions: Color = Color(0x66FFFFFF),
    disabledOnSurface: Color = Color(0xFF666666),
    outline: Color = Color(0xFF404040),
    dividerLine: Color = Color(0xFF393939),
    surfaceContainer: Color = Color(0xFF161616),
    onSurfaceContainer: Color = Color(0xFFD0D0D0),
    onSurfaceContainerVariant: Color = Color(0xFF737373),
    surfaceContainerHigh: Color = Color(0xFF242424),
    onSurfaceContainerHigh: Color = Color(0xFF666666),
    surfaceContainerHighest: Color = Color(0xFF2D2D2D),
    onSurfaceContainerHighest: Color = Color(0xFFE9E9E9),
    windowDimming: Color = Color.Black.copy(alpha = 0.6F),
): Colors =
    Colors(
        primary,
        onPrimary,
        primaryVariant,
        onPrimaryVariant,
        disabledPrimary,
        disabledOnPrimary,
        disabledPrimaryButton,
        disabledOnPrimaryButton,
        disabledPrimarySlider,
        primaryContainer,
        onPrimaryContainer,
        secondary,
        onSecondary,
        secondaryVariant,
        onSecondaryVariant,
        disabledSecondary,
        disabledOnSecondary,
        disabledSecondaryVariant,
        disabledOnSecondaryVariant,
        secondaryContainer,
        onSecondaryContainer,
        secondaryContainerVariant,
        onSecondaryContainerVariant,
        tertiaryContainer,
        onTertiaryContainer,
        tertiaryContainerVariant,
        background,
        onBackground,
        onBackgroundVariant,
        surface,
        onSurface,
        surfaceVariant,
        onSurfaceSecondary,
        onSurfaceVariantSummary,
        onSurfaceVariantActions,
        disabledOnSurface,
        outline,
        dividerLine,
        surfaceContainer,
        onSurfaceContainer,
        onSurfaceContainerVariant,
        surfaceContainerHigh,
        onSurfaceContainerHigh,
        surfaceContainerHighest,
        onSurfaceContainerHighest,
        windowDimming,
    )

internal fun Colors.updateColorsFrom(other: Colors) {
    primary = other.primary
    onPrimary = other.onPrimary
    primaryVariant = other.primaryVariant
    onPrimaryVariant = other.onPrimaryVariant
    disabledPrimary = other.disabledPrimary
    disabledOnPrimary = other.disabledOnPrimary
    disabledPrimaryButton = other.disabledPrimaryButton
    disabledOnPrimaryButton = other.disabledOnPrimaryButton
    disabledPrimarySlider = other.disabledPrimarySlider
    primaryContainer = other.primaryContainer
    onPrimaryContainer = other.onPrimaryContainer
    secondary = other.secondary
    onSecondary = other.onSecondary
    secondaryVariant = other.secondaryVariant
    onSecondaryVariant = other.onSecondaryVariant
    disabledSecondary = other.disabledSecondary
    disabledOnSecondary = other.disabledOnSecondary
    disabledSecondaryVariant = other.disabledSecondaryVariant
    disabledOnSecondaryVariant = other.disabledOnSecondaryVariant
    secondaryContainer = other.secondaryContainer
    onSecondaryContainer = other.onSecondaryContainer
    secondaryContainerVariant = other.secondaryContainerVariant
    onSecondaryContainerVariant = other.onSecondaryContainerVariant
    tertiaryContainer = other.tertiaryContainer
    onTertiaryContainer = other.onTertiaryContainer
    tertiaryContainerVariant = other.tertiaryContainerVariant
    background = other.background
    onBackground = other.onBackground
    onBackgroundVariant = other.onBackgroundVariant
    surface = other.surface
    onSurface = other.onSurface
    surfaceVariant = other.surfaceVariant
    onSurfaceSecondary = other.onSurfaceSecondary
    onSurfaceVariantSummary = other.onSurfaceVariantSummary
    onSurfaceVariantActions = other.onSurfaceVariantActions
    disabledOnSurface = other.disabledOnSurface
    outline = other.outline
    dividerLine = other.dividerLine
    surfaceContainer = other.surfaceContainer
    onSurfaceContainer = other.onSurfaceContainer
    onSurfaceContainerVariant = other.onSurfaceContainerVariant
    surfaceContainerHigh = other.surfaceContainerHigh
    onSurfaceContainerHigh = other.onSurfaceContainerHigh
    surfaceContainerHighest = other.surfaceContainerHighest
    onSurfaceContainerHighest = other.onSurfaceContainerHighest
    windowDimming = other.windowDimming
}

val LocalColors = staticCompositionLocalOf { lightColorScheme() }
