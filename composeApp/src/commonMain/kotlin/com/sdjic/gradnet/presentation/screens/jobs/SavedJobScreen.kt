package com.sdjic.gradnet.presentation.screens.jobs

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.composables.images.LongBackButton
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.core.model.Job
import com.sdjic.gradnet.presentation.helper.CashPagingListUi
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.theme.AppTheme

class SavedJobScreen : Screen {
    @Composable
    override fun Content() {
        AppTheme { SavedJobsContent() }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SavedJobsContent() {

        val pullToRefreshState = rememberPullToRefreshState()
        val navigator = LocalNavigator.currentOrThrow
        val lifecycleOwner = LocalLifecycleOwner.current

        var hasLoadedOnce by remember { mutableStateOf(false) }
        val viewModel = koinScreenModel<SavedJobScreenModel>()
        val data: LazyPagingItems<Job> = viewModel.jobs.collectAsLazyPagingItems()

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

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Title(text = "Saved Jobs")
                    },
                    navigationIcon = {
                        LongBackButton(
                            onBackPressed = { navigator.pop() },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                )
            }) { pVal ->

            PullToRefreshBox(
                modifier = Modifier.padding(top = pVal.calculateTopPadding()),
                isRefreshing = isRefreshing,
                state = pullToRefreshState,
                onRefresh = { data.refresh() }
            ) {
                CashPagingListUi(
                    paddingValues = PaddingValues(10.dp),
                    data = data,
                    state = rememberLazyListState()
                ) { item, modifier ->
                    item?.let { JobItem(job = item) { navigator.push(JobDetailScreen(item)) } }
                }
            }
        }
    }
}