# PullToRefresh

`PullToRefresh` 是 Miuix 中的下拉刷新组件，可为列表和其他可滚动内容提供刷新功能。它提供了具有动画效果的交互式刷新指示器，适用于需要刷新数据的各种场景。

::: warning 注意
该组件只适用于支持触控的场景，并且在网页构建目标中工作不佳!
:::

如需演示，请查看 <a href="https://miuix-kotlin-multiplatform.github.io/miuix-jsCanvas/" target="_blank">Miuix Example</a> 的 DropDown 页。

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.PullToRefresh
import top.yukonga.miuix.kmp.basic.rememberPullToRefreshState
```

## 基本用法

PullToRefresh 组件可以包裹任何可滚动的内容：

```kotlin
val pullToRefreshState = rememberPullToRefreshState()
var items by remember { mutableStateOf(1) }
val scope = rememberCoroutineScope()

Surface {
    PullToRefresh(
        pullToRefreshState = pullToRefreshState,
        onRefresh = {
            scope.launch {
                // 确定刷新状态
                if (pullToRefreshState.isRefreshing) {
                    delay(300) // 模拟刷新操作
                    // 刷新完成
                    pullToRefreshState.completeRefreshing {
                        // 更新数据
                        items++
                    }
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(items) { index ->
                SuperArrow(
                    title = "Item $index",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { /* 点击事件 */ }
                )
            }
        }
    }
}
```

## 组件状态

PullToRefresh 组件有以下几种状态：

1. `Idle`：初始状态，无交互
2. `Pulling`：用户正在下拉但尚未达到刷新阈值
3. `ThresholdReached`：下拉达到阈值，松开可以刷新
4. `Refreshing`：正在刷新
5. `RefreshComplete`：刷新完成，正在回到初始状态

```kotlin
val pullToRefreshState = rememberPullToRefreshState()
// 检查是否正在刷新
if (pullToRefreshState.isRefreshing) {
    // 正在刷新时的逻辑
    delay(2000) // 模拟网络请求
    pullToRefreshState.completeRefreshing {} // 完成刷新时的回调
}
```

## 属性

### PullToRefresh 属性

| 属性名             | 类型                   | 说明                     | 默认值                                 | 是否必须 |
| ------------------ | ---------------------- | ------------------------ | -------------------------------------- | -------- |
| pullToRefreshState | PullToRefreshState     | 下拉刷新状态控制器       | -                                      | 是       |
| onRefresh          | () -> Unit             | 刷新触发时的回调函数     | {}                                     | 是       |
| modifier           | Modifier               | 应用于容器的修饰符       | Modifier                               | 否       |
| contentPadding     | PaddingValues          | 内容区的内边距           | PaddingValues(0.dp)                    | 否       |
| color              | Color                  | 刷新指示器的颜色         | PullToRefreshDefaults.color            | 否       |
| circleSize         | Dp                     | 刷新指示器圆圈的大小     | PullToRefreshDefaults.circleSize       | 否       |
| refreshTexts       | List\<String>          | 不同状态下显示的文本列表 | PullToRefreshDefaults.refreshTexts     | 否       |
| refreshTextStyle   | TextStyle              | 刷新文本的样式           | PullToRefreshDefaults.refreshTextStyle | 否       |
| content            | @Composable () -> Unit | 可滚动内容的可组合函数   | -                                      | 是       |

### PullToRefreshState 类

PullToRefreshState 用于控制下拉刷新状态，可通过 `rememberPullToRefreshState()` 创建。

#### 属性

| 属性名                      | 类型         | 说明                | 是否必须 |
| --------------------------- | ------------ | ------------------- | -------- |
| refreshState                | RefreshState | 当前刷新状态        | 是       |
| isRefreshing                | Boolean      | 是否正在刷新        | 是       |
| pullProgress                | Float        | 下拉进度（0-1之间） | 是       |
| refreshCompleteAnimProgress | Float        | 刷新完成动画进度    | 是       |

#### 方法

| 方法名             | 参数                 | 说明                     | 是否必须 |
| ------------------ | -------------------- | ------------------------ | -------- |
| completeRefreshing | (suspend () -> Unit) | 完成刷新并执行提供的操作 | 是       |

### PullToRefreshDefaults 对象

PullToRefreshDefaults 提供下拉刷新组件的默认值。

| 属性名           | 类型          | 说明                 | 默认值                                                                                    |
| ---------------- | ------------- | -------------------- | ----------------------------------------------------------------------------------------- |
| color            | Color         | 刷新指示器的默认颜色 | Color.Gray                                                                                |
| circleSize       | Dp            | 指示器圆圈的默认大小 | 20.dp                                                                                     |
| refreshTexts     | List\<String> | 默认的刷新文本列表   | ["Pull down to refresh", "Release to refresh", "Refreshing...", "Refreshed successfully"] |
| refreshTextStyle | TextStyle     | 默认的文本样式       | TextStyle(fontSize = 14.sp, fontWeight = Bold, color = color)                             |

## 进阶用法

### 自定义刷新指示器颜色

```kotlin
val pullToRefreshState = rememberPullToRefreshState()

PullToRefresh(
    pullToRefreshState = pullToRefreshState,
    color = Color.Blue,
    onRefresh = { 
        // 执行刷新操作
    }
) {
    // 内容
}
```

### 自定义刷新文本

```kotlin
val pullToRefreshState = rememberPullToRefreshState()

PullToRefresh(
    pullToRefreshState = pullToRefreshState,
    refreshTexts = listOf(
        "下拉刷新",
        "松开刷新",
        "正在刷新",
        "刷新成功"
    ),
    onRefresh = {
        // 执行刷新操作
    }
) {
    // 内容
}
```

### 结合加载状态使用

```kotlin
val pullToRefreshState = rememberPullToRefreshState()
var items by remember { mutableStateOf(List(5) { "Item $it" }) }
val scope = rememberCoroutineScope()

PullToRefresh(
    pullToRefreshState = pullToRefreshState,
    onRefresh = {
        scope.launch {
            delay(2000) // 模拟刷新操作
            pullToRefreshState.completeRefreshing {
                items = List(8) { "Updated Item $it" }
            }
        }
    }
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items.size) { item ->
            Text(
                text = items[item],
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            )
        }
    }
}
```
