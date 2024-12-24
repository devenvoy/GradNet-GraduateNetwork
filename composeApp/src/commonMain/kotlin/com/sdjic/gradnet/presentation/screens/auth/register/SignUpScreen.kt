package com.sdjic.gradnet.presentation.screens.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.composables.CustomInputField
import com.sdjic.gradnet.presentation.composables.CustomInputPasswordField
import com.sdjic.gradnet.presentation.composables.PrimaryButton
import com.sdjic.gradnet.presentation.composables.Title
import com.sdjic.gradnet.presentation.helper.UiStateHandler
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import compose.icons.FeatherIcons
import compose.icons.feathericons.Mail
import compose.icons.feathericons.User
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.stringResource

class SignUpScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val signUpScreenModel = koinScreenModel<SignUpScreenModel>()
        SignUpScreenContent(
            viewModel = signUpScreenModel,
            onSignUpSuccess = { }
        )
    }

    @Composable
    fun SignUpScreenContent(onSignUpSuccess: () -> Unit, viewModel: SignUpScreenModel) {

        val keyboardController = LocalSoftwareKeyboardController.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.sdp),
            verticalArrangement = Arrangement.spacedBy(10.sdp)
        ) {
            Title(
                text = "Create free account",
                modifier = Modifier
                    .padding(top = 60.sdp),
                textColor = Color.Black,
                size = 22.ssp
            )

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
                onValueChange = { viewModel.onEmailChange(it) },
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
                placeholder = { Text("Ex: 12345678") },
                isPasswordField = true
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

            UiStateHandler(
                uiState = viewModel.signUpState.collectAsState().value,
                onErrorShowed = {},
                content = { onSignUpSuccess() }
            )

            TextButton(
                onClick = {}
            ){
                Text(
                    text = "Already have an account? Sign in",
                    style = TextStyle(
                        fontSize = 10.ssp,
                        fontWeight = W400,
                        textAlign = TextAlign.Center
                    )
                )
            }

        }
    }
}