package com.sdjic.gradnet.presentation.screens.posts


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.internal.BackHandler
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.alorma.compose.settings.ui.SettingsCheckbox
import com.sdjic.gradnet.presentation.composables.LoadingAnimation
import com.sdjic.gradnet.presentation.composables.images.CircularProfileImage
import com.sdjic.gradnet.presentation.composables.text.ExpandableRichText
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.core.model.Post
import com.sdjic.gradnet.presentation.helper.DateTimeUtils
import com.sdjic.gradnet.presentation.helper.DateTimeUtils.parseDateAsync
import com.sdjic.gradnet.presentation.helper.DateTimeUtils.toEpochMillis
import com.sdjic.gradnet.presentation.helper.LocalRootNavigator
import com.sdjic.gradnet.presentation.helper.LocalScrollBehavior
import com.sdjic.gradnet.presentation.helper.PagingListUI
import com.sdjic.gradnet.presentation.helper.isScrollingUp
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.onboarding.OnboardingPagerSlide
import com.sdjic.gradnet.presentation.theme.AppTheme
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.app_name
import gradnet_graduatenetwork.composeapp.generated.resources.heart
import gradnet_graduatenetwork.composeapp.generated.resources.heart_outlined
import gradnet_graduatenetwork.composeapp.generated.resources.ic_share
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.snapBackZoomable
import network.chaintech.kmp_date_time_picker.utils.noRippleEffect
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


class PostScreen : Screen {
    @Composable
    override fun Content() {
        AppTheme { PostScreenContent() }
    }

    @OptIn(ExperimentalMaterial3Api::class, InternalVoyagerApi::class)
    @Composable
    fun PostScreenContent() {
        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        val pullToRefreshState = rememberPullToRefreshState()
        val modalSheetState = rememberModalBottomSheetState()
        val scrollBehavior = LocalScrollBehavior.current
        val rootNavigator = LocalRootNavigator.current
        val lifecycleOwner = LocalLifecycleOwner.current

        var hasLoadedOnce by remember { mutableStateOf(false) }
        val postScreenModel = koinScreenModel<PostScreenModel>()
        val data: LazyPagingItems<Post> = postScreenModel.posts.collectAsLazyPagingItems()


        val isRefreshing by remember {
            derivedStateOf {
                if (!hasLoadedOnce) false
                else data.loadState.refresh is LoadState.Loading
            }
        }

        // Auto refresh every 5 minutes
        LaunchedEffect(Unit) {
            while (true) {
                delay(5 * 60 * 1000) // 5 minutes
                data.refresh()
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
                AnimatedVisibility(listState.isScrollingUp(),
                    enter = fadeIn() + slideInVertically { -1 },
                    exit = fadeOut() + slideOutVertically { -1 }
                ) {
                    TopBar(onFilterIconClicked = {
                        postScreenModel.onAction(PostScreenAction.OnFilterSheetStateChange(true))
                    }
                    )
                }
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = listState.isScrollingUp(), enter = fadeIn(), exit = fadeOut()
                ) {
                    FloatingActionButton(
                        onClick = { rootNavigator.push(AddPostScreen()) }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                    }
                }
            }) { pVal ->

            if (postScreenModel.showFilterSheet.collectAsState().value) {
                ModalBottomSheet(
                    sheetState = modalSheetState,
                    dragHandle = null,
                    containerColor = MaterialTheme.colorScheme.background,
                    onDismissRequest = {
                        scope.launch { modalSheetState.hide() }
                        postScreenModel.onAction(PostScreenAction.OnFilterSheetStateChange(false))
                        postScreenModel.onAction(PostScreenAction.OnUpdateFilter)
                    }
                ) {
                    FilterBottomSheetContent(
                        onDismiss = {},
                        postScreenModel = postScreenModel
                    )
                }
            }

            PullToRefreshBox(
                modifier = Modifier.padding(top = pVal.calculateTopPadding()),
                isRefreshing = isRefreshing,
                state = pullToRefreshState,
                onRefresh = { data.refresh() }
            ) {
                PagingListUI(
                    contentPadding = PaddingValues(bottom = pVal.calculateBottomPadding()),
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    data = data,
                    state = listState,
                ) { item ->
                    PostItem(
                        modifier = Modifier.animateItem(
                            fadeInSpec = null, fadeOutSpec = null, placementSpec = tween(500)
                        ),
                        post = item,
                        onLikeClicked = {
                            postScreenModel.toggleLike(item)
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun FilterBottomSheetContent(
        onDismiss: () -> Unit,
        postScreenModel: PostScreenModel
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(12.sdp),
        ) {

            Box(
                modifier = Modifier.width(50.sdp).height(3.sdp).clip(RoundedCornerShape(50))
                    .background(Color(0xFFBBC0C4)).align(Alignment.CenterHorizontally)
            )

            Title(
                text = "Filters",
                size = 16.ssp,
                textColor = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 8.sdp).align(Alignment.CenterHorizontally)
            )

            SettingsCheckbox(
                state = postScreenModel.selectAllUserTypes.value,
                title = { Text(text = "Select All", fontWeight = FontWeight.W600) },
                enabled = true,
                onCheckedChange = { postScreenModel.onAction(PostScreenAction.OnToggleSelectAll) },
            )

            postScreenModel.userTypeFilters.collectAsState().value
                .forEachIndexed { ixd, filter ->
                    SettingsCheckbox(
                        modifier = Modifier.padding(start = 10.dp)
                            .offset(y = (-20 * (ixd + 1)).dp),
                        state = filter.value,
                        title = { Text(text = filter.key.lowercase()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }) },
                        enabled = true,
                        onCheckedChange = {
                            postScreenModel.onAction(
                                PostScreenAction.OnFilterChange(
                                    filter.copy(value = !filter.value)
                                )
                            )
                        },
                    )
                }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar(onFilterIconClicked: () -> Unit) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(Res.string.app_name),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            },
            actions = {
                IconButton(onClick = onFilterIconClicked) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.FilterList,
                        contentDescription = null
                    )
                }
            }
        )
    }

    @Composable
    fun PostItem(
        modifier: Modifier = Modifier,
        post: Post,
        onLikeClicked: () -> Unit = {},
    ) {
        var postedAgo by remember { mutableStateOf("Loading...") }
        LaunchedEffect(post.createdAt) {
            val localDateTime = parseDateAsync(post.createdAt)
            postedAgo =
                DateTimeUtils.getTimeAgoAsync(toEpochMillis(localDateTime))
        }
        Column(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        ) {

            Row {
                CircularProfileImage(
                    placeHolderName = post.userName,
                    data = post.userImage,
                    imageSize = 36.dp,
                    borderWidth = 0.dp
                )
                Column(modifier = Modifier.padding(horizontal = 6.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        SText(
                            text = post.userName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(800)
                        )
                        SText(
                            text = " • $postedAgo",
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400),
                            textColor = Color.Gray
                        )
                    }
                    ExpandableRichText(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = post.content
                    )
                }
            }
            if (post.images.isNotEmpty()) {
                PostImages(images = post.images, onLikeClicked = onLikeClicked)
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(if (post.liked) Res.drawable.heart else Res.drawable.heart_outlined),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp).noRippleEffect(onLikeClicked),
                    tint = if (post.liked) Color.Red
                    else MaterialTheme.colorScheme.onSurface.copy(.8f)
                )
                SText(post.likesCount.toString())
                Icon(
                    painter = painterResource(Res.drawable.ic_share),
                    contentDescription = null,
                    modifier = Modifier.noRippleEffect(onClick = {}).size(28.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(.8f)
                )
            }
        }
    }

    @Composable
    fun PostImages(
        images: List<String>,
        onLikeClicked: () -> Unit
    ) {
        val pagerState = rememberPagerState(initialPage = 0, pageCount = { images.size })
        Box {
            HorizontalPager(
                state = pagerState,
                beyondViewportPageCount = 2,
                modifier = Modifier.fillMaxWidth().heightIn(min = 300.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.Black, RoundedCornerShape(6.dp))
            ) { page ->

                val painterReq = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(images[page])
                        .crossfade(true)
                        .crossfade(300)
                        .build()
                )

                when (painterReq.state.collectAsState().value) {
                    is AsyncImagePainter.State.Success -> {

                        Image(
                            painter = painterReq,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .heightIn(max = 400.dp)
                                .snapBackZoomable(rememberZoomState(),
                                    onDoubleTap = { onLikeClicked() },
                                    onLongPress = { /* todo */ }
                                ),
                            contentScale = ContentScale.Fit,
                        )
                    }

                    AsyncImagePainter.State.Empty, is AsyncImagePainter.State.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp).noRippleEffect {
                                    painterReq.restart()
                                }
                            )
                        }
                    }

                    is AsyncImagePainter.State.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            LoadingAnimation()
                        }
                    }
                }
            }
            if (images.size > 1) {
                Row(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                        .padding(bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    images.forEachIndexed { index, _ ->
                        OnboardingPagerSlide(
                            isSelected = index == pagerState.currentPage,
                            selectedColor = MaterialTheme.colorScheme.primary,
                            unselectedColor = Color(0xFFD9D9D9),
                            size = if (index == pagerState.currentPage) 6 else 4,
                            spacer = 4,
                            selectedLength = 6
                        )
                    }
                }
            }
        }
    }
}