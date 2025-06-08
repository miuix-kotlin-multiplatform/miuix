# SuperSpinner

`SuperSpinner` is a dropdown selector component in Miuix that provides titles, summaries, and a list of options with icons and text. It supports click interaction and various display modes, commonly used in option settings with visual aids. This component is similar to `SuperDropdown` but offers richer functionality and interaction experience.

<div style="position: relative; max-width: 700px; height: 282px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=superSpinner" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

::: warning
`SuperSpinner` must be used within a `Scaffold` component!
:::

## Import

```kotlin
import top.yukonga.miuix.kmp.extra.SuperSpinner
import top.yukonga.miuix.kmp.extra.SpinnerEntry
import top.yukonga.miuix.kmp.extra.SpinnerMode
```

## Basic Usage

The SuperSpinner component provides basic dropdown selector functionality:

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf(
    SpinnerEntry(title = "Option 1"),
    SpinnerEntry(title = "Option 2"),
    SpinnerEntry(title = "Option 3"),
)

Scaffold {
    SuperSpinner(
        title = "Dropdown Selector",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it }
    )
}
```

## Options with Icons and Summaries

```kotlin
// Create a rounded rectangle Painter
class RoundedRectanglePainter(
    private val cornerRadius: Dp = 6.dp
) : Painter() {
    override val intrinsicSize = Size.Unspecified

    override fun DrawScope.onDraw() {
        drawRoundRect(
            color = Color.White,
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        )
    }
}

var selectedIndex by remember { mutableStateOf(0) }
val options = listOf(
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFFFF5B29)) },
        title = "Red Theme",
        summary = "Vibrant red"
    ),
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFF3482FF)) },
        title = "Blue Theme",
        summary = "Calm blue"
    ),
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFF36D167)) },
        title = "Green Theme",
        summary = "Fresh green"
    ),
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFFFFB21D)) }, 
        title = "Yellow Theme",
        summary = "Bright yellow"
    )
)

Scaffold {
    SuperSpinner(
        title = "Function Selection",
        summary = "Choose the action you want to perform",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it }
    )
}
```

## Component States

### Disabled State

```kotlin
SuperSpinner(
    title = "Disabled Selector",
    summary = "This selector is currently unavailable",
    items = listOf(SpinnerEntry(title = "Option 1")),
    selectedIndex = 0,
    onSelectedIndexChange = {},
    enabled = false
)
```

## Display Modes

SuperSpinner supports different display modes:

### Normal Mode (Adaptive to Click Position)

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf(
    SpinnerEntry(title = "Option 1"),
    SpinnerEntry(title = "Option 2"),
    SpinnerEntry(title = "Option 3")
)

Scaffold {
    SuperSpinner(
        title = "Normal Mode",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        mode = SpinnerMode.Normal // Default value
    )
}
```

### Always on Right Mode

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf(
    SpinnerEntry(title = "Option 1"),
    SpinnerEntry(title = "Option 2"),
    SpinnerEntry(title = "Option 3")
)

Scaffold {
    SuperSpinner(
        title = "Always on Right Mode",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        mode = SpinnerMode.AlwaysOnRight // Always on right mode
    )
}
```

### Display Dropdown Menu in Dialog

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf(
    SpinnerEntry(title = "Option 1"),
    SpinnerEntry(title = "Option 2"),
    SpinnerEntry(title = "Option 3")
)

Scaffold {
    SuperSpinner(
        title = "Dialog Mode",
        dialogButtonString = "Cancel",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it }
    )
}
```

## Properties

### SuperSpinner Properties (Dropdown Mode)

| Property Name         | Type                      | Description                 | Default Value                         | Required |
| --------------------- | ------------------------- | --------------------------- | ------------------------------------- | -------- |
| items                 | List\<SpinnerEntry>       | Options list                | -                                     | Yes      |
| selectedIndex         | Int                       | Current selected item index | -                                     | Yes      |
| title                 | String                    | Selector title              | -                                     | Yes      |
| titleColor            | BasicComponentColors      | Title text color config     | BasicComponentDefaults.titleColor()   | No       |
| summary               | String?                   | Selector description        | null                                  | No       |
| summaryColor          | BasicComponentColors      | Summary text color config   | BasicComponentDefaults.summaryColor() | No       |
| leftAction            | @Composable (() -> Unit)? | Custom left content         | null                                  | No       |
| mode                  | SpinnerMode               | Display mode                | SpinnerMode.Normal                    | No       |
| modifier              | Modifier                  | Component modifier          | Modifier                              | No       |
| insideMargin          | PaddingValues             | Internal content padding    | BasicComponentDefaults.InsideMargin   | No       |
| maxHeight             | Dp?                       | Maximum dropdown height     | null                                  | No       |
| enabled               | Boolean                   | Interactive state           | true                                  | No       |
| showValue             | Boolean                   | Show current selected value | true                                  | No       |
| onClick               | (() -> Unit)?             | Additional click callback   | null                                  | No       |
| onSelectedIndexChange | ((Int) -> Unit)?          | Selection change callback   | -                                     | Yes      |

### SuperSpinner Properties (Dialog Mode)

| Property Name         | Type                      | Description                 | Default Value                         | Required |
| --------------------- | ------------------------- | --------------------------- | ------------------------------------- | -------- |
| items                 | List\<SpinnerEntry>       | Options list                | -                                     | Yes      |
| selectedIndex         | Int                       | Current selected item index | -                                     | Yes      |
| title                 | String                    | Selector title              | -                                     | Yes      |
| titleColor            | BasicComponentColors      | Title text color config     | BasicComponentDefaults.titleColor()   | No       |
| summary               | String?                   | Selector description        | null                                  | No       |
| summaryColor          | BasicComponentColors      | Summary text color config   | BasicComponentDefaults.summaryColor() | No       |
| leftAction            | @Composable (() -> Unit)? | Custom left content         | null                                  | No       |
| dialogButtonString    | String                    | Dialog bottom button text   | -                                     | Yes      |
| popupModifier         | Modifier                  | Dialog popup modifier       | Modifier                              | No       |
| modifier              | Modifier                  | Component modifier          | Modifier                              | No       |
| insideMargin          | PaddingValues             | Internal content padding    | BasicComponentDefaults.InsideMargin   | No       |
| enabled               | Boolean                   | Interactive state           | true                                  | No       |
| showValue             | Boolean                   | Show current selected value | true                                  | No       |
| onClick               | (() -> Unit)?             | Additional click callback   | null                                  | No       |
| onSelectedIndexChange | ((Int) -> Unit)?          | Selection change callback   | -                                     | Yes      |

### SpinnerEntry Properties

| Property Name | Type                              | Description        |
| ------------- | --------------------------------- | ------------------ |
| icon          | @Composable ((Modifier) -> Unit)? | Option icon        |
| title         | String?                           | Option title       |
| summary       | String?                           | Option description |

## Advanced Usage

### Custom Left Content

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf(
    SpinnerEntry(title = "Red"),
    SpinnerEntry(title = "Green"),
    SpinnerEntry(title = "Blue")
)

Scaffold {
    SuperSpinner(
        title = "Custom Left Content",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        leftAction = {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(
                        when (selectedIndex) {
                            0 -> Color.Red
                            1 -> Color.Green
                            else -> Color.Blue
                        },
                        shape = CircleShape
                    )
            )
            Spacer(Modifier.width(8.dp))
        }
    )
}
```

### Limit Dropdown Menu Height

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = List(20) { SpinnerEntry(title = "Option ${it + 1}") }

Scaffold {
    SuperSpinner(
        title = "Limit Height",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        maxHeight = 300.dp // Limit dropdown menu maximum height to 300dp
    )
}
```

### Hide Selected Value Display

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf(
    SpinnerEntry(title = "Option 1"),
    SpinnerEntry(title = "Option 2"),
    SpinnerEntry(title = "Option 3")
)

Scaffold {
    SuperSpinner(
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
// Create a rounded rectangle Painter
class RoundedRectanglePainter(
    private val cornerRadius: Dp = 6.dp
) : Painter() {
    override val intrinsicSize = Size.Unspecified

    override fun DrawScope.onDraw() {
        drawRoundRect(
            color = Color.White,
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        )
    }
}

var showDialog = remember { mutableStateOf(false) }
var selectedIndex by remember { mutableStateOf(0) }
val colorOptions = listOf(
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFFFF5B29)) },
        title = "Red Theme",
        summary = "Vibrant red"
    ),
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFF3482FF)) },
        title = "Blue Theme",
        summary = "Calm blue"
    ),
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFF36D167)) },
        title = "Green Theme",
        summary = "Fresh green"
    ),
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFFFFB21D)) }, 
        title = "Yellow Theme",
        summary = "Bright yellow"
    )
)

Scaffold {
    SuperArrow(
        title = "Theme Color",
        onClick = { showDialog.value = true },
        holdDownState = showDialog.value
    )
    
    SuperDialog(
        title = "Theme Color Settings",
        show = showDialog,
        onDismissRequest = { showDialog.value = false } // Close dialog
    ) {
        Card {
            SuperSpinner(
                title = "Choose Theme Color",
                summary = "Select your preferred theme color",
                items = colorOptions,
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
                onClick = { showDialog.value = false }, // Close dialog
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(16.dp))
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
