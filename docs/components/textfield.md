# TextField

`TextField` 是 Miuix 中的基础输入组件，用于接收用户的文本输入。组件提供了丰富的自定义选项，支持标签动画、前置和后置图标等功能。

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.TextField
```

## 基本用法

TextField 组件可以用于获取用户输入：

```kotlin
var text by remember { mutableStateOf("") }
TextField(
    value = text,
    onValueChange = { text = it },
    label = "用户名"
)
```

## 输入框类型

### 带标签输入框

标签会在输入框获得焦点或有内容时自动移动到顶部：

```kotlin
var text by remember { mutableStateOf("") }
TextField(
    value = text,
    onValueChange = { text = it },
    label = "邮箱地址"
)
```

### 使用标签作为占位符

```kotlin
var text by remember { mutableStateOf("") }
TextField(
    value = text,
    onValueChange = { text = it },
    label = "请输入内容",
    useLabelAsPlaceholder = true
)
```

### 禁用状态

```kotlin
var text by remember { mutableStateOf("") }
TextField(
    value = text,
    onValueChange = { text = it },
    label = "禁用输入框",
    enabled = false
)
```

### 只读状态

```kotlin
var text by remember { mutableStateOf("这是只读内容") }
TextField(
    value = text,
    onValueChange = { text = it },
    label = "只读输入框",
    readOnly = true
)
```

## 属性

### TextField 属性

| 属性名                | 类型                                         | 默认值                                               | 说明                   |
| --------------------- | -------------------------------------------- | ---------------------------------------------------- | ---------------------- |
| value                 | String 或 TextFieldValue                     | -                                                    | 输入框的文本值         |
| onValueChange         | (String) -> Unit 或 (TextFieldValue) -> Unit | -                                                    | 文本变化时的回调函数   |
| modifier              | Modifier                                     | Modifier                                             | 应用于输入框的修饰符   |
| insideMargin          | DpSize                                       | DpSize(16.dp, 16.dp)                                 | 输入框内部边距         |
| backgroundColor       | Color                                        | MiuixTheme.colorScheme.secondaryContainer            | 输入框背景颜色         |
| cornerRadius          | Dp                                           | 16.dp                                                | 输入框圆角半径         |
| label                 | String                                       | ""                                                   | 输入框标签文本         |
| labelColor            | Color                                        | MiuixTheme.colorScheme.onSecondaryContainer          | 标签文本颜色           |
| useLabelAsPlaceholder | Boolean                                      | false                                                | 是否使用标签作为占位符 |
| enabled               | Boolean                                      | true                                                 | 输入框是否可用         |
| readOnly              | Boolean                                      | false                                                | 输入框是否只读         |
| textStyle             | TextStyle                                    | MiuixTheme.textStyles.main                           | 输入文本样式           |
| keyboardOptions       | KeyboardOptions                              | KeyboardOptions.Default                              | 键盘选项配置           |
| keyboardActions       | KeyboardActions                              | KeyboardActions.Default                              | 键盘操作配置           |
| leadingIcon           | @Composable (() -> Unit)?                    | null                                                 | 前置图标               |
| trailingIcon          | @Composable (() -> Unit)?                    | null                                                 | 后置图标               |
| singleLine            | Boolean                                      | false                                                | 是否为单行输入         |
| maxLines              | Int                                          | 如果 singleLine 为 true 则为 1，否则为 Int.MAX_VALUE | 最大行数               |
| minLines              | Int                                          | 1                                                    | 最小行数               |
| visualTransformation  | VisualTransformation                         | VisualTransformation.None                            | 视觉转换器             |
| onTextLayout          | (TextLayoutResult) -> Unit                   | {}                                                   | 文本布局变化回调       |
| interactionSource     | MutableInteractionSource?                    | null                                                 | 交互源                 |

## 进阶用法

### 带图标输入框

```kotlin
var text by remember { mutableStateOf("") }
TextField(
    value = text,
    onValueChange = { text = it },
    label = "搜索",
    leadingIcon = {
        Icon(
            imageVector = MiuixIcons.Useful.Search,
            contentDescription = "搜索图标",
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
)
```

### 密码输入框

```kotlin
var password by remember { mutableStateOf("") }
var passwordVisible by remember { mutableStateOf(false) }
TextField(
    value = password,
    onValueChange = { password = it },
    label = "密码",
    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    trailingIcon = {
        IconButton(
            onClick = { passwordVisible = !passwordVisible },
            modifier = Modifier.padding(end = 12.dp)
        ) {
            Icon(
                imageVector = MiuixIcons.Useful.Rename,
                tint = if (passwordVisible) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.onSecondaryContainer,
                contentDescription = if (passwordVisible) "隐藏密码" else "显示密码"
            )
        }
    }
)
```

### 带验证的输入框

```kotlin
var email by remember { mutableStateOf("") }
var isError by remember { mutableStateOf(false) }
var errorColor = Color.Red.copy(0.3f)
val emailPattern = remember { Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") }
Column {
    TextField(
        value = email,
        onValueChange = {
            email = it
            isError = email.isNotEmpty() && !emailPattern.matches(email)
        },
        label = "电子邮箱",
        labelColor = if (isError) errorColor else MiuixTheme.colorScheme.onSecondaryContainer,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
    if (isError) {
        Text(
            text = "请输入有效的邮箱地址",
            color = errorColor,
                style = MiuixTheme.textStyles.body2,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}
```

### 自定义样式

```kotlin
var text by remember { mutableStateOf("") }
TextField(
    value = text,
    onValueChange = { text = it },
    label = "自定义输入框",
    cornerRadius = 8.dp,
    backgroundColor = MiuixTheme.colorScheme.primary.copy(alpha = 0.1f),
    textStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = MiuixTheme.colorScheme.primary
    )
)
```

### 使用 TextFieldValue

当需要更细致地控制文本选择和光标位置时：

```kotlin
var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
TextField(
    value = textFieldValue,
    onValueChange = { textFieldValue = it },
    label = "高级输入控制",
    // TextFieldValue 提供了对文本、选择范围和光标位置的控制
)
```
