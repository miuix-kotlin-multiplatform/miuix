# LazyColumn

::: tip
`LazyColumn` 组件是直接继承自 foundation 库的基础组件，具体的使用说明请参考 [LazyColumn](https://developer.android.com/reference/kotlin/androidx/compose/foundation/lazy/package-summary#LazyColumn(androidx.compose.ui.Modifier,androidx.compose.foundation.lazy.LazyListState,androidx.compose.foundation.layout.PaddingValues,kotlin.Boolean,androidx.compose.foundation.layout.Arrangement.Vertical,androidx.compose.ui.Alignment.Horizontal,androidx.compose.foundation.gestures.FlingBehavior,kotlin.Boolean,androidx.compose.foundation.OverscrollEffect,kotlin.Function1))。
:::

Miuix 包下的该组件在原本的 `LazyColumn` 基础上添加了一个额外的 `isEnabledOverScroll` 属性，用于控制是否启用 Miuix 的越界回弹效果。 同时将该组件原有的 `overscrollEffect` 属性设为 `null`。

默认情况下，`LazyColumn` 的 `isEnabledOverScroll` 属性为 `{ platform() == Platform.Android }`，表示仅为安卓平台启用越界回弹效果。