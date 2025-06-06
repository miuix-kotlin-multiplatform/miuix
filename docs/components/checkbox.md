# CheckBox

`CheckBox` is a basic selection component in Miuix, used for toggling between checked and unchecked states. It provides an interactive selection control with animation effects, suitable for multiple selection scenarios and enabling/disabling configuration items.

<div style="position: relative; max-width: 700px; height: 100px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=checkbox" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.Checkbox
```

## Basic Usage

The CheckBox component can be used to create selectable options:

```kotlin
var checked by remember { mutableStateOf(false) }

Checkbox(
    checked = checked,
    onCheckedChange = { checked = it }
)
```

## Component States

### Disabled State

```kotlin
var checked by remember { mutableStateOf(false) }

Checkbox(
    checked = checked,
    onCheckedChange = { checked = it },
    enabled = false
)
```

## Properties

### Checkbox Properties

| Property Name   | Type                 | Description                       | Default Value                     | Required |
| --------------- | -------------------- | --------------------------------- | --------------------------------- | -------- |
| checked         | Boolean              | Whether checkbox is checked       | -                                 | Yes      |
| onCheckedChange | ((Boolean) -> Unit)? | Callback when check state changes | -                                 | Yes      |
| modifier        | Modifier             | Modifier applied to the checkbox  | Modifier                          | No       |
| colors          | CheckboxColors       | Color configuration for checkbox  | CheckboxDefaults.checkboxColors() | No       |
| enabled         | Boolean              | Whether checkbox is interactive   | true                              | No       |

### CheckboxDefaults Object

The CheckboxDefaults object provides default color configurations for the Checkbox component.

#### Methods

| Method Name      | Type           | Description                               |
| ---------------- | -------------- | ----------------------------------------- |
| checkboxColors() | CheckboxColors | Creates default color config for checkbox |

### CheckboxColors Class

| Property Name                    | Type  | Description                                  |
| -------------------------------- | ----- | -------------------------------------------- |
| checkedForegroundColor           | Color | Foreground color when checked (checkmark)    |
| uncheckedForegroundColor         | Color | Foreground color when unchecked              |
| disabledCheckedForegroundColor   | Color | Foreground color when disabled and checked   |
| disabledUncheckedForegroundColor | Color | Foreground color when disabled and unchecked |
| checkedBackgroundColor           | Color | Background color when checked                |
| uncheckedBackgroundColor         | Color | Background color when unchecked              |
| disabledCheckedBackgroundColor   | Color | Background color when disabled and checked   |
| disabledUncheckedBackgroundColor | Color | Background color when disabled and unchecked |

## Advanced Usage

### Custom Colors

```kotlin
var checked by remember { mutableStateOf(false) }

Checkbox(
    checked = checked,
    onCheckedChange = { checked = it },
    colors = CheckboxDefaults.checkboxColors(
        checkedBackgroundColor = Color.Red,
        checkedForegroundColor = Color.White
    )
)
```

### Using with Text

```kotlin
var checked by remember { mutableStateOf(false) }

Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.padding(16.dp)
) {
    Checkbox(
        checked = checked,
        onCheckedChange = { checked = it }
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(text = "Accept Terms and Privacy Policy")
}
```

### Using in Lists

```kotlin
val options = listOf("Option A", "Option B", "Option C")
val checkedStates = remember { mutableStateListOf(false, true, false) }

LazyColumn {
    items(options.size) { index ->
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedStates[index],
                onCheckedChange = { checkedStates[index] = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = options[index])
        }
    }
}
```

### Clickable Row in List

```kotlin
data class Option(val text: String, var isSelected: Boolean)
val options = remember {
    mutableStateListOf(
        Option("Option 1", false),
        Option("Option 2", true),
        Option("Option 3", false)
    )
}

LazyColumn {
    items(options.size) { index ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    options[index] = options[index].copy(
                        isSelected = !options[index].isSelected
                    )
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = options[index].isSelected,
                onCheckedChange = { selected ->
                    options[index] = options[index].copy(isSelected = selected)
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = options[index].text)
        }
    }
}
