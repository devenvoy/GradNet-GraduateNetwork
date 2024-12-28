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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.composables.CustomInputField
import com.sdjic.gradnet.presentation.composables.CustomInputPasswordField
import com.sdjic.gradnet.presentation.composables.PrimaryButton
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.composables.SecondaryOutlinedButton
import com.sdjic.gradnet.presentation.composables.Title
import com.sdjic.gradnet.presentation.helper.UiState
import com.sdjic.gradnet.presentation.helper.UiStateHandler
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.auth.register.SignUpScreen
import com.sdjic.gradnet.presentation.screens.home.HomeScreen
import compose.icons.FeatherIcons
import compose.icons.feathericons.Mail
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.btn_google_sing_in
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val loginScreenModel = koinScreenModel<LoginScreenModel>()
        LoginScreenContent(
            loginScreenModel = loginScreenModel,
            onLoginSuccess = {navigator.replace(HomeScreen())},
            navigateToSignUp = { navigator.replace(SignUpScreen(true)) },
            navigateToForgotPasswordScreen = {}
        )
        Button(onClick = { navigator.replace(HomeScreen()) }) {
            SText("Click")
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun LoginScreenContent(
        loginScreenModel: LoginScreenModel,
        onLoginSuccess: () -> Unit,
        navigateToSignUp: () -> Unit,
        navigateToForgotPasswordScreen: () -> Unit
    ) {
        val loginState by loginScreenModel.loginState.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val composition by rememberLottieComposition {
                LottieCompositionSpec.JsonString(
                    Res.readBytes("files/working.json").decodeToString()
                )
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
            LoginStateUI(
                loginState = loginState,
                onLoginSuccess = onLoginSuccess
            )
        }

    }

    @Composable
    fun LoginStateUI(loginState: UiState<Any>, onLoginSuccess: () -> Unit) {
        UiStateHandler(
            uiState = loginState,
            onErrorShowed = {},
            content = { onLoginSuccess() }
        )
    }

    @Composable
    fun SignInLayout(
        viewModel: LoginScreenModel,
        navigateToSignUp: () -> Unit,
        navigateToForgotPasswordScreen: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .padding(top = 180.sdp)
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 32.sdp, topEnd = 32.sdp))
                .padding(top = 10.sdp)
                .padding(10.sdp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.sdp)
        ) {
            Title(
                text = "Welcome Back",
                size = 22.ssp,
                textColor = MaterialTheme.colorScheme.onSurface,
            )
            Column {
                CustomInputField(
                    fieldTitle = "Email",
                    textFieldValue = viewModel.email.value,
                    onValueChange = { viewModel.email.value = it },
                    placeholder = { Text("Enter email") },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.size(20.sdp),
                            imageVector = FeatherIcons.Mail,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "Email icon",
                        )
                    }
                )
                Spacer(modifier = Modifier.height(10.sdp))
                CustomInputPasswordField(
                    fieldTitle = "Password",
                    textFieldValue = viewModel.password.value,
                    onValueChange = { viewModel.password.value = it },
                    placeholder = { Text("Password") },
                    isPasswordField = true
                )
            }
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.login() },
            ) {
                SText(
                    text = "Sign In",
                    fontWeight = FontWeight.SemiBold,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.ssp
                )
            }
            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = { navigateToForgotPasswordScreen() }) {
                SText(
                    text = "Forgot Password? Click here",
                    textColor = Color(0xFFB6B6B6),
                    fontSize = 12.ssp
                )
            }
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable(onClick = {
                        /* val options =
                             GoogleSignInOptions
                                 .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                 .requestIdToken(App.instance.resources.getString(R.string.web_api_key))
                                 .requestEmail()
                                 .build()
                         val gso = GoogleSignIn.getClient(App.instance, options)
                         gso.signOut()
                         launcher.launch(gso.signInIntent)*/
                    }),
                painter = painterResource(Res.drawable.btn_google_sing_in),
                contentDescription = "google sign in",
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.sdp))
                SText(
                    text = "Don't have an account yet?",
                    fontSize = 12.ssp,
                    fontWeight = FontWeight.W400
                )
                Spacer(modifier = Modifier.height(10.sdp))
                SecondaryOutlinedButton(
                    modifier = Modifier
                        .padding(horizontal = 10.sdp)
                        .fillMaxWidth(),
                    onClick = { navigateToSignUp() }
                )
            }
        }
    }
}