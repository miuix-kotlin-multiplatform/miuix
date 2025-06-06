# NavigationBar

`NavigationBar` is a bottom navigation bar component in Miuix, used to create navigation menus fixed at the bottom of applications. It supports 2 to 5 navigation items, each containing an icon and a text label.

`FloatingNavigationBar` is a floating-style bottom navigation bar component, also supporting 2 to 5 navigation items, offering different display modes (icon only, text only, icon and text).

These components are typically used in conjunction with the `Scaffold` component to maintain consistent layout and behavior across different pages in the application.

<div style="position: relative; max-width: 700px; height: 300px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=navigationBar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.FloatingNavigationBar
import top.yukonga.miuix.kmp.basic.FloatingNavigationBarMode
import top.yukonga.miuix.kmp.basic.NavigationItem
```

## Basic Usage

### NavigationBar

The NavigationBar component can be used to create bottom navigation menus fixed to the bottom:

```kotlin
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
)
```

### FloatingNavigationBar

The FloatingNavigationBar component can be used to create floating navigation menus at the bottom:

```kotlin
val items = listOf(
    NavigationItem("Home", MiuixIcons.Useful.NavigatorSwitch),
    NavigationItem("Profile", MiuixIcons.Useful.Personal),
    NavigationItem("Settings", MiuixIcons.Useful.Settings)
)
var selectedIndex by remember { mutableStateOf(0) }

Scaffold(
    bottomBar = {
        FloatingNavigationBar(
            items = items,
            selected = selectedIndex,
            onClick = { selectedIndex = it },
            mode = FloatingNavigationBarMode.IconOnly // Show icons only
        )
    }
)
```

## Component States

### Selected State

Both `NavigationBar` and `FloatingNavigationBar` automatically handle the visual style of the selected item, displaying it with bold text and highlighting the icon/text.

## Properties

### NavigationBar Properties

| Property Name              | Type                  | Description                         | Default Value                           | Required |
| -------------------------- | --------------------- | ----------------------------------- | --------------------------------------- | -------- |
| items                      | List\<NavigationItem> | List of navigation items (2-5)      | -                                       | Yes      |
| selected                   | Int                   | Index of the current selected item  | -                                       | Yes      |
| onClick                    | (Int) -> Unit         | Callback when clicking nav items    | -                                       | Yes      |
| modifier                   | Modifier              | Modifier applied to the nav bar     | Modifier                                | No       |
| color                      | Color                 | Background color of the nav bar     | MiuixTheme.colorScheme.surfaceContainer | No       |
| showDivider                | Boolean               | Show top divider line or not        | true                                    | No       |
| defaultWindowInsetsPadding | Boolean               | Apply default window insets padding | true                                    | No       |

### FloatingNavigationBar Properties

| Property Name              | Type                      | Description                             | Default Value                           | Required |
| -------------------------- | ------------------------- | --------------------------------------- | --------------------------------------- | -------- |
| items                      | List\<NavigationItem>     | List of navigation items (2-5)          | -                                       | Yes      |
| selected                   | Int                       | Index of the current selected item      | -                                       | Yes      |
| onClick                    | (Int) -> Unit             | Callback when clicking nav items        | -                                       | Yes      |
| modifier                   | Modifier                  | Modifier applied to the nav bar         | Modifier                                | No       |
| color                      | Color                     | Background color of the nav bar         | MiuixTheme.colorScheme.surfaceContainer | No       |
| cornerRadius               | Dp                        | Corner radius of the nav bar            | FloatingToolbarDefaults.CornerRadius    | No       |
| horizontalAlignment        | Alignment.Horizontal      | Horizontal alignment within its parent  | CenterHorizontally                      | No       |
| horizontalOutSidePadding   | Dp                        | Horizontal padding outside the nav bar  | 36.dp                                   | No       |
| shadowElevation            | Dp                        | The shadow elevation of the nav bar     | 1.dp                                    | No       |
| showDivider                | Boolean                   | Show divider line around the nav bar    | false                                   | No       |
| defaultWindowInsetsPadding | Boolean                   | Apply default window insets padding     | true                                    | No       |
| mode                       | FloatingNavigationBarMode | Display mode for items (icon/text/both) | FloatingNavigationBarMode.IconOnly      | No       |

### NavigationItem Properties

| Property Name | Type        | Description             | Default Value | Required |
| ------------- | ----------- | ----------------------- | ------------- | -------- |
| label         | String      | Text label of nav item  | -             | Yes      |
| icon          | ImageVector | Icon of navigation item | -             | Yes      |

### FloatingNavigationBarMode Enum

| Value       | Description        |
| ----------- | ------------------ |
| IconAndText | Show icon and text |
| IconOnly    | Show icon only     |
| TextOnly    | Show text only     |

## Advanced Usage

### NavigationBar

#### Custom Colors

```kotlin
// ... items and selectedIndex defined ...
NavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it },
    color = Color.Red.copy(alpha = 0.3f)
)
```

#### Without Divider

```kotlin
// ... items and selectedIndex defined ...
NavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it },
    showDivider = false
)
```

#### Handling Window Insets

```kotlin
// ... items and selectedIndex defined ...
NavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it },
    defaultWindowInsetsPadding = false // Handle window insets padding manually
)
```

### FloatingNavigationBar

#### Custom Mode (Icon and Text)

```kotlin
// ... items and selectedIndex defined ...
FloatingNavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it },
    mode = FloatingNavigationBarMode.IconAndText
)
```

#### Custom Mode (Text Only)

```kotlin
// ... items and selectedIndex defined ...
FloatingNavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it },
    mode = FloatingNavigationBarMode.TextOnly
)
```

#### Custom Color and Corner Radius

```kotlin
// ... items and selectedIndex defined ...
FloatingNavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it },
    color = MiuixTheme.colorScheme.primaryContainer,
    cornerRadius = 28.dp,
    mode = FloatingNavigationBarMode.IconAndText
)
```

#### Custom Alignment and Padding

```kotlin
// ... items and selectedIndex defined ...
FloatingNavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it },
    horizontalAlignment = Alignment.Start, // Align to start
    horizontalOutSidePadding = 16.dp, // Set outside padding
    mode = FloatingNavigationBarMode.IconOnly
)
```

### Using with Page Navigation (Using Scaffold)

#### Using NavigationBar

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

#### Using FloatingNavigationBar

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
        FloatingNavigationBar(
            items = items,
            selected = selectedIndex,
            onClick = { selectedIndex = it },
            mode = FloatingNavigationBarMode.IconOnly // Show icons only
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
