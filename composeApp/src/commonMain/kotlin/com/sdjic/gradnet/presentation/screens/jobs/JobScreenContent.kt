package com.sdjic.gradnet.presentation.screens.jobs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sdjic.gradnet.data.network.entity.UserDto
import com.sdjic.gradnet.presentation.composables.CircularProfileImage
import com.sdjic.gradnet.presentation.composables.ExpandableText
import com.sdjic.gradnet.presentation.composables.LoadingAnimation
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.composables.Title
import com.sdjic.gradnet.presentation.core.DummyBgImage
import com.sdjic.gradnet.presentation.core.DummyDpImage
import com.sdjic.gradnet.presentation.core.LOREM
import com.sdjic.gradnet.presentation.core.getEmptyUserDto
import com.sdjic.gradnet.presentation.screens.onboarding.OnboardingPagerSlide
import com.sdjic.gradnet.presentation.screens.onboarding.onboardingList
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.heart
import gradnet_graduatenetwork.composeapp.generated.resources.heart_outlined
import org.jetbrains.compose.resources.painterResource

@Composable
fun JobScreenContent() {

    PostItem(
        post = Post(
            postId = 1,
            user = getEmptyUserDto(),
            content = LOREM,
            images = listOf(
                DummyBgImage,
                "https://farm4.staticflickr.com/3049/2327691528_f060ee2d1f.jpg",
                "https://farm4.staticflickr.com/3224/3081748027_0ee3d59fea_z_d.jpg"
            ),
        )
    )
}

@Composable
fun PostItem(post: Post) {
    val platformContext = LocalPlatformContext.current
    var isLiked by remember { mutableStateOf(false) }
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        CircularProfileImage(
            context = platformContext,
            data = DummyDpImage,
            imageSize = 42.dp,
            borderWidth = 0.dp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                SText(
                    text = post.user.username ?: "",
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700)
                )
                SText(
                    "  . 1h",
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    textColor = Color.Gray
                )
            }
            ExpandableText(
                text = LOREM,
                fontSize = 15.sp
            )
            if (post.images.isNotEmpty()) {
                PostImages(images = post.images)
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    painter = painterResource(if (isLiked) Res.drawable.heart else Res.drawable.heart_outlined),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp).clickable(onClick = { isLiked = !isLiked }),
                    tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurface.copy(.2f)
                )
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = {}),
                    tint = MaterialTheme.colorScheme.onSurface.copy(.2f)
                )
            }
            HorizontalDivider(
                thickness = .5.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(.2f)
            )
        }
    }
}

@Composable
fun ColumnScope.PostImages(images: List<String>) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { images.size })
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth().heightIn(max = 200.dp)
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
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillBounds
                )
            }

            AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Title(
                        "error",
                        modifier = Modifier.clickable(onClick = { painterReq.restart() })
                    )
                }
            }

            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier.size(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation()
                }
            }
        }
    }
    if (images.size > 1) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            onboardingList.forEachIndexed { index, _ ->
                OnboardingPagerSlide(
                    isSelected = index == pagerState.currentPage,
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unselectedColor = Color.Gray,
                    size = if (index == pagerState.currentPage) 6 else 4,
                    spacer = 4,
                    selectedLength = 6
                )
            }
        }
    }
}

data class Post(
    val postId: Long,
    val user: UserDto,
    val content: String,
    val images: List<String>,
)