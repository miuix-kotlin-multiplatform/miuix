# SuperCheckbox

`SuperCheckbox` is a checkbox component in Miuix that provides a title, summary, and checkbox control. It supports click interactions and is commonly used in multi-select settings and selection lists.

<div style="position: relative; max-width: 700px; height: 293px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=superCheckbox" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.extra.SuperCheckbox
import top.yukonga.miuix.kmp.extra.CheckboxLocation
```

## Basic Usage

SuperCheckbox component provides basic checkbox functionality:

```kotlin
var isChecked by remember { mutableStateOf(false) }

SuperCheckbox(
    title = "Checkbox Option",
    checked = isChecked,
    onCheckedChange = { isChecked = it }
)
```

## Checkbox with Summary

```kotlin
var notificationsEnabled by remember { mutableStateOf(false) }

SuperCheckbox(
    title = "Notifications",
    summary = "Receive push notifications from the app",
    checked = notificationsEnabled,
    onCheckedChange = { notificationsEnabled = it }
)
```

## Component States

### Disabled State

```kotlin
SuperCheckbox(
    title = "Disabled Checkbox",
    summary = "This checkbox is currently unavailable",
    checked = true,
    onCheckedChange = {},
    enabled = false
)
```

## Checkbox Position

SuperCheckbox supports placing the checkbox on the left or right side:

### Left Checkbox (Default)

```kotlin
var leftChecked by remember { mutableStateOf(false) }

SuperCheckbox(
    title = "Left Checkbox",
    summary = "Checkbox is on the left side (default)",
    checked = leftChecked,
    onCheckedChange = { leftChecked = it },
    checkboxLocation = CheckboxLocation.Left // Default value
)
```

### Right Checkbox

```kotlin
var rightChecked by remember { mutableStateOf(false) }

SuperCheckbox(
    title = "Right Checkbox",
    summary = "Checkbox is on the right side",
    checked = rightChecked,
    onCheckedChange = { rightChecked = it },
    checkboxLocation = CheckboxLocation.Right
)
```

## Properties

### SuperCheckbox Properties

| Property Name    | Type                            | Description                          | Default Value                         | Required |
| ---------------- | ------------------------------- | ------------------------------------ | ------------------------------------- | -------- |
| title            | String                          | Title of the checkbox item           | -                                     | Yes      |
| checked          | Boolean                         | Checkbox checked state               | -                                     | Yes      |
| onCheckedChange  | ((Boolean) -> Unit)?            | Callback when checkbox state changes | -                                     | Yes      |
| modifier         | Modifier                        | Modifier applied to component        | Modifier                              | No       |
| titleColor       | BasicComponentColors            | Title text color configuration       | BasicComponentDefaults.titleColor()   | No       |
| summary          | String?                         | Summary description                  | null                                  | No       |
| summaryColor     | BasicComponentColors            | Summary text color configuration     | BasicComponentDefaults.summaryColor() | No       |
| checkboxColors   | CheckboxColors                  | Checkbox control color configuration | CheckboxDefaults.checkboxColors()     | No       |
| rightActions     | @Composable RowScope.() -> Unit | Custom content before checkbox       | {}                                    | No       |
| checkboxLocation | CheckboxLocation                | Checkbox position                    | CheckboxLocation.Left                 | No       |
| onClick          | (() -> Unit)?                   | Additional callback on item click    | null                                  | No       |
| insideMargin     | PaddingValues                   | Internal content padding             | BasicComponentDefaults.InsideMargin   | No       |
| enabled          | Boolean                         | Whether component is interactive     | true                                  | No       |

## Advanced Usage

### With Right Actions

```kotlin
var backupEnabled by remember { mutableStateOf(false) }

SuperCheckbox(
    title = "Auto Backup",
    summary = "Periodically backup your data",
    checked = backupEnabled,
    onCheckedChange = { backupEnabled = it },
    rightActions = {
        Text(
            text = if (backupEnabled) "Enabled" else "Disabled",
            color = MiuixTheme.colorScheme.onSurfaceVariantActions,
            modifier = Modifier.padding(end = 6.dp)
        )
    }
)
```

### Nested Checkboxes

```kotlin
var allSelected by remember { mutableStateOf(false) }
var option1 by remember { mutableStateOf(false) }
var option2 by remember { mutableStateOf(false) }
var option3 by remember { mutableStateOf(false) }

Column {
    SuperCheckbox(
        title = "Select All",
        checked = allSelected,
        onCheckedChange = { newState ->
            allSelected = newState
            option1 = newState
            option2 = newState
            option3 = newState
        }
    )
    SuperCheckbox(
        title = "Option 1",
        checked = option1,
        onCheckedChange = { 
            option1 = it 
            allSelected = option1 && option2 && option3
        },
        modifier = Modifier.padding(start = 24.dp)
    )
    SuperCheckbox(
        title = "Option 2",
        checked = option2,
        onCheckedChange = { 
            option2 = it 
            allSelected = option1 && option2 && option3
        },
        modifier = Modifier.padding(start = 24.dp)
    )
    SuperCheckbox(
        title = "Option 3",
        checked = option3,
        onCheckedChange = { 
            option3 = it 
            allSelected = option1 && option2 && option3
        },
        modifier = Modifier.padding(start = 24.dp)
    )
}
```

### Custom Colors

```kotlin
var customChecked by remember { mutableStateOf(false) }

SuperCheckbox(
    title = "Custom Colors",
    titleColor = BasicComponentDefaults.titleColor(
        color = MiuixTheme.colorScheme.primary
    ),
    summary = "Checkbox with custom colors",
    summaryColor = BasicComponentDefaults.summaryColor(
        color = MiuixTheme.colorScheme.secondary
    ),
    checked = customChecked,
    onCheckedChange = { customChecked = it },
    checkboxColors = CheckboxDefaults.checkboxColors(
        checkedForegroundColor = Color.Red,
        checkedBackgroundColor = MiuixTheme.colorScheme.secondary
    )
)
```

### Using with Dialog

```kotlin
var showDialog = remember { mutableStateOf(false) }
var privacyOption by remember { mutableStateOf(false) }
var analyticsOption by remember { mutableStateOf(false) }

Scaffold {
    SuperArrow(
        title = "Privacy Settings",
        onClick = { showDialog.value = true },
        holdDownState = showDialog.value
    )
    
    SuperDialog(
        title = "Privacy Settings",
        show = showDialog,
        onDismissRequest = { showDialog.value = false } // Close dialog
    ) {
        Card {
            SuperCheckbox(
                title = "Privacy Policy",
                summary = "Agree to the privacy policy terms",
                checked = privacyOption,
                onCheckedChange = { privacyOption = it }
            )
            
            SuperCheckbox(
                title = "Analytics Data",
                summary = "Allow collection of anonymous usage data to improve services",
                checked = analyticsOption,
                onCheckedChange = { analyticsOption = it }
            )
        }
        
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            TextButton(
                text = "Cancel",
                onClick = { showDialog.value = false }, // Close dialog
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(16.dp))
            TextButton(
                text = "Confirm",
                onClick = { showDialog.value = false }, // Close dialog
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColorsPrimary() // Use theme colors
            )
        }
    }
}
```
