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

| Preview                                                                   | Icon Name               | Description                       |
|---------------------------------------------------------------------------|-------------------------|-----------------------------------|
| <img src="/icons/basic/Check.svg" />                                      | `Check`                 | Checkmark icon                    |
| <img src="/icons/basic/ArrowUpDown.svg" />                                | `ArrowUpDown`           | Up and down arrow icon            |
| <img src="/icons/basic/ArrowUpDownIntegrated.svg" style="width: 15px;" /> | `ArrowUpDownIntegrated` | Integrated up and down arrow icon |
| <img src="/icons/basic/ArrowRight.svg" style="width: 15px;" />            | `ArrowRight`            | Right arrow icon                  |
| <img src="/icons/basic/Search.svg" />                                     | `Search`                | Search icon                       |
| <img src="/icons/basic/SearchCleanup.svg" />                              | `SearchCleanup`         | Search cleanup icon               |

### Useful Icons

Useful icons include a large number of functional icons suitable for various application scenarios. Below is the complete list:

| Preview                                         | Icon Name         | Description              | Common Usage                         |
|-------------------------------------------------|-------------------|--------------------------|--------------------------------------|
| <img src="/icons/useful/AddSecret.svg" />       | `AddSecret`       | Add encrypted content    | Add passwords, private items, etc.   |
| <img src="/icons/useful/Back.svg" />            | `Back`            | Back icon                | Navigate back to the previous screen |
| <img src="/icons/useful/Blocklist.svg" />       | `Blocklist`       | Blocklist icon           | Block users, add to blacklist        |
| <img src="/icons/useful/Cancel.svg" />          | `Cancel`          | Cancel icon              | Cancel operations, close dialogs     |
| <img src="/icons/useful/Copy.svg" />            | `Copy`            | Copy icon                | Copy content to clipboard            |
| <img src="/icons/useful/Cut.svg" />             | `Cut`             | Cut icon                 | Cut content to clipboard             |
| <img src="/icons/useful/Delete.svg" />          | `Delete`          | Delete icon              | Delete items, content, or files      |
| <img src="/icons/useful/Edit.svg" />            | `Edit`            | Edit icon                | Edit content, modify information     |
| <img src="/icons/useful/ImmersionMore.svg" />   | `ImmersionMore`   | Immersive more options   | Show more options in immersive mode  |
| <img src="/icons/useful/Info.svg" />            | `Info`            | Info icon                | Show detailed information, tips      |
| <img src="/icons/useful/Like.svg" />            | `Like`            | Like icon                | Like, favorite functionality         |
| <img src="/icons/useful/More.svg" />            | `More`            | More icon                | Show more options or menus           |
| <img src="/icons/useful/Move.svg" />            | `Move`            | Move icon                | Move items to other locations        |
| <img src="/icons/useful/NavigatorSwitch.svg" /> | `NavigatorSwitch` | Navigation switch icon   | Switch navigation views              |
| <img src="/icons/useful/New.svg" />             | `New`             | New icon                 | Create new content, files, or items  |
| <img src="/icons/useful/Order.svg" />           | `Order`           | Order icon               | Sort content                         |
| <img src="/icons/useful/Paste.svg" />           | `Paste`           | Paste icon               | Paste content from clipboard         |
| <img src="/icons/useful/Personal.svg" />        | `Personal`        | Personal/user icon       | Personal information, user page      |
| <img src="/icons/useful/Play.svg" />            | `Play`            | Play icon                | Play media content                   |
| <img src="/icons/useful/Reboot.svg" />          | `Reboot`          | Reboot icon              | Restart app or system                |
| <img src="/icons/useful/Refresh.svg" />         | `Refresh`         | Refresh icon             | Refresh content or page              |
| <img src="/icons/useful/Remove.svg" />          | `Remove`          | Remove icon              | Remove items (soft delete)           |
| <img src="/icons/useful/RemoveBlocklist.svg" /> | `RemoveBlocklist` | Remove blocklist icon    | Unblock                              |
| <img src="/icons/useful/RemoveSecret.svg" />    | `RemoveSecret`    | Remove encrypted content | Remove encrypted items               |
| <img src="/icons/useful/Rename.svg" />          | `Rename`          | Rename icon              | Rename files or items                |
| <img src="/icons/useful/Restore.svg" />         | `Restore`         | Restore icon             | Restore deleted content              |
| <img src="/icons/useful/Save.svg" />            | `Save`            | Save icon                | Save content or changes              |
| <img src="/icons/useful/Scan.svg" />            | `Scan`            | Scan icon                | Scan QR codes, etc.                  |
| <img src="/icons/useful/Search.svg" />          | `Search`          | Search icon              | Search content                       |
| <img src="/icons/useful/SelectAll.svg" />       | `SelectAll`       | Select all icon          | Select all items                     |
| <img src="/icons/useful/Settings.svg" />        | `Settings`        | Settings icon            | App or system settings               |
| <img src="/icons/useful/Share.svg" />           | `Share`           | Share icon               | Share content to other platforms     |
| <img src="/icons/useful/Stick.svg" />           | `Stick`           | Stick icon               | Pin content to the top               |
| <img src="/icons/useful/Unlike.svg" />          | `Unlike`          | Unlike icon              | Unlike or unfavorite                 |
| <img src="/icons/useful/Unstick.svg" />         | `Unstick`         | Unpin content            | Unpin content                        |
| <img src="/icons/useful/Update.svg" />          | `Update`          | Update icon              | Update apps or content               |

### Other Icons

The "Other" category includes icons for specific scenarios.

| Preview                               | Icon Name | Description |
|---------------------------------------|-----------|-------------|
| <img src="/icons/other/GitHub.svg" /> | `GitHub`  | GitHub icon |
