# Theme System

Miuix provides a complete theme system that allows you to easily maintain a consistent design style throughout your application. The theme system consists of color schemes and text styles.

## Using the Theme

To use the Miuix theme in your application, simply wrap your content in the `MiuixTheme` composable function:

```kotlin
MiuixTheme {
    // Your application content
    Scaffold(
        topBar = { /* ... */ },
    ) { padding ->
        // Main content
    }
}
```

By default, Miuix automatically selects a light or dark theme suitable for the current system settings.

## Color System

The Miuix color system is based on the HyperOS design language and provides a complete set of color schemes, including:

- Primary colors
- Secondary colors
- Background and surface colors
- Text colors
- Special state colors (e.g., disabled state)

### Key Color Properties

Below are some core properties of the color system:

| Property Name      | Description               | Usage                        |
| ------------------ | ------------------------- | ---------------------------- |
| primary            | Primary color             | Switches, buttons, sliders   |
| onPrimary          | Text color on primary     | Text on primary color        |
| primaryContainer   | Primary container         | Components with primary      |
| onPrimaryContainer | Text on primary container | Text on primary containers   |
| secondary          | Secondary color           | Secondary buttons, cards     |
| onSecondary        | Text on secondary         | Text on secondary color      |
| background         | Background color          | App background               |
| onBackground       | Text on background        | Text on background           |
| surface            | Surface color             | Cards, dialogs               |
| onSurface          | Text on surface           | Regular text                 |
| onSurfaceSecondary | Secondary text on surface | Secondary text               |
| outline            | Outline color             | Borders, outlines            |
| dividerLine        | Divider line color        | List dividers                |
| windowDimming      | Window dimming color      | Dialog, dropdown backgrounds |

### Using Colors

In composable functions, you can access the current theme's colors via `MiuixTheme.colorScheme`:

```kotlin
val backgroundColor = MiuixTheme.colorScheme.background
val textColor = MiuixTheme.colorScheme.onBackground

Surface(
    color = backgroundColor,
    modifier = Modifier.fillMaxSize()
) {
    Text(
        text = "Hello Miuix!",
        color = textColor
    )
}
```

### Light and Dark Themes

Miuix provides default light and dark theme color schemes:

- `lightColorScheme()` - Light theme color scheme
- `darkColorScheme()` - Dark theme color scheme

## Text Styles

Miuix provides a set of predefined text styles to maintain consistency in text throughout the application:

| Style Name | Usage                      |
| ---------- | -------------------------- |
| main       | Main text                  |
| title1     | Large title (32sp)         |
| title2     | Medium title (24sp)        |
| title3     | Small title (20sp)         |
| body1      | Body text (16sp)           |
| body2      | Secondary body text (14sp) |
| button     | Button text (17sp)         |
| footnote1  | Footnote (13sp)            |
| footnote2  | Small footnote (11sp)      |

### Using Text Styles

You can access the current theme's text styles via `MiuixTheme.textStyles`:

```kotlin
Text(
    text = "This is a title",
    style = MiuixTheme.textStyles.title2
)

Text(
    text = "This is body content",
    style = MiuixTheme.textStyles.body1
)
```

## Customizing the Theme

You can globally customize the Miuix theme by providing your own `Colors` and `TextStyles` instances:

```kotlin
// Custom color scheme
val customColors = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    background = Color(0xFFF5F5F5),
    // Other colors...
)

// Custom text styles
val customTextStyles = defaultTextStyles(
    title1 = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold
    ),
    // Other text styles...
)

// Apply custom theme
MiuixTheme(
    colors = customColors,
    textStyles = customTextStyles
) {
    // Your application content
}
```

## Follow to System Dark Mode

To automatically follow the system's dark mode switch, you should use the `isSystemInDarkTheme()` function:

```kotlin
@Composable
fun MyApp() {
    val isDarkTheme = isSystemInDarkTheme()
    val colors = if (isDarkTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }
    
    MiuixTheme(colors = colors) {
        // Application content
    }
}
```
