package com.sdjic.gradnet.presentation.screens.accountSetup

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterDefaults
import com.dokar.sonner.rememberToasterState
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.tabs.FancyIndicator
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.helper.UiState
import com.sdjic.gradnet.presentation.helper.UiStateHandler
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicSetUpScreen
import com.sdjic.gradnet.presentation.screens.accountSetup.education.EducationSetUpScreen
import com.sdjic.gradnet.presentation.screens.accountSetup.profession.ProfessionSetUpScreen
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import com.sdjic.gradnet.presentation.screens.home.HomeScreen
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
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

        val scope = rememberCoroutineScope()
        val setUpAccountViewModel = koinScreenModel<SetUpAccountViewModel>()
        val state by setUpAccountViewModel.userData.collectAsState()
        val userRole by setUpAccountViewModel.userRole.collectAsState()

        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = {
                TopAppBar(
                    title = {
                        Title(
                            text = "${if (isEditProfile) "Edit " else "Set Up "}Profile",
                            size = 14.ssp
                        )
                    },
                    actions = {
                        PrimaryButton(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            shape = CircleShape,
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            onClick = {
                                if ((state as UiState.Success).data.isVerified) {
                                    setUpAccountViewModel.updateUserProfile {
                                        if (!isEditProfile) navigator.replace(HomeScreen())
                                    }
//                                    else navigator.pop()
                                } else {
                                    setUpAccountViewModel.showErrorState("Please verify to proceed")
                                }
                            }
                        ) {
                            SText(
                                text = "Save",
                                fontSize = 14.sp,
                                fontWeight = Bold,
                                textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }
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

            Box(
                modifier = Modifier.padding(sPad)
            ) {
                UiStateHandler(
                    uiState = state,
                    content = { userProfile ->

                        val setUpScreenTabs by remember {
                            mutableStateOf(
                                when (userRole) {
                                    UserRole.Admin,
                                    UserRole.Alumni,
                                    UserRole.Faculty -> listOf(
                                        TabItem.Basic,
                                        TabItem.Education,
                                        TabItem.Profession
                                    )

                                    UserRole.Organization, null -> listOf(
                                        TabItem.Basic,
                                        TabItem.Profession
                                    )
                                }
                            )
                        }

                        val pagerState = rememberPagerState(pageCount = { setUpScreenTabs.size })

                        Column {
                            Tabs(
                                tabs = setUpScreenTabs,
                                pagerState = pagerState,
                                onClick = { scope.launch { pagerState.animateScrollToPage(it) } })
                            TabsContent(
                                modifier = Modifier.weight(1f),
                                setUpAccountViewModel = setUpAccountViewModel,
                                tabs = setUpScreenTabs,
                                pagerState = pagerState,
                                userRole = userRole ?: UserRole.Organization
                            )
                        }
                    },
                    onErrorShowed = { }        // logout user
                )


                UiStateHandler(
                    uiState = setUpAccountViewModel.setUpOrEditState.collectAsState().value,
                    content = {
                        val toaster = rememberToasterState()
                        LaunchedEffect(Clock.System.now()) {
                            toaster.show(
                                message = it,
                                duration = ToasterDefaults.DurationLong,
                                type = ToastType.Success
                            )
                        }
                        Toaster(
                            state = toaster,
                            richColors = true,
                            darkTheme = isSystemInDarkTheme(),
                            showCloseButton = true,
                            alignment = Alignment.BottomCenter,
                        )
                    },
                    onErrorShowed = {}
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
            indicator = {
                FancyIndicator(
                    MaterialTheme.colorScheme.surfaceVariant,
                    Modifier.tabIndicatorOffset(pagerState.currentPage)
                )
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = Color.Gray,
                    onClick = { onClick(index) },
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = tab.title,
                        fontWeight = if (pagerState.currentPage == index) Bold else Normal,
                        fontFamily = displayFontFamily(), fontSize = 14.sp
                    )
                }
            }
        }
    }

    sealed class TabItem(
        var icon: Int,
        var title: String,
        var screen: @Composable (SetUpAccountViewModel, UserRole) -> Unit
    ) {
        data object Basic : TabItem(
            0, "Basic",
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