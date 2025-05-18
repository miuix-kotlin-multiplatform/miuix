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
    visible = showDialogState,     // MutableState<Boolean> to control dialog visibility
    enterTransition = fadeIn(),    // Optional, custom enter animation for dialog content
    exitTransition = fadeOut(),    // Optional, custom exit animation for dialog content
    enableWindowDim = true,        // Optional, whether to enable dimming layer
    dimEnterTransition = fadeIn(), // Optional, custom enter animation for dim layer
    dimExitTransition = fadeOut()  // Optional, custom exit animation for dim layer
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
    visible = showPopupState,      // MutableState<Boolean> to control popup visibility
    enterTransition = fadeIn(),    // Optional, custom enter animation for dialog content
    exitTransition = fadeOut(),    // Optional, custom exit animation for dialog content
    enableWindowDim = true,        // Optional, whether to enable dimming layer
    dimEnterTransition = fadeIn(), // Optional, custom enter animation for dim layer
    dimExitTransition = fadeOut(), // Optional, custom exit animation for dim layer
    transformOrigin = { TransformOrigin.Center }, // Transform origin for the popup
) {
    // Popup content
}
```

Normally, you don't need to use it actively. See the [ListPopup](../components/listpopup.md) documentation for details.

## Overscroll Effects

Miuix provides easy-to-use overscroll effects modifier for smoother and more natural scrolling experiences.

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


## Scroll End Haptic Feedback

Miuix provides a `scrollEndHaptic` modifier to trigger haptic feedback when a scrollable container is flung to its start or end boundaries. This enhances the user experience by providing a tactile confirmation that the end of the list has been reached.

```kotlin
LazyColumn(
    modifier = Modifier
        .fillMaxSize()
        // Add scroll end haptic feedback
        .scrollEndHaptic(
            hapticFeedbackType = HapticFeedbackType.TextHandleMove // Default value
        )
) {
    // List content
}
```

**Parameter Explanation:**

*   `hapticFeedbackType`: Specifies the type of haptic feedback to be performed when the scroll reaches its end. Defaults to `HapticFeedbackType.TextHandleMove`. You can use other types available in `androidx.compose.ui.hapticfeedback.HapticFeedbackType`.

## Press Feedback Effects

Miuix provides visual feedback effects to enhance user interaction experience, improving operability through tactile-like responses.

### Sink Effect

The `pressSink` modifier applies a "sink" visual feedback when the component is pressed, by smoothly scaling down the component.

```kotlin
val interactionSource = remember { MutableInteractionSource() }

Box(
    modifier = Modifier
        .clickable(interactionSource = interactionSource, indication = null)
        .pressSink(interactionSource)
        .background(Color.Blue)
        .size(100.dp)
)
```

### Tilt Effect

The `pressTilt` modifier applies a "tilt" effect based on the position where the user pressed the component. The tilt direction is determined by touch offset, giving the effect that one corner "sinks" while the other "static".

```kotlin
val interactionSource = remember { MutableInteractionSource() }

Box(
    modifier = Modifier
        .clickable(interactionSource = interactionSource, indication = null)
        .pressTilt(interactionSource)
        .background(Color.Green)
        .size(100.dp)
)
```

### Prerequisites for Triggering Press Feedback

Press feedback effects require detecting `interactionSource.collectIsPressedAsState()` to be triggered.

You can use responsive modifiers like `Modifier.clickable()` to add `PressInteraction.Press` to the `interactionSource` and trigger press feedback effects.

However, it's recommended to use the method below to add `PressInteraction.Press` to the `interactionSource` for faster response triggering of press feedback effects.

```kotlin
val interactionSource = remember { MutableInteractionSource() }

Box(
    modifier = Modifier
        .pointerInput(Unit) {
            awaitEachGesture {
                val pressInteraction: PressInteraction.Press
                awaitFirstDown().also {
                    pressInteraction = PressInteraction.Press(it.position)
                    interactionSource.tryEmit(pressInteraction)
                }
                if (waitForUpOrCancellation() == null) {
                    interactionSource.tryEmit(PressInteraction.Cancel(pressInteraction))
                } else {
                    interactionSource.tryEmit(PressInteraction.Release(pressInteraction))
                }
            }
        }
)
```

### Press Feedback Type (`PressFeedbackType`)

The `PressFeedbackType` enum defines different types of visual feedback that can be applied when the component is pressed.

| Type | Description                                                                           |
| ---- | ------------------------------------------------------------------------------------- |
| None | No visual feedback                                                                    |
| Sink | Applies a sink effect, where the component scales down slightly when pressed          |
| Tilt | Applies a tilt effect, where the component tilts slightly based on the touch position |

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
