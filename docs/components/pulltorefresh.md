# PullToRefresh

`PullToRefresh` is a pull-to-refresh component in Miuix that provides refresh functionality for lists and other scrollable content. It features an animated interactive refresh indicator suitable for various scenarios where data refresh is needed.

::: warning
This component is only available in touch-enabled scenes and does not work well in the Web build target!
:::

For a demonstration, see the DropDown page of the <a href="https://miuix-kotlin-multiplatform.github.io/miuix-jsCanvas/" target="_blank" rel="noopener noreferrer">Miuix Example</a>.

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.PullToRefresh
import top.yukonga.miuix.kmp.basic.rememberPullToRefreshState
```

## Basic Usage

PullToRefresh can wrap any scrollable content:

```kotlin
var isRefreshing by rememberSaveable { mutableStateOf(false) }
val pullToRefreshState = rememberPullToRefreshState()
var items by remember { mutableStateOf(1) }

LaunchedEffect(isRefreshing) {
    if (isRefreshing) {
        delay(500)
        items += 6
        isRefreshing = false
    }
}

Surface {
    PullToRefresh(
        isRefreshing = isRefreshing,
        onRefresh = { isRefreshing = true },
        pullToRefreshState = pullToRefreshState,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(items) { index ->
                SuperArrow(
                    title = "Item $index",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { /* Click event */ }
                )
            }
        }
    }
}
```

## Component States

PullToRefresh has the following states:

1. `Idle`: Initial state, no interaction
2. `Pulling`: User is pulling but hasn't reached the refresh threshold
3. `ThresholdReached`: Pull threshold reached, release to refresh
4. `Refreshing`: Currently refreshing
5. `RefreshComplete`: Refresh completed, returning to initial state

## Properties

### PullToRefresh Properties

| Property Name           | Type                   | Description                    | Default Value                          | Required |
| ----------------------- | ---------------------- | ------------------------------ | -------------------------------------- | -------- |
| isRefreshing            | Boolean                | Refresh state                  | None                                   | Yes      |
| onRefresh               | () -> Unit             | Refresh callback function      | None                                   | Yes      |
| modifier                | Modifier               | Container modifier             | Modifier                               | No       |
| pullToRefreshState      | PullToRefreshState     | PullToRefresh state            | rememberPullToRefreshState()           | No       |
| contentPadding          | PaddingValues          | Content padding                | PaddingValues(0.dp)                    | No       |
| topAppBarScrollBehavior | ScrollBehavior         | Top app bar scroll behavior    | null                                   | No       |
| color                   | Color                  | Indicator color                | PullToRefreshDefaults.color            | No       |
| circleSize              | Dp                     | Indicator circle size          | PullToRefreshDefaults.circleSize       | No       |
| refreshTexts            | List<String>           | Text list for different states | PullToRefreshDefaults.refreshTexts     | No       |
| refreshTextStyle        | TextStyle              | Refresh text style             | PullToRefreshDefaults.refreshTextStyle | No       |
| content                 | @Composable () -> Unit | Scrollable content composable  | None                                   | Yes      |

### PullToRefreshState Class

PullToRefreshState manages the UI state of the refresh indicator and can be created using `rememberPullToRefreshState()`. It should only be used for UI state, while refresh logic should be controlled by `isRefreshing` and `onRefresh`.

| Property Name                | Type         | Description                  |
| ---------------------------- | ------------ | ---------------------------- |
| refreshState                 | RefreshState | Current refresh state        |
| isRefreshing                 | Boolean      | Whether it is refreshing     |
| pullProgress                 | Float        | Pull progress (0-1)          |
| refreshCompleteAnimProgress  | Float        | Refresh complete animation   |

### PullToRefreshDefaults Object

PullToRefreshDefaults provides default values for the component.

| Property Name    | Type          | Description             | Default Value                                                                             |
| ---------------- | ------------- | ----------------------- | ----------------------------------------------------------------------------------------- |
| color            | Color         | Default indicator color | Color.Gray                                                                                |
| circleSize       | Dp            | Default indicator size  | 20.dp                                                                                     |
| refreshTexts     | List\<String> | Default text list       | ["Pull down to refresh", "Release to refresh", "Refreshing...", "Refreshed successfully"] |
| refreshTextStyle | TextStyle     | Default text style      | TextStyle(fontSize = 14.sp, fontWeight = Bold, color = color)                             |

## Advanced Usage

### Custom Indicator Color

```kotlin
PullToRefresh(
    color = Color.Blue,
    // Other properties
) {
    // Content
}
```

### Custom Refresh Texts

```kotlin
PullToRefresh(
    refreshTexts = listOf(
        "Pull to refresh",
        "Release to refresh",
        "Refreshing",
        "Refresh successful"
    ),
    // Other properties
) {
    // Content
}
```
