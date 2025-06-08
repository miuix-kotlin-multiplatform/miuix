# Scaffold

`Scaffold` is a scaffolding component in Miuix used to implement basic design layout structures. It provides the fundamental framework for application interfaces, including containers for top bars, bottom bars, floating action buttons, and other elements.

<div style="position: relative; max-width: 700px; height: 350px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=scaffold" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

::: warning
The Scaffold component provides a suitable container for cross-platform popup windows. Components such as `SuperDialog`, `SuperDropdown`, `SuperSpinner`, and `ListPopup` are all implemented based on this and therefore need to be wrapped by this component.
:::

::: info
Why not use the official `Popup` and `Dialog` instead of creating our own popup window container? Because their cross-platform support is currently incomplete, with some parameters that cannot be modified.
:::

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.Scaffold
```

## Basic Usage

The Scaffold component can be used to build a page layout with a top bar:

```kotlin
Scaffold(
    topBar = {
        SmallTopAppBar(title = "Title" )
    },
    content = { paddingValues ->
        // The content area needs to consider padding
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding(), start = 26.dp)
                .fillMaxSize()
        ) {
            Text("Content Area")
        }
    }
)
```

## Properties

### Scaffold Properties

| Property Name                | Type                                | Description                                                              | Default Value                     | Required |
| ---------------------------- | ----------------------------------- | ------------------------------------------------------------------------ | --------------------------------- | -------- |
| modifier                     | Modifier                            | Modifier applied to the scaffold                                         | Modifier                          | No       |
| topBar                       | @Composable () -> Unit              | Top bar, usually a TopAppBar                                             | {}                                | No       |
| bottomBar                    | @Composable () -> Unit              | Bottom bar, usually a NavigationBar                                      | {}                                | No       |
| floatingActionButton         | @Composable () -> Unit              | Floating action button                                                   | {}                                | No       |
| floatingActionButtonPosition | FabPosition                         | Position to display the floating action button                           | FabPosition.End                   | No       |
| floatingToolbar              | @Composable () -> Unit              | Floating toolbar                                                         | {}                                | No       |
| floatingToolbarPosition      | ToolbarPosition                     | Position to display the floating toolbar                                 | ToolbarPosition.BottomCenter      | No       |
| snackbarHost                 | @Composable () -> Unit              | Container for displaying Snackbar, Miuix does not provide this component | {}                                | No       |
| popupHost                    | @Composable () -> Unit              | Container for displaying popup windows                                   | \{ MiuixPopupHost() }             | No       |
| containerColor               | Color                               | Background color of the scaffold                                         | MiuixTheme.colorScheme.background | No       |
| contentWindowInsets          | WindowInsets                        | Window insets passed to the content                                      | WindowInsets.statusBars           | No       |
| content                      | @Composable (PaddingValues) -> Unit | Main content area of the scaffold                                        | -                                 | Yes      |

### FabPosition Options

| Option Name | Description                                                                                    |
| ----------- | ---------------------------------------------------------------------------------------------- |
| Start       | Places the floating action button at the bottom left of the screen, above the bottom bar       |
| Center      | Places the floating action button at the bottom center of the screen, above the bottom bar     |
| End         | Places the floating action button at the bottom right of the screen, above the bottom bar      |
| EndOverlay  | Places the floating action button at the bottom right of the screen, overlaying the bottom bar |

### ToolbarPosition Options

| Option Name  | Description                                                          |
| ------------ | -------------------------------------------------------------------- |
| TopStart     | Places the floating toolbar at the top start corner                  |
| CenterStart  | Places the floating toolbar vertically centered on the start edge    |
| BottomStart  | Places the floating toolbar at the bottom start corner               |
| TopEnd       | Places the floating toolbar at the top end corner                    |
| CenterEnd    | Places the floating toolbar vertically centered on the end edge      |
| BottomEnd    | Places the floating toolbar at the bottom end corner                 |
| TopCenter    | Places the floating toolbar horizontally centered at the top edge    |
| BottomCenter | Places the floating toolbar horizontally centered at the bottom edge |

## Advanced Usage

### Page Layout with Top Bar and Bottom Bar

```kotlin
val topAppBarScrollBehavior = MiuixScrollBehavior(rememberTopAppBarState())

Scaffold(
    topBar = {
        TopAppBar(
            title = "Title",
            navigationIcon = {
                IconButton(onClick = { /* Handle navigation click */ }) {
                    Icon(MiuixIcons.Useful.Back, contentDescription = "Back")
                }
            },
            scrollBehavior = topAppBarScrollBehavior
        )
    },
    bottomBar = {
        val items = listOf(
            NavigationItem("Home", MiuixIcons.Useful.NavigatorSwitch),
            NavigationItem("Settings", MiuixIcons.Useful.Settings)
        )
        var selectedItem by remember { mutableStateOf(0) }
        NavigationBar(
            items = items,
            selected = selectedItem,
            onClick = { index ->
                selectedItem = index
            },
        )
    },
    content = { paddingValues ->
        // The content area needs to consider padding
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                // Bind the scroll behavior of the top bar
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
        ) {
            items(20) { index ->
                SuperArrow(
                    title = "Item $index",
                    onClick = { /* Handle click */ }
                )
                if (index < 19) HorizontalDivider()
            }
        }
    }
)
```

### Page Layout with Floating Action Button

```kotlin
Scaffold(
    topBar = {
        SmallTopAppBar(title = "Title")
    },
    floatingActionButton = {
        FloatingActionButton(
            onClick = { /* Handle click event */ }
        ) {
            Icon(MiuixIcons.Useful.New, contentDescription = "Add")
        }
    },
    floatingActionButtonPosition = FabPosition.End,
    content = { paddingValues ->
        // The content area needs to consider padding
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding(), start = 26.dp)
                .fillMaxSize()
        ) {
               Text("Click the button in the bottom right corner to add content")
        }
    }
)
```

### Page Layout with Snackbar (requires Material components)

```kotlin
val snackbarHostState = remember { SnackbarHostState() }
val scope = rememberCoroutineScope()

Scaffold(
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    topBar = {
        SmallTopAppBar(title = "Title")
    },
    floatingActionButton = {
        FloatingActionButton(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("This is a message!")
                }
            }
        ) {
            Icon(MiuixIcons.Useful.Info, contentDescription = "Show message")
        }
    },
    content = { paddingValues ->
        // The content area needs to consider padding
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding(), start = 26.dp)
                .fillMaxSize()
        ) {
            Text("Click the button to show Snackbar")
        }
    }
)
```

### Custom Window Insets

```kotlin
Scaffold(
    contentWindowInsets = WindowInsets.systemBars,
    topBar = {
        SmallTopAppBar(title = "Title")
    },
    content = { paddingValues ->
        // The content area needs to consider padding
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding(), start = 26.dp)
                .fillMaxSize()
        ) {
            Text("Content area considering system bars")
        }
    }
)
```
