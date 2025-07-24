// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import top.yukonga.miuix.kmp.basic.LocalPullToRefreshState
import top.yukonga.miuix.kmp.basic.PullToRefreshState
import top.yukonga.miuix.kmp.basic.RefreshState
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import kotlin.math.abs

@Stable
class ScrollCoordinatorState {
    var currentPriority by mutableStateOf(ScrollPriority.None)
        internal set
}

@Stable
enum class ScrollPriority {
    None,
    TopAppBar,
    PullToRefresh,
    OverScroll
}

@Composable
fun rememberScrollCoordinator(
    topAppBarScrollBehavior: ScrollBehavior?,
    pullToRefreshState: PullToRefreshState? = LocalPullToRefreshState.current,
    overScrollState: OverScrollState = LocalOverScrollState.current
): ScrollCoordinatorConnection {
    return remember(topAppBarScrollBehavior, pullToRefreshState, overScrollState) {
        ScrollCoordinatorConnection(
            topAppBarScrollBehavior = topAppBarScrollBehavior,
            pullToRefreshState = pullToRefreshState,
            overScrollState = overScrollState
        )
    }
}

@Stable
class ScrollCoordinatorConnection(
    private val topAppBarScrollBehavior: ScrollBehavior?,
    private val pullToRefreshState: PullToRefreshState?,
    private val overScrollState: OverScrollState
) : NestedScrollConnection {

    val state = ScrollCoordinatorState()

    /**
     * 判断是否应该处理 TopAppBar
     */
    private fun shouldHandleTopAppBar(available: Offset): Boolean {
        val topAppBarState = topAppBarScrollBehavior?.state ?: return false
        val canExpandMore = topAppBarState.heightOffset < 0f
        val canCollapseMore = topAppBarState.heightOffset > topAppBarState.heightOffsetLimit

        // 如果下拉刷新已经被触发，则不允许 TopAppBar 收起
        val refreshState = pullToRefreshState?.refreshState
        if (refreshState != null && refreshState != RefreshState.Idle && refreshState != RefreshState.Refreshing) {
            // 下拉刷新激活时，只允许 TopAppBar 展开，不允许收起
            return when {
                // 向下滚动且 TopAppBar 可以展开
                available.y > 0 && canExpandMore -> true
                else -> false
            }
        }

        return when {
            // 向上滚动且 TopAppBar 可以收起
            available.y < 0 && canCollapseMore -> true
            // 向下滚动且 TopAppBar 可以展开
            available.y > 0 && canExpandMore -> true
            else -> false
        }
    }

    /**
     * 判断是否应该处理下拉刷新
     */
    private fun shouldHandlePullToRefresh(available: Offset, source: NestedScrollSource): Boolean {
        val refreshState = pullToRefreshState?.refreshState

        return when {
            // 只在手动滚动时处理下拉刷新
            source != NestedScrollSource.UserInput -> false
            // 如果下拉刷新已经被触发，则优先处理所有滚动事件
            refreshState != null && refreshState != RefreshState.Idle -> true
            // 只在向下滚动时处理
            available.y <= 0 -> false
            // TopAppBar 完全展开后才能下拉刷新
            topAppBarScrollBehavior?.state?.heightOffset == 0f -> true
            else -> false
        }
    }

    /**
     * 判断是否应该处理越界回弹
     */
    private fun shouldHandleOverScroll(available: Offset, source: NestedScrollSource = NestedScrollSource.SideEffect): Boolean {
        return when (source) {
            // 惯性滚动时：无条件允许
            NestedScrollSource.SideEffect -> {
                true
            }
            // 手动滚动时：向下滑动到底部无条件允许，向上滑动到顶部需要检查条件
            NestedScrollSource.UserInput -> {
                // 向下滑动到底部：无条件允许越界回弹
                if (available.y < 0) {
                    true
                } else {
                    // 向上滑动到顶部：需要检查其他条件
                    val topAppBarFullyExpanded = topAppBarScrollBehavior?.state?.heightOffset == 0f
                    val notInPullToRefresh = pullToRefreshState?.refreshState == RefreshState.Idle
                    topAppBarFullyExpanded && notInPullToRefresh
                }
            }

            else -> false
        }
    }

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        var remainingAvailable = available

        val refreshState = pullToRefreshState?.refreshState
        if (refreshState != null && refreshState == RefreshState.Refreshing) {
            // 如果下拉刷新已经被触发，则不允许其他组件处理滚动
            state.currentPriority = ScrollPriority.PullToRefresh
            return available
        }

        // 1. 优先处理 TopAppBar（向上滚动收起）
        if (available.y < 0 && shouldHandleTopAppBar(available)) {
            state.currentPriority = ScrollPriority.TopAppBar
            val topbarConsumer = topAppBarScrollBehavior?.nestedScrollConnection?.onPreScroll(available, source) ?: Offset.Zero
            remainingAvailable -= topbarConsumer
        }

        // 2. 处理下拉刷新的预滚动
        if (shouldHandlePullToRefresh(remainingAvailable, source)) {
            pullToRefreshState?.let { refreshState ->
                val connection = refreshState.createNestedScrollConnection(overScrollState)
                val refreshConsumed = connection.onPreScroll(remainingAvailable, source)
                if (refreshConsumed != Offset.Zero) {
                    state.currentPriority = ScrollPriority.PullToRefresh
                    remainingAvailable -= refreshConsumed
                }
            }
        }

        return available - remainingAvailable
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        var remainingAvailable = available


        val refreshState = pullToRefreshState?.refreshState
        if (refreshState != null && refreshState == RefreshState.Refreshing) {
            // 如果下拉刷新已经被触发，则不允许其他组件处理滚动
            state.currentPriority = ScrollPriority.PullToRefresh
            return available
        }

        // 1. TopAppBar 展开（向下滚动时）
        if (remainingAvailable.y > 0 && shouldHandleTopAppBar(remainingAvailable)) {
            state.currentPriority = ScrollPriority.TopAppBar
            val topbarConsumer = topAppBarScrollBehavior?.nestedScrollConnection?.onPostScroll(consumed, remainingAvailable, source) ?: Offset.Zero
            remainingAvailable -= topbarConsumer
        }

        // 2. 下拉刷新
        if (remainingAvailable.y > 0 && shouldHandlePullToRefresh(remainingAvailable, source)) {
            pullToRefreshState?.let { refreshState ->

                val connection = refreshState.createNestedScrollConnection(overScrollState)
                val refreshConsumed = connection.onPostScroll(consumed, remainingAvailable, source)
                if (refreshConsumed != Offset.Zero) {
                    state.currentPriority = ScrollPriority.PullToRefresh
                    remainingAvailable -= refreshConsumed
                }
            }
        }

        // 3. 越界回弹
        if (shouldHandleOverScroll(remainingAvailable, source) && abs(remainingAvailable.y) > 0.1f) {
            state.currentPriority = ScrollPriority.OverScroll
            return Offset.Zero
        }

        return available - remainingAvailable
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        var remainingAvailable = available

        val refreshState = pullToRefreshState?.refreshState
        if (refreshState != null && refreshState == RefreshState.Refreshing) {
            // 如果下拉刷新已经被触发，则不允许其他组件处理滚动
            state.currentPriority = ScrollPriority.PullToRefresh
            return available
        }

        // 1. 优先处理 TopAppBar 的惯性滚动
        topAppBarScrollBehavior?.let { behavior ->
            if (shouldHandleTopAppBar(Offset(remainingAvailable.x, remainingAvailable.y))) {
                val topbarConsumer = behavior.nestedScrollConnection.onPreFling(remainingAvailable)
                if (topbarConsumer != Velocity.Zero) {
                    state.currentPriority = ScrollPriority.TopAppBar
                    remainingAvailable -= topbarConsumer
                }
            }
        }

        // 2. 如果还有剩余速度，处理越界回弹
        if (abs(remainingAvailable.y) > 0.1f && shouldHandleOverScroll(Offset(remainingAvailable.x, remainingAvailable.y))) {
            println("onPreFling: Handling over-scroll with velocity: $remainingAvailable")
            state.currentPriority = ScrollPriority.OverScroll
            return Velocity.Zero
        }
        return available
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        var remainingAvailable = available

        val refreshState = pullToRefreshState?.refreshState
        if (refreshState != null && refreshState == RefreshState.Refreshing) {
            // 如果下拉刷新已经被触发，则不允许其他组件处理滚动
            state.currentPriority = ScrollPriority.PullToRefresh
            return available
        }

        // 1. 处理 TopAppBar 的后置惯性滚动
        topAppBarScrollBehavior?.let { behavior ->
            if (shouldHandleTopAppBar(Offset(available.x, available.y))) {
                val topbarConsumer = behavior.nestedScrollConnection.onPostFling(consumed, available)
                if (topbarConsumer != Velocity.Zero) {
                    state.currentPriority = ScrollPriority.TopAppBar
                    remainingAvailable -= topbarConsumer
                }
            }
        }

        // 2. 如果还有剩余速度，处理越界回弹
        if (abs(remainingAvailable.y) > 0.1f && shouldHandleOverScroll(Offset(remainingAvailable.x, remainingAvailable.y))) {
            println("onPostFling: Handling over-scroll with velocity: $remainingAvailable")
            state.currentPriority = ScrollPriority.OverScroll
            return Velocity.Zero
        }

        return available - remainingAvailable
    }
}

/**
 * LocalScrollCoordinatorState
 */
val LocalScrollCoordinatorState = compositionLocalOf<ScrollCoordinatorState?> { null }
