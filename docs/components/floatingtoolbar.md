# FloatingToolbar

`FloatingToolbar` is a floating toolbar component in Miuix that renders its content within a container with a rounded background, arranged either horizontally or vertically. The actual placement on the screen is handled by the parent component, typically `Scaffold`.

This component is usually used in conjunction with `Scaffold`, placed in a specific position on the page to provide quick actions or display information.

<div style="position: relative; max-width: 700px; height: 300px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=floatingToolbar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.FloatingToolbar
import top.yukonga.miuix.kmp.basic.FloatingToolbarDefaults
import top.yukonga.miuix.kmp.basic.ToolbarPosition // Used for Scaffold
```

## Basic Usage

```kotlin
Scaffold(
    floatingToolbar = {
        FloatingToolbar {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) { // or Column
                IconButton(onClick = { /* Action 1 */ }) {
                    Icon(MiuixIcons.Useful.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { /* Action 2 */ }) {
                    Icon(MiuixIcons.Useful.Delete, contentDescription = "Delete")
                }
            }
        }
    },
    floatingToolbarPosition = ToolbarPosition.BottomCenter // Set the position
)
```

## Properties

### FloatingToolbar Properties

| Property Name              | Type                   | Description                            | Default Value                          | Required |
| -------------------------- | ---------------------- | -------------------------------------- | -------------------------------------- | -------- |
| modifier                   | Modifier               | Modifier applied to the toolbar        | Modifier                               | No       |
| color                      | Color                  | Background color of the toolbar        | FloatingToolbarDefaults.DefaultColor() | No       |
| cornerRadius               | Dp                     | Corner radius of the toolbar           | FloatingToolbarDefaults.CornerRadius   | No       |
| outSidePadding             | PaddingValues          | Padding outside the toolbar            | FloatingToolbarDefaults.OutSidePadding | No       |
| shadowElevation            | Dp                     | The shadow elevation of the toolbar    | 4.dp                                   | No       |
| showDivider                | Boolean                | Show divider line around the toolbar   | false                                  | No       |
| defaultWindowInsetsPadding | Boolean                | Apply default window insets padding    | true                                   | No       |
| content                    | @Composable () -> Unit | Composable content area of the toolbar | -                                      | Yes      |

### FloatingToolbarDefaults Object

| Property Name  | Type                    | Description              | Value                                   |
| -------------- | ----------------------- | ------------------------ | --------------------------------------- |
| CornerRadius   | Dp                      | Default corner radius    | 50.dp                                   |
| DefaultColor() | @Composable () -> Color | Default background color | MiuixTheme.colorScheme.surfaceContainer |
| OutSidePadding | PaddingValues           | Default outside padding  | PaddingValues(12.dp, 8.dp)              |

### ToolbarPosition (for Scaffold)

Please refer to the `ToolbarPosition` options in the [Scaffold](../components/scaffold#toolbarposition-options) documentation.

## Advanced Usage

### Custom Styles

```kotlin
FloatingToolbar(
    color = MiuixTheme.colorScheme.primaryContainer,
    cornerRadius = 16.dp,
    outSidePadding = PaddingValues(24.dp),
    showDivider = false
) {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) { // or Column
        IconButton(onClick = { /* Action 1 */ }) {
            Icon(MiuixIcons.Useful.Edit, contentDescription = "Edit", tint = MiuixTheme.colorScheme.onPrimaryContainer)
        }
        IconButton(onClick = { /* Action 2 */ }) {
            Icon(MiuixIcons.Useful.Delete, contentDescription = "Delete", tint = MiuixTheme.colorScheme.onPrimaryContainer)
        }
    }
}
```

### Vertically Arranged Content

```kotlin
FloatingToolbar {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(onClick = { /* Action 1 */ }) {
            Icon(MiuixIcons.Useful.Edit, contentDescription = "Edit")
        }
        IconButton(onClick = { /* Action 2 */ }) {
            Icon(MiuixIcons.Useful.Delete, contentDescription = "Delete")
        }
    }
}
```

### Changing Position with Scaffold

```kotlin
Scaffold(
    floatingToolbar = {
        FloatingToolbar {
            // toolbar content
        }
    },
    floatingToolbarPosition = ToolbarPosition.CenterEnd // Place centered on the right edge
)
```

### Handling Window Insets

```kotlin
FloatingToolbar(
    defaultWindowInsetsPadding = false // Handle window insets padding manually
) {
    // toolbar content
}
```
