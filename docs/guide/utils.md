# Utility Functions

Miuix provides a series of utility functions to help you develop applications more efficiently. Below is a detailed introduction and usage examples of the main utility functions.

## Popup Utilities (MiuixPopupUtils)

`MiuixPopupUtils` is a utility class for displaying popups and dialogs. This class is already integrated into the `Scaffold` component and can be used directly.

### Show Dialog

```kotlin
showDialog {
    // Dialog content
}
```
See the [SuperDialog](../components/superdialog.md) documentation for details.

### Dismiss Dialog

```kotlin
dismissDialog(showDialog) 
```

See the [SuperDialog](../components/superdialog.md) documentation for details.

### Show Popup

```kotlin
showPopup(
    transformOrigin = { TransformOrigin(0.5f, 0f) }, // Expand from the top center
    windowDimming = true // Dim the background
) {
    // Popup content
}
```

See the [ListPopup](../components/listpopup.md) documentation for details.

### Dismiss Popup

```kotlin
dismissPopup(showPopup) 
```

See the [ListPopup](../components/listpopup.md) documentation for details.

## Overscroll Effects

Miuix provides easy-to-use overscroll effects for smoother and more natural scrolling experiences.

### Vertical Overscroll

### 垂直越界回弹

```kotlin
LazyColumn(
    modifier = Modifier
        // Add overscroll effect
        .overScrollVertical()
        // If you want to bind the TopAppBar scroll behavior, please add it after the overscroll effect
        .nestedScroll(scrollBehavior.nestedScrollConnection)
) {
    // List content
}
```

### Horizontal Overscroll

```kotlin
LazyRow(
    modifier = Modifier.overScrollHorizontal()
) {
    // List content
}
```

### Custom Overscroll Parameters

```kotlin
LazyColumn(
    modifier = Modifier.overScrollVertical(
        nestedScrollToParent = true,
        springStiff = 150f, // Spring stiffness
        springDamp = 0.8f,  // Damping coefficient
        isEnabled = { true } // Enable or disable
    )
) {
    // List content
}
```

## Smooth Rounded Corners (SmoothRoundedCornerShape)

`SmoothRoundedCornerShape` provides smoother rounded corners compared to the standard `RoundedCornerShape`.

### Basic Usage

```kotlin
Surface(
    shape = SmoothRoundedCornerShape(16.dp)
) {
    // Content
}
```

### Custom Smoothness and Different Angles

```kotlin
Surface(
    shape = SmoothRoundedCornerShape(
        smoothing = 0.8f, // Smoothness, higher values are smoother
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = 8.dp,
        bottomEnd = 8.dp
    )
) {
    // Content
}
```
