# Icon System

Miuix provides a rich built-in icon system that meets the primary design needs of most applications. These icons are categorized by functionality and usage, making them easy to use in your projects.

## Usage

To use Miuix icons in your project, first import them correctly:

```kotlin
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.icon.MiuixIcons
```

Then, you can use the icons as follows:

```kotlin
// Use the Check icon from the Basic category
Icon(
    imageVector = MiuixIcons.Basic.Check,
    contentDescription = "Check",
    modifier = Modifier.size(24.dp)
)

// Use the Settings icon from the Useful category
Icon(
    imageVector = MiuixIcons.Useful.Settings,
    contentDescription = "Settings",
    modifier = Modifier.size(24.dp)
)
```

## Icon Categories

The Miuix icon system currently includes the following main categories:

### Basic Icons

Basic icons include commonly used basic UI elements such as arrows and checkmarks. These icons are also used in Miuix's own components. Below is the complete list:

- `Check`: Checkmark icon
- `ArrowUpDown`: Up and down arrow icon
- `ArrowUpDownIntegrated`: Integrated up and down arrow icon
- `ArrowRight`: Right arrow icon

### Useful Icons

Useful icons include a large number of functional icons suitable for various application scenarios. Below is the complete list:

| Icon Name         | Description              | Common Usage                         |
| ----------------- | ------------------------ | ------------------------------------ |
| `AddSecret`       | Add encrypted content    | Add passwords, private items, etc.   |
| `Back`            | Back icon                | Navigate back to the previous screen |
| `Blocklist`       | Blocklist icon           | Block users, add to blacklist        |
| `Cancel`          | Cancel icon              | Cancel operations, close dialogs     |
| `Copy`            | Copy icon                | Copy content to clipboard            |
| `Cut`             | Cut icon                 | Cut content to clipboard             |
| `Delete`          | Delete icon              | Delete items, content, or files      |
| `Edit`            | Edit icon                | Edit content, modify information     |
| `ImmersionMore`   | Immersive more options   | Show more options in immersive mode  |
| `Info`            | Info icon                | Show detailed information, tips      |
| `Like`            | Like icon                | Like, favorite functionality         |
| `More`            | More icon                | Show more options or menus           |
| `Move`            | Move icon                | Move items to other locations        |
| `NavigatorSwitch` | Navigation switch icon   | Switch navigation views              |
| `New`             | New icon                 | Create new content, files, or items  |
| `Order`           | Order icon               | Sort content                         |
| `Paste`           | Paste icon               | Paste content from clipboard         |
| `Personal`        | Personal/user icon       | Personal information, user page      |
| `Play`            | Play icon                | Play media content                   |
| `Reboot`          | Reboot icon              | Restart app or system                |
| `Refresh`         | Refresh icon             | Refresh content or page              |
| `Remove`          | Remove icon              | Remove items (soft delete)           |
| `RemoveBlocklist` | Remove blocklist icon    | Unblock                              |
| `RemoveSecret`    | Remove encrypted content | Remove encrypted items               |
| `Rename`          | Rename icon              | Rename files or items                |
| `Restore`         | Restore icon             | Restore deleted content              |
| `Save`            | Save icon                | Save content or changes              |
| `Scan`            | Scan icon                | Scan QR codes, etc.                  |
| `Search`          | Search icon              | Search content                       |
| `SelectAll`       | Select all icon          | Select all items                     |
| `Settings`        | Settings icon            | App or system settings               |
| `Share`           | Share icon               | Share content to other platforms     |
| `Stick`           | Stick icon               | Pin content to the top               |
| `Unlike`          | Unlike icon              | Unlike or unfavorite                 |
| `Unstick`         | Unpin content            | Unpin content                        |
| `Update`          | Update icon              | Update apps or content               |

### Other Icons

The "Other" category includes icons for specific scenarios.

- `GitHub`: GitHub icon
