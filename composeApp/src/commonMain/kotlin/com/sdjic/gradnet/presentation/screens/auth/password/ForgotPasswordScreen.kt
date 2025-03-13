package com.sdjic.gradnet.presentation.screens.auth.password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.images.LongBackButton
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputField
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.alternate_email
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource

class ForgotPasswordScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val forgotPasswordScreenModel = koinScreenModel<ForgotPasswordScreenModel>()
        ForgotPasswordContent(
            viewModel = forgotPasswordScreenModel, onBackPressed = navigator::pop
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ForgotPasswordContent(onBackPressed: () -> Unit, viewModel: ForgotPasswordScreenModel) {
        Scaffold(
            modifier = Modifier.imePadding(), topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ), title = {
                    Title(
                        textColor = Color.White, text = "Forgot Password", size = 14.ssp
                    )
                }, navigationIcon = {
                    LongBackButton(
                        iconColor = Color.White, onBackPressed = onBackPressed
                    )
                })
            }) { innerPad ->
            Column(
                modifier = Modifier.padding(innerPad).fillMaxSize().padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {


                CustomInputField(
                    fieldTitle = "Email",
                    textFieldValue = viewModel.email.value.text,
                    onValueChange = { viewModel.email.value = viewModel.email.value.copy(it) },
                    placeholder = { Text("Enter email") },
                    supportingText = {
                        Text(
                            text = "We will send you a link to reset your password",
                            fontFamily = displayFontFamily(),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.size(20.sdp),
                            painter = painterResource(Res.drawable.alternate_email),
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "Email icon",
                        )
                    },
                )

                PrimaryButton(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp)
                        .fillMaxWidth(),
                    onClick = viewModel::sendLink,
                ) {
                    SText(
                        text = "Get New Password",
                        fontWeight = FontWeight.Normal,
                        textColor = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.ssp
                    )
                }
            }
        }
    }
}