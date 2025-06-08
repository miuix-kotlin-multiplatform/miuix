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
val pullToRefreshState = rememberPullToRefreshState()
var items by remember { mutableStateOf(1) }
val scope = rememberCoroutineScope()

Surface {
    PullToRefresh(
        pullToRefreshState = pullToRefreshState,
        onRefresh = {
            scope.launch {
                // Check refresh state
                if (pullToRefreshState.isRefreshing) {
                    delay(300) // Simulate refresh operation
                    // Complete refresh
                    pullToRefreshState.completeRefreshing {
                        // Update data
                        items++
                    }
                }
            }
        }
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

```kotlin
val pullToRefreshState = rememberPullToRefreshState()
// Check if refreshing
if (pullToRefreshState.isRefreshing) {
    // Logic during refresh
    delay(2000) // Simulate network request
    pullToRefreshState.completeRefreshing {} // Callback when refresh completes
}
```

## Properties

### PullToRefresh Properties

| Property Name      | Type                   | Description                    | Default Value                          | Required |
| ------------------ | ---------------------- | ------------------------------ | -------------------------------------- | -------- |
| pullToRefreshState | PullToRefreshState     | Refresh state controller       | -                                      | Yes      |
| onRefresh          | () -> Unit             | Refresh callback function      | {}                                     | Yes      |
| modifier           | Modifier               | Container modifier             | Modifier                               | No       |
| contentPadding     | PaddingValues          | Content padding                | PaddingValues(0.dp)                    | No       |
| color              | Color                  | Indicator color                | PullToRefreshDefaults.color            | No       |
| circleSize         | Dp                     | Indicator circle size          | PullToRefreshDefaults.circleSize       | No       |
| refreshTexts       | List\<String>          | Text list for different states | PullToRefreshDefaults.refreshTexts     | No       |
| refreshTextStyle   | TextStyle              | Refresh text style             | PullToRefreshDefaults.refreshTextStyle | No       |
| content            | @Composable () -> Unit | Scrollable content composable  | -                                      | Yes      |

### PullToRefreshState Class

PullToRefreshState controls the refresh state and can be created using `rememberPullToRefreshState()`.

#### Properties

| Property Name               | Type         | Description            | Required |
| --------------------------- | ------------ | ---------------------- | -------- |
| refreshState                | RefreshState | Current refresh state  | Yes      |
| isRefreshing                | Boolean      | Is refreshing          | Yes      |
| pullProgress                | Float        | Pull progress (0-1)    | Yes      |
| refreshCompleteAnimProgress | Float        | Complete anim progress | Yes      |

#### Methods

| Method Name        | Parameters           | Description                    | Required |
| ------------------ | -------------------- | ------------------------------ | -------- |
| completeRefreshing | (suspend () -> Unit) | Complete refresh with callback | Yes      |

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
val pullToRefreshState = rememberPullToRefreshState()

PullToRefresh(
    pullToRefreshState = pullToRefreshState,
    color = Color.Blue,
    onRefresh = { 
        // Perform refresh
    }
) {
    // Content
}
```

### Custom Refresh Texts

```kotlin
val pullToRefreshState = rememberPullToRefreshState()

PullToRefresh(
    pullToRefreshState = pullToRefreshState,
    refreshTexts = listOf(
        "Pull to refresh",
        "Release to refresh",
        "Refreshing",
        "Refresh successful"
    ),
    onRefresh = {
        // Perform refresh
    }
) {
    // Content
}
```

### Using with Loading State

```kotlin
val pullToRefreshState = rememberPullToRefreshState()
var items by remember { mutableStateOf(List(5) { "Item $it" }) }
val scope = rememberCoroutineScope()

PullToRefresh(
    pullToRefreshState = pullToRefreshState,
    onRefresh = {
        scope.launch {
            delay(2000) // Simulate refresh operation
            pullToRefreshState.completeRefreshing {
                items = List(8) { "Updated Item $it" }
            }
        }
    }
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items.size) { item ->
            Text(
                text = items[item],
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            )
        }
    }
}
```
