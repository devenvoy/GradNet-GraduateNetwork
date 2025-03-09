package com.sdjic.gradnet.presentation.screens.event

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import com.sdjic.gradnet.data.network.entity.dto.EventDto
import com.sdjic.gradnet.presentation.composables.images.BannerWidget
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.core.DummyBgImage
import com.sdjic.gradnet.presentation.helper.AutoSwipePagerEffect
import com.sdjic.gradnet.presentation.helper.LocalRootNavigator
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.helper.shimmerLoadingAnimation
import com.sdjic.gradnet.presentation.theme.AppTheme
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import network.chaintech.sdpcomposemultiplatform.ssp

class EventScreen : Screen {
    @Composable
    override fun Content() {
        AppTheme { EventScreenContent() }
    }

    @Composable
    fun EventScreenContent() {
        val eventScreenModel = koinScreenModel<EventScreenModel>()
        val navigator = LocalRootNavigator.current
        val list by eventScreenModel.eventList.collectAsStateWithLifecycle()

        Scaffold { pVal ->
            Column(
                modifier = Modifier.padding(pVal).fillMaxSize(),
            ) {

                if (eventScreenModel.isEventLoading.value) {
                    EventLoadingShimmer()
                } else {
                    if (list.isNotEmpty())
                        Row(
                            modifier = Modifier.padding(12.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Title("Trending Events")
                            TextButton(onClick = {}) {
                                SText("View more")
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    BannerCarouselWidget(list) {
                        navigator.push(EventDetailScreen(it))
                    }
                }
            }
        }
    }

    @Composable
    private fun EventLoadingShimmer() {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(30.dp).width(150.dp)
                    .background(CardDefaults.cardColors().containerColor)
                    .shimmerLoadingAnimation()
            )
            Box(
                modifier = Modifier.width(100.dp).height(40.dp)
                    .background(CardDefaults.cardColors().containerColor)
                    .shimmerLoadingAnimation()
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .fillMaxWidth()
                .height(220.dp)
                .clip(CardDefaults.shape)
                .background(CardDefaults.cardColors().containerColor)
                .shimmerLoadingAnimation()
        ) {}
    }

    @Composable
    fun BannerCarouselWidget(
        banners: List<EventDto>,
        modifier: Modifier = Modifier,
        onBannerClick: (EventDto) -> Unit = {}
    ) {
        val pagerState = rememberPagerState(pageCount = { banners.size })
        if (banners.isNotEmpty()) {
            AutoSwipePagerEffect(pagerState, 4000L)
        }
        Column(modifier) {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 32.dp),
                pageSpacing = 8.dp,
                verticalAlignment = Alignment.Top,
            ) { page ->
                Card(
                    modifier = Modifier.height(if (pagerState.currentPage == page) 220.dp else 190.dp)
                        .offset(y = if (pagerState.currentPage == page) (-10).dp else 0.dp)
                        .shadow(
                            20.dp,
                            CardDefaults.shape,
                            ambientColor = MaterialTheme.colorScheme.onBackground,
                            spotColor = MaterialTheme.colorScheme.onBackground
                        )
                ) {
                    Box(
                        contentAlignment = Alignment.BottomStart
                    ) {
                        BannerWidget(
                            imageUrl = banners[page].eventPic ?: DummyBgImage,
                            contentDescription = banners[page].eventTitle,
                            modifier = Modifier.fillMaxHeight()
                                .clickable(enabled = page == pagerState.currentPage) {
                                    onBannerClick(banners[page])
                                }
                        )
                        Column(
                            modifier = Modifier.padding(20.dp).fillMaxWidth(),
                        ) {
                            Title(
                                text = banners[page].eventTitle ?: "",
                                textColor = Color.White
                            )
                            Text(
                                text = banners[page].description ?: "",
                                color = Color.White.copy(.7f),
                                fontFamily = displayFontFamily(),
                                fontSize = 10.ssp,
                                lineHeight = TextUnit.Unspecified,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2
                            )
                        }
                    }
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }
    }
}