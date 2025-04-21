package com.sdjic.gradnet.presentation.screens.posts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.internal.BackHandler
import com.sdjic.gradnet.di.platform_di.getContactsUtil
import com.sdjic.gradnet.presentation.composables.images.LongBackButton
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.core.model.Post
import com.sdjic.gradnet.presentation.helper.CashPagingListUi
import com.sdjic.gradnet.presentation.helper.isScrollingUp
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.profile.ProfileScreen
import com.sdjic.gradnet.presentation.theme.AppTheme
import kotlinx.coroutines.launch

class LikedPostScreen : Screen {
    @Composable
    override fun Content() {
        AppTheme { LikedPostScreenContent() }
    }

    @OptIn(ExperimentalMaterial3Api::class, InternalVoyagerApi::class)
    @Composable
    fun LikedPostScreenContent() {

        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        val pullToRefreshState = rememberPullToRefreshState()
        val rootNavigator = LocalNavigator.currentOrThrow
        val lifecycleOwner = LocalLifecycleOwner.current

        var hasLoadedOnce by remember { mutableStateOf(false) }
        val viewModel = koinScreenModel<LikedPostScreenModel>()
        val data: LazyPagingItems<Post> = viewModel.posts.collectAsLazyPagingItems()

        val isRefreshing by remember {
            derivedStateOf {
                if (!hasLoadedOnce) false
                else data.loadState.refresh is LoadState.Loading
            }
        }

        // Refresh on resume
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    data.refresh()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

        // Track when data is first loaded
        LaunchedEffect(data.loadState) {
            if (data.loadState.refresh is LoadState.NotLoading) {
                hasLoadedOnce = true
            }
        }

        BackHandler(enabled = listState.firstVisibleItemIndex > 5) {
            scope.launch {
                data.refresh()
                listState.animateScrollToItem(0)
            }
        }

        Scaffold(
            topBar = {
                AnimatedVisibility(
                    listState.isScrollingUp(),
                    enter = fadeIn() + slideInVertically { -1 },
                    exit = fadeOut() + slideOutVertically { -1 }
                ) {
                    TopAppBar(
                        title = {
                            Title(text = "Liked Posts")
                        },
                        navigationIcon = {
                            LongBackButton(
                                onBackPressed = { rootNavigator.pop() },
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    )
                }
            }) { pVal ->


            PullToRefreshBox(
                modifier = Modifier.padding(top = pVal.calculateTopPadding()),
                isRefreshing = isRefreshing,
                state = pullToRefreshState,
                onRefresh = { data.refresh() }
            ) {
                CashPagingListUi(
                    paddingValues = PaddingValues(bottom = pVal.calculateBottomPadding()),
                    data = data,
                    state = listState,
                ) { item, modifier ->
                    item?.let {
                        PostItem(
                            modifier = modifier.animateItem(),
                            post = item,
                            onLikeClicked = { viewModel.toggleLike(item) },
                            onShareClick = { getContactsUtil().sharePost(item) },
                            onProfileClick = { rootNavigator.push(ProfileScreen(item.userId)) },
                            checkProfileEnable = true
                        )
                    }
                }
            }
        }
    }
}