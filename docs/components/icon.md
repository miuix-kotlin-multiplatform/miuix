# Icon

`Icon` is a fundamental icon component in Miuix used to display various vector icons, bitmap icons, or custom drawn content in the interface. It provides multiple ways to render icons to accommodate different icon resource types.

To follow night mode or theme changes, you need to actively use the `tint` property of the `Icon` component to set the icon color, commonly using `MiuixTheme.colorScheme.onBackground`.

<div style="position: relative; max-width: 700px; height: 120px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=icon" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.Icon
```

## Basic Usage

The Icon component can be used to display icons:

```kotlin
Icon(
    imageVector = MiuixIcons.Useful.Like,
    contentDescription = "Like Icon"
)
```

## Icon Types

Miuix Icon supports multiple types of icon resources:

### Vector Icon

```kotlin
Icon(
    imageVector = MiuixIcons.Useful.Settings,
    contentDescription = "Settings Icon"
)
```

### Bitmap Icon

```kotlin
val bitmap = ImageBitmap(...)
Icon(
    bitmap = bitmap,
    contentDescription = "Bitmap Icon"
)
```

### Custom Painter

```kotlin
val customPainter = remember { /* Custom Painter */ }

Icon(
    painter = customPainter,
    contentDescription = "Custom Icon"
)
```

## Component States

### Custom Color

```kotlin
Icon(
    imageVector = MiuixIcons.Useful.Personal,
    contentDescription = "Personal Icon",
    tint = Color.Red
)
```

### Original Color (No Tinting)

```kotlin
Icon(
    imageVector = MiuixIcons.Useful.Like,
    contentDescription = "Like Icon",
    tint = Color.Unspecified // Default
)
```

## Properties

### Icon Properties (ImageVector Version)

| Property Name      | Type        | Description                    | Default Value              | Required |
| ------------------ | ----------- | ------------------------------ | -------------------------- | -------- |
| imageVector        | ImageVector | Vector icon to draw            | -                          | Yes      |
| contentDescription | String?     | Accessibility description text | -                          | No       |
| modifier           | Modifier    | Modifier applied to the icon   | Modifier                   | No       |
| tint               | Color       | Color tint applied to the icon | IconDefaults.DefaultTint() | No       |

### Icon Properties (ImageBitmap Version)

| Property Name      | Type        | Description                    | Default Value              | Required |
| ------------------ | ----------- | ------------------------------ | -------------------------- | -------- |
| bitmap             | ImageBitmap | Bitmap icon to draw            | -                          | Yes      |
| contentDescription | String?     | Accessibility description text | -                          | No       |
| modifier           | Modifier    | Modifier applied to the icon   | Modifier                   | No       |
| tint               | Color       | Color tint applied to the icon | IconDefaults.DefaultTint() | No       |

### Icon Properties (Painter Version)

| Property Name      | Type     | Description                    | Default Value              | Required |
| ------------------ | -------- | ------------------------------ | -------------------------- | -------- |
| painter            | Painter  | Painter to use                 | -                          | Yes      |
| contentDescription | String?  | Accessibility description text | -                          | No       |
| modifier           | Modifier | Modifier applied to the icon   | Modifier                   | No       |
| tint               | Color    | Color tint applied to the icon | IconDefaults.DefaultTint() | No       |

### IconDefaults Object

The IconDefaults object provides default configurations for the Icon component.

#### Methods

| Method Name | Return Type | Description                                    |
| ----------- | ----------- | ---------------------------------------------- |
| DefaultTint | Color       | Returns default tint color (Color.Unspecified) |

## Advanced Usage

### Custom Size

```kotlin
Icon(
    imageVector = MiuixIcons.Useful.Like,
    contentDescription = "Like Icon",
    modifier = Modifier.size(32.dp)
)
```

### Using with Other Components

```kotlin
Button(
    onClick = { /* Handle click event */ }
) {
    Icon(
        imageVector = MiuixIcons.Useful.Save,
        contentDescription = "Download Icon"
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text("Download")
}
```

### Custom Style

```kotlin
Icon(
    imageVector = MiuixIcons.Useful.Info,
    contentDescription = "Warning Icon",
    tint = Color(0xFFFFA500),
    modifier = Modifier
        .size(48.dp)
        .background(
            color = Color.LightGray.copy(alpha = 0.3f),
            shape = CircleShape
        )
        .padding(8.dp)
)
```

### Dynamic Icon

```kotlin
var isSelected by remember { mutableStateOf(false) }

Icon(
    imageVector = if (isSelected) MiuixIcons.Useful.Like else MiuixIcons.Useful.Unlike,
    contentDescription = "Like Icon",
    modifier = Modifier.clickable { isSelected = !isSelected }
)
```
