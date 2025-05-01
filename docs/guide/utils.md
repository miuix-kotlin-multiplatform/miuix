# Utility Functions

Miuix provides a series of utility functions to help you develop applications more efficiently. Below is a detailed introduction and usage examples of the main utility functions.

## Popup Utilities (MiuixPopupUtils)

`MiuixPopupUtils` is a utility class for displaying popup layout and dialog layout. This class is already integrated into the `Scaffold` component and can be used directly.

If you use multiple Scaffolds, you need to set the `popupHost` parameter in the subordinate `Scaffold` to `null`.

### DialogLayout

```kotlin
// Requires a MutableState<Boolean> to control the display state
val showDialogState = remember { mutableStateOf(false) }

DialogLayout(
    visible = showDialogState
) {
    // Dialog content
}
```
Normally, you don't need to use it actively. See the [SuperDialog](../components/superdialog.md) documentation for details.

### PopupLayout

```kotlin
// Requires a MutableState<Boolean> to control the display state
val showPopupState = remember { mutableStateOf(false) }

PopupLayout(
    visible = showPopupState,
    transformOrigin = { TransformOrigin.Center }, // Transform origin for the popup
    windowDimming = true // Dim the background
) {
    // Popup content
}
```

Normally, you don't need to use it actively. See the [ListPopup](../components/listpopup.md) documentation for details.

## Overscroll Effects

Miuix provides easy-to-use overscroll effects for smoother and more natural scrolling experiences.

### Vertical Overscroll

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

You can customize the parameters of the overscroll effect to meet specific requirements.

```kotlin
LazyColumn(
    modifier = Modifier.overScrollVertical(
        nestedScrollToParent = true, // Whether to dispatch nested scroll events to the parent, default is true
        scrollEasing = { currentOffset, newOffset -> // Custom scroll easing function
            parabolaScrollEasing(currentOffset, newOffset, p = 25f, density = LocalDensity.current.density)
        },
        springStiff = 200f, // Spring stiffness for the rebound animation, default is 200f
        springDamp = 1f,  // Spring damping for the rebound animation, default is 1f
        isEnabled = { platform() == Platform.Android || platform() == Platform.IOS } // Whether to enable the overscroll effect, enabled by default on Android and iOS
        ),
    overscrollEffect = null // It is recommended to set this parameter to null to disable the default effect
) {
    // List content
}
```

**Parameter Explanations:**

*   `nestedScrollToParent`: Boolean, controls whether nested scroll events (e.g., from parent scroll containers) are dispatched to the parent. Defaults to `true`.
*   `scrollEasing`: A function that defines the easing effect when scrolling beyond the bounds. It takes the current offset (`currentOffset`) and the new offset delta (`newOffset`) as parameters and returns the calculated new offset. By default, it uses `parabolaScrollEasing`, providing an iOS-like damping effect.
*   `springStiff`: Float, defines the spring stiffness for the rebound animation. Higher values result in a faster and stiffer rebound. Defaults to `200f`.
*   `springDamp`: Float, defines the spring damping for the rebound animation. Higher values result in less oscillation. Defaults to `1f`.
*   `isEnabled`: A lambda expression returning a Boolean, used to dynamically control whether the overscroll effect is enabled. By default, it is enabled only on Android and iOS platforms.

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
