# ListPopup

`ListPopup` is a popup list component in Miuix used to display a popup menu with multiple options. It provides a lightweight, floating temporary list suitable for various dropdown menus, context menus, and similar scenarios.

<div style="position: relative; max-width: 700px; height: 250px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=listPopup" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

::: warning
`ListPopup` must be used within a `Scaffold` component!
:::

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.ListPopup
import top.yukonga.miuix.kmp.basic.ListPopupColumn
```

## Basic Usage

The ListPopup component can be used to create simple dropdown menus:

```kotlin
val showPopup = remember { mutableStateOf(false) }
var selectedIndex by remember { mutableStateOf(0) }
val items = listOf("Option 1", "Option 2", "Option 3")

Scaffold {
    Box {
        TextButton(
            text = "Click to show menu",
            alignment = PopupPositionProvider.Align.Left,
            onClick = { showPopup.value = true }
        )
        ListPopup(
            show = showPopup,
            onDismissRequest = { showPopup.value = false } // Close the popup menu
        ) {
            ListPopupColumn {
                items.forEachIndexed { index, string ->
                    DropdownImpl(
                        text = string,
                        optionSize = items.size,
                        isSelected = selectedIndex == index,
                        onSelectedIndexChange = {
                            selectedIndex = index
                            showPopup.value = false // Close the popup menu
                        },
                        index = index
                    )
                }
            }
        }
    }
}
```

## Component States

### Different Alignments

ListPopup can be set with different alignment options:

```kotlin
var showPopup = remember { mutableStateOf(false) }

ListPopup(
    show = showPopup,
    onDismissRequest = { showPopup.value = false } // Close the popup menu
    alignment = PopupPositionProvider.Align.Left
) {
    ListPopupColumn {
        // Custom content
    }
}
```

### Disable Window Dimming

```kotlin
var showPopup = remember { mutableStateOf(false) }

ListPopup(
    show = showPopup,
    onDismissRequest = { showPopup.value = false } // Close the popup menu
    enableWindowDim = false // Disable dimming layer
) {
    ListPopupColumn {
        // Custom content
    }
}
```

## Properties

### ListPopup Properties

| Property Name         | Type                        | Description                               | Default Value                              | Required |
| --------------------- | --------------------------- | ----------------------------------------- | ------------------------------------------ | -------- |
| show                  | MutableState\<Boolean>      | State object controlling popup visibility | -                                          | Yes      |
| popupModifier         | Modifier                    | Modifier applied to the popup list        | Modifier                                   | No       |
| popupPositionProvider | PopupPositionProvider       | Position provider for the popup           | ListPopupDefaults.DropdownPositionProvider | No       |
| alignment             | PopupPositionProvider.Align | Alignment of the popup list               | PopupPositionProvider.Align.Right          | No       |
| enableWindowDim       | Boolean                     | Whether to enable dimming layer           | true                                       | No       |
| shadowElevation       | Dp                          | Shadow elevation of the popup             | 11.dp                                      | No       |
| onDismissRequest      | (() -> Unit)?               | Callback when popup is dismissed          | null                                       | No       |
| maxHeight             | Dp?                         | Maximum height of the popup list          | null (auto-calculated)                     | No       |
| minWidth              | Dp                          | Minimum width of the popup list           | 200.dp                                     | No       |
| content               | @Composable () -> Unit      | Content of the popup list                 | -                                          | Yes      |

### ListPopupDefaults Object

The ListPopupDefaults object provides default settings for the ListPopup component.

#### Properties

| Property Name               | Type                  | Description                              |
| --------------------------- | --------------------- | ---------------------------------------- |
| DropdownPositionProvider    | PopupPositionProvider | Position provider for dropdown scenarios |
| ContextMenuPositionProvider | PopupPositionProvider | Position provider for context menus      |

### PopupPositionProvider Interface

The PopupPositionProvider interface defines methods for calculating popup list positions.

#### PopupPositionProvider.Align Options

| Option Name | Description                             |
| ----------- | --------------------------------------- |
| Left        | Align to the left of the window         |
| Right       | Align to the right of the window        |
| TopLeft     | Align to the top-left of the window     |
| TopRight    | Align to the top-right of the window    |
| BottomLeft  | Align to the bottom-left of the window  |
| BottomRight | Align to the bottom-right of the window |

## Advanced Usage

### Custom Content Layout

```kotlin
var showPopup = remember { mutableStateOf(false) }

Scaffold {
    Box {
        TextButton(
            text = "Click to show menu",
            onClick = { showPopup.value = true }
        )
        ListPopup(
            show = showPopup,
            minWidth = 250.dp,
            maxHeight = 300.dp,
            onDismissRequest = { showPopup.value = false } // Close the popup menu
            alignment = PopupPositionProvider.Align.BottomRight,
        ) {
            ListPopupColumn {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Custom Title",
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("This is a custom content popup menu. You can add various components as needed.")
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            text = "Confirm",
                            onClick = { showPopup.value = false } // Close the popup menu
                        )
                    }
                }
            }
        }
    }
}
```
