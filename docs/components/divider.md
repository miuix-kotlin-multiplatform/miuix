# Divider

`Divider` is a basic layout component in Miuix used to separate content in lists and layouts. It provides both horizontal and vertical divider styles.

<div style="position: relative; max-width: 700px; height: 160px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=divider" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.HorizontalDivider // Horizontal divider
import top.yukonga.miuix.kmp.basic.VerticalDivider   // Vertical divider
```

## Basic Usage

### Horizontal Divider

Horizontal divider is used to separate vertically arranged content:

```kotlin
Column {
    Text("Content Above")
    HorizontalDivider()
    Text("Content Below")
}
```

### Vertical Divider

Vertical divider is used to separate horizontally arranged content:

```kotlin
Row {
    Text("Left Content")
    VerticalDivider()
    Text("Right Content")
}
```

## Properties

### HorizontalDivider Properties

| Property Name | Type     | Description                     | Default Value                | Required |
| ------------- | -------- | ------------------------------- | ---------------------------- | -------- |
| modifier      | Modifier | Modifier applied to the divider | Modifier                     | No       |
| thickness     | Dp       | Thickness of the divider        | DividerDefaults.Thickness    | No       |
| color         | Color    | Color of the divider            | DividerDefaults.DividerColor | No       |

### VerticalDivider Properties

| Property Name | Type     | Description                     | Default Value                | Required |
| ------------- | -------- | ------------------------------- | ---------------------------- | -------- |
| modifier      | Modifier | Modifier applied to the divider | Modifier                     | No       |
| thickness     | Dp       | Thickness of the divider        | DividerDefaults.Thickness    | No       |
| color         | Color    | Color of the divider            | DividerDefaults.DividerColor | No       |

### DividerDefaults Object

The DividerDefaults object provides default values for the divider components.

#### Constants

| Constant Name | Type  | Description                  | Default Value                      |
| ------------- | ----- | ---------------------------- | ---------------------------------- |
| Thickness     | Dp    | Default thickness of divider | 0.75.dp                            |
| DividerColor  | Color | Default color of divider     | MiuixTheme.colorScheme.dividerLine |

## Advanced Usage

### Custom Thickness Divider

```kotlin
Column {
    Text("Content Above")
    HorizontalDivider(
        thickness = 2.dp
    )
    Text("Content Below")
}
```

### Custom Color Divider

```kotlin
Column {
    Text("Content Above")
    HorizontalDivider(
        color = Color.Red
    )
    Text("Content Below")
}
```

### Divider with Padding

```kotlin
Column {
    Text("Content Above")
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Text("Content Below")
}
```

### Using Vertical Divider Between Buttons

```kotlin
Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) {
    Button(onClick = { /* Handle click event */ }) {
        Text("Cancel")
    }
    VerticalDivider(
        modifier = Modifier.height(24.dp)
    )
    Button(onClick = { /* Handle click event */ }) {
        Text("Confirm")
    }
}
```
