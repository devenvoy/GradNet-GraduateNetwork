package com.sdjic.gradnet.presentation.screens.setting.lost_found

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.internal.BackHandler
import com.sdjic.gradnet.data.network.entity.dto.LostItemDto
import com.sdjic.gradnet.presentation.composables.images.LongBackButton
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.helper.CashPagingListUi
import com.sdjic.gradnet.presentation.helper.DateTimeUtils
import com.sdjic.gradnet.presentation.helper.DateTimeUtils.parseDateAsync
import com.sdjic.gradnet.presentation.helper.DateTimeUtils.toEpochMillis
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.posts.PostImages
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.ssp

class LostItemListScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<LostItemListScreenModel>()
        LostItemListScreenContent(
            viewModel = viewModel,
            navigateUp = { navigator.popUntilRoot() },
            fabButtonClick = {
                navigator.push(LostItemReportScreen())
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class, InternalVoyagerApi::class)
    @Composable
    fun LostItemListScreenContent(
        viewModel: LostItemListScreenModel,
        navigateUp: () -> Unit,
        fabButtonClick: () -> Unit
    ) {

        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        val pullToRefreshState = rememberPullToRefreshState()
        val lifecycleOwner = LocalLifecycleOwner.current
        val data = viewModel.lostItems.collectAsLazyPagingItems()

        var hasLoadedOnce by remember { mutableStateOf(false) }

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
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ), title = {
                        Title(
                            textColor = Color.White, text = "Lost & Found News", size = 14.ssp
                        )
                    }, navigationIcon = {
                        LongBackButton(
                            iconColor = Color.White, onBackPressed = navigateUp
                        )
                    }
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text(text = "Add news", color = Color.White) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.EditNote,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    onClick = fabButtonClick
                )
            }
        )
        {
            PullToRefreshBox(
                modifier = Modifier.padding(it),
                isRefreshing = isRefreshing,
                state = pullToRefreshState,
                onRefresh = { data.refresh() }
            ) {
                CashPagingListUi(
                    paddingValues = PaddingValues(bottom = it.calculateBottomPadding()),
                    data = data,
                    state = listState,
                ) { item, modifier ->
                    item?.let {
                        LostItemCard(item)
                    }
                }
            }
        }
    }

    @Composable
    fun LostItemCard(lostItem: LostItemDto) {
        var postedAgo by remember { mutableStateOf("Loading...") }
        LaunchedEffect(lostItem.createdAt) {
            val localDateTime = parseDateAsync(lostItem.createdAt)
            postedAgo =
                DateTimeUtils.getTimeAgoAsync(toEpochMillis(localDateTime))
        }
        Card(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(4),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                PostImages(lostItem.photos, onLikeClicked = {})
                Text(
                    text = postedAgo,
                    style = TextStyle(
                        fontFamily = displayFontFamily(),
                        textAlign = TextAlign.Right
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = lostItem.description,
                    style = TextStyle(
                        fontFamily = displayFontFamily()
                    )
                )
            }
        }
    }
}