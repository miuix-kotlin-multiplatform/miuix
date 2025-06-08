# SuperDropdown

`SuperDropdown` is a dropdown menu component in Miuix that provides a title, summary, and a list of dropdown options. It supports click interaction and is commonly used in option settings and list selections.

<div style="position: relative; max-width: 700px; height: 285px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=superDropdown" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

::: warning
`SuperDropdown` must be used within a `Scaffold` component!
:::

## Import

```kotlin
import top.yukonga.miuix.kmp.extra.SuperDropdown
import top.yukonga.miuix.kmp.extra.DropDownMode
```

## Basic Usage

The SuperDropdown component provides basic dropdown menu functionality:

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf("Option 1", "Option 2", "Option 3")

Scaffold {
    SuperDropdown(
        title = "Dropdown Menu",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it }
    )
}
```

## Dropdown with Summary

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf("中文", "English", "日本語")

Scaffold {
    SuperDropdown(
        title = "Language Settings",
        summary = "Choose your preferred language",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it }
    )
}
```

## Component States

### Disabled State

```kotlin
SuperDropdown(
    title = "Disabled Dropdown",
    summary = "This dropdown menu is currently unavailable",
    items = listOf("Option 1"),
    selectedIndex = 0,
    onSelectedIndexChange = {},
    enabled = false
)
```

## Dropdown Position

SuperDropdown supports different dropdown position modes:

### Normal Mode (Auto-adaptive based on click position)

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf("Option 1", "Option 2", "Option 3")

Scaffold {
    SuperDropdown(
        title = "Normal Mode",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        mode = DropDownMode.Normal // Default value
    )
}
```

### Always on Right Mode

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf("Option 1", "Option 2", "Option 3")

Scaffold {
    SuperDropdown(
        title = "Always on Right Mode",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        mode = DropDownMode.AlwaysOnRight
    )
}
```

## Properties

### SuperDropdown Properties

| Property Name         | Type                 | Description                       | Default Value                         | Required |
| --------------------- | -------------------- | --------------------------------- | ------------------------------------- | -------- |
| items                 | List\<String>        | List of dropdown options          | -                                     | Yes      |
| selectedIndex         | Int                  | Index of currently selected item  | -                                     | Yes      |
| title                 | String               | Title of the dropdown menu        | -                                     | Yes      |
| titleColor            | BasicComponentColors | Title text color configuration    | BasicComponentDefaults.titleColor()   | No       |
| summary               | String?              | Summary description of dropdown   | null                                  | No       |
| summaryColor          | BasicComponentColors | Summary text color configuration  | BasicComponentDefaults.summaryColor() | No       |
| dropdownColors        | DropdownColors       | Color configuration for dropdown  | DropdownDefaults.dropdownColors()     | No       |
| mode                  | DropDownMode         | Display mode of dropdown menu     | DropDownMode.Normal                   | No       |
| modifier              | Modifier             | Modifier applied to the component | Modifier                              | No       |
| insideMargin          | PaddingValues        | Internal content padding          | BasicComponentDefaults.InsideMargin   | No       |
| maxHeight             | Dp?                  | Maximum height of dropdown menu   | null                                  | No       |
| enabled               | Boolean              | Whether component is interactive  | true                                  | No       |
| showValue             | Boolean              | Whether to show selected value    | true                                  | No       |
| onClick               | (() -> Unit)?        | Additional callback on click      | null                                  | No       |
| onSelectedIndexChange | ((Int) -> Unit)?     | Callback when selection changes   | -                                     | Yes      |

### DropdownColors Properties

| Property Name          | Type  | Description                    |
| ---------------------- | ----- | ------------------------------ |
| contentColor           | Color | Option text color              |
| containerColor         | Color | Option background color        |
| selectedContentColor   | Color | Selected item text color       |
| selectedContainerColor | Color | Selected item background color |

## Advanced Usage

### Custom Colors

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf("Red", "Green", "Blue")

Scaffold {
    SuperDropdown(
        title = "Custom Colors",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        dropdownColors = DropdownDefaults.dropdownColors(
            selectedContentColor = Color.Red,
            selectedContainerColor = Color.Red.copy(alpha = 0.2f)
        )
    )
}
```

### Limited Dropdown Height

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = List(20) { "Option ${it + 1}" }

Scaffold {
    SuperDropdown(
        title = "Limited Height",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        maxHeight = 200.dp // Limit dropdown menu maximum height to 200dp
    )
}
```

### Hide Selected Value Display

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf("Option 1", "Option 2", "Option 3")

Scaffold {
    SuperDropdown(
        title = "Hide Selected Value",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        showValue = false // Hide selected value display
    )
}
```

### Use with Dialog

```kotlin
var showDialog = remember { mutableStateOf(false) }
var selectedIndex by remember { mutableStateOf(0) }
val themes = listOf("Light Mode", "Dark Mode", "System Default")

Scaffold {
    SuperArrow(
        title = "Theme Settings",
        onClick = { showDialog.value = true },
        holdDownState = showDialog.value
    )
    
    SuperDialog(
        title = "Theme Settings",
        show = showDialog,
        onDismissRequest = { showDialog.value = false }
    ) {
        Card {
            SuperDropdown(
                title = "Theme Selection",
                summary = "Choose your preferred theme appearance",
                items = themes,
                selectedIndex = selectedIndex,
                onSelectedIndexChange = { selectedIndex = it }
            )
        }
        
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            TextButton(
                text = "Cancel",
                onClick = { showDialog.value = false },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(16.dp))
            TextButton(
                text = "Confirm",
                onClick = { showDialog.value = false },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColorsPrimary()
            )
        }
    }
}
```
