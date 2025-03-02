package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import coil3.compose.LocalPlatformContext
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.presentation.composables.filter.InterestTag
import com.sdjic.gradnet.presentation.composables.images.BackgroundImage
import com.sdjic.gradnet.presentation.composables.images.CircularProfileImage
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


@Composable
fun ProfileScreenContent(
    userProfile: UserProfile,
    onEditClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState(0)
    val scope = rememberCoroutineScope()
    val drawerState = LocalDrawerController.current

    Scaffold(
        topBar = {
            TopAppBarView(
                userProfile = userProfile,
                scroll = scrollState.value.toFloat(),
                onMenuClick = {
                    scope.launch { drawerState.open() }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBackground(userProfile.backgroundPic ?: "")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
            ) {
                Spacer(modifier = Modifier.height(120.sdp))
                TopScrollingContent(userProfile = userProfile, scrollState = scrollState)
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(8.sdp)
                ) {
                    EditButtonRow(onEditClick = onEditClick, onShareClick = {})
                    AboutMeSection(userProfile.about)
                    InterestsSection(title = "Skills", data = userProfile.skills)
                    InterestsSection(title = "Languages", data = userProfile.languages)
                    MoreInfoSection(
                        phoneNumber = userProfile.phoneNumber,
                        email = userProfile.email,
                        socialUrls = userProfile.socialUrls
                    )
                }
            }

            if (scrollState.value < initialImageFloat + 240) {
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd)
                        .padding(vertical = 28.sdp, horizontal = 4.sdp),
                    onClick = {
                        scope.launch { drawerState.open() }
                    },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.background)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestsSection(title: String = "Interests", data: List<String>?) {
    if (data.isNullOrEmpty()) return
    SText(
        text = title,
        fontSize = 14.ssp,
        fontWeight = FontWeight.W600,
        textColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(start = 6.sdp, top = 12.sdp)
    )
    FlowRow(modifier = Modifier.padding(6.sdp)) {
        data.forEach { InterestTag(it) }
    }
}


@Composable
fun AboutMeSection(about: String) {
    SText(
        text = "About Me",
        fontSize = 14.ssp,
        fontWeight = FontWeight.W600,
        textColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(start = 6.sdp, top = 12.sdp)
    )
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
        scroll > initialImageFloat + 350,
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