package com.sdjic.gradnet.presentation.screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.composables.CustomInputField
import com.sdjic.gradnet.presentation.composables.CustomInputPasswordField
import com.sdjic.gradnet.presentation.composables.Label
import com.sdjic.gradnet.presentation.composables.PrimaryButton
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.composables.SecondaryOutlinedButton
import com.sdjic.gradnet.presentation.helper.UiState
import com.sdjic.gradnet.presentation.helper.UiStateHandler
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import compose.icons.FeatherIcons
import compose.icons.feathericons.Image
import compose.icons.feathericons.Mail
import compose.icons.feathericons.Voicemail
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.btn_google_sing_in
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val loginScreenModel = koinScreenModel<LoginScreenModel>()
        LoginScreenContent(
            loginScreenModel = loginScreenModel,
            onLoginSuccess = {},
            navigateToSignUp = {}
        )
    }

    @Composable
    fun LoginScreenContent(
        loginScreenModel: LoginScreenModel,
        onLoginSuccess: () -> Unit,
        navigateToSignUp: () -> Unit
    ) {
        val loginState by loginScreenModel.loginState.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
//            Image(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                painter = painterResource(R.drawable.sign_in_bg),
//                contentDescription = null,
//                contentScale = ContentScale.FillWidth
//            )
            SignInLayout(loginScreenModel, navigateToSignUp)
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
            onErrorShowed = {}
        ) {
            onLoginSuccess()
        }
    }

    @Composable
    fun SignInLayout(viewModel: LoginScreenModel, navigateToSignUp: () -> Unit) {
        Column(
            modifier = Modifier
                .padding(top = 160.sdp)
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 32.sdp, topEnd = 32.sdp))
                .background(color = Color.White)
                .padding(20.sdp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.sdp)
        ) {
            Label(
                text = "Sign In",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textColor = Color.Black,
            )
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(10.sdp)
            ) {
                CustomInputField(
                    fieldTitle = "Email",
                    textFieldValue = viewModel.email.value,
                    onValueChange = { viewModel.email.value = it },
                    placeholder = { Text("Enter email") },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(end = 6.sdp)
                                .size(20.sdp),
                            imageVector = FeatherIcons.Mail,
                            contentDescription = "Email icon",
//                            tint = PlaceHolderTextColor
                        )
                    }
                )
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
           TextButton(onClick = {}){
               SText(
                   text = "Forgot Password? Click here",
                   textColor = Color(0xFFB6B6B6),
                   fontSize = 10.ssp
               )
           }
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .clickable(
                        onClick = {
                           /* val options =
                                GoogleSignInOptions
                                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(App.instance.resources.getString(R.string.web_api_key))
                                    .requestEmail()
                                    .build()
                            val gso = GoogleSignIn.getClient(App.instance, options)
                            gso.signOut()
                            launcher.launch(gso.signInIntent)*/
                        }
                    ),
                painter = painterResource(Res.drawable.btn_google_sing_in),
                contentDescription = "google sign in",
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.sdp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Don't have an account yet?",
                    textColor = Color.Black,
                    fontSize = 12.ssp,
                    fontWeight = FontWeight.W400

                )
                SecondaryOutlinedButton(
                    modifier = Modifier
                        .padding(horizontal = 20.sdp)
                        .fillMaxWidth(),
                    onClick = { navigateToSignUp() }
                )
            }
        }
    }
}