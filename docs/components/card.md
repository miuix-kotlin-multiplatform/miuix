# Card

`Card` is a basic container component in Miuix, used to hold related content and actions. It provides a card container with Miuix style, suitable for scenarios such as information display and content grouping. Supports both static display and interactive modes.

<div style="position: relative; max-width: 700px; height: 300px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=card" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.utils.PressFeedbackType // If using interactive card
```

## Basic Usage

The Card component can be used to wrap and organize content (static card):

```kotlin
Card {
    Text("This is card content")
}
```

## Properties

### Card Properties

| Property Name     | Type                               | Description                               | Default Value               | Required | Applies To  |
| ----------------- | ---------------------------------- | ----------------------------------------- | --------------------------- | -------- | ----------- |
| modifier          | Modifier                           | Modifier applied to the card              | Modifier                    | No       | All         |
| cornerRadius      | Dp                                 | Card corner radius                        | CardDefaults.CornerRadius   | No       | All         |
| insideMargin      | PaddingValues                      | Card inner padding                        | CardDefaults.InsideMargin   | No       | All         |
| color             | Color                              | Card background color                     | CardDefaults.DefaultColor() | No       | All         |
| pressFeedbackType | PressFeedbackType                  | Feedback type when pressed                | PressFeedbackType.None      | No       | Interactive |
| showIndication    | Boolean?                           | Show indication on interaction            | false                       | No       | Interactive |
| onClick           | (() -> Unit)?                      | Callback when clicked                     | null                        | No       | Interactive |
| onLongPress       | (() -> Unit)?                      | Callback when long pressed                | null                        | No       | Interactive |
| content           | @Composable ColumnScope.() -> Unit | Composable function for card content area | -                           | Yes      | All         |

::: warning
Some properties are only available when creating an interactive card!
:::

### CardDefaults Object

The CardDefaults object provides default values and color configurations for the card component.

#### Constants

| Constant Name | Type          | Description        | Default Value       |
| ------------- | ------------- | ------------------ | ------------------- |
| CornerRadius  | Dp            | Card corner radius | 16.dp               |
| InsideMargin  | PaddingValues | Card inner padding | PaddingValues(0.dp) |

#### Methods

| Method Name    | Type  | Description                               |
| -------------- | ----- | ----------------------------------------- |
| DefaultColor() | Color | The default background color for the card |

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
    pressFeedbackType = PressFeedbackType.Sink, // Set press feedback to sink effect
    showIndication = true, // Show indication on click
    onClick = {/* Handle click event */ },
    onLongPress = {/* Handle long press event */ }
) {
    Text("Interactive Card")
}
```
