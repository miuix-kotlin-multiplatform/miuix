# TextField

`TextField` is a basic input component in Miuix for receiving text input from users. The component provides rich customization options, supporting label animations, leading and trailing icons, and other features.

<div style="position: relative; max-width: 700px; height: 340px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=textField" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.TextField
```

## Basic Usage

The TextField component can be used to get user input:

```kotlin
var text by remember { mutableStateOf("") }

TextField(
    value = text,
    onValueChange = { text = it },
    label = "Username"
)
```

## Input Types

### TextField with Label

The label automatically moves to the top when the input field gains focus or has content:

```kotlin
var text by remember { mutableStateOf("") }

TextField(
    value = text,
    onValueChange = { text = it },
    label = "Email Address"
)
```

### Using Label as Placeholder

```kotlin
var text by remember { mutableStateOf("") }

TextField(
    value = text,
    onValueChange = { text = it },
    label = "Please enter content",
    useLabelAsPlaceholder = true
)
```

## Component States

### Disabled State

```kotlin
var text by remember { mutableStateOf("") }
TextField(
    value = text,
    onValueChange = { text = it },
    label = "Disabled Input Field",
    enabled = false
)
```

### Read-Only State

```kotlin
var text by remember { mutableStateOf("This is read-only content") }

TextField(
    value = text,
    onValueChange = { text = it },
    label = "Read-Only Input Field",
    readOnly = true
)
```

## Properties

### TextField Properties

| Property Name         | Type                                         | Description                         | Default Value                                    | Required |
| --------------------- | -------------------------------------------- | ----------------------------------- | ------------------------------------------------ | -------- |
| value                 | String or TextFieldValue                     | Text value of the input field       | -                                                | Yes      |
| onValueChange         | (String) -> Unit or (TextFieldValue) -> Unit | Callback when text changes          | -                                                | Yes      |
| modifier              | Modifier                                     | Modifier applied to the input field | Modifier                                         | No       |
| insideMargin          | DpSize                                       | Internal padding of input field     | DpSize(16.dp, 16.dp)                             | No       |
| backgroundColor       | Color                                        | Background color                    | MiuixTheme.colorScheme.secondaryContainer        | No       |
| cornerRadius          | Dp                                           | Corner radius                       | 16.dp                                            | No       |
| label                 | String                                       | Label text                          | ""                                               | No       |
| labelColor            | Color                                        | Label text color                    | MiuixTheme.colorScheme.onSecondaryContainer      | No       |
| borderColor           | Color                                        | Border color when focused           | MiuixTheme.colorScheme.primary                   | No       |
| useLabelAsPlaceholder | Boolean                                      | Use label as placeholder            | false                                            | No       |
| enabled               | Boolean                                      | Whether input field is enabled      | true                                             | No       |
| readOnly              | Boolean                                      | Whether input field is read-only    | false                                            | No       |
| textStyle             | TextStyle                                    | Text style                          | MiuixTheme.textStyles.main                       | No       |
| keyboardOptions       | KeyboardOptions                              | Keyboard options                    | KeyboardOptions.Default                          | No       |
| keyboardActions       | KeyboardActions                              | Keyboard actions                    | KeyboardActions.Default                          | No       |
| leadingIcon           | @Composable (() -> Unit)?                    | Leading icon                        | null                                             | No       |
| trailingIcon          | @Composable (() -> Unit)?                    | Trailing icon                       | null                                             | No       |
| singleLine            | Boolean                                      | Single line input                   | false                                            | No       |
| maxLines              | Int                                          | Maximum lines                       | If singleLine is true then 1, else Int.MAX_VALUE | No       |
| minLines              | Int                                          | Minimum lines                       | 1                                                | No       |
| visualTransformation  | VisualTransformation                         | Visual transformation               | VisualTransformation.None                        | No       |
| onTextLayout          | (TextLayoutResult) -> Unit                   | Text layout callback                | {}                                               | No       |
| interactionSource     | MutableInteractionSource?                    | Interaction source                  | null                                             | No       |
| cursorBrush           | Brush                                        | Cursor brush                        | SolidColor(MiuixTheme.colorScheme.primary)       | No       |

## Advanced Usage

### TextField with Icons

```kotlin
var text by remember { mutableStateOf("") }

TextField(
    value = text,
    onValueChange = { text = it },
    label = "Search",
    leadingIcon = {
        Icon(
            imageVector = MiuixIcons.Useful.Search,
            contentDescription = "Search Icon",
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
)
```

### Password Input Field

```kotlin
var password by remember { mutableStateOf("") }
var passwordVisible by remember { mutableStateOf(false) }

TextField(
    value = password,
    onValueChange = { password = it },
    label = "Password",
    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    trailingIcon = {
        IconButton(
            onClick = { passwordVisible = !passwordVisible },
            modifier = Modifier.padding(end = 12.dp)
        ) {
            Icon(
                imageVector = MiuixIcons.Useful.Rename,
                tint = if (passwordVisible) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.onSecondaryContainer,
                contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
            )
        }
    }
)
```

### Input Field with Validation

```kotlin
var email by remember { mutableStateOf("") }
var isError by remember { mutableStateOf(false) }
var errorColor = Color.Red.copy(0.3f)
val emailPattern = remember { Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") }

Column {
    TextField(
        value = email,
        onValueChange = {
            email = it
            isError = email.isNotEmpty() && !emailPattern.matches(email)
        },
        label = "Email",
        labelColor = if (isError) errorColor else MiuixTheme.colorScheme.onSecondaryContainer,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
    if (isError) {
        Text(
            text = "Please enter a valid email address",
            color = errorColor,
                style = MiuixTheme.textStyles.body2,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}
```

### Custom Styles

```kotlin
var text by remember { mutableStateOf("") }

TextField(
    value = text,
    onValueChange = { text = it },
    label = "Custom Input Field",
    cornerRadius = 8.dp,
    backgroundColor = MiuixTheme.colorScheme.primary.copy(alpha = 0.1f),
    textStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = MiuixTheme.colorScheme.primary
    )
)
```

### Using TextFieldValue

When you need more fine-grained control over text selection and cursor position:

```kotlin
var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

TextField(
    value = textFieldValue,
    onValueChange = { textFieldValue = it },
    label = "Advanced Input Control",
    // TextFieldValue provides control over text, selection range, and cursor position
)
```
