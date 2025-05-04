# Card

`Card` is a basic container component in Miuix, used to hold related content and actions. It provides a card container with Miuix style, suitable for scenarios such as information display and content grouping.

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.Card
```

## Basic Usage

The Card component can be used to wrap and organize content:

```kotlin
Card {
    Text("This is card content")
}
```

## Properties

### Card Properties

| Property Name | Type                               | Description              | Default Value               | Required |
| ------------- | ---------------------------------- | ------------------------ | --------------------------- | -------- |
| modifier      | Modifier                           | Modifier applied to card | Modifier                    | No       |
| cornerRadius  | Dp                                 | Card corner radius       | CardDefaults.CornerRadius   | No       |
| insideMargin  | PaddingValues                      | Card inner padding       | CardDefaults.InsideMargin   | No       |
| color         | Color                              | Card background color    | CardDefaults.DefaultColor() | No       |
| pressFeedbackType | PressFeedbackType                | The type of feedback when the card is pressed | PressFeedbackType.None | No       |
| showIndication | Boolean?                          | Whether to show indication on interaction | false | No       |
| onClick       | (() -> Unit)?                    | Callback invoked when the card is clicked | null | No       |
| onLongPress   | (() -> Unit)?                    | Callback invoked when the card is long pressed | null | No       |
| content       | @Composable ColumnScope.() -> Unit | Composable function for card content area | - | Yes |

### CardDefaults Object

The CardDefaults object provides default values and color configurations for the card component.

#### Constants

| Constant Name | Type          | Description        | Default Value         |
| ------------- | ------------- | ------------------ | --------------------- |
| CornerRadius  | Dp            | Card corner radius | 16.dp                 |
| InsideMargin  | PaddingValues | Card inner padding | PaddingValues(0.dp)   |

#### Methods

| Method Name    | Type  | Description               |
| -------------- | ----- | ------------------------- |
| DefaultColor() | Color | Creates the default color for the card |

## Advanced Usage

### Custom Style Card

```kotlin
Card(
    cornerRadius = 8.dp,
    insideMargin = PaddingValues(16.dp),
    color = Color.LightGray.copy(alpha = 0.5f)
) {
    Text("Custom Style Card")
}
```

### Content-Rich Card

```kotlin
Card(
    modifier = Modifier.padding(16.dp),
    insideMargin = PaddingValues(16.dp)
) {
    Text(
        text = "Card Title",
        style = MiuixTheme.textStyles.title2
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "This is a detailed description of the card, which can contain multiple lines of text."
    )
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            text = "Cancel",
            onClick = { /* Handle cancel event */ }
        )
        Spacer(modifier = Modifier.width(8.dp))
        TextButton(
            text = "Confirm",
            colors = ButtonDefaults.textButtonColorsPrimary(), // Use theme colors
            onClick = { /* Handle confirm event */ }
        )
    }
}
```

### Cards in a List

```kotlin
LazyColumn {
    items(5) { index ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            insideMargin = PaddingValues(16.dp)
        ) {
            Text("List Item ${index + 1}")
        }
    }
}
```

### Interactive Card

```kotlin
Card(
    modifier = Modifier.padding(16.dp),
    pressFeedbackType = PressFeedbackType.Sink,
    showIndication = true,
    onClick = { /* Handle click event */ },
    onLongPress = { /* Handle long press event */ }
) {
    Text("Interactive Card")
}
```

In this example:
- `pressFeedbackType = PressFeedbackType.Sink` adds a sink animation when pressing the card.
- `showIndication = true` enables visual indication during interactions.
- `onClick` and `onLongPress` define the respective callbacks.