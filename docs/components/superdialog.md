# SuperDialog

`SuperDialog` is a dialog component in Miuix used to display important information, collect user input, or confirm user actions. The dialog appears above the current interface and supports custom styles and content layouts.

<div style="position: relative; max-width: 700px; height: 210px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=superDialog" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

::: warning
`SuperDialog` must be used within a `Scaffold` component!
:::

## Import

```kotlin
import top.yukonga.miuix.kmp.extra.SuperDialog
```

## Basic Usage

SuperDialog component provides basic dialog functionality:

```kotlin
var showDialog = remember { mutableStateOf(false) }

Scaffold {
    TextButton(
        text = "Show Dialog",
        onClick = { showDialog.value = true }
    )

    SuperDialog(
        title = "Dialog Title",
        summary = "This is a basic dialog example that can contain various content.",
        show = showDialog,
        onDismissRequest = { showDialog.value = false } // Close dialog
    ) {
        TextButton(
            text = "Confirm",
            onClick = { showDialog.value = false }, // Close dialog
            modifier = Modifier.fillMaxWidth()
        )
    }
}
```

## Properties

### SuperDialog Properties

| Property Name              | Type                   | Description                                    | Default Value                         | Required |
| -------------------------- | ---------------------- | ---------------------------------------------- | ------------------------------------- | -------- |
| show                       | MutableState\<Boolean> | State object to control dialog visibility      | -                                     | Yes      |
| modifier                   | Modifier               | Modifier applied to the dialog                 | Modifier                              | No       |
| title                      | String?                | Dialog title                                   | null                                  | No       |
| titleColor                 | Color                  | Title text color                               | SuperDialogDefaults.titleColor()      | No       |
| summary                    | String?                | Dialog summary text                            | null                                  | No       |
| summaryColor               | Color                  | Summary text color                             | SuperDialogDefaults.summaryColor()    | No       |
| backgroundColor            | Color                  | Dialog background color                        | SuperDialogDefaults.backgroundColor() | No       |
| enableWindowDim            | Boolean                | Whether to enable dimming layer                | true                                  | No       |
| onDismissRequest           | (() -> Unit)?          | Callback when dialog is closed                 | null                                  | No       |
| outsideMargin              | DpSize                 | Dialog external margin                         | SuperDialogDefaults.outsideMargin     | No       |
| insideMargin               | DpSize                 | Dialog internal content margin                 | SuperDialogDefaults.insideMargin      | No       |
| defaultWindowInsetsPadding | Boolean                | Whether to apply default window insets padding | true                                  | No       |
| content                    | @Composable () -> Unit | Dialog content                                 | -                                     | Yes      |

### SuperDialogDefaults Object

The SuperDialogDefaults object provides default settings for the SuperDialog component.

#### Properties

| Property Name | Type   | Description                    |
| ------------- | ------ | ------------------------------ |
| outsideMargin | DpSize | Default dialog external margin |
| insideMargin  | DpSize | Default dialog internal margin |

#### Functions

| Function Name     | Return Type | Description                         |
| ----------------- | ----------- | ----------------------------------- |
| titleColor()      | Color       | Get default title color             |
| summaryColor()    | Color       | Get default summary color           |
| backgroundColor() | Color       | Get default dialog background color |

## Advanced Usage

### Custom Styled Dialog

```kotlin
var showDialog = remember { mutableStateOf(false) }

Scaffold {
    TextButton(
        text = "Show Custom Styled Dialog",
        onClick = { showDialog.value = true }
    )

    SuperDialog(
        title = "Custom Style",
        summary = "This dialog uses custom colors and margins",
        show = showDialog,
        onDismissRequest = { showDialog.value = false }, // Close dialog
        titleColor = Color.Blue,
        summaryColor = Color.Gray,
        backgroundColor = Color(0xFFF5F5F5),
        outsideMargin = DpSize(20.dp, 20.dp),
        insideMargin = DpSize(30.dp, 30.dp)
    ) {
        Text(
            text = "Custom Content Area",
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        TextButton(
            text = "Close",
            onClick = { showDialog.value = false }, // Close dialog
            modifier = Modifier.fillMaxWidth()
        )
    }
}
```

### Creating a Confirmation Dialog

```kotlin
var showConfirmDialog = remember { mutableStateOf(false) }
var result by remember { mutableStateOf("") }

Scaffold {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TextButton(
            text = "Show Confirmation Dialog",
            onClick = { showConfirmDialog.value = true }
        )
        
        Text("Result: $result")
    }
    
    SuperDialog(
        title = "Confirm Action",
        summary = "This action is irreversible, do you want to proceed?",
        show = showConfirmDialog,
        onDismissRequest = { showConfirmDialog.value = false } // Close dialog
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                text = "Cancel",
                onClick = { 
                    result = "User cancelled the action"
                    showConfirmDialog.value = false // Close dialog
                },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(20.dp))
            TextButton(
                text = "Confirm",
                onClick = { 
                    result = "User confirmed the action"
                    showConfirmDialog.value = false // Close dialog 
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColorsPrimary()
            )
        }
    }
}
```

### Dialog with Input Field

```kotlin
var showDialog = remember { mutableStateOf(false) }
var textFieldValue by remember { mutableStateOf("") }

Scaffold {
    TextButton(
        text = "Show Input Dialog",
        onClick = { showDialog.value = true }
    )

    SuperDialog(
        title = "Please Enter Content",
        show = showDialog,
        onDismissRequest = { showDialog.value = false } // Close dialog
    ) {
        TextField(
            modifier = Modifier.padding(bottom = 16.dp),
            value = textFieldValue,
            maxLines = 1,
            onValueChange = { textFieldValue = it }
        )
        
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                text = "Cancel",
                onClick = { showDialog.value = false }, // Close dialog
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(20.dp))
            TextButton(
                text = "Confirm",
                onClick = { showDialog.value = false }, // Close dialog
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColorsPrimary() // Use theme color
            )
        }
    }
}
```

### Dialog with Form

```kotlin
var showDialog = remember { mutableStateOf(false) }
var dropdownSelectedOption by remember { mutableStateOf(0) }
var switchState by remember { mutableStateOf(false) }
val dropdownOptions = listOf("Option 1", "Option 2")

Scaffold {
    TextButton(
        text = "Show Form Dialog",
        onClick = { showDialog.value = true }
    )

    SuperDialog(
        title = "Form Dialog",
        show = showDialog,
        onDismissRequest = { showDialog.value = false } // Close dialog
    ) {
        Card(
            color = MiuixTheme.colorScheme.secondaryContainer,
        ) {
            SuperDropdown(
                title = "Dropdown Selection",
                items = dropdownOptions,
                selectedIndex = dropdownSelectedOption,
                onSelectedIndexChange = { dropdownSelectedOption = it }
            )
            
            SuperSwitch(
                title = "Switch Option",
                checked = switchState,
                onCheckedChange = { switchState = it }
            )
        }
        
        Spacer(Modifier.height(12.dp))
        
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                text = "Cancel",
                onClick = { showDialog.value = false }, // Close dialog
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(20.dp))
            TextButton(
                text = "Confirm",
                onClick = { showDialog.value = false }, // Close dialog
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColorsPrimary() // Use theme color
            )
        }
    }
}
```

### Dialog with Color Picker

```kotlin
var showColorDialog = remember { mutableStateOf(false) }
var selectedColor by remember { mutableStateOf(Color.Red) }

Scaffold {
    TextButton(
        text = "Select Color",
        onClick = { showColorDialog.value = true }
    )
    
    SuperDialog(
        title = "Select Color",
        show = showColorDialog,
        onDismissRequest = { showColorDialog.value = false } // Close dialog
    ) {
        Column {
            ColorPicker(
                initialColor = selectedColor,
                onColorChanged = { selectedColor = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                TextButton(
                    modifier = Modifier.weight(1f),
                    text = "Cancel",
                    onClick = { showColorDialog.value = false } // Close dialog
                )
                TextButton(
                    modifier = Modifier.weight(1f),
                    text = "Confirm",
                    colors = ButtonDefaults.textButtonColorsPrimary(), // Use theme color
                    onClick = {
                        showColorDialog.value = false // Close dialog
                        // Handle confirm logic
                    }
                )
            }
        }
    }
}
```
