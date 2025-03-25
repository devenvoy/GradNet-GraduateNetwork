@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)

package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Monitor
import androidx.compose.material.icons.outlined.RememberMe
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import coil3.compose.LocalPlatformContext
import com.sdjic.gradnet.di.platform_di.getContactsUtil
import com.sdjic.gradnet.di.platform_di.getScreenHeight
import com.sdjic.gradnet.presentation.composables.InterestsSection
import com.sdjic.gradnet.presentation.composables.LoadingAnimation
import com.sdjic.gradnet.presentation.composables.SectionTitle
import com.sdjic.gradnet.presentation.composables.filter.UserRoleChip
import com.sdjic.gradnet.presentation.composables.images.BackgroundImage
import com.sdjic.gradnet.presentation.composables.images.CircularProfileImage
import com.sdjic.gradnet.presentation.composables.images.LongBackButton
import com.sdjic.gradnet.presentation.composables.tabs.FancyIndicator
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.core.DummyBgImage
import com.sdjic.gradnet.presentation.core.model.UserProfile
import com.sdjic.gradnet.presentation.helper.LocalDrawerController
import com.sdjic.gradnet.presentation.helper.LocalRootNavigator
import com.sdjic.gradnet.presentation.helper.UiState
import com.sdjic.gradnet.presentation.helper.UiStateHandler
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.accountSetup.SetUpScreen
import com.sdjic.gradnet.presentation.screens.accountSetup.education.EducationItem
import com.sdjic.gradnet.presentation.screens.accountSetup.profession.ExperienceItem
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import com.sdjic.gradnet.presentation.theme.AppTheme
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.ic_share
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource

const val initialImageFloat = 120f


typealias ProfileScreenState = UiState<UserProfile>


class ProfileScreen(val userId: String? = null) : Screen {
    @Composable
    override fun Content() {
        val parentNavigator = runCatching { LocalRootNavigator.current }.getOrNull()
        val drawerState = runCatching { LocalDrawerController.current }.getOrNull()
        val localNavigator = LocalNavigator.current
        val profileScreenModel = koinScreenModel<ProfileScreenModel>()

        val state by profileScreenModel.profileState.collectAsState()
        val isReadOnlyMode by profileScreenModel.isReadOnlyMode.collectAsState()

        LaunchedEffect(userId) {
            if (userId == null) {
                profileScreenModel.loadUserProfile()
            } else {
                profileScreenModel.fetchAndLoadUserProfile(userId)
            }
        }

        AppTheme {
            UiStateHandler(
                uiState = state,
                onErrorShowed = {
                    localNavigator?.pop()
                },
                content = {
                    ProfileScreenContent(
                        viewModel = profileScreenModel,
                        userProfile = it,
                        isReadOnlyMode = isReadOnlyMode,
                        drawerState = drawerState,
                        onEditClick = { parentNavigator?.push(SetUpScreen(true)) },
                        onBackPress = { localNavigator?.pop() }
                    )
                }
            )
        }
    }
}

@Composable
fun ProfileScreenContent(
    userProfile: UserProfile,
    viewModel: ProfileScreenModel,
    onEditClick: () -> Unit = {},
    onBackPress: () -> Unit = {},
    isReadOnlyMode: Boolean = false,
    drawerState: DrawerState? = null
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val pagerState = rememberPagerState(if (isReadOnlyMode) 1 else 0, pageCount = { 2 })
    val userRole = viewModel.userRole

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage == 0) {
//            if (!isReadOnlyMode) {
            scrollState.scrollToItem(3)
//            }
        } else {
            scrollState.scrollToItem(0)
        }
    }

    val headerOffset by remember {
        derivedStateOf {
            val visibleItems = scrollState.layoutInfo.visibleItemsInfo
            val stickyHeader = visibleItems.firstOrNull { it.index == 2 } // TabRow index
            stickyHeader?.offset ?: Int.MAX_VALUE // Use max value if not in view
        }
    }

    val isStickyHeaderNearTop = headerOffset <= 200

    val shareProfile = {
        getContactsUtil().shareText(
            "https://devenvoy.github.io/#/profile/${userProfile.userId}"
        )
    }

    Scaffold(
        topBar = {
            TopAppBarView(
                isVisible = isStickyHeaderNearTop,
                onMenuClick = { scope.launch { drawerState?.open() } },
                userProfile = userProfile,
                isReadOnlyMode = isReadOnlyMode,
                onBackPress = onBackPress
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            TopBackground(userProfile.backgroundPic ?: "")

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .clipToBounds()
                    .fillMaxSize(),
                state = scrollState
            ) {
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(30.dp))
                        if (isReadOnlyMode) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                LongBackButton(
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp),
                                    onBackPressed = onBackPress,
                                    iconColor = Color.White
                                )
                                IconButton(onClick = shareProfile) {
                                    Icon(
                                        tint = Color.White,
                                        imageVector = Icons.Filled.Share,
                                        contentDescription = "Share"
                                    )
                                }
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                                    .size(28.dp)
                                    .align(Alignment.End)
                                    .clickable(
                                        enabled = !isReadOnlyMode,
                                        onClick = { scope.launch { drawerState?.open() } }
                                    )
                            )
                        }
                        Spacer(modifier = Modifier.height(70.dp))
                        TopScrollingContent(
                            profilePic = userProfile.profilePic,
                            profileName = userProfile.userName,
                            userRole = viewModel.userRole.value,
                            listState = scrollState
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.sdp)
                        ) {
                            SText(
                                text = userProfile.name,
                                fontSize = 14.ssp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 2.sdp)
                            )
                            SText(
                                text = userProfile.designation ?: (userProfile.course
                                    ?: userProfile.email),
                                fontSize = 10.ssp,
                                fontWeight = FontWeight(400)
                            )
                        }
                        userRole.value?.let {
                            UserRoleChip(
                                modifier = Modifier.padding(horizontal = 16.dp), userRole = it
                            )
                        }
                    }
                    if (isReadOnlyMode.not()) {
                        EditButtonRow(onEditClick = onEditClick, onShareClick = shareProfile)
                    }
                }

                // Sticky TabRow
                stickyHeader {
                    SecondaryTabRow(
                        selectedTabIndex = pagerState.currentPage,
                        containerColor = MaterialTheme.colorScheme.background,
                        indicator = {
                            FancyIndicator(
                                MaterialTheme.colorScheme.surfaceVariant,
                                Modifier.tabIndicatorOffset(pagerState.currentPage)
                            )
                        }
                    ) {
                        listOf("Posts", "Details").forEachIndexed { index, title ->
                            Tab(
                                selected = pagerState.currentPage == index,
                                selectedContentColor = MaterialTheme.colorScheme.primary,
                                unselectedContentColor = Color.Gray,
                                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                                text = { Text(title) })
                        }
                    }
                }

                item {
                    HorizontalPager(
                        state = pagerState,
                        beyondViewportPageCount = 1,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) { page ->
                        when (page) {
                            0 -> UserPostsContent(viewModel)
                            1 -> UserDetailsContent(isReadOnlyMode, userProfile)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserDetailsContent(
    isReadOnlyMode: Boolean,
    userProfile: UserProfile
) {
    LazyColumn(modifier = Modifier.height(getScreenHeight())) {
        item {
            AboutMeSection(userProfile.about)
        }

        item {
            InterestsSection(
                icon = Icons.Outlined.Monitor,
                title = "Skills",
                data = userProfile.skills
            )
        }
        item {
            if (userProfile.educations.isNotEmpty()) {
                SectionTitle(icon = Icons.AutoMirrored.Outlined.LibraryBooks, title = "Education")
                Card(
                    modifier = Modifier.padding(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    userProfile.educations.fastForEachIndexed { idx, it ->
                        EducationItem(it)
                    }
                }
            }
        }
        item {

            if (userProfile.experiences.isNotEmpty()) {
                SectionTitle(icon = Icons.Outlined.WorkOutline, title = "Experience")
                Card(
                    modifier = Modifier.padding(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    userProfile.experiences.fastForEachIndexed { idx, it ->
                        ExperienceItem(it)
                    }
                }
            }
        }

        item {
            InterestsSection(
                icon = Icons.Outlined.Translate,
                title = "Languages",
                data = userProfile.languages
            )
        }
        item {
            MoreInfoSection(
                isReadOnlyMode = isReadOnlyMode,
                userProfile = userProfile
            )
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun UserPostsContent(
    viewModel: ProfileScreenModel
) {
    val isFetchingPost by viewModel.isFetchingPost
    val userPosts by viewModel.userPosts.collectAsState()
    val isReadOnlyMode by viewModel.isReadOnlyMode.collectAsState()
    if (isFetchingPost) {
        Column(
            modifier = Modifier.fillMaxWidth().height(getScreenHeight() / 1.2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(200.dp))
            LoadingAnimation()
        }
    } else {
        if (userPosts.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth().height(getScreenHeight() / 1.2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(200.dp))
                Title("No Posts Found")
            }
        } else {
            LazyColumn(modifier = Modifier.height(getScreenHeight())) {
                items(userPosts) { post ->
                    ProfilePostItem(
                        post = post,
                        isReadOnly = isReadOnlyMode,
                        onShareClick = {
                            getContactsUtil().shareText(
                                "https://devenvoy.github.io/#/post_detail/${post.postId}"
                            )
                        },
                        onLikeClicked = {},
                        onDeleteClicked = {
                            viewModel.deletePost(post)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun AboutMeSection(about: String) {
    SectionTitle(icon = Icons.Outlined.RememberMe, title = "About Me")
    if (about.isNotEmpty())
        SText(
            text = about,
            modifier = Modifier
                .padding(6.sdp)
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .5f),
                    RoundedCornerShape(3.sdp)
                )
                .padding(6.sdp),
        )
}

@Composable
fun TopAppBarView(
    isVisible: Boolean,
    onMenuClick: () -> Unit,
    userProfile: UserProfile,
    isReadOnlyMode: Boolean,
    onBackPress: () -> Unit
) {

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(tween(600)),
        exit = fadeOut(tween(600))
    ) {
        TopAppBar(
            title = { Text(text = userProfile.name.ifEmpty { userProfile.userName }) },
            navigationIcon = {
                CircularProfileImage(
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    placeHolderName = userProfile.name.ifEmpty { userProfile.userName },
                    data = userProfile.profilePic,
                    borderWidth = 0.dp,
                    imageSize = 32.dp
                )
            },
            actions = {
                if (isReadOnlyMode) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 12.dp)
                            .clickable(onClick = onBackPress)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 12.dp)
                            .clickable(onClick = onMenuClick)
                    )
                }
            }
        )
    }
}

@Composable
private fun TopBackground(backGroundPic: String) {

    val gradient = listOf(
        Color.Transparent,
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
    )
    Box(
        modifier = Modifier
            .height(170.sdp)
            .fillMaxWidth()
    ) {
        val platformContext = LocalPlatformContext.current
        BackgroundImage(
            data = backGroundPic.ifEmpty { DummyBgImage },
            modifier = Modifier,
            context = platformContext,
            height = 180.sdp
        )

        Spacer(
            modifier = Modifier
                .height(180.sdp)
                .fillMaxWidth()
                .background(Brush.verticalGradient(gradient))
        )
    }
}


@Composable
fun EditButtonRow(onEditClick: () -> Unit, onShareClick: () -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)
            .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.sdp)
    ) {
        OutlinedButton(
            shape = RoundedCornerShape(4.sdp),
            modifier = Modifier.weight(1f),
            onClick = onEditClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f),
                containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .1f)
            )
        ) {
            SText("Edit Profile")
        }

        OutlinedButton(
            shape = RoundedCornerShape(4.sdp),
            onClick = onShareClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f),
                containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .1f)
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_share),
                contentDescription = "share"
            )
        }
    }
}

@Composable
fun TopScrollingContent(
    profilePic: String?,
    profileName: String,
    userRole: UserRole?,
    listState: LazyListState
) {
    val scrollOffset = listState.firstVisibleItemScrollOffset * 2
    val visibilityChangeFloat = scrollOffset > initialImageFloat

    Row {
        val dynamicAnimationSizeValue =
            (initialImageFloat - scrollOffset.toFloat()).coerceIn(42f, initialImageFloat)

        CircularProfileImage(
            modifier = Modifier
                .padding(start = 12.sdp)
                .size(animateDpAsState(Dp(dynamicAnimationSizeValue)).value),
            placeHolderName = profileName,
            data = profilePic,
            imageSize = 120.dp
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(animateFloatAsState(if (visibilityChangeFloat) 0f else 1f).value)
        ) {
//            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}