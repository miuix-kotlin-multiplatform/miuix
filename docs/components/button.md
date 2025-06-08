# Button

`Button` is a basic interactive component in Miuix, used to trigger actions or events. It provides multiple style options, including primary buttons, secondary buttons, and text buttons.

<div style="position: relative; max-width: 700px; height: 200px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=button" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.Button
```

## Basic Usage

The Button component can be used to trigger actions or events:

```kotlin
Button(
    onClick = { /* Handle click event */ }
) {
    Text("Button")
}
```

## Button Types

Miuix provides various types of buttons suitable for different scenarios and levels of importance:

### Primary Button

```kotlin
Button(
    onClick = { /* Handle click event */ },
    colors = ButtonDefaults.buttonColorsPrimary()
) {
    Text("Primary Button")
}
```

### Secondary Button

```kotlin
Button(
    onClick = { /* Handle click event */ },
    colors = ButtonDefaults.buttonColors()
) {
    Text("Secondary Button")
}
```

### Text Button

```kotlin
TextButton(
    text = "Text Button",
    onClick = { /* Handle click event */ }
)
```

## Component States

### Disabled State

```kotlin
Button(
    onClick = { /* Handle click event */ },
    enabled = false
) {
    Text("Disabled Button")
}
```

## Properties

### Button Properties

| Property Name | Type                            | Description                            | Default Value                 | Required |
| ------------- | ------------------------------- | -------------------------------------- | ----------------------------- | -------- |
| onClick       | () -> Unit                      | Callback triggered on click            | -                             | Yes      |
| modifier      | Modifier                        | Modifier applied to the button         | Modifier                      | No       |
| enabled       | Boolean                         | Whether the button is clickable        | true                          | No       |
| cornerRadius  | Dp                              | Corner radius of the button            | ButtonDefaults.CornerRadius   | No       |
| minWidth      | Dp                              | Minimum width of the button            | ButtonDefaults.MinWidth       | No       |
| minHeight     | Dp                              | Minimum height of the button           | ButtonDefaults.MinHeight      | No       |
| colors        | ButtonColors                    | Button color configuration             | ButtonDefaults.buttonColors() | No       |
| insideMargin  | PaddingValues                   | Internal padding of the button         | ButtonDefaults.InsideMargin   | No       |
| content       | @Composable RowScope.() -> Unit | Composable function for button content | -                             | Yes      |

### TextButton Properties

| Property Name | Type             | Description                     | Default Value                     | Required |
| ------------- | ---------------- | ------------------------------- | --------------------------------- | -------- |
| text          | String           | Text displayed on the button    | -                                 | Yes      |
| onClick       | () -> Unit       | Callback triggered on click     | -                                 | Yes      |
| modifier      | Modifier         | Modifier applied to the button  | Modifier                          | No       |
| enabled       | Boolean          | Whether the button is clickable | true                              | No       |
| colors        | TextButtonColors | Text button color configuration | ButtonDefaults.textButtonColors() | No       |
| cornerRadius  | Dp               | Corner radius of the button     | ButtonDefaults.CornerRadius       | No       |
| minWidth      | Dp               | Minimum width of the button     | ButtonDefaults.MinWidth           | No       |
| minHeight     | Dp               | Minimum height of the button    | ButtonDefaults.MinHeight          | No       |
| insideMargin  | PaddingValues    | Internal padding of the button  | ButtonDefaults.InsideMargin       | No       |

### ButtonDefaults Object

The ButtonDefaults object provides default values and color configurations for button components.

#### Constants

| Constant Name | Type          | Description                    | Default Value        |
| ------------- | ------------- | ------------------------------ | -------------------- |
| MinWidth      | Dp            | Minimum width of the button    | 58.dp                |
| MinHeight     | Dp            | Minimum height of the button   | 40.dp                |
| CornerRadius  | Dp            | Corner radius of the button    | 16.dp                |
| InsideMargin  | PaddingValues | Internal padding of the button | PaddingValues(16.dp) |

#### Methods

| Method Name               | Type             | Description                                            |
| ------------------------- | ---------------- | ------------------------------------------------------ |
| buttonColors()            | ButtonColors     | Creates color configuration for secondary buttons      |
| buttonColorsPrimary()     | ButtonColors     | Creates color configuration for primary buttons        |
| textButtonColors()        | TextButtonColors | Creates color configuration for secondary text buttons |
| textButtonColorsPrimary() | TextButtonColors | Creates color configuration for primary text buttons   |

## Advanced Usage

### Button with Icon

```kotlin
Button(
    onClick = { /* Handle click event */ }
) {
    Icon(
        imageVector = MiuixIcons.Useful.Like,
        contentDescription = "Icon"
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text("Button with Icon")
}
```

### Custom Style Button

```kotlin
Button(
    onClick = { /* Handle click event */ },
    colors = ButtonDefaults.buttonColors(
        color = Color.Red.copy(alpha = 0.7f)
    ),
    cornerRadius = 8.dp
) {
    Text("Custom Button")
}
```

### Loading State Button

```kotlin
var isLoading by remember { mutableStateOf(false) }
val scope = rememberCoroutineScope()

Button(
    onClick = {
        isLoading = true
        // Simulate operation
        scope.launch {
            delay(2000)
            isLoading = false
        }
    },
    enabled = !isLoading
) {
     AnimatedVisibility(
        visible = isLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(end = 8.dp),
            size = 20.dp,
            strokeWidth = 4.dp
        )
    }
    Text("Submit")
}
```
