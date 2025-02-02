package com.sdjic.gradnet.presentation.screens.auth.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.internal.BackHandler
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.button.SecondaryOutlinedButton
import com.sdjic.gradnet.presentation.composables.filter.RoleSelectionItem
import com.sdjic.gradnet.presentation.composables.images.BackButton
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputField
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputPasswordField
import com.sdjic.gradnet.presentation.helper.UiStateHandler
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.auth.login.LoginScreen
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import com.sdjic.gradnet.presentation.screens.home.HomeScreen
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.User
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.alternate_email
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource

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
            UiStateHandler(uiState = signUpScreenModel.signUpState.collectAsState().value,
                onErrorShowed = {},
                content = { navigator.replace(HomeScreen()) })
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SignUpScreenContent(
        viewModel: SignUpScreenModel, onBackPressed: () -> Unit, showNavigatorIcon: Boolean
    ) {

        val keyboardController = LocalSoftwareKeyboardController.current

        val scrollBehavior =
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

        val dialogState by viewModel.googleProcessDialog.collectAsState()

        val selectedUserRole by viewModel.selectedUserRole.collectAsState()

        if (dialogState) {
            BasicAlertDialog(properties = DialogProperties(
                usePlatformDefaultWidth = false, dismissOnClickOutside = false
            ), onDismissRequest = {
                viewModel.googleUser.value = null
                viewModel.changeGoogleDialogState(false)
            }) {
                Surface(
                    modifier = Modifier.padding(10.sdp), shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(10.sdp)) {

                        Title(
                            modifier = Modifier.padding(10.sdp),
                            size = 16.ssp,
                            text = "Select account type"
                        )

                        RoleSelectionGrid(
                            userRoles = viewModel.userRoles.value,
                            onClick = { viewModel.onUserRoleSelected(it) },
                            selectedUserRole = selectedUserRole
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 10.sdp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            SecondaryOutlinedButton(onClick = {
                                viewModel.googleUser.value = null
                                viewModel.changeGoogleDialogState(false)
                            }) {
                                SText(
                                    text = "Cancel",
                                    modifier = Modifier.padding(horizontal = 10.sdp)
                                )
                            }
                            Spacer(modifier = Modifier.width(20.sdp))
                            PrimaryButton(onClick = {
                                viewModel.signUpWithGoogle()
                                viewModel.changeGoogleDialogState(false)
                            }) {
                                SText(
                                    modifier = Modifier.padding(horizontal = 10.sdp),
                                    text = "Create account",
                                    textColor = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
            }
        }

        Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            LargeTopAppBar(colors = TopAppBarDefaults.largeTopAppBarColors(
                scrolledContainerColor = MaterialTheme.colorScheme.background,
                containerColor = MaterialTheme.colorScheme.background
            ), scrollBehavior = scrollBehavior, title = {
                Title(
                    text = "Create account",
                    size = if (scrollBehavior.state.collapsedFraction == 1f) 16.ssp else 22.ssp
                )
            }, navigationIcon = {
                if (showNavigatorIcon) {
                    BackButton(onBackPressed)
                }
            })
        }) { padding ->
            Column(
                modifier = Modifier.padding(padding).fillMaxSize()
                    .verticalScroll(rememberScrollState()).padding(horizontal = 10.sdp),
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
                    },
                )

                CustomInputField(
                    fieldTitle = "Email",
                    textFieldValue = viewModel.email.collectAsState().value,
                    onValueChange = { s -> viewModel.onEmailChange(s) },
                    placeholder = { Text("Enter email") },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.padding(end = 6.sdp),
                            painter = painterResource(Res.drawable.alternate_email),
                            contentDescription = "Email icon",
                        )
                    },
                )

                /*CustomInputField(
                    fieldTitle = "Phone number",
                    textFieldValue = viewModel.phone.collectAsState().value,
                    onValueChange = { s -> viewModel.onPhoneChange(s) },
                    placeholder = { Text("Enter Phone number") },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.padding(end = 6.sdp),
                            imageVector = FeatherIcons.Phone,
                            contentDescription = "Email icon",
                        )
                    },
                    prefix = {
                        Title(text = "+91 ")
                    },
                    keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )*/

                CustomInputPasswordField(
                    fieldTitle = "Password",
                    textFieldValue = viewModel.password.collectAsState().value,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    placeholder = { Text("ex: sfg+b4dbd4") },
                    isPasswordField = true
                )

                Title(
                    modifier = Modifier.padding(top = 10.sdp), text = "Select account type"
                )

                RoleSelectionGrid(
                    userRoles = viewModel.userRoles.value,
                    onClick = { viewModel.onUserRoleSelected(it) },
                    selectedUserRole = selectedUserRole
                )

                PrimaryButton(
                    modifier = Modifier.fillMaxWidth().padding(top = 20.sdp),
                    onClick = {
                        keyboardController?.hide()
                        viewModel.signUp()
                    },
                ) {
                    Text(
                        text = "Register", style = TextStyle(
                            fontWeight = FontWeight.SemiBold, fontSize = 16.ssp
                        )
                    )
                }

                GoogleButtonUiContainer(modifier = Modifier.padding(vertical = 10.sdp)
                    .align(Alignment.CenterHorizontally), onGoogleSignInResult = { googleUser ->
                    viewModel.googleUser.value = googleUser
                    viewModel.changeGoogleDialogState(true)
                }) {
                    GoogleSignInButton(onClick = { this.onClick() }, text = "Sign up with google")
                }

                Text(modifier = Modifier.padding(8.sdp).align(Alignment.End)
                    .clickable(onClick = { onBackPressed() }), text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 12.ssp,
                            fontWeight = W400,
                            fontFamily = displayFontFamily()
                        )
                    ) {
                        append("Already have an account?")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            fontSize = 12.ssp,
                            fontWeight = W400,
                            fontFamily = displayFontFamily(),
                            textDecoration = TextDecoration.Underline,
                        )
                    ) {
                        append("Sign In")
                    }
                })
            }
        }
    }

    @Composable
    fun RoleSelectionGrid(
        userRoles: List<UserRole>,
        onClick: (UserRole) -> Unit,
        selectedUserRole: UserRole?,
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