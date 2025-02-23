package com.sdjic.gradnet.presentation.screens.posts


import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

        var isRefreshing by remember { mutableStateOf(false) }

        val postScreenModel = koinScreenModel<PostScreenModel>()
        val data = postScreenModel.posts.collectAsLazyPagingItems()

        BackHandler(enabled = listState.firstVisibleItemIndex > 5) {
            scope.launch {
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
                        onClick = {
                            rootNavigator.push(AddPost())
                        }) {
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
                        isRefreshing = true
                        scope.launch { modalSheetState.hide() }
                        postScreenModel.onAction(PostScreenAction.OnFilterSheetStateChange(false))
                        postScreenModel.onAction(PostScreenAction.OnUpdateFilter)
                        isRefreshing = false
                    }
                ) {
                    FilterBottomSheetContent(
                        onDismiss = {},
                        postScreenModel = postScreenModel
                    )
                }
            }

            PullToRefreshBox(
                modifier = Modifier.padding(pVal),
                isRefreshing = isRefreshing,
                state = pullToRefreshState,
                onRefresh = {
                    scope.launch {
                        isRefreshing = true
                        delay(2000L)
                        isRefreshing = false
                    }
                }
            ) {
                PagingListUI(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    data = data,
                    state = listState,
                ) { item ->
                    PostItem(item)
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
                        title = { Text(text = filter.key.lowercase().capitalize()) },
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
    fun PostItem(post: Post) {
        val platformContext = LocalPlatformContext.current
        var isLiked by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        ) {


            var postedAgo by remember { mutableStateOf("Loading...") }

            LaunchedEffect(post.createdAt) {
                val localDateTime = parseDateAsync(post.createdAt)
                postedAgo =
                    DateTimeUtils.getTimeAgoAsync(toEpochMillis(localDateTime))
            }


            Row {
                CircularProfileImage(
                    context = platformContext,
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
                PostImages(images = post.images)
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(if (isLiked) Res.drawable.heart else Res.drawable.heart_outlined),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp).clickable(onClick = { isLiked = !isLiked }),
                    tint = if (isLiked) Color.Red
                    else MaterialTheme.colorScheme.onSurface.copy(.4f)
                )
                SText(post.likesCount.toString())
                Icon(
                    painter = painterResource(Res.drawable.ic_share),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = {}).size(28.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(.4f)
                )
            }
        }
    }

    @Composable
    fun PostImages(images: List<String>) {
        val pagerState = rememberPagerState(initialPage = 0, pageCount = { images.size })
        Box {
            HorizontalPager(
                state = pagerState, modifier = Modifier.fillMaxWidth().heightIn(min = 200.dp)
            ) { page ->

                val painterReq = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalPlatformContext.current).data(images[page])
                        .crossfade(true).crossfade(300).build()
                )

                when (painterReq.state.collectAsState().value) {
                    is AsyncImagePainter.State.Success -> {

                        Image(
                            painter = painterReq,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                                .aspectRatio(16 / 9f)
                                .clip(RoundedCornerShape(4.dp))
                        )
                    }

                    AsyncImagePainter.State.Empty, is AsyncImagePainter.State.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            Title(
                                text = "error",
                                modifier = Modifier.clickable(onClick = { painterReq.restart() })
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