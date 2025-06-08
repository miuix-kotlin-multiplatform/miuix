# BasicComponent

`BasicComponent` 是 Miuix 中的基础标准组件。提供了标题、摘要以及左右两侧的可自定义内容区域，常用于构建列表项、设置项等界面元素。

本项目以此为基础提供了一些扩展组件，方便开发者快速构建符合设计规范的 UI 组件，详见[扩展组件](../components/#扩展组件)的使用。

<div style="position: relative; max-width: 700px; height: 350px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=basicComponent" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.BasicComponent
```

## 基本用法

BasicComponent 组件可以用于展示标题和摘要信息：

```kotlin
BasicComponent(
    title = "设置项标题",
    summary = "这里是设置项的描述文本"
)
```

## 组件变体

### 可点击组件

```kotlin
BasicComponent(
    title = "Wi-Fi",
    summary = "已连接到 MIUI-WiFi",
    onClick = { /* 处理点击事件 */ }
)
```

### 带左侧图标的组件

```kotlin
BasicComponent(
    title = "昵称",
    summary = "一段简介",
    leftAction = {
        Icon(
            modifier = Modifier.padding(end = 16.dp),
            imageVector = MiuixIcons.Useful.Personal,
            contentDescription = "头像图标",
            tint = MiuixTheme.colorScheme.onBackground
        )
    },
    onClick = { /* 处理点击事件 */ }
)
```

### 带右侧操作的组件

```kotlin
var isFlightMode by remember { mutableStateOf(false) }

BasicComponent(
    title = "飞行模式",
    rightActions = {
        Switch(
            checked = isFlightMode,
            onCheckedChange = { isFlightMode = it }
        )
    }
)
```

## 组件状态

### 禁用状态

```kotlin
BasicComponent(
    title = "移动网络",
    summary = "SIM卡未插入",
    enabled = false
)
```

## 属性

### BasicComponent 属性

| 属性名            | 类型                            | 说明                 | 默认值                                   | 是否必须 |
| ----------------- | ------------------------------- | -------------------- | ---------------------------------------- | -------- |
| title             | String?                         | 组件标题             | null                                     | 否       |
| titleColor        | BasicComponentColors            | 标题颜色配置         | BasicComponentDefaults.titleColor()      | 否       |
| summary           | String?                         | 组件摘要             | null                                     | 否       |
| summaryColor      | BasicComponentColors            | 摘要颜色配置         | BasicComponentDefaults.summaryColor()    | 否       |
| leftAction        | @Composable (() -> Unit?)?      | 组件左侧的可组合内容 | null                                     | 否       |
| rightActions      | @Composable RowScope.() -> Unit | 组件右侧的可组合内容 | {}                                       | 否       |
| modifier          | Modifier                        | 应用于组件的修饰符   | Modifier                                 | 否       |
| insideMargin      | PaddingValues                   | 组件内部边距         | BasicComponentDefaults.InsideMargin      | 否       |
| onClick           | (() -> Unit)?                   | 点击组件时触发的回调 | null                                     | 否       |
| holdDownState     | Boolean                         | 组件是否处于按下状态 | false                                    | 否       |
| enabled           | Boolean                         | 组件是否可用         | true                                     | 否       |
| interactionSource | MutableInteractionSource        | 组件的交互源         | remember \{ MutableInteractionSource() } | 否       |

### BasicComponentDefaults 对象

BasicComponentDefaults 对象提供了 BasicComponent 组件的默认值和颜色配置。

#### 常量

| 常量名       | 类型          | 说明           | 默认值               |
| ------------ | ------------- | -------------- | -------------------- |
| InsideMargin | PaddingValues | 组件的内部边距 | PaddingValues(16.dp) |

#### 方法

| 方法名         | 类型                 | 说明             |
| -------------- | -------------------- | ---------------- |
| titleColor()   | BasicComponentColors | 创建标题颜色配置 |
| summaryColor() | BasicComponentColors | 创建摘要颜色配置 |

### BasicComponentColors 类

用于配置组件的颜色状态。

| 属性名        | 类型  | 说明           |
| ------------- | ----- | -------------- |
| color         | Color | 正常状态的颜色 |
| disabledColor | Color | 禁用状态的颜色 |

## 进阶用法

### 复杂布局组件

```kotlin
BasicComponent(
    title = "音量",
    summary = "媒体音量：70%",
        leftAction = {
        Icon(
        modifier = Modifier.padding(end = 16.dp),
            imageVector = MiuixIcons.Useful.Play,
            contentDescription = "音量图标",
            tint = MiuixTheme.colorScheme.onBackground
        )
    },
    rightActions = {
        IconButton(onClick = { /* 减小音量 */ }) {
            Icon(
                imageVector = MiuixIcons.Useful.Remove,
                contentDescription = "减小音量",
                tint = MiuixTheme.colorScheme.onBackground
            )
        }
        Text("70%")
        IconButton(onClick = { /* 增大音量 */ }) {
            Icon(
                imageVector = MiuixIcons.Useful.New,
                contentDescription = "增大音量",
                tint = MiuixTheme.colorScheme.onBackground
            )
        }
    }
)
```

### 自定义样式组件

```kotlin
BasicComponent(
    title = "自定义组件",
    summary = "使用自定义颜色",
    titleColor = BasicComponentColors(
        color = Color.Blue,
        disabledColor = Color.Gray
    ),
    summaryColor = BasicComponentColors(
        color = Color.DarkGray,
        disabledColor = Color.LightGray
    ),
    insideMargin = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
    onClick = { /* 处理选项点击 */ }
)
```

### 列表中使用

```kotlin
val options = listOf("选项1", "选项2", "选项3", "选项4")

Column {
    options.forEach { option ->
        BasicComponent(
            title = option,
            onClick = { /* 处理选项点击 */ }
        )
    }
}
```
