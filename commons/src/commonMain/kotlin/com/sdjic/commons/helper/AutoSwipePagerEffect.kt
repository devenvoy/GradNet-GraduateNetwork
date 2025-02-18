package com.sdjic.commons.helper

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun AutoSwipePagerEffect(
    pagerState: PagerState,
    durationMillis: Long
) {
    LaunchedEffect(pagerState, durationMillis) {
        while (true) {
            delay(durationMillis)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }
}
