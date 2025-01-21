package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import com.sdjic.gradnet.presentation.composables.BackgroundImage
import com.sdjic.gradnet.presentation.composables.CircularProfileImage
import com.sdjic.gradnet.presentation.core.DummyBgImage
import com.sdjic.gradnet.presentation.core.DummyDpImage
import compose.icons.FeatherIcons
import compose.icons.feathericons.Menu
import network.chaintech.sdpcomposemultiplatform.sdp

const val initialImageFloat = 120f
const val name = "Devansh Amdavadwala"
const val email = "devanshamdavadwala@gmail.com"
const val twitterUrl = "https://www.twitter.com/_gurupreet"
const val linkedInUrl = "https://www.linkedin.com/in/gurupreet-singh-491a7668/"
const val githubUrl = "https://github.com"
const val githubRepoUrl = "https://github.com/Gurupreet/ComposeCookBook"

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


@Composable
fun ProfileScreen() {
    val scrollState = rememberScrollState(0)
    Scaffold(
        topBar = {
            TopAppBarView(scrollState.value.toFloat())
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics { testTag = "Profile Screen" }
        ) {
            TopBackground()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
            ) {
                Spacer(modifier = Modifier.height(100.sdp))
                TopScrollingContent(scrollState)
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(8.sdp)
                ) {

                    EditButtonRow(onEditClick = {}, onShareClick = {})
                    //        SocialRow()
                    AboutMeSection()
                    InterestsSection()
                    LanguagesSection()
                    MoreInfoSection()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarView(scroll: Float) {
    AnimatedVisibility (scroll > initialImageFloat + 350,
        enter = fadeIn() + expandVertically (),
        exit = fadeOut() + shrinkVertically()
    ) {
        TopAppBar(
            title = { Text(text = name) },
            navigationIcon = {
                val platformContext = LocalPlatformContext.current
                CircularProfileImage(
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    context = platformContext,
                    data = DummyDpImage,
                    imageSize = 30.sdp
                )
            },
            actions = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        )
    }
}

@Composable
private fun TopBackground() {

    val gradient = listOf(
        Color.Transparent,
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
    )

    Box(
        modifier = Modifier
            .height(150.sdp)
            .fillMaxWidth()
    ){
        val platformContext = LocalPlatformContext.current
        BackgroundImage(
            data = DummyBgImage,
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

        Row(modifier = Modifier.padding(top = 25.sdp).fillMaxWidth(),
            horizontalArrangement = Arrangement.End){
            IconButton(onClick = {}, colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.background)){
                Icon(imageVector = FeatherIcons.Menu, contentDescription = null)
            }
        }
    }
}