# BasicComponent

`BasicComponent` is a foundational standard component in Miuix. It provides customizable content areas on the left and right sides, along with a title and summary, making it suitable for building list items, settings items, and other UI elements.

This project builds upon it to provide some extended components, enabling developers to quickly create UI components that conform to design specifications. See the usage of [Extended Components](../components/#extended-components) for details.

<div style="position: relative; max-width: 700px; height: 350px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=basicComponent" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.BasicComponent
```

## Basic Usage

The BasicComponent can be used to display title and summary information:

```kotlin
BasicComponent(
    title = "Setting Item Title",
    summary = "This is the description text for the setting item"
)
```

## Component Variants

### Clickable Component

```kotlin
BasicComponent(
    title = "Wi-Fi",
    summary = "Connected to MIUI-WiFi",
    onClick = { /* Handle click event */ }
)
```

### Component with Left Icon

```kotlin
BasicComponent(
    title = "Nickname",
    summary = "A brief introduction",
    leftAction = {
        Icon(
            modifier = Modifier.padding(end = 16.dp),
            imageVector = MiuixIcons.Useful.Personal,
            contentDescription = "Avatar Icon",
            tint = MiuixTheme.colorScheme.onBackground
        )
    },
    onClick = { /* Handle click event */ }
)
```

### Component with Right Actions

```kotlin
var isFlightMode by remember { mutableStateOf(false) }

BasicComponent(
    title = "Flight Mode",
    rightActions = {
        Switch(
            checked = isFlightMode,
            onCheckedChange = { isFlightMode = it }
        )
    }
)
```

## Component States

### Disabled State

```kotlin
BasicComponent(
    title = "Mobile Network",
    summary = "SIM card not inserted",
    enabled = false
)
```

## Properties

### BasicComponent Properties

| Property Name     | Type                            | Description                                           | Default Value                            | Required |
| ----------------- | ------------------------------- | ----------------------------------------------------- | ---------------------------------------- | -------- |
| title             | String?                         | Title of the component                                | null                                     | No       |
| titleColor        | BasicComponentColors            | Title color configuration                             | BasicComponentDefaults.titleColor()      | No       |
| summary           | String?                         | Summary of the component                              | null                                     | No       |
| summaryColor      | BasicComponentColors            | Summary color configuration                           | BasicComponentDefaults.summaryColor()    | No       |
| leftAction        | @Composable (() -> Unit?)?      | Composable content on the left side of the component  | null                                     | No       |
| rightActions      | @Composable RowScope.() -> Unit | Composable content on the right side of the component | {}                                       | No       |
| modifier          | Modifier                        | Modifier for the component                            | Modifier                                 | No       |
| insideMargin      | PaddingValues                   | Internal padding of the component                     | BasicComponentDefaults.InsideMargin      | No       |
| onClick           | (() -> Unit)?                   | Callback triggered when the component is clicked      | null                                     | No       |
| holdDownState     | Boolean                         | Whether the component is in the pressed state         | false                                    | No       |
| enabled           | Boolean                         | Whether the component is enabled                      | true                                     | No       |
| interactionSource | MutableInteractionSource        | Interaction source of the component                   | remember \{ MutableInteractionSource() } | No       |

### BasicComponentDefaults Object

The BasicComponentDefaults object provides default values and color configurations for the BasicComponent.

#### Constants

| Constant Name | Type          | Description                       | Default Value        |
| ------------- | ------------- | --------------------------------- | -------------------- |
| InsideMargin  | PaddingValues | Internal padding of the component | PaddingValues(16.dp) |

#### Methods

| Method Name    | Type                 | Description                         |
| -------------- | -------------------- | ----------------------------------- |
| titleColor()   | BasicComponentColors | Creates title color configuration   |
| summaryColor() | BasicComponentColors | Creates summary color configuration |

### BasicComponentColors Class

Used to configure the color states of the component.

| Property Name | Type  | Description             |
| ------------- | ----- | ----------------------- |
| color         | Color | Color in normal state   |
| disabledColor | Color | Color in disabled state |

## Advanced Usage

### Complex Layout Component

```kotlin
BasicComponent(
    title = "Volume",
    summary = "Media Volume: 70%",
    leftAction = {
        Icon(
            modifier = Modifier.padding(end = 16.dp),
            imageVector = MiuixIcons.Useful.Play,
            contentDescription = "Volume Icon",
            tint = MiuixTheme.colorScheme.onBackground
        )
    },
    rightActions = {
        IconButton(onClick = { /* Decrease volume */ }) {
            Icon(
                imageVector = MiuixIcons.Useful.Remove,
                contentDescription = "Decrease Volume",
                tint = MiuixTheme.colorScheme.onBackground
            )
        }
        Text("70%")
        IconButton(onClick = { /* Increase volume */ }) {
            Icon(
                imageVector = MiuixIcons.Useful.New,
                contentDescription = "Increase Volume",
                tint = MiuixTheme.colorScheme.onBackground
            )
        }
    }
)
```

### Custom Style Component

```kotlin
BasicComponent(
    title = "Custom Component",
    summary = "Using custom colors",
    titleColor = BasicComponentColors(
        color = Color.Blue,
        disabledColor = Color.Gray
    ),
    summaryColor = BasicComponentColors(
        color = Color.DarkGray,
        disabledColor = Color.LightGray
    ),
    insideMargin = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
    onClick = { /* Handle option click */ }
)
```

### Usage in a List

```kotlin
val options = listOf("Option 1", "Option 2", "Option 3", "Option 4")

Column {
    options.forEach { option ->
        BasicComponent(
            title = option,
            onClick = { /* Handle option click */ }
        )
    }
}
```
