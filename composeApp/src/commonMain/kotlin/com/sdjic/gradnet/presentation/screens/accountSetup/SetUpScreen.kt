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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.sdjic.gradnet.presentation.helper.UiStateHandler
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicScreenAction
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicSetUpScreen
import com.sdjic.gradnet.presentation.screens.accountSetup.education.EducationSetUpScreen
import com.sdjic.gradnet.presentation.screens.accountSetup.profession.ProfessionSetUpScreen
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

class SetUpScreen(private val isEditProfile: Boolean) : Screen {

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
                        Title(text = "${if (isEditProfile) "Edit " else "Set Up "}Profile")
                    },
                    navigationIcon = {
                        if (isEditProfile) {
                            IconButton(onClick = { navigator.pop() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "back"
                                )
                            }
                        }
                    }
                )
            }
        )
        { sPad ->
            val setUpAccountViewModel = koinScreenModel<SetUpAccountViewModel>()
            val scope = rememberCoroutineScope()
            Box {
                UiStateHandler(
                    uiState = setUpAccountViewModel.userData.collectAsState().value,
                    content = { userProfile ->
                        val userRole by remember { mutableStateOf(userProfile.userRole) }

                        val setUpScreenTabs by remember {
                            mutableStateOf(
                                when (userRole) {
                                    UserRole.Alumni,
                                    UserRole.Faculty -> listOf(
                                        TabItem.Basic,
                                        TabItem.Education,
                                        TabItem.Profession
                                    )

                                    UserRole.Organization -> listOf(
                                        TabItem.Basic,
                                        TabItem.Profession
                                    )
                                }
                            )
                        }

                        val pagerState = rememberPagerState(pageCount = { setUpScreenTabs.size })

                        Column(
                            modifier = Modifier.padding(sPad)
                        ) {
                            Tabs(tabs = setUpScreenTabs,
                                pagerState = pagerState,
                                onClick = { scope.launch { pagerState.animateScrollToPage(it) } })
                            TabsContent(
                                modifier = Modifier.weight(1f),
                                setUpAccountViewModel = setUpAccountViewModel,
                                tabs = setUpScreenTabs,
                                pagerState = pagerState,
                                userRole = userRole
                            )
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.sdp),
                            ) {
                                PrimaryButton(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF43c71c)
                                    ),
                                    modifier = Modifier.padding(10.sdp, 20.sdp).fillMaxWidth(),
                                    contentPadding = PaddingValues(20.sdp, 10.sdp),
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
                    },
                    onErrorShowed = { }        // logout user
                )
            }
        }
    }


    @Composable
    fun TabsContent(
        modifier: Modifier = Modifier,
        pagerState: PagerState,
        tabs: List<TabItem>,
        setUpAccountViewModel: SetUpAccountViewModel,
        userRole: UserRole
    ) {
        HorizontalPager(modifier = modifier, state = pagerState) { page ->
            LaunchedEffect(page) {
                setUpAccountViewModel.onBasicAction(
                    BasicScreenAction.OnOtpBottomSheetStateChange(false)
                )
            }
            tabs[page].screen(setUpAccountViewModel, userRole)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Tabs(
        pagerState: PagerState,
        onClick: (Int) -> Unit,
        tabs: List<TabItem>
    ) {
        SecondaryTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background,
//            indicator = { FancyAnimatedIndicatorWithModifier(state) }
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
        var screen: @Composable (SetUpAccountViewModel, UserRole) -> Unit
    ) {
        data object Basic : TabItem(0, "Basic",
            { viewModel, role ->
                BasicSetUpScreen(
                    isVerified = viewModel.isVerified.collectAsState().value,
                    basicState = viewModel.basicState.collectAsState().value,
                    userRole = role,
                    onAction = viewModel::onBasicAction
                )
            })

        data object Education :
            TabItem(0, "Education", { viewModel, role ->
                EducationSetUpScreen(
                    isVerified = viewModel.isVerified.collectAsState().value,
                    educationState = viewModel.educationState.collectAsState().value,
                    onAction = viewModel::onEducationAction,
                    userRole = role
                )
            })

        data object Profession :
            TabItem(0, "Profession", { viewModel, role ->
                ProfessionSetUpScreen(
                    isVerified = viewModel.isVerified.collectAsState().value,
                    professionState = viewModel.professionState.collectAsState().value,
                    userRole = role,
                    onAction = viewModel::onProfessionAction
                )
            })
    }
}