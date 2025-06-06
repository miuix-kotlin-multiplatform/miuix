# TabRow

`TabRow` is a navigation component in Miuix used to create horizontally scrollable tabs. It provides two variants: standard style and contour style, suitable for content categorization and navigation scenarios.

<div style="position: relative; max-width: 700px; height: 180px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=tabRow" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.TabRow // Standard style
import top.yukonga.miuix.kmp.basic.TabRowWithContour // Contour style
```

## Basic Usage

### Standard Style

```kotlin
val tabs = listOf("Recommended", "Following", "Popular", "Featured")
var selectedTabIndex by remember { mutableStateOf(0) }

TabRow(
    tabs = tabs,
    selectedTabIndex = selectedTabIndex,
    onTabSelected = { selectedTabIndex = it }
)
```

### Contour Style

```kotlin
val tabs = listOf("All", "Photos", "Videos", "Documents")
var selectedTabIndex by remember { mutableStateOf(0) }

TabRowWithContour(
    tabs = tabs,
    selectedTabIndex = selectedTabIndex,
    onTabSelected = { selectedTabIndex = it }
)
```

## Properties

### TabRow Properties

| Property Name    | Type             | Description                   | Default Value                     | Required |
| ---------------- | ---------------- | ----------------------------- | --------------------------------- | -------- |
| tabs             | List\<String>    | List of tab texts             | -                                 | Yes      |
| selectedTabIndex | Int              | Current selected tab index    | -                                 | Yes      |
| modifier         | Modifier         | Modifier for the tab row      | Modifier                          | No       |
| colors           | TabRowColors     | Color configuration           | TabRowDefaults.tabRowColors()     | No       |
| minWidth         | Dp               | Minimum width of each tab     | TabRowDefaults.TabRowMinWidth     | No       |
| maxWidth         | Dp               | Maximum width of each tab     | TabRowDefaults.TabRowMaxWidth     | No       |
| height           | Dp               | Height of the tab row         | TabRowDefaults.TabRowHeight       | No       |
| cornerRadius     | Dp               | Corner radius of tabs         | TabRowDefaults.TabRowCornerRadius | No       |
| onTabSelected    | ((Int) -> Unit)? | Callback when tab is selected | null                              | No       |

### TabRowWithContour Properties

| Property Name    | Type             | Description                   | Default Value                                | Required |
| ---------------- | ---------------- | ----------------------------- | -------------------------------------------- | -------- |
| tabs             | List\<String>    | List of tab texts             | -                                            | Yes      |
| selectedTabIndex | Int              | Current selected tab index    | -                                            | Yes      |
| modifier         | Modifier         | Modifier for the tab row      | Modifier                                     | No       |
| colors           | TabRowColors     | Color configuration           | TabRowDefaults.tabRowColors()                | No       |
| minWidth         | Dp               | Minimum width of each tab     | TabRowDefaults.TabRowWithContourMinWidth     | No       |
| maxWidth         | Dp               | Maximum width of each tab     | TabRowDefaults.TabRowWithContourMaxWidth     | No       |
| height           | Dp               | Height of the tab row         | TabRowDefaults.TabRowHeight                  | No       |
| cornerRadius     | Dp               | Corner radius of tabs         | TabRowDefaults.TabRowWithContourCornerRadius | No       |
| onTabSelected    | ((Int) -> Unit)? | Callback when tab is selected | null                                         | No       |

### TabRowDefaults Object

The TabRowDefaults object provides default configurations for the TabRow component.

#### Constants

| Constant Name                 | Type | Value | Description                              |
| ----------------------------- | ---- | ----- | ---------------------------------------- |
| TabRowHeight                  | Dp   | 42.dp | Default height of tab row                |
| TabRowCornerRadius            | Dp   | 8.dp  | Default corner radius for standard style |
| TabRowWithContourCornerRadius | Dp   | 10.dp | Default corner radius for contour style  |
| TabRowMinWidth                | Dp   | 76.dp | Min width of tabs for standard style     |
| TabRowWithContourMinWidth     | Dp   | 62.dp | Min width of tabs for contour style      |
| TabRowMaxWidth                | Dp   | 98.dp | Max width of tabs for standard style     |
| TabRowWithContourMaxWidth     | Dp   | 84.dp | Max width of tabs for contour style      |

#### Methods

| Method Name    | Type         | Description                        |
| -------------- | ------------ | ---------------------------------- |
| tabRowColors() | TabRowColors | Create default color configuration |

### TabRowColors Class

| Property Name           | Type  | Description                      |
| ----------------------- | ----- | -------------------------------- |
| backgroundColor         | Color | Default background color of tabs |
| contentColor            | Color | Default content color of tabs    |
| selectedBackgroundColor | Color | Background color of selected tab |
| selectedContentColor    | Color | Content color of selected tab    |

## Advanced Usage

### Custom Colors

```kotlin
val tabs = listOf("Latest", "Popular", "Following")
var selectedTabIndex by remember { mutableStateOf(0) }

TabRow(
    tabs = tabs,
    selectedTabIndex = selectedTabIndex,
    onTabSelected = { selectedTabIndex = it },
    colors = TabRowDefaults.tabRowColors(
        backgroundColor = Color.LightGray.copy(alpha = 0.5f),
        contentColor = Color.Gray,
        selectedBackgroundColor = MiuixTheme.colorScheme.primary,
        selectedContentColor = Color.White
    )
)
```

### Custom Dimensions

```kotlin
val tabs = listOf("Short Videos", "Live", "Articles")
var selectedTabIndex by remember { mutableStateOf(0) }

TabRowWithContour(
    tabs = tabs,
    selectedTabIndex = selectedTabIndex,
    onTabSelected = { selectedTabIndex = it },
    minWidth = 100.dp,
    height = 50.dp,
    cornerRadius = 15.dp
)
```

### Using with Pager

```kotlin
val tabs = listOf("Page 1", "Page 2", "Page 3")
val pagerState = rememberPagerState { tabs.size }
var selectedTabIndex by remember { mutableStateOf(0) }

LaunchedEffect(pagerState.currentPage) {
    selectedTabIndex = pagerState.currentPage
}

LaunchedEffect(selectedTabIndex) {
    pagerState.animateScrollToPage(selectedTabIndex)
}

Surface {
    Column {
        TabRow(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it }
        )
        HorizontalPager(
            pagerState = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Page Content ${page + 1}")
            }
        }
    }
}
```
