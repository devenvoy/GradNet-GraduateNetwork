package com.sdjic.gradnet.presentation.screens.accountSetup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.composables.Title
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicSetUpScreen
import com.sdjic.gradnet.presentation.screens.demo.DemoScreen
import kotlinx.coroutines.launch

class SetUpScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        BaseSetUpContent(navigator)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BaseSetUpContent(navigator: Navigator) {

        val setUpScreenTabs by remember  {
            mutableStateOf(
                listOf(TabItem.Basic, TabItem.Education, TabItem.Profession)
            )
        }

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
                    tabs = setUpScreenTabs,
                    pagerState = pagerState,
                    onClick = { scope.launch { pagerState.animateScrollToPage(it) } }
                )
                TabsContent(tabs = setUpScreenTabs, pagerState = pagerState)
            }
        }
    }

    @Composable
    fun TabsContent(pagerState: PagerState, tabs: List<TabItem>) {
        val setUpAccountViewModel = koinScreenModel<SetUpAccountViewModel>()
        HorizontalPager(state = pagerState) { page ->
            tabs[page].screen(setUpAccountViewModel)
        }
    }

    @Composable
    fun Tabs(
        pagerState: PagerState,
        onClick: (Int) -> Unit,
        tabs: List<TabItem>
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            tabs.forEachIndexed { index, tab ->
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

    sealed class TabItem(
        var icon: Int,
        var title: String,
        var screen: @Composable (SetUpAccountViewModel) -> Unit
    ) {
        data object Basic : TabItem(0, "Basic",
            {
                BasicSetUpScreen(
                    basicState = it.basicState.collectAsState().value,
                    onAction = it::onBasicAction
                )
            })
        data object Education : TabItem(0, "Education", { DemoScreen("Education") })
        data object Profession : TabItem(0, "Profession", { DemoScreen("Profession") })
    }
}