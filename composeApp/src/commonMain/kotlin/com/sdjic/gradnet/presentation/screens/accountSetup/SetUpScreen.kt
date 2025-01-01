package com.sdjic.gradnet.presentation.screens.accountSetup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.composables.Title
import com.sdjic.gradnet.presentation.screens.demo.DemoScreen
import kotlinx.coroutines.launch

class SetUpScreen : Screen {
    private val setUpScreenTabs = listOf(TabItem.Basic, TabItem.Education, TabItem.Profession)

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        BaseSetUpContent(navigator)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BaseSetUpContent(navigator: Navigator) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Title(text = "Set Up Profile")
                    }
                )
            }
        ) {
            val pagerState = rememberPagerState(pageCount = { setUpScreenTabs.size })
            val scope = rememberCoroutineScope()
            Column(
                modifier = Modifier.padding(it)
            ) {
                Tabs(
                    pagerState = pagerState,
                    onClick = { scope.launch { pagerState.animateScrollToPage(it) } }
                )
                TabsContent(pagerState)
            }
        }
    }

    @Composable
    fun TabsContent(pagerState: PagerState) {
        HorizontalPager(state = pagerState) { page ->
            setUpScreenTabs[page].screen()
        }
    }

    @Composable
    fun Tabs(
        pagerState: PagerState,
        onClick: (Int) -> Unit
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            setUpScreenTabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { onClick(index) },
                    modifier = Modifier.padding(12.dp)
                ) {
                    SText(text = tab.title)
                }
            }
        }
    }

    sealed class TabItem(var icon: Int, var title: String, var screen: @Composable () -> Unit) {
        data object Basic : TabItem(0, "Basic", { DemoScreen("Basic") })
        data object Education : TabItem(0, "Education", { DemoScreen("Education") })
        data object Profession : TabItem(0, "Profession", { DemoScreen("Profession") })
    }
}