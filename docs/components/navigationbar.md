# NavigationBar

`NavigationBar` is a bottom navigation bar component in Miuix, used to create navigation menus at the bottom of applications. It supports 2 to 5 navigation items, each containing an icon and a text label.

This component is typically used in conjunction with the `Scaffold` component to maintain consistent layout and behavior across different pages in the application.

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationItem
```

## Basic Usage

The NavigationBar component can be used to create bottom navigation menus:

```kotlin
val items = listOf(
    NavigationItem("Home", MiuixIcons.Useful.NavigatorSwitch),
    NavigationItem("Profile", MiuixIcons.Useful.Personal),
    NavigationItem("Settings", MiuixIcons.Useful.Settings)
)
var selectedIndex by remember { mutableStateOf(0) }

NavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it }
)
```

## Component States

### Selected State

NavigationBar automatically handles the visual style of the selected item, displaying it with bold text and highlighting.

## Properties

### NavigationBar Properties

| Property Name              | Type                  | Description                         | Default Value                           | Required |
| -------------------------- | --------------------- | ----------------------------------- | --------------------------------------- | -------- |
| items                      | List\<NavigationItem> | List of navigation items            | -                                       | Yes      |
| selected                   | Int                   | Index of the current selected item  | -                                       | Yes      |
| onClick                    | (Int) -> Unit         | Callback when clicking nav items    | -                                       | Yes      |
| modifier                   | Modifier              | Modifier applied to the nav bar     | Modifier                                | No       |
| color                      | Color                 | Background color of the nav bar     | MiuixTheme.colorScheme.surfaceContainer | No       |
| showDivider                | Boolean               | Show top divider or not             | true                                    | No       |
| defaultWindowInsetsPadding | Boolean               | Apply default window insets padding | true                                    | No       |

### NavigationItem Properties

| Property Name | Type        | Description             | Default Value | Required |
| ------------- | ----------- | ----------------------- | ------------- | -------- |
| label         | String      | Text label of nav item  | -             | Yes      |
| icon          | ImageVector | Icon of navigation item | -             | Yes      |

### Constants

| Constant Name       | Type | Description               | Value                          | Required |
| ------------------- | ---- | ------------------------- | ------------------------------ | -------- |
| NavigationBarHeight | Dp   | Default height of nav bar | 64.dp on non-iOS, 48.dp on iOS | Yes      |

## Advanced Usage

### Custom Colors

```kotlin
val items = listOf(
    NavigationItem("Home", MiuixIcons.Useful.NavigatorSwitch),
    NavigationItem("Profile", MiuixIcons.Useful.Personal),
    NavigationItem("Settings", MiuixIcons.Useful.Settings)
)
var selectedIndex by remember { mutableStateOf(0) }

NavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it },
    color = Color.Red.copy(alpha = 0.3f)
)
```

### Without Divider

```kotlin
val items = listOf(
    NavigationItem("Home", MiuixIcons.Useful.NavigatorSwitch),
    NavigationItem("Profile", MiuixIcons.Useful.Personal),
    NavigationItem("Settings", MiuixIcons.Useful.Settings)
)
var selectedIndex by remember { mutableStateOf(0) }

NavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it },
    showDivider = false
)
```

### Handling Window Insets

```kotlin
NavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it },
    defaultWindowInsetsPadding = false // Handle window insets padding manually
)
```

### Using with Page Navigation (Using Scaffold)

```kotlin
val pages = listOf("Home", "Profile", "Settings")
val items = listOf(
    NavigationItem("Home", MiuixIcons.Useful.NavigatorSwitch),
    NavigationItem("Profile", MiuixIcons.Useful.Personal),
    NavigationItem("Settings", MiuixIcons.Useful.Settings)
)
var selectedIndex by remember { mutableStateOf(0) }

Scaffold(
    bottomBar = {
        NavigationBar(
            items = items,
            selected = selectedIndex,
            onClick = { selectedIndex = it }
        )
    }
) { paddingValues ->
    // Content area needs to consider padding
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Current Page: ${pages[selectedIndex]}",
            style = MiuixTheme.textStyles.title1
        )
    }
}
```
