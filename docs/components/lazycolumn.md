# LazyColumn

::: tip
The `LazyColumn` component is directly inherited from the foundation library's basic component. For detailed usage instructions, please refer to [LazyColumn](https://developer.android.com/reference/kotlin/androidx/compose/foundation/lazy/package-summary#LazyColumn(androidx.compose.ui.Modifier,androidx.compose.foundation.lazy.LazyListState,androidx.compose.foundation.layout.PaddingValues,kotlin.Boolean,androidx.compose.foundation.layout.Arrangement.Vertical,androidx.compose.ui.Alignment.Horizontal,androidx.compose.foundation.gestures.FlingBehavior,kotlin.Boolean,androidx.compose.foundation.OverscrollEffect,kotlin.Function1)).
:::

The Miuix version of this component adds an extra `isEnabledOverScroll` property to the original `LazyColumn`, which controls whether to enable Miuix's overscroll bounce effect. At the same time, it sets the component's original `overscrollEffect` property to `null`.

By default, the `isEnabledOverScroll` property of `LazyColumn` is set to `{ platform() == Platform.Android }`, indicating that the overscroll bounce effect is enabled only for the Android platform.