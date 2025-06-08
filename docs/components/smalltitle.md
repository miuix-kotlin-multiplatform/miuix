# SmallTitle

`SmallTitle` is a basic title component in Miuix used to create small-sized title text. It follows Miuix's design style with preset font size, weight, and padding.

<div style="position: relative; max-width: 700px; height: 160px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=smallTitle" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.SmallTitle
```

## Basic Usage

SmallTitle component can be used to display small title text:

```kotlin
SmallTitle(
    text = "Small Title"
)
```

## Customization

### Custom Color

```kotlin
SmallTitle(
    text = "Small Title with Custom Color",
    textColor = Color.Red
)
```

### Custom Padding

```kotlin
SmallTitle(
    text = "Small Title with Custom Padding",
    insideMargin = PaddingValues(16.dp, 4.dp)
)
```

## Properties

### SmallTitle Properties

| Property Name | Type          | Description                   | Default Value                              | Required |
| ------------- | ------------- | ----------------------------- | ------------------------------------------ | -------- |
| text          | String        | Text content to display       | -                                          | Yes      |
| modifier      | Modifier      | Modifier applied to component | Modifier                                   | No       |
| textColor     | Color         | Title text color              | MiuixTheme.colorScheme.onBackgroundVariant | No       |
| insideMargin  | PaddingValues | Component internal padding    | PaddingValues(28.dp, 8.dp)                 | No       |

## Advanced Usage

### Using with Other Components

```kotlin
var checked by remember { mutableStateOf(false) }

Column {
    SmallTitle(text = "Settings")
    Card(
        modifier = Modifier.padding(horizontal = 12.dp).padding(bottom = 12.dp)
    ) {
        SuperSwitch(
            title = "Bluetooth",
            checked = checked,
            onCheckedChange = { checked = it }
        )
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 12.dp)
    )
    SmallTitle(text = "Advanced Settings")
    // Other settings items
}
```

### Custom Styling

```kotlin
SmallTitle(
    text = "Fully Customized Style",
    modifier = Modifier
        .fillMaxWidth()
        .background(Color.LightGray),
    textColor = Color.Blue,
    insideMargin = PaddingValues(32.dp, 12.dp)
)
```