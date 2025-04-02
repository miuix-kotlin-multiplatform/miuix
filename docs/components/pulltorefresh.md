# PullToRefresh

`PullToRefresh` 是 Miuix 中的下拉刷新组件，可为列表和其他可滚动内容提供刷新功能。它提供了具有动画效果的交互式刷新指示器，适用于需要刷新数据的各种场景。

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
Surface{
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

| 属性名             | 类型                   | 默认值                                 | 说明                     |
| ------------------ | ---------------------- | -------------------------------------- | ------------------------ |
| pullToRefreshState | PullToRefreshState     | -                                      | 下拉刷新状态控制器       |
| onRefresh          | () -> Unit             | {}                                     | 刷新触发时的回调函数     |
| modifier           | Modifier               | Modifier                               | 应用于容器的修饰符       |
| color              | Color                  | PullToRefreshDefaults.color            | 刷新指示器的颜色         |
| circleSize         | Dp                     | PullToRefreshDefaults.circleSize       | 刷新指示器圆圈的大小     |
| refreshTexts       | List\<String>          | PullToRefreshDefaults.refreshTexts     | 不同状态下显示的文本列表 |
| refreshTextStyle   | TextStyle              | PullToRefreshDefaults.refreshTextStyle | 刷新文本的样式           |
| content            | @Composable () -> Unit | -                                      | 可滚动内容的可组合函数   |

### PullToRefreshState 类

PullToRefreshState 是控制下拉刷新状态的类，可以通过 `rememberPullToRefreshState()` 创建。

#### 属性

| 属性名                      | 类型         | 说明                |
| --------------------------- | ------------ | ------------------- |
| refreshState                | RefreshState | 当前刷新状态        |
| isRefreshing                | Boolean      | 是否正在刷新        |
| pullProgress                | Float        | 下拉进度（0-1之间） |
| refreshCompleteAnimProgress | Float        | 刷新完成动画进度    |

#### 方法

| 方法名             | 参数                 | 返回类型 | 说明                     |
| ------------------ | -------------------- | -------- | ------------------------ |
| completeRefreshing | (suspend () -> Unit) | -        | 完成刷新并执行提供的操作 |

### PullToRefreshDefaults 对象

PullToRefreshDefaults 对象提供了下拉刷新组件的默认值。

| 属性名           | 类型          | 默认值                                              | 说明                 |
| ---------------- | ------------- | --------------------------------------------------- | -------------------- |
| color            | Color         | Color.Gray                                          | 刷新指示器的默认颜色 |
| circleSize       | Dp            | 20.dp                                               | 指示器圆圈的默认大小 |
| refreshTexts     | List\<String> | ["Pull down to refresh", "Release to refresh", ...] | 默认的刷新文本列表   |
| refreshTextStyle | TextStyle     | TextStyle(fontSize = 14.sp, fontWeight = Bold, ...) | 默认的文本样式       |

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
