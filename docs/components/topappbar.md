# TopAppBar

`TopAppBar` is a top application bar component in Miuix, used to provide navigation, title, and action buttons at the top of the interface. It supports both large title and regular modes, as well as dynamic effects during scrolling.

This component is typically used in conjunction with the `Scaffold` component to maintain consistent layout and behavior across different pages in the application.

<div style="position: relative; max-width: 700px; height: 300px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=topAppBar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.basic.SmallTopAppBar
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.rememberTopAppBarState
```

## Basic Usage

### Small TopAppBar

```kotlin
Scaffold(
    topBar = {
        SmallTopAppBar(
            title = "Title",
            navigationIcon = {
                IconButton(onClick = { /* Handle click event */ }) {
                    Icon(MiuixIcons.Useful.Back, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* Handle click event */ }) {
                    Icon(MiuixIcons.Useful.More, contentDescription = "More")
                }
            }
        )
    }
)
```

### Large TopAppBar

```kotlin
Scaffold(
    topBar = {
        TopAppBar(
            title = "Title",
            largeTitle = "Large Title", // If not specified, title value will be used
            navigationIcon = {
                IconButton(onClick = { /* Handle click event */ }) {
                    Icon(MiuixIcons.Useful.Back, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* Handle click event */ }) {
                    Icon(MiuixIcons.Useful.More, contentDescription = "More")
                }
            }
        )
    }
)
```

## Large TopAppBar Scroll Behavior (Using Scaffold)

TopAppBar supports changing its display state when content scrolls:

```kotlin
val scrollBehavior = MiuixScrollBehavior()

Scaffold(
    topBar = {
        TopAppBar(
            title = "Title",
            largeTitle = "Large Title", // If not specified, title value will be used
            scrollBehavior = scrollBehavior
        )
    }
) { paddingValues ->
    // Content area needs to consider padding
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            // If you want to add the overscroll effect, please add it before the scroll behavior
            .overScrollVertical()
            // Bind TopAppBar scroll behavior
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        contentPadding = PaddingValues(top = paddingValues.calculateTopPadding())
    ) {
        // List content
    }
}
```

## Custom Styles

### Custom Colors

```kotlin
TopAppBar(
    title = "Title",
    color = MiuixTheme.colorScheme.primary
)
```

### Custom Content Padding

```kotlin
TopAppBar(
    title = "Title",
    horizontalPadding = 32.dp
)
```

## Properties

### TopAppBar Properties

| Property Name              | Type                            | Description                                    | Default Value                     | Required |
| -------------------------- | ------------------------------- | ---------------------------------------------- | --------------------------------- | -------- |
| title                      | String                          | Top bar title                                  | -                                 | Yes      |
| modifier                   | Modifier                        | Modifier applied to the top bar                | Modifier                          | No       |
| color                      | Color                           | Top bar background color                       | MiuixTheme.colorScheme.background | No       |
| largeTitle                 | String?                         | Large title text, uses title if not specified  | null                              | No       |
| navigationIcon             | @Composable () -> Unit          | Composable function for navigation icon area   | {}                                | No       |
| actions                    | @Composable RowScope.() -> Unit | Composable function for action buttons area    | {}                                | No       |
| scrollBehavior             | ScrollBehavior?                 | Controls top bar scroll behavior               | null                              | No       |
| defaultWindowInsetsPadding | Boolean                         | Whether to apply default window insets padding | true                              | No       |
| horizontalPadding          | Dp                              | Horizontal content padding                     | 26.dp                             | No       |

### SmallTopAppBar Properties

| Property Name              | Type                            | Description                                    | Default Value                     | Required |
| -------------------------- | ------------------------------- | ---------------------------------------------- | --------------------------------- | -------- |
| title                      | String                          | Top bar title                                  | -                                 | Yes      |
| modifier                   | Modifier                        | Modifier applied to the top bar                | Modifier                          | No       |
| color                      | Color                           | Top bar background color                       | MiuixTheme.colorScheme.background | No       |
| navigationIcon             | @Composable () -> Unit          | Composable function for navigation icon area   | {}                                | No       |
| actions                    | @Composable RowScope.() -> Unit | Composable function for action buttons area    | {}                                | No       |
| scrollBehavior             | ScrollBehavior?                 | Controls top bar scroll behavior               | null                              | No       |
| defaultWindowInsetsPadding | Boolean                         | Whether to apply default window insets padding | true                              | No       |
| horizontalPadding          | Dp                              | Horizontal content padding                     | 26.dp                             | No       |

### ScrollBehavior

MiuixScrollBehavior is a configuration object used to control the scroll behavior of the top bar.

#### rememberTopAppBarState

Used to create and remember TopAppBarState:

```kotlin
val scrollBehavior = MiuixScrollBehavior(
    state = rememberTopAppBarState(),
    snapAnimationSpec = spring(stiffness = 3000f),
    canScroll = { true }
)
```

| Parameter Name     | Type                        | Default Value              | Description                                      |
| ------------------ | --------------------------- | -------------------------- | ------------------------------------------------ |
| state              | TopAppBarState              | rememberTopAppBarState()   | State object controlling scroll state            |
| canScroll          | () -> Boolean               | { true }                   | Callback to control whether scrolling is allowed |
| snapAnimationSpec  | AnimationSpec\<Float>?      | spring(stiffness = 3000f)  | Defines snap animation after scrolling           |
| flingAnimationSpec | DecayAnimationSpec\<Float>? | rememberSplineBasedDecay() | Defines decay animation for fling                |

## Advanced Usage

### Handling Window Insets

```kotlin
TopAppBar(
    title = "Title",
    largeTitle = "Large Title",
    defaultWindowInsetsPadding = false // Handle window insets manually
)
```

### Custom Scroll Behavior Animation

```kotlin
var isScrollingEnabled by remember { mutableStateOf(true) }
val scrollBehavior = MiuixScrollBehavior(
    snapAnimationSpec = tween(durationMillis = 100),
    flingAnimationSpec = rememberSplineBasedDecay(),
    canScroll = { isScrollingEnabled } // Can dynamically control whether scrolling is allowed
)

TopAppBar(
    title = "Title",
    largeTitle = "Large Title",
    scrollBehavior = scrollBehavior
)
```

### Combining Large and Small Titles

You can use the BoxWithConstraints method provided by foundation or the [getWindowSize()](../guide/multiplatform.md#window-size-management) method provided by Miuix to get the current window size and decide whether to use a large title or a small title based on the window width.

```kotlin
var useSmallTopBar by remember { mutableStateOf(false) }

Box(modifier = Modifier.fillMaxSize()) {
    if (useSmallTopBar) {
        SmallTopAppBar(
            title = "Compact Mode",
            navigationIcon = {
                IconButton(onClick = { useSmallTopBar = false }) {
                    Icon(
                        imageVector = MiuixIcons.Useful.Back,
                        contentDescription = "Switch to Large Title",
                        tint = MiuixTheme.colorScheme.onBackground
                    )
                }
            }
        )
    } else {
        TopAppBar(
            title = "Title",
            largeTitle = "Expanded Mode",
            navigationIcon = {
                IconButton(onClick = { useSmallTopBar = true }) {
                    Icon(
                        imageVector = MiuixIcons.Useful.Back,
                        contentDescription = "Switch to Small Title",
                        tint = MiuixTheme.colorScheme.onBackground
                    )
                }
            }
        )
    }
}
```
