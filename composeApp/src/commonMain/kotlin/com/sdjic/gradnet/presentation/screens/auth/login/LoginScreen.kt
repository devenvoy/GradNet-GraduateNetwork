package com.sdjic.gradnet.presentation.screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputField
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputPasswordField
import com.sdjic.gradnet.presentation.helper.UiStateHandler
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.auth.password.ForgotPasswordScreen
import com.sdjic.gradnet.presentation.screens.auth.register.SignUpScreen
import com.sdjic.gradnet.presentation.screens.home.HomeScreen
import com.sdjic.gradnet.presentation.screens.verification.UserVerificationScreen
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.alternate_email
import gradnet_graduatenetwork.composeapp.generated.resources.create_account
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.DotLottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val loginScreenModel = koinScreenModel<LoginScreenModel>()
        LoginScreenContent(
            loginScreenModel = loginScreenModel,
            onLoginResult = {
                navigator.replace(if (it) HomeScreen() else UserVerificationScreen())
            },
            navigateToSignUp = { navigator.replace(SignUpScreen(true)) },
            navigateToForgotPasswordScreen = {
                navigator.push(ForgotPasswordScreen())
            }
        )
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun LoginScreenContent(
        loginScreenModel: LoginScreenModel,
        onLoginResult: (Boolean) -> Unit,
        navigateToSignUp: () -> Unit,
        navigateToForgotPasswordScreen: () -> Unit
    ) {
        val loginState by loginScreenModel.loginState.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val composition by rememberLottieComposition {
                LottieCompositionSpec.DotLottie(Res.readBytes("files/working.lottie"))
            }
            Image(
                modifier = Modifier.fillMaxWidth().height(185.sdp),
                painter = rememberLottiePainter(
                    composition = composition,
                    iterations = Compottie.IterateForever
                ),
                contentDescription = "loader"
            )
            SignInLayout(loginScreenModel, navigateToSignUp, navigateToForgotPasswordScreen)

            UiStateHandler(
                uiState = loginState,
                onErrorShowed = {},
                content = { LaunchedEffect(Unit) { onLoginResult(it) } }
            )
        }
    }

    @Composable
    fun SignInLayout(
        viewModel: LoginScreenModel,
        navigateToSignUp: () -> Unit,
        navigateToForgotPasswordScreen: () -> Unit
    ) {
        Surface(
            modifier = Modifier.padding(top = 180.sdp)
                .fillMaxSize(), shadowElevation = 12.dp,
            shape = RoundedCornerShape(topStart = 32.sdp, topEnd = 32.sdp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .padding(top = 10.sdp)
                    .padding(10.sdp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.sdp)
            ) {
                Title(
                    text = "Welcome Back",
                    size = 26.sp,
                    textColor = MaterialTheme.colorScheme.onSurface,
                )
                CustomInputField(
                    fieldTitle = "Email",
                    textFieldValue = viewModel.email.value.text,
                    onValueChange = { viewModel.email.value = viewModel.email.value.copy(it) },
                    placeholder = { Text("Enter email") },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.size(20.sdp),
                            painter = painterResource(Res.drawable.alternate_email),
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "Email icon",
                        )
                    },
                )
                CustomInputPasswordField(
                    fieldTitle = "Password",
                    textFieldValue = viewModel.password.value,
                    onValueChange = { viewModel.password.value = it },
                    placeholder = { Text("Password") },
                    isPasswordField = true
                )
                TextButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = navigateToForgotPasswordScreen
                ) {
                    SText(
                        text = "Forgot Password? Click here",
                        textColor = Color(0xFFB8B8B8),
                        fontSize = 11.ssp
                    )
                }
                PrimaryButton(
                    modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth(),
                    onClick = { viewModel.login() },
                ) {
                    SText(
                        text = "Sign In",
                        fontWeight = FontWeight.SemiBold,
                        textColor = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.ssp
                    )
                }

                GoogleButtonUiContainer(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onGoogleSignInResult = { googleUser ->
                        if (googleUser != null) {
                            viewModel.loginWithGoogle(googleUser)
                        } else {
                            viewModel.showErrorState("Login Failed")
                        }
                    }) {
                    GoogleSignInButton(onClick = { this.onClick() })
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.sdp))
                    Text(
                        modifier = Modifier.clickable(onClick = { navigateToSignUp() }),
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 12.ssp,
                                    fontWeight = FontWeight.W400,
                                    fontFamily = displayFontFamily()
                                )
                            ) {
                                append("Don't have an account yet?")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 12.ssp,
                                    fontWeight = FontWeight.W400,
                                    fontFamily = displayFontFamily(),
                                    textDecoration = TextDecoration.Underline,
                                )
                            ) {
                                append(stringResource(Res.string.create_account))
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(20.sdp))
                }
            }
        }
    }
}