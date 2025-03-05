package com.sdjic.gradnet.presentation.screens.auth.password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.images.BackButton
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputPasswordField
import com.sdjic.gradnet.presentation.helper.UiState
import com.sdjic.gradnet.presentation.helper.UiStateHandler
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp


typealias ChangePasswordState = UiState<String>

class ChangePasswordScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: ChangePasswordScreenModel = koinScreenModel()
        Box(modifier = Modifier.fillMaxSize()) {
            ChangePasswordScreenContent(viewModel = viewModel)
            UiStateHandler(
                uiState = viewModel.uiState.collectAsState().value,
                onErrorShowed = {},
                content = {}
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ChangePasswordScreenContent(
        viewModel: ChangePasswordScreenModel = koinScreenModel()
    ) {
        val navigator = LocalNavigator.currentOrThrow
        val keyboardController = LocalSoftwareKeyboardController.current

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Title(text = "Change Password") },
                    navigationIcon = {
                        BackButton { navigator.pop() }
                    }
                )
            }
        ) { ip ->

            Column(
                modifier = Modifier.padding(ip).padding(horizontal = 10.sdp),
                verticalArrangement = Arrangement.spacedBy(10.sdp)
            ) {

                CustomInputPasswordField(
                    fieldTitle = "Old Password",
                    textFieldValue = viewModel.oldPassword.value,
                    onValueChange = { viewModel.onOldPasswordChange(it) },
                    placeholder = { Text("ex: sfg+b4dbd4") },
                    isPasswordField = true,
                    imeAction = ImeAction.Next
                )
                CustomInputPasswordField(
                    fieldTitle = "New Password",
                    textFieldValue = viewModel.newPassword.value,
                    onValueChange = { viewModel.onNewPasswordChange(it) },
                    placeholder = { Text("ex: sfg+b4dbd4") },
                    isPasswordField = true,
                    imeAction = ImeAction.Next
                )
                CustomInputPasswordField(
                    fieldTitle = "Confirm new Password",
                    textFieldValue = viewModel.confirmPassword.value,
                    onValueChange = { viewModel.onConfirmPasswordChange(it) },
                    placeholder = { Text("ex: sfg+b4dbd4") },
                    isPasswordField = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                PrimaryButton(
                    modifier = Modifier.fillMaxWidth().padding(top = 20.sdp),
                    onClick = {
                        keyboardController?.hide()
                        viewModel.changePassword()
                    },
                ) {
                    Text(
                        text = "Change password",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold, fontSize = 16.ssp
                        )
                    )
                }
            }
        }
    }
}