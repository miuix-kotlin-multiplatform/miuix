# SearchBar

`SearchBar` is a component in Miuix used for user search input. It provides an intuitive and easy-to-use search interface with support for expanded/collapsed state switching and search suggestions display.

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.SearchBar
import top.yukonga.miuix.kmp.basic.InputField
```

## Basic Usage

```kotlin
var searchText by remember { mutableStateOf("") }
var expanded by remember { mutableStateOf(false) }

SearchBar(
    inputField = {
        InputField(
            query = searchText,
            onQueryChange = { searchText = it },
            onSearch = { /* Handle search action */ },
            expanded = expanded,
            onExpandedChange = { expanded = it }
        )
    },
    expanded = expanded,
    onExpandedChange = { expanded = it }
) {
    // Search results content
    Column {
        // Add search suggestions or results here
    }
}
```

## Properties

### SearchBar Properties

| Property Name      | Type                               | Description                                       | Default Value | Required |
| ------------------ | ---------------------------------- | ------------------------------------------------- | ------------- | -------- |
| inputField         | @Composable () -> Unit             | Search input field component                      | -             | Yes      |
| expanded           | Boolean                            | Whether to show search results                    | false         | Yes      |
| onExpandedChange   | (Boolean) -> Unit                  | Callback when expanded state changes              | -             | Yes      |
| modifier           | Modifier                           | Modifier applied to the search bar                | Modifier      | No       |
| outsideRightAction | @Composable (() -> Unit)?          | Action component shown on the right when expanded | null          | No       |
| content            | @Composable ColumnScope.() -> Unit | Content shown when expanded                       | -             | Yes      |

### InputField Properties

| Property Name     | Type                      | Description                          | Default Value        | Required |
| ----------------- | ------------------------- | ------------------------------------ | -------------------- | -------- |
| query             | String                    | Text content in search field         | -                    | Yes      |
| onQueryChange     | (String) -> Unit          | Callback when text content changes   | -                    | Yes      |
| onSearch          | (String) -> Unit          | Callback when search is executed     | -                    | Yes      |
| expanded          | Boolean                   | Whether in expanded state            | -                    | Yes      |
| onExpandedChange  | (Boolean) -> Unit         | Callback when expanded state changes | -                    | Yes      |
| label             | String                    | Placeholder text when empty          | ""                   | No       |
| enabled           | Boolean                   | Whether search field is enabled      | true                 | No       |
| insideMargin      | DpSize                    | Internal padding                     | DpSize(12.dp, 12.dp) | No       |
| leadingIcon       | @Composable (() -> Unit)? | Leading icon                         | null                 | No       |
| trailingIcon      | @Composable (() -> Unit)? | Trailing icon                        | null                 | No       |
| interactionSource | MutableInteractionSource? | Interaction source                   | null                 | No       |

## Advanced Usage

### SearchBar with Icons

```kotlin
var searchText by remember { mutableStateOf("") }
var expanded by remember { mutableStateOf(false) }

SearchBar(
    inputField = {
        InputField(
            query = searchText,
            onQueryChange = { searchText = it },
            onSearch = { /* Handle search action */ },
            expanded = expanded,
            onExpandedChange = { expanded = it },
            leadingIcon = {
                Icon(
                    modifier = Modifier.padding(start = 12.dp, end = 8.dp),
                    imageVector = MiuixIcons.Useful.Search,
                    contentDescription = "Search"
                )
            }
        )
    },
    expanded = expanded,
    onExpandedChange = { expanded = it }
) {
    // Search results content
}
```

### SearchBar with Suggestions

```kotlin
var searchText by remember { mutableStateOf("") }
var expanded by remember { mutableStateOf(false) }
val suggestions = listOf("Suggestion 1", "Suggestion 2", "Suggestion 3")

SearchBar(
    inputField = {
        InputField(
            query = searchText,
            onQueryChange = { searchText = it },
            onSearch = { /* Handle search action */ },
            expanded = expanded,
            onExpandedChange = { expanded = it }
        )
    },
    expanded = expanded,
    onExpandedChange = { expanded = it }
) {
    Column {
        suggestions.forEach { suggestion ->
            BasicComponent(
                title = suggestion,
                onClick = {
                    searchText = suggestion
                    expanded = false
                }
            )
        }
    }
}
```

### SearchBar with Cancel Button

```kotlin
var searchText by remember { mutableStateOf("") }
var expanded by remember { mutableStateOf(false) }

SearchBar(
    modifier = Modifier.padding(horizontal = 12.dp),
    inputField = {
        InputField(
            query = searchText,
            onQueryChange = { searchText = it },
            onSearch = { expanded = false },
            expanded = expanded,
            onExpandedChange = { expanded = it },
            label = "Search"
        )
    },
    expanded = expanded,
    onExpandedChange = { expanded = it },
    outsideRightAction = {
        Text(
            modifier = Modifier
                .padding(start = 12.dp)
                .clickable(
                    interactionSource = null,
                    indication = null
                ) {
                    expanded = false
                    searchText = ""
                },
            text = "Cancel",
            color = MiuixTheme.colorScheme.primary
        )
    }
) {
    // Search results content
}
```