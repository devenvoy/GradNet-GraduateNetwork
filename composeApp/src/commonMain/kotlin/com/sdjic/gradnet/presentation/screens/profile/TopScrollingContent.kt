package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import com.sdjic.gradnet.presentation.composables.images.CircularProfileImage
import com.sdjic.gradnet.presentation.core.model.UserProfile
import network.chaintech.sdpcomposemultiplatform.sdp


@Composable
fun TopScrollingContent(
    userProfile: UserProfile,
    listState: LazyListState
) {
    val scrollOffset = listState.firstVisibleItemScrollOffset
    val visibilityChangeFloat = scrollOffset > initialImageFloat - 20

    Row {
        val dynamicAnimationSizeValue =
            (initialImageFloat - scrollOffset.toFloat()).coerceIn(26f, initialImageFloat)

        CircularProfileImage(
            modifier = Modifier
                .padding(start = 12.sdp)
                .size(animateDpAsState(Dp(dynamicAnimationSizeValue)).value),
            placeHolderName = userProfile.name,
            data = userProfile.profilePic,
            imageSize = 80.sdp
        )
        Column(
            modifier = Modifier
                .padding(start = 8.sdp, top = 16.sdp)
                .alpha(animateFloatAsState(if (visibilityChangeFloat) 0f else 1f).value)
        ) {
            // TODO: Show badges here
        }
    }
}
