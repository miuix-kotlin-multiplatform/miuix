# SuperSwitch

`SuperSwitch` is a switch component in Miuix that provides a title, summary, and a switch control on the right. It supports click interaction and is commonly used in settings items and preference toggles.

<div style="position: relative; max-width: 700px; height: 231px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=superSwitch" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.extra.SuperSwitch
```

## Basic Usage

The SuperSwitch component provides basic switch functionality:

```kotlin
var isChecked by remember { mutableStateOf(false) }

SuperSwitch(
    title = "Switch Option",
    checked = isChecked,
    onCheckedChange = { isChecked = it }
)
```

## Switch with Summary

```kotlin
var wifiEnabled by remember { mutableStateOf(false) }

SuperSwitch(
    title = "WiFi",
    summary = "Turn on to connect to wireless networks",
    checked = wifiEnabled,
    onCheckedChange = { wifiEnabled = it }
)
```

## Component States

### Disabled State

```kotlin
SuperSwitch(
    title = "Disabled Switch",
    summary = "This switch is currently unavailable",
    checked = true,
    onCheckedChange = {},
    enabled = false
)
```

## Properties

### SuperSwitch Properties

| Property Name   | Type                            | Description                              | Default Value                         | Required |
| --------------- | ------------------------------- | ---------------------------------------- | ------------------------------------- | -------- |
| checked         | Boolean                         | Switch checked state                     | -                                     | Yes      |
| onCheckedChange | ((Boolean) -> Unit)?            | Switch state change callback             | -                                     | Yes      |
| title           | String                          | Switch item title                        | -                                     | Yes      |
| titleColor      | BasicComponentColors            | Title text color configuration           | BasicComponentDefaults.titleColor()   | No       |
| summary         | String?                         | Switch item summary                      | null                                  | No       |
| summaryColor    | BasicComponentColors            | Summary text color configuration         | BasicComponentDefaults.summaryColor() | No       |
| leftAction      | @Composable (() -> Unit)?       | Custom left content                      | null                                  | No       |
| rightActions    | @Composable RowScope.() -> Unit | Custom right content (before switch)     | {}                                    | No       |
| switchColors    | SwitchColors                    | Switch control color configuration       | SwitchDefaults.switchColors()         | No       |
| modifier        | Modifier                        | Component modifier                       | Modifier                              | No       |
| insideMargin    | PaddingValues                   | Component internal content padding       | BasicComponentDefaults.InsideMargin   | No       |
| onClick         | (() -> Unit)?                   | Additional callback on switch item click | null                                  | No       |
| holdDownState   | Boolean                         | Whether the component is held down       | false                                 | No       |
| enabled         | Boolean                         | Component interactive state              | true                                  | No       |

## Advanced Usage

### With Left Icon

```kotlin
var enabled by remember { mutableStateOf(false) }

SuperSwitch(
    title = "Test",
    summary = "Enable to allow testing",
    checked = enabled,
    onCheckedChange = { enabled = it },
    leftAction = {
        Icon(
            imageVector = MiuixIcons.Useful.Order,
            contentDescription = "Command Icon",
            tint = MiuixTheme.colorScheme.onBackground,
            modifier = Modifier.padding(end = 12.dp)
        )
    }
)
```

### With Right Additional Content

```kotlin
var locationEnabled by remember { mutableStateOf(false) }

SuperSwitch(
    title = "Location Services",
    summary = "Allow apps to access location information",
    checked = locationEnabled,
    onCheckedChange = { locationEnabled = it },
    rightActions = {
        Text(
            text = if (locationEnabled) "Enabled" else "Disabled",
            color = MiuixTheme.colorScheme.onSurfaceVariantActions,
            modifier = Modifier.padding(end = 6.dp)
        )
    }
)
```

### With Animated Content Visibility

```kotlin
var parentEnabled by remember { mutableStateOf(false) }
var childEnabled by remember { mutableStateOf(false) }

Column {
    SuperSwitch(
        title = "Main Setting",
        summary = "Turn on to show more options",
        checked = parentEnabled,
        onCheckedChange = { parentEnabled = it }
    )
    AnimatedVisibility(
        visible = parentEnabled
    ) {
        SuperSwitch(
            title = "Sub Setting",
            summary = "Only available when main setting is enabled",
            checked = childEnabled,
            onCheckedChange = { childEnabled = it }
        )
    }
}
```

### Custom Colors

```kotlin
var customEnabled by remember { mutableStateOf(false) }

SuperSwitch(
    title = "Custom Colors",
    titleColor = BasicComponentDefaults.titleColor(
        color = MiuixTheme.colorScheme.primary
    ),
    summary = "Switch with custom colors",
    summaryColor = BasicComponentDefaults.summaryColor(
        color = MiuixTheme.colorScheme.secondary
    ),
    checked = customEnabled,
    onCheckedChange = { customEnabled = it },
    switchColors = SwitchDefaults.switchColors(
        checkedThumbColor = MiuixTheme.colorScheme.primary,
        checkedTrackColor = MiuixTheme.colorScheme.primary.copy(alpha = 0.2f)
    )
)
```

### Use with Dialog

```kotlin
var showDialog = remember { mutableStateOf(false) }
var option by remember { mutableStateOf(false) }

Scaffold {
    SuperArrow(
        title = "Advanced Settings",
        onClick = { showDialog.value = true },
        holdDownState = showDialog.value
    )
    SuperDialog(
        title = "Advanced Settings",
        show = showDialog,
        onDismissRequest = { showDialog.value = false }
    ) {
        Card {
            SuperSwitch(
                title = "Experimental Features",
                summary = "Enable features under development",
                checked = option,
                onCheckedChange = { option = it }
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            TextButton(
                text = "Cancel",
                onClick = { showDialog.value = false },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(16.dp))
            TextButton(
                text = "Confirm",
                onClick = { showDialog.value = false },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColorsPrimary()
            )
        }
    }
}
```
