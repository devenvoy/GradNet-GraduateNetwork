package com.sdjic.gradnet.presentation.screens.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.internal.BackHandler
import com.sdjic.gradnet.presentation.composables.CustomInputField
import com.sdjic.gradnet.presentation.composables.CustomInputPasswordField
import com.sdjic.gradnet.presentation.composables.PrimaryButton
import com.sdjic.gradnet.presentation.composables.RoleSelectionItem
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.composables.Title
import com.sdjic.gradnet.presentation.helper.UiStateHandler
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.auth.login.LoginScreen
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import com.sdjic.gradnet.presentation.screens.home.HomeScreen
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Mail
import compose.icons.feathericons.User
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

class SignUpScreen(
    private val showNavigatorIcon: Boolean = false
) : Screen {

    @OptIn(InternalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val signUpScreenModel = koinScreenModel<SignUpScreenModel>()

        BackHandler(enabled = showNavigatorIcon) {
            navigator.replace(LoginScreen())
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            SignUpScreenContent(
                viewModel = signUpScreenModel,
                onBackPressed = { navigator.replace(LoginScreen()) },
                showNavigatorIcon = showNavigatorIcon
            )
            Button(onClick = { navigator.replace(HomeScreen()) }) {
                SText("Click")
            }
            UiStateHandler(
                uiState = signUpScreenModel.signUpState.collectAsState().value,
                onErrorShowed = {},
                content = {}
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SignUpScreenContent(
        viewModel: SignUpScreenModel,
        onBackPressed: () -> Unit,
        showNavigatorIcon: Boolean
    ) {

        val keyboardController = LocalSoftwareKeyboardController.current

        val scrollBehavior =
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        scrolledContainerColor = MaterialTheme.colorScheme.background,
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    scrollBehavior = scrollBehavior,
                    title = {
                        Title(
                            text = "Create free account",
                            size = if (scrollBehavior.state.collapsedFraction == 1f) 16.ssp else 22.ssp
                        )
                    },
                    navigationIcon = {
                        if(showNavigatorIcon){
                            IconButton(onClick = onBackPressed) {
                                Icon(
                                    imageVector = FeatherIcons.ArrowLeft,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 10.sdp),
                verticalArrangement = Arrangement.spacedBy(10.sdp)
            ) {

                CustomInputField(
                    fieldTitle = "Full Name",
                    textFieldValue = viewModel.name.collectAsState().value,
                    onValueChange = { viewModel.onNameChange(it) },
                    placeholder = { Text("Enter Name") },
                    trailingIcon = {
                        Icon(
                            imageVector = FeatherIcons.User,
                            contentDescription = "Name icon",
                        )
                    }
                )

                CustomInputField(
                    fieldTitle = "Email",
                    textFieldValue = viewModel.email.collectAsState().value,
                    onValueChange = { s -> viewModel.onEmailChange(s) },
                    placeholder = { Text("Enter email") },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.padding(end = 6.sdp),
                            imageVector = FeatherIcons.Mail,
                            contentDescription = "Email icon",
                        )
                    }
                )

                CustomInputPasswordField(
                    fieldTitle = "Password",
                    textFieldValue = viewModel.password.collectAsState().value,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    placeholder = { Text("ex: 12345678") },
                    isPasswordField = true
                )

                Title(
                    modifier = Modifier.padding(top = 10.sdp),
                    text = "Select account type"
                )

                RoleSelectionGrid(
                    userRoles = viewModel.userRoles.value,
                    onClick = { viewModel.onUserRoleSelected(it) },
                    selectedUserRole = viewModel.selectedUserRole.collectAsState().value
                )

                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.sdp),
                    onClick = {
                        keyboardController?.hide()
                        viewModel.signUp()
                    },
                ) {
                    Text(
                        text = "Register",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.ssp
                        )
                    )
                }
                TextButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { onBackPressed() }
                ) {
                    Text(
                        text = "Already have an account? Sign in",
                        style = TextStyle(fontWeight = W400)
                    )
                }
            }
        }
    }

    @Composable
    fun RoleSelectionGrid(
        userRoles: List<UserRole>,
        onClick: (UserRole) -> Unit,
        selectedUserRole: UserRole,
    ) {
        LazyVerticalGrid(
            modifier = Modifier.heightIn(max = 100.sdp),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.sdp),
            horizontalArrangement = Arrangement.spacedBy(8.sdp)
        ) {
            items(userRoles) { userrole ->
                RoleSelectionItem(
                    isSelected = selectedUserRole == userrole,
                    onClick = { onClick(userrole) },
                    userRole = userrole
                )
            }
        }
    }
}