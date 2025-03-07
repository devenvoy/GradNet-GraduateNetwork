package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Monitor
import androidx.compose.material.icons.outlined.RememberMe
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import coil3.compose.LocalPlatformContext
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.presentation.composables.filter.InterestTag
import com.sdjic.gradnet.presentation.composables.images.BackgroundImage
import com.sdjic.gradnet.presentation.composables.images.CircularProfileImage
import com.sdjic.gradnet.presentation.composables.tabs.FancyIndicator
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.core.DummyBgImage
import com.sdjic.gradnet.presentation.core.model.UserProfile
import com.sdjic.gradnet.presentation.helper.LocalDrawerController
import com.sdjic.gradnet.presentation.helper.LocalRootNavigator
import com.sdjic.gradnet.presentation.screens.accountSetup.SetUpScreen
import com.sdjic.gradnet.presentation.theme.AppTheme
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.koin.compose.koinInject

const val initialImageFloat = 120f

//NOTE: This stuff should usually be in a parent activity/Navigator
// We can pass callback to profileScreen to get the click.
//internal fun launchSocialActivity(context: Context, socialType: String) {
//    val intent = when (socialType) {
//        "github" -> Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl))
//        "repository" -> Intent(Intent.ACTION_VIEW, Uri.parse(githubRepoUrl))
//        "linkedin" -> Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl))
//        else -> Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl))
//    }
//    context.startActivity(intent)
//}

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val parentNavigator = LocalRootNavigator.current
        val appCacheSetting = koinInject<AppCacheSetting>()
        AppTheme {
            ProfileScreenContent(
                userProfile = appCacheSetting.getUserProfile(),
                onEditClick = { parentNavigator.push(SetUpScreen(true)) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProfileScreenContent(
    userProfile: UserProfile,
    onEditClick: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val drawerState = LocalDrawerController.current
    val scrollState = rememberLazyListState()
    val pagerState = rememberPagerState(1, pageCount = { 2 })

    Scaffold(
        topBar = {
            TopAppBarView(
                userProfile = userProfile,
                scroll = scrollState.firstVisibleItemScrollOffset.toFloat(),
                onMenuClick = { scope.launch { drawerState.open() } }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            TopBackground(userProfile.backgroundPic ?: "")

            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                state = scrollState
            ) {
                item {
                    Spacer(modifier = Modifier.height(120.sdp))
                    TopScrollingContent(
                        userProfile = userProfile,
                        listState = scrollState
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(8.sdp)
                    ) {
                        SText(
                            text = userProfile.name,
                            fontSize = 14.ssp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 2.sdp)
                        )
                        SText(
                            text = userProfile.email,
                            fontSize = 10.ssp,
                            fontWeight = FontWeight(400)
                        )
                        userProfile.designation?.let { desgn ->
                            SText(
                                text = desgn,
                                fontSize = 10.ssp,
                                fontWeight = FontWeight(400)
                            )
                        }
                        EditButtonRow(onEditClick = onEditClick, onShareClick = {})
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
                        modifier = Modifier.fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) { page ->
                        when (page) {
                            0 -> UserPostsContent()
                            1 -> UserDetailsContent(userProfile)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserDetailsContent(userProfile: UserProfile) {
    Column {
        AboutMeSection(userProfile.about)
        InterestsSection(icon = Icons.Outlined.Monitor, title = "Skills", data = userProfile.skills)
        InterestsSection(
            icon = Icons.Outlined.Translate,
            title = "Languages",
            data = userProfile.languages
        )
        MoreInfoSection(
            phoneNumber = userProfile.phoneNumber,
            email = userProfile.email,
            socialUrls = userProfile.socialUrls
        )

        repeat(10) {
            InterestsSection(
                icon = Icons.Outlined.Translate,
                title = "Languages",
                data = userProfile.languages
            )
        }
    }
}

@Composable
fun UserPostsContent() {

}


@Composable
fun SectionTitle(
    icon: ImageVector,
    title: String,
) {
    Row(
        modifier = Modifier.padding(start = 6.sdp, top = 10.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.sdp))
        SText(
            text = title,
            fontSize = 14.ssp,
            fontWeight = FontWeight.W600,
            textColor = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestsSection(
    icon: ImageVector = Icons.Outlined.Info,
    title: String = "Interests",
    data: List<String>?
) {
    if (data.isNullOrEmpty()) return
    SectionTitle(icon = icon, title = title)
    FlowRow(modifier = Modifier.padding(6.sdp)) {
        data.forEach { InterestTag(it) }
    }
}


@Composable
fun AboutMeSection(about: String) {
    SectionTitle(icon = Icons.Outlined.RememberMe, title = "About Me")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarView(scroll: Float, onMenuClick: () -> Unit, userProfile: UserProfile) {
    AnimatedVisibility(
        scroll > initialImageFloat,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
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
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 8.dp)
                        .clickable(
                            onClick = onMenuClick
                        )
                )
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