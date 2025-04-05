# IconButton

`IconButton` is a button component in Miuix used for providing auxiliary interaction points. They are typically used in scenarios that require compact buttons, such as toolbars or image lists.

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.IconButton
```

## Basic Usage

The IconButton component can be used to trigger actions or events:

```kotlin
IconButton(
    onClick = { /* Handle click event */ }
) {
    Icon(
        imageVector = MiuixIcons.Useful.Like,
        contentDescription = "Like"
    )
}
```

## Component States

### Disabled State

```kotlin
IconButton(
    onClick = { /* Handle click event */ },
    enabled = false
) {
    Icon(
        imageVector = MiuixIcons.Useful.Like,
        contentDescription = "Like"
    )
}
```

### Hold Down State

IconButton supports controlling the hold down state through the `holdDownState` parameter, typically used for visual feedback when displaying pop-up dialogs:

```kotlin
var showDialog = remember { mutableStateOf(false) }

Scaffold {
    IconButton(
        onClick = { showDialog.value = true },
        holdDownState = showDialog.value
    ) {
        Icon(
            imageVector = MiuixIcons.Useful.Like,
            contentDescription = "Like"
        )
    }
    // Define dialog elsewhere
    SuperDialog(
        title = "Dialog",
        show = showDialog,
        onDismissRequest = { dismissDialog(showDialog) }
    ) {
        // Dialog content
    }
}
```

## Properties

### IconButton Properties

| Property Name   | Type                   | Description                        | Default Value                   | Required |
| --------------- | ---------------------- | ---------------------------------- | ------------------------------- | -------- |
| onClick         | () -> Unit             | Callback triggered on button click | -                               | Yes      |
| modifier        | Modifier               | Modifier applied to the button     | Modifier                        | No       |
| enabled         | Boolean                | Whether button is clickable        | true                            | No       |
| holdDownState   | Boolean                | Whether button is held down        | false                           | No       |
| backgroundColor | Color                  | Button background color            | Color.Unspecified               | No       |
| cornerRadius    | Dp                     | Button corner radius               | IconButtonDefaults.CornerRadius | No       |
| minHeight       | Dp                     | Button minimum height              | IconButtonDefaults.MinHeight    | No       |
| minWidth        | Dp                     | Button minimum width               | IconButtonDefaults.MinWidth     | No       |
| content         | @Composable () -> Unit | Button content, typically an Icon  | -                               | Yes      |

### IconButtonDefaults Object

The IconButtonDefaults object provides default values for the icon button component.

#### Constants

| Constant Name | Type | Description           | Default Value |
| ------------- | ---- | --------------------- | ------------- |
| MinWidth      | Dp   | Minimum button width  | 40.dp         |
| MinHeight     | Dp   | Minimum button height | 40.dp         |
| CornerRadius  | Dp   | Button corner radius  | 40.dp         |

## Advanced Usage

### Custom Background Color

```kotlin
IconButton(
    onClick = { /* Handle click event */ },
    backgroundColor = Color.LightGray.copy(alpha = 0.3f)
) {
    Icon(
        imageVector = MiuixIcons.Useful.Like,
        contentDescription = "Like"
    )
}
```

### Custom Size and Corner Radius

```kotlin
IconButton(
    onClick = { /* Handle click event */ },
    minWidth = 48.dp,
    minHeight = 48.dp,
    cornerRadius = 12.dp
) {
    Icon(
        imageVector = MiuixIcons.Useful.Like,
        contentDescription = "Like"
    )
}
```

### Using with Other Components

```kotlin
Surface {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { /* Handle click event */ }
        ) {
            Icon(
                imageVector = MiuixIcons.Useful.New,
                tint = MiuixTheme.colorScheme.onBackground,
                contentDescription = "Add"
            )
        }
        Text("Add New Item")
    }
}
```

### Dynamic Icon Button

```kotlin
var isLiked by remember { mutableStateOf(false) }

IconButton(
    onClick = { isLiked = !isLiked }
) {
    Icon(
        imageVector = if (isLiked) MiuixIcons.Useful.Like else MiuixIcons.Useful.Unlike,
        contentDescription = if (isLiked) "Liked" else "Like",
        tint = if (isLiked) Color.Red else MiuixTheme.colorScheme.onBackground
    )
}
```

