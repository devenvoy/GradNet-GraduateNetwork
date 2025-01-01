package com.sdjic.gradnet.presentation.screens.accountSetup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.composables.PrimaryButton
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.composables.Title
import com.sdjic.gradnet.presentation.core.model.BaseUser
import com.sdjic.gradnet.presentation.helper.UiStateHandler
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicSetUpScreen
import com.sdjic.gradnet.presentation.screens.demo.DemoScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

class SetUpScreen : Screen {

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
        ) {sPad->
            val setUpAccountViewModel = koinScreenModel<SetUpAccountViewModel>()
            val scope = rememberCoroutineScope()
            Box {
                UiStateHandler(
                    uiState = setUpAccountViewModel.userData.collectAsState().value,
                    content = {baseuser->
                        when(baseuser){
                            is BaseUser.AlumniUser -> {
                                // Alumni
                                AlumniSetUpScreen(
                                    sPad,
                                    scope,
                                    setUpAccountViewModel,
                                    baseuser
                                )
                            }
                            is BaseUser.FacultyUser -> {
                                FacultySetUpScreen(
                                    sPad,
                                    scope,
                                    setUpAccountViewModel,
                                    baseuser
                                )
                            }
                            is BaseUser.OrganizationUser -> {
                                OrganizationSetUpScreen(
                                    sPad,
                                    scope,
                                    setUpAccountViewModel,
                                    baseuser
                                )
                            }
                        }
                    },
                    onErrorShowed = {
                        // logout user
                    }
                )
            }
        }
    }

    @Composable
    private fun AlumniSetUpScreen(
        sPad: PaddingValues,
        scope: CoroutineScope,
        setUpAccountViewModel: SetUpAccountViewModel,
        baseUser: BaseUser.AlumniUser
    ) {

        val setUpScreenTabs by remember  {
            mutableStateOf(
                listOf(TabItem.Basic, TabItem.Education, TabItem.Profession)
            )
        }
        val pagerState = rememberPagerState(pageCount = { setUpScreenTabs.size })

        Column(
            modifier = Modifier.padding(sPad)
        ) {
            Tabs(
                tabs = setUpScreenTabs,
                pagerState = pagerState,
                onClick = { scope.launch { pagerState.animateScrollToPage(it) } }
            )
            TabsContent(
                modifier = Modifier.weight(1f),
                setUpAccountViewModel = setUpAccountViewModel,
                tabs = setUpScreenTabs,
                pagerState = pagerState,
                baseUser = baseUser
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.sdp),
            ) {
                PrimaryButton(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF43c71c)),
                    modifier = Modifier
                        .padding(horizontal = 10.sdp, vertical = 20.sdp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.sdp, vertical = 10.sdp),
                    onClick = { }
                ) {
                    SText(
                        text = "Save",
                        fontSize = 14.ssp,
                        fontWeight = W600,
                        textColor = MaterialTheme.colorScheme.surface,
                    )
                }
            }
        }
    }

    @Composable
    private fun FacultySetUpScreen(
        sPad: PaddingValues,
        scope: CoroutineScope,
        setUpAccountViewModel: SetUpAccountViewModel,
        baseUser: BaseUser.FacultyUser
    ) {
        val setUpScreenTabs by remember  {
            mutableStateOf(
                listOf(TabItem.Basic, TabItem.Education, TabItem.Profession)
            )
        }
        val pagerState = rememberPagerState(pageCount = { setUpScreenTabs.size })

        Column(
            modifier = Modifier.padding(sPad)
        ) {
            Tabs(
                tabs = setUpScreenTabs,
                pagerState = pagerState,
                onClick = { scope.launch { pagerState.animateScrollToPage(it) } }
            )
            TabsContent(
                modifier = Modifier.weight(1f),
                pagerState = pagerState,
                tabs = setUpScreenTabs,
                setUpAccountViewModel = setUpAccountViewModel,
                baseUser = baseUser
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.sdp),
            ) {
                PrimaryButton(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF43c71c)),
                    modifier = Modifier
                        .padding(horizontal = 10.sdp, vertical = 20.sdp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.sdp, vertical = 10.sdp),
                    onClick = { }
                ) {
                    SText(
                        text = "Save",
                        fontSize = 14.ssp,
                        fontWeight = W600,
                        textColor = MaterialTheme.colorScheme.surface,
                    )
                }
            }
        }
    }

    @Composable
    private fun OrganizationSetUpScreen(
        sPad: PaddingValues,
        scope: CoroutineScope,
        setUpAccountViewModel: SetUpAccountViewModel,
        baseUser: BaseUser.OrganizationUser
    ) {
        val setUpScreenTabs by remember  {
            mutableStateOf(
                listOf(TabItem.Basic, TabItem.Profession)
            )
        }
        val pagerState = rememberPagerState(pageCount = { setUpScreenTabs.size })

        Column(
            modifier = Modifier.padding(sPad)
        ) {
            Tabs(
                tabs = setUpScreenTabs,
                pagerState = pagerState,
                onClick = { scope.launch { pagerState.animateScrollToPage(it) } }
            )
            TabsContent(
                modifier = Modifier.weight(1f),
                pagerState = pagerState,
                tabs = setUpScreenTabs,
                setUpAccountViewModel = setUpAccountViewModel,
                baseUser = baseUser
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.sdp),
            ) {
                PrimaryButton(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF43c71c)),
                    modifier = Modifier
                        .padding(horizontal = 10.sdp, vertical = 20.sdp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.sdp, vertical = 10.sdp),
                    onClick = { }
                ) {
                    SText(
                        text = "Save",
                        fontSize = 14.ssp,
                        fontWeight = W600,
                        textColor = MaterialTheme.colorScheme.surface,
                    )
                }
            }
        }
    }


    @Composable
    fun TabsContent(
        modifier: Modifier = Modifier,
        pagerState: PagerState,
        tabs: List<TabItem>,
        setUpAccountViewModel: SetUpAccountViewModel,
        baseUser: BaseUser
    ) {
        HorizontalPager(modifier = modifier, state = pagerState) { page ->
            tabs[page].screen(setUpAccountViewModel,baseUser)
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
        var screen: @Composable (SetUpAccountViewModel,BaseUser) -> Unit
    ) {
        data object Basic : TabItem(0, "Basic",
            { viewModel ,baseUser->
                BasicSetUpScreen(
                    basicState = viewModel.basicState.collectAsState().value,
                    baseUser = baseUser,
                    onAction = viewModel::onBasicAction
                )
            })
        data object Education : TabItem(0, "Education", { viewModel ,baseUser -> DemoScreen("Education") })
        data object Profession : TabItem(0, "Profession", { viewModel ,baseUser -> DemoScreen("Profession") })
    }
}