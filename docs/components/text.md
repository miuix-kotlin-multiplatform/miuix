# Text

`Text` component is a basic text component in Miuix, used to display text content. It supports customizing various text styles, alignment, and decoration effects.

<div style="position: relative; max-width: 700px; height: 300px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=text" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.Text
```

## Basic Usage

The simplest text display:

```kotlin
Text("This is a basic text")
```

## Text Styles

Miuix provides multiple predefined text styles:

```kotlin
Text(
    text = "Title Text",
    style = MiuixTheme.textStyles.headline1
)

Text(
    text = "Subtitle Text",
    style = MiuixTheme.textStyles.subtitle
)
Text(
    text = "Summary Text",
    style = MiuixTheme.textStyles.body2
)

Text(
    text = "Main Text",
    style = MiuixTheme.textStyles.main
)

```

## Text Color

```kotlin
Text(
    text = "Default Color Text",
    color = MiuixTheme.colorScheme.onBackground
)

Text(
    text = "Primary Color Text",
    color = MiuixTheme.colorScheme.primary
)

Text(
    text = "Secondary Text",
    color = MiuixTheme.colorScheme.onSurfaceContainerVariant
)
```

## Properties

| Property Name  | Type                           | Description                                   | Default Value                       | Required |
| -------------- | ------------------------------ | --------------------------------------------- | ----------------------------------- | -------- |
| text           | String / AnnotatedString       | The text content to display                   | -                                   | Yes      |
| modifier       | Modifier                       | Modifiers applied to the text                 | Modifier                            | No       |
| color          | Color                          | Text color                                    | MiuixTheme.colorScheme.onBackground | No       |
| fontSize       | TextUnit                       | Text size                                     | TextUnit.Unspecified                | No       |
| fontStyle      | FontStyle?                     | Text font style (e.g., italic)                | null                                | No       |
| fontWeight     | FontWeight?                    | Text font weight                              | null                                | No       |
| fontFamily     | FontFamily?                    | Text font family                              | null                                | No       |
| letterSpacing  | TextUnit                       | Letter spacing                                | TextUnit.Unspecified                | No       |
| textDecoration | TextDecoration?                | Text decoration (e.g., underline)             | null                                | No       |
| textAlign      | TextAlign?                     | Text alignment                                | null                                | No       |
| lineHeight     | TextUnit                       | Line height                                   | TextUnit.Unspecified                | No       |
| overflow       | TextOverflow                   | Text overflow handling                        | TextOverflow.Clip                   | No       |
| softWrap       | Boolean                        | Whether to wrap text automatically            | true                                | No       |
| maxLines       | Int                            | Maximum number of lines                       | Int.MAX_VALUE                       | No       |
| minLines       | Int                            | Minimum number of lines                       | 1                                   | No       |
| onTextLayout   | (TextLayoutResult) -> Unit     | Callback after text layout is completed       | null                                | No       |
| style          | TextStyle                      | Text style                                    | MiuixTheme.textStyles.main          | No       |
| inlineContent  | Map<String, InlineTextContent> | Mapping for inserting inline composable items | mapOf()                             | No       |

## Advanced Usage

### Multi-line Text Truncation

```kotlin
Text(
    text = "This is a very long text that will be truncated and show ellipsis when there is not enough space. This is useful for displaying long content summaries.",
    maxLines = 2,
    overflow = TextOverflow.Ellipsis
)
```

### Text Decoration

```kotlin
Text(
    text = "Underlined Text",
    textDecoration = TextDecoration.Underline
)

Text(
    text = "Strikethrough Text",
    textDecoration = TextDecoration.LineThrough
)
```

### Rich Text Mixing

```kotlin
Text(
    buildAnnotatedString {
        withStyle(style = SpanStyle(color = MiuixTheme.colorScheme.primary)) {
            append("Miuix ")
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("UI Library")
        }
        append(", this is a piece of rich text")
    }
)
```
