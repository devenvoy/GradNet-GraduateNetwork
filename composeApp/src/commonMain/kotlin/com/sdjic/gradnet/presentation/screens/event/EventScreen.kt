package com.sdjic.gradnet.presentation.screens.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import com.sdjic.gradnet.data.network.entity.dto.EventDto
import com.sdjic.gradnet.presentation.composables.EmptyScreen
import com.sdjic.gradnet.presentation.composables.images.BannerWidget
import com.sdjic.gradnet.presentation.helper.koinScreenModel


// for see about university and current affairs
class EventScreen : Screen {
    @Composable
    override fun Content() {
        EventScreenContent()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private @Composable
    fun EventScreenContent() {
        val eventScreenModel = koinScreenModel<EventScreenModel>()

        val list by eventScreenModel.eventList.collectAsStateWithLifecycle()

        BottomSheetScaffold(
            sheetContent = {
                EmptyScreen()
            }
        ){
            Column {
                BannerCarouselWidget(
                    list,
                )
            }
        }

        /*LazyColumn {
            items(list) {
                Text(list.toString())
            }
        }*/
    }

    @Composable
    fun BannerCarouselWidget(
        banners: List<EventDto?>,
        modifier: Modifier = Modifier
    ) {
        val pagerState = rememberPagerState(
            initialPage = 2,
            pageCount = { banners.size }
        )

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = modifier
        ) {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 16.dp),
                pageSpacing = 8.dp,
                verticalAlignment = Alignment.Top,
            ) { page ->
                banners[page]?.eventPic?.let {
                    BannerWidget(
                        imageUrl = banners[page]?.eventPic!!,
                        contentDescription = banners[page]?.eventTitle
                    )
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
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