# FloatingActionButton

`FloatingActionButton` is a floating button component in Miuix, typically used to display the most important or frequently used actions on a page. It usually floats above the interface with a prominent visual effect for quick user access.

This component is typically used in conjunction with the `Scaffold` component to maintain consistent layout and behavior across different pages in the application.

<div style="position: relative; max-width: 700px; height: 300px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=floatingActionButton" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.FloatingActionButton
```

## Basic Usage

The FloatingActionButton component can be used to trigger important actions:

```kotlin
FloatingActionButton(
    onClick = { /* Handle click event */ }
) {
    Icon(
        imageVector = MiuixIcons.Useful.New,
        contentDescription = "Add"
    )
}
```

## Component States

Since this component is typically used for the most common actions, it does not have built-in state variations.

## Properties

### FloatingActionButton Properties

| Property Name              | Type                   | Description                         | Default Value                  | Required |
| -------------------------- | ---------------------- | ----------------------------------- | ------------------------------ | -------- |
| onClick                    | () -> Unit             | Callback triggered when clicked     | -                              | Yes      |
| modifier                   | Modifier               | Modifier applied to the button      | Modifier                       | No       |
| shape                      | Shape                  | Shape of the button                 | CircleShape                    | No       |
| containerColor             | Color                  | Background color of the button      | MiuixTheme.colorScheme.primary | No       |
| shadowElevation            | Dp                     | Shadow elevation of the button      | 4.dp                           | No       |
| minWidth                   | Dp                     | Minimum width of the button         | 60.dp                          | No       |
| minHeight                  | Dp                     | Minimum height of the button        | 60.dp                          | No       |
| defaultWindowInsetsPadding | Boolean                | Apply default window insets padding | true                           | No       |
| content                    | @Composable () -> Unit | Composable content of the button    | -                              | Yes      |

## Advanced Usage

### Custom Color

```kotlin
FloatingActionButton(
    onClick = { /* Handle click event */ },
    containerColor = Color.Red
) {
    Icon(
        imageVector = MiuixIcons.Useful.Like,
        contentDescription = "Like",
        tint = Color.White
    )
}
```

### Extended Floating Action Button

```kotlin
FloatingActionButton(
    onClick = { /* Handle click event */ },
    shape = RoundedCornerShape(16.dp),
    minWidth = 120.dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Icon(
            imageVector = MiuixIcons.Useful.New,
            contentDescription = "Add",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Add", color = Color.White)
    }
}
```

### Floating Action Button with Scaffold

```kotlin
Scaffold(
    floatingActionButton = {
        FloatingActionButton(
            onClick = { /* Handle click event */ }
        ) {
            Icon(
                imageVector = MiuixIcons.Useful.Add,
                contentDescription = "Add"
            )
        }
    },
    floatingActionButtonPosition = FabPosition.End
) { paddingValues ->
    // Content area needs to consider padding
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        // ...
    }
}
```

### Animated Floating Action Button

```kotlin
var expanded by remember { mutableStateOf(false) }
val animatedSize by animateDpAsState(
    targetValue = if (expanded) 65.dp else 60.dp,
    label = "FAB size animation",
)

FloatingActionButton(
    onClick = { expanded = !expanded },
    minWidth = animatedSize,
    minHeight = animatedSize
) {
    Icon(
        imageVector = if (expanded) MiuixIcons.Useful.Remove else MiuixIcons.Useful.New,
        contentDescription = if (expanded) "Remove" else "Add",
        tint = Color.White
    )
}
```
