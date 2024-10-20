package top.yukonga.miuix.kmp.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp


/**
 * The default text styles for the Miuix components.
 *
 * @param main The main text style.
 * @param title The title text style.
 * @param paragraph The paragraph text style.
 * @param body1 dialog_message_text_size
 * @param body2 preference_secondary_text_size, preference_right_text_size, spinner_text_size_integrated
 * @param headline1 preference_normal_text_size, edit_text_font_size
 * @param subtitle preference_category_text_size
 * @param title1 action_bar_tab_expand_text_size
 * @param title3 action_bar_tab_text_size
 * @param title4 dialog_title_text_size
 */
@Immutable
class TextStyles(
    val main: TextStyle,
    val title: TextStyle,
    val paragraph: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val button: TextStyle,
    val footnote1: TextStyle,
    val footnote2: TextStyle,
    val headline1: TextStyle,
    val headline2: TextStyle,
    val subtitle: TextStyle,
    val title1: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,
    val title4: TextStyle,
)

fun defaultTextStyles(
    main: TextStyle = DefaultTextStyle,
    title: TextStyle = TitleTextStyle,
    paragraph: TextStyle = ParagraphTextStyle,
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
    title4: TextStyle = Title4
): TextStyles = TextStyles(
    main = main,
    title = title,
    paragraph = paragraph,
    body1 = body1,
    body2 = body2,
    button = button,
    footnote1 = footnote1,
    footnote2 = footnote2,
    headline1 = headline1,
    headline2 = headline2,
    subtitle = subtitle,
    title1 = title1,
    title2 = title2,
    title3 = title3,
    title4 = title4,
)

private val DefaultTextStyle: TextStyle
    get() = TextStyle(
        fontSize = 17.sp,
    )

private val TitleTextStyle: TextStyle
    get() = TextStyle(
        fontSize = 12.sp
    )

private val ParagraphTextStyle: TextStyle
    get() = TextStyle(
        fontSize = 17.sp,
        lineHeight = 1.2f.em
    )

private val Body1: TextStyle
    get() = TextStyle(
        fontSize = 16.sp
    )

private val Body2: TextStyle
    get() = TextStyle(
        fontSize = 14.sp
    )

private val Button: TextStyle
    get() = TextStyle(
        fontSize = 17.sp
    )

private val Footnote1: TextStyle
    get() = TextStyle(
        fontSize = 13.sp
    )

private val Footnote2: TextStyle
    get() = TextStyle(
        fontSize = 11.sp
    )

private val Headline1: TextStyle
    get() = TextStyle(
        fontSize = 17.sp
    )

private val Headline2: TextStyle
    get() = TextStyle(
        fontSize = 16.sp
    )

private val Subtitle: TextStyle
    get() = TextStyle(
        fontSize = 14.sp
    )

private val Title1: TextStyle
    get() = TextStyle(
        fontSize = 32.sp
    )

private val Title2: TextStyle
    get() = TextStyle(
        fontSize = 24.sp
    )

private val Title3: TextStyle
    get() = TextStyle(
        fontSize = 20.sp
    )

private val Title4: TextStyle
    get() = TextStyle(
        fontSize = 18.sp
    )

val LocalTextStyles = staticCompositionLocalOf { defaultTextStyles() }