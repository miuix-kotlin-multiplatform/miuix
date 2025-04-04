# 图标系统

Miuix 提供了丰富的内置图标系统，可以满足多数应用的主要设计需求。这些图标按功能和用途分为不同的类别，便于您在项目中使用。

## 使用方法

要在您的项目中使用 Miuix 图标，首先需要正确引入：

```kotlin
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.icon.MiuixIcons
```

然后，您可以通过以下方式使用图标：

```kotlin
// 使用 Basic 分类下的 Check 图标
Icon(
    imageVector = MiuixIcons.Basic.Check,
    contentDescription = "Check",
    modifier = Modifier.size(24.dp)
)

// 使用 Useful 分类下的 Settings 图标
Icon(
    imageVector = MiuixIcons.Useful.Settings,
    contentDescription = "Settings",
    modifier = Modifier.size(24.dp)
)
```

## 图标分类

Miuix 图标系统目前包含以下几个主要分类：

### Basic（基础图标）

基础图标包含一些常用的基本界面元素，如箭头、勾选等，这些图标在 Miuix 本身的组件中也会使用到。以下是完整的列表：

- `Check`: 勾选图标
- `ArrowUpDown`: 上下箭头图标
- `ArrowUpDownIntegrated`: 集成的上下箭头图标
- `ArrowRight`: 向右箭头图标

### Useful（实用图标）

实用图标包含大量常用的功能性图标，适用于各种应用场景。以下是完整的列表：

| 图标名            | 描述               | 常见用途                         |
| ----------------- | ------------------ | -------------------------------- |
| `AddSecret`       | 添加加密内容图标   | 添加密码、隐私项等需要加密的内容 |
| `Back`            | 返回图标           | 导航返回上一级界面               |
| `Blocklist`       | 黑名单图标         | 拉黑用户、添加屏蔽项             |
| `Cancel`          | 取消图标           | 取消操作、关闭弹窗               |
| `Copy`            | 复制图标           | 复制内容到剪贴板                 |
| `Cut`             | 剪切图标           | 剪切内容到剪贴板                 |
| `Delete`          | 删除图标           | 删除项目、内容或文件             |
| `Edit`            | 编辑图标           | 编辑内容、修改信息               |
| `ImmersionMore`   | 沉浸式更多选项图标 | 沉浸模式下显示更多选项           |
| `Info`            | 信息图标           | 显示详细信息、提示               |
| `Like`            | 喜欢图标           | 点赞、收藏功能                   |
| `More`            | 更多图标           | 显示更多选项或菜单               |
| `Move`            | 移动图标           | 移动项目到其他位置               |
| `NavigatorSwitch` | 导航切换图标       | 切换导航视图                     |
| `New`             | 新建图标           | 创建新内容、文件或项目           |
| `Order`           | 排序图标           | 内容排序                         |
| `Paste`           | 粘贴图标           | 从剪贴板粘贴内容                 |
| `Personal`        | 个人/用户图标      | 个人信息、用户页面               |
| `Play`            | 播放图标           | 播放媒体内容                     |
| `Reboot`          | 重启图标           | 重启应用或系统                   |
| `Refresh`         | 刷新图标           | 刷新内容或页面                   |
| `Remove`          | 移除图标           | 移除项目（轻微删除）             |
| `RemoveBlocklist` | 移除黑名单图标     | 解除屏蔽                         |
| `RemoveSecret`    | 移除加密内容图标   | 移除加密项                       |
| `Rename`          | 重命名图标         | 重命名文件或项目                 |
| `Restore`         | 恢复图标           | 恢复删除的内容                   |
| `Save`            | 保存图标           | 保存内容或更改                   |
| `Scan`            | 扫描图标           | 扫描二维码等                     |
| `Search`          | 搜索图标           | 搜索内容                         |
| `SelectAll`       | 全选图标           | 选择所有项目                     |
| `Settings`        | 设置图标           | 应用或系统设置                   |
| `Share`           | 分享图标           | 分享内容到其他平台               |
| `Stick`           | 置顶图标           | 将内容置顶                       |
| `Unlike`          | 取消喜欢图标       | 取消点赞或收藏                   |
| `Unstick`         | 取消置顶图标       | 取消内容置顶                     |
| `Update`          | 更新图标           | 更新应用或内容                   |

### Other（其他图标）

其他类别包含一些特定场景的图标。

- `GitHub`: GitHub 图标
