package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdjic.gradnet.presentation.composables.text.ExpandableRichText
import com.sdjic.gradnet.presentation.core.model.Post
import com.sdjic.gradnet.presentation.helper.DateTimeUtils
import com.sdjic.gradnet.presentation.helper.DateTimeUtils.parseDateAsync
import com.sdjic.gradnet.presentation.helper.DateTimeUtils.toEpochMillis
import com.sdjic.gradnet.presentation.screens.posts.PostImages
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.empty_trash
import gradnet_graduatenetwork.composeapp.generated.resources.ic_share
import network.chaintech.kmp_date_time_picker.utils.noRippleEffect
import network.chaintech.sdpcomposemultiplatform.sdp
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProfilePostItem(
    post: Post,
    isReadOnly: Boolean,
    onShareClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    onLikeClicked: () -> Unit,
    onDeleteClicked: () -> Unit
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

            Column(modifier = Modifier.padding(horizontal = 6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    HorizontalDivider(modifier = Modifier.fillMaxWidth(.3f))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontSize = 14.sp)
                            ) {
                                append(" Posted")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append("  $postedAgo")
                            }
                        }
                    )
                    Spacer(Modifier.weight(1f))
                    if (isReadOnly.not())
                        Icon(
                            painter = painterResource(Res.drawable.empty_trash),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp).noRippleEffect(onDeleteClicked),
                            tint = if (post.liked) Color.Red
                            else MaterialTheme.colorScheme.error.copy(.8f)
                        )
                }
                ExpandableRichText(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = post.content
                )
            }
        }
        if (post.images.isNotEmpty()) {
            PostImages(images = post.images, onLikeClicked = {})
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Gray, fontSize = 14.sp)) {
                        append("Liked by  ")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)) {
                        append(post.likesCount.toString())
                    }
                    withStyle(style = SpanStyle(color = Color.Gray, fontSize = 14.sp)) {
                        append("  Users")
                    }
                },
                modifier = Modifier.weight(1f)
            )
            /*
            Icon(
                painter = painterResource(if (post.liked) Res.drawable.heart else Res.drawable.heart_outlined),
                contentDescription = null,
                modifier = Modifier.size(28.dp).noRippleEffect(onLikeClicked),
                tint = if (post.liked) Color.Red
                else MaterialTheme.colorScheme.onSurface.copy(.8f)
            )
*/
            Icon(
                painter = painterResource(Res.drawable.ic_share),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 4.dp)
                    .size(28.dp)
                    .noRippleEffect(onShareClick),
                tint = MaterialTheme.colorScheme.onSurface.copy(.8f)
            )
        }
    }
}