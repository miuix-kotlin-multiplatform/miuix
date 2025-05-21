// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

/**
 * The default text styles for the Miuix components.
 *
 * @param main The main text style.
 * @param paragraph The paragraph text style.
 * @param body1 The body1 text style.
 * @param body2 The body2 text style.
 * @param button The button text style.
 * @param footnote1 The footnote1 text style.
 * @param footnote2 The footnote2 text style.
 * @param headline1 The headline1 text style.
 * @param headline2 The headline2 text style.
 * @param subtitle The subtitle text style.
 * @param title1 The title1 text style.
 * @param title2 The title2 text style.
 * @param title3 The title3 text style.
 * @param title4 The title4 text style.
 */
@Immutable
class TextStyles(
    main: TextStyle,
    paragraph: TextStyle,
    body1: TextStyle,
    body2: TextStyle,
    button: TextStyle,
    footnote1: TextStyle,
    footnote2: TextStyle,
    headline1: TextStyle,
    headline2: TextStyle,
    subtitle: TextStyle,
    title1: TextStyle,
    title2: TextStyle,
    title3: TextStyle,
    title4: TextStyle,
) {
    var main by mutableStateOf(main, structuralEqualityPolicy())
        internal set
    var paragraph by mutableStateOf(paragraph, structuralEqualityPolicy())
        internal set
    var body1 by mutableStateOf(body1, structuralEqualityPolicy())
        internal set
    var body2 by mutableStateOf(body2, structuralEqualityPolicy())
        internal set
    var button by mutableStateOf(button, structuralEqualityPolicy())
        internal set
    var footnote1 by mutableStateOf(footnote1, structuralEqualityPolicy())
        internal set
    var footnote2 by mutableStateOf(footnote2, structuralEqualityPolicy())
        internal set
    var headline1 by mutableStateOf(headline1, structuralEqualityPolicy())
        internal set
    var headline2 by mutableStateOf(headline2, structuralEqualityPolicy())
        internal set
    var subtitle by mutableStateOf(subtitle, structuralEqualityPolicy())
        internal set
    var title1 by mutableStateOf(title1, structuralEqualityPolicy())
        internal set
    var title2 by mutableStateOf(title2, structuralEqualityPolicy())
        internal set
    var title3 by mutableStateOf(title3, structuralEqualityPolicy())
        internal set
    var title4 by mutableStateOf(title4, structuralEqualityPolicy())
        internal set

    fun copy(
        main: TextStyle = Main,
        paragraph: TextStyle = Paragraph,
        body1: TextStyle = Body1,
        body2: TextStyle = Body2,
        button: TextStyle = Button,
        footnote1: TextStyle = Footnote1,
        footnote2: TextStyle = Footnote2,
        headline1: TextStyle = Headline1,
        headline2: TextStyle = Headline2,
        subtitle: TextStyle = Subtitle,
        title1: TextStyle = Title1,
        title2: TextStyle = Title2,
        title3: TextStyle = Title3,
        title4: TextStyle = Title4,
    ): TextStyles =
        TextStyles(
            main,
            paragraph,
            body1,
            body2,
            button,
            footnote1,
            footnote2,
            headline1,
            headline2,
            subtitle,
            title1,
            title2,
            title3,
            title4,
        )
}

fun defaultTextStyles(
    main: TextStyle = Main,
    paragraph: TextStyle = Paragraph,
    body1: TextStyle = Body1,
    body2: TextStyle = Body2,
    button: TextStyle = Button,
    footnote1: TextStyle = Footnote1,
    footnote2: TextStyle = Footnote2,
    headline1: TextStyle = Headline1,
    headline2: TextStyle = Headline2,
    subtitle: TextStyle = Subtitle,
    title1: TextStyle = Title1,
    title2: TextStyle = Title2,
    title3: TextStyle = Title3,
    title4: TextStyle = Title4,
): TextStyles =
    TextStyles(
        main,
        paragraph,
        body1,
        body2,
        button,
        footnote1,
        footnote2,
        headline1,
        headline2,
        subtitle,
        title1,
        title2,
        title3,
        title4,
    )

private val Main: TextStyle
    get() =
        TextStyle(
            fontSize = 17.sp,
        )

private val Paragraph: TextStyle
    get() =
        TextStyle(
            fontSize = 17.sp,
            lineHeight = 1.2f.em,
        )

private val Body1: TextStyle
    get() =
        TextStyle(
            fontSize = 16.sp,
        )

private val Body2: TextStyle
    get() =
        TextStyle(
            fontSize = 14.sp,
        )

private val Button: TextStyle
    get() =
        TextStyle(
            fontSize = 17.sp,
        )

private val Footnote1: TextStyle
    get() =
        TextStyle(
            fontSize = 13.sp,
        )

private val Footnote2: TextStyle
    get() =
        TextStyle(
            fontSize = 11.sp,
        )

private val Headline1: TextStyle
    get() =
        TextStyle(
            fontSize = 17.sp,
        )

private val Headline2: TextStyle
    get() =
        TextStyle(
            fontSize = 16.sp,
        )

private val Subtitle: TextStyle
    get() =
        TextStyle(
            fontSize = 14.sp,
        )

private val Title1: TextStyle
    get() =
        TextStyle(
            fontSize = 32.sp,
        )

private val Title2: TextStyle
    get() =
        TextStyle(
            fontSize = 24.sp,
        )

private val Title3: TextStyle
    get() =
        TextStyle(
            fontSize = 20.sp,
        )

private val Title4: TextStyle
    get() =
        TextStyle(
            fontSize = 18.sp,
        )

internal fun TextStyles.updateColorsFrom(other: Colors) {
    main = main.copy(color = other.onBackground)
    paragraph = paragraph.copy(color = other.onBackground)
    body1 = body1.copy(color = other.onBackground)
    body2 = body2.copy(color = other.onBackground)
    button = button.copy(color = other.onBackground)
    footnote1 = footnote1.copy(color = other.onBackground)
    footnote2 = footnote2.copy(color = other.onBackground)
    headline1 = headline1.copy(color = other.onBackground)
    headline2 = headline2.copy(color = other.onBackground)
    subtitle = subtitle.copy(color = other.onBackground)
    title1 = title1.copy(color = other.onBackground)
    title2 = title2.copy(color = other.onBackground)
    title3 = title3.copy(color = other.onBackground)
    title4 = title4.copy(color = other.onBackground)
}

val LocalTextStyles = staticCompositionLocalOf { defaultTextStyles() }
