# Button

`Button` 是 Miuix 中的基础交互组件，用于触发操作或事件。提供了多种风格选择，包括主要按钮、次要按钮和文本按钮。

<div style="position: relative; max-width: 700px; height: 200px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=button" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.Button
```

## 基本用法

Button 组件可以用于触发操作或事件：

```kotlin
Button(
    onClick = { /* 处理点击事件 */ }
) {
    Text("按钮")
}
```

## 按钮类型

Miuix 提供了多种类型的按钮，适用于不同的场景和重要程度：

### 主要按钮（Primary Button）

```kotlin
Button(
    onClick = { /* 处理点击事件 */ },
    colors = ButtonDefaults.buttonColorsPrimary()
) {
    Text("主要按钮")
}
```

### 次要按钮（Secondary Button）

```kotlin
Button(
    onClick = { /* 处理点击事件 */ },
    colors = ButtonDefaults.buttonColors()
) {
    Text("次要按钮")
}
```

### 文本按钮（Text Button）

```kotlin
TextButton(
    text = "文本按钮",
    onClick = { /* 处理点击事件 */ }
)
```

## 组件状态

### 禁用状态

```kotlin
Button(
    onClick = { /* 处理点击事件 */ },
    enabled = false
) {
    Text("禁用按钮")
}
```

## 属性

### Button 属性

| 属性名       | 类型                            | 说明                     | 默认值                        | 是否必须 |
| ------------ | ------------------------------- | ------------------------ | ----------------------------- | -------- |
| onClick      | () -> Unit                      | 点击按钮时触发的回调     | -                             | 是       |
| modifier     | Modifier                        | 应用于按钮的修饰符       | Modifier                      | 否       |
| enabled      | Boolean                         | 按钮是否可点击           | true                          | 否       |
| cornerRadius | Dp                              | 按钮圆角半径             | ButtonDefaults.CornerRadius   | 否       |
| minWidth     | Dp                              | 按钮最小宽度             | ButtonDefaults.MinWidth       | 否       |
| minHeight    | Dp                              | 按钮最小高度             | ButtonDefaults.MinHeight      | 否       |
| colors       | ButtonColors                    | 按钮颜色配置             | ButtonDefaults.buttonColors() | 否       |
| insideMargin | PaddingValues                   | 按钮内部边距             | ButtonDefaults.InsideMargin   | 否       |
| content      | @Composable RowScope.() -> Unit | 按钮内容区域的可组合函数 | -                             | 是       |

### TextButton 属性

| 属性名       | 类型             | 说明                 | 默认值                            | 是否必须 |
| ------------ | ---------------- | -------------------- | --------------------------------- | -------- |
| text         | String           | 按钮显示的文本       | -                                 | 是       |
| onClick      | () -> Unit       | 点击按钮时触发的回调 | -                                 | 是       |
| modifier     | Modifier         | 应用于按钮的修饰符   | Modifier                          | 否       |
| enabled      | Boolean          | 按钮是否可点击       | true                              | 否       |
| colors       | TextButtonColors | 文本按钮颜色配置     | ButtonDefaults.textButtonColors() | 否       |
| cornerRadius | Dp               | 按钮圆角半径         | ButtonDefaults.CornerRadius       | 否       |
| minWidth     | Dp               | 按钮最小宽度         | ButtonDefaults.MinWidth           | 否       |
| minHeight    | Dp               | 按钮最小高度         | ButtonDefaults.MinHeight          | 否       |
| insideMargin | PaddingValues    | 按钮内部边距         | ButtonDefaults.InsideMargin       | 否       |

### ButtonDefaults 对象

ButtonDefaults 对象提供了按钮组件的默认值和颜色配置。

#### 常量

| 常量名       | 类型          | 说明           | 默认值               |
| ------------ | ------------- | -------------- | -------------------- |
| MinWidth     | Dp            | 按钮的最小宽度 | 58.dp                |
| MinHeight    | Dp            | 按钮的最小高度 | 40.dp                |
| CornerRadius | Dp            | 按钮的圆角半径 | 16.dp                |
| InsideMargin | PaddingValues | 按钮的内部边距 | PaddingValues(16.dp) |

#### 方法

| 方法名                    | 类型             | 说明                       |
| ------------------------- | ---------------- | -------------------------- |
| buttonColors()            | ButtonColors     | 创建次要按钮的颜色配置     |
| buttonColorsPrimary()     | ButtonColors     | 创建主要按钮的颜色配置     |
| textButtonColors()        | TextButtonColors | 创建次要文本按钮的颜色配置 |
| textButtonColorsPrimary() | TextButtonColors | 创建主要文本按钮的颜色配置 |

## 进阶用法

### 带图标按钮

```kotlin
Button(
    onClick = { /* 处理点击事件 */ }
) {
    Icon(
        imageVector = MiuixIcons.Useful.Like,
        contentDescription = "图标"
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text("带图标按钮")
    }
```

### 自定义样式按钮

```kotlin
Button(
    onClick = { /* 处理点击事件 */ },
    colors = ButtonDefaults.buttonColors(
        color = Color.Red.copy(alpha = 0.7f)
    ),
    cornerRadius = 8.dp
) {
    Text("自定义按钮")
}
```

### 加载状态按钮

```kotlin
var isLoading by remember { mutableStateOf(false) }
val scope = rememberCoroutineScope()

Button(
    onClick = {
        isLoading = true
        // 模拟操作
        scope.launch {
            delay(2000)
            isLoading = false
        }
    },
    enabled = !isLoading
) {
     AnimatedVisibility(
        visible = isLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(end = 8.dp),
            size = 20.dp,
            strokeWidth = 4.dp
        )
    }
    Text("提交")
}
```
