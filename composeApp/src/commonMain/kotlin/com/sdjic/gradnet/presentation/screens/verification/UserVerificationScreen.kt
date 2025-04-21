package com.sdjic.gradnet.presentation.screens.verification

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.di.platform_di.exitProcess
import com.sdjic.gradnet.presentation.composables.OtpBottomSheet
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.images.BackButton
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputField
import com.sdjic.gradnet.presentation.helper.UiState
import com.sdjic.gradnet.presentation.helper.UiStateHandler
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.accountSetup.SetUpScreen
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import com.sdjic.gradnet.presentation.screens.splash.SplashScreen
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

typealias UserVerificationState = UiState<Boolean>

class UserVerificationScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val keyboardManager = LocalSoftwareKeyboardController.current
        val viewModel: UserVerificationScreenModel = koinScreenModel()

        val sheetState = rememberModalBottomSheetState(true)

        val uiState by viewModel.verificationState.collectAsState()
        val showBottomSheet by viewModel.showOtpBottomSheet.collectAsState()
        val otpField by viewModel.otpField.collectAsState()
        val otpEmailField by viewModel.otpEmailField.collectAsState()
        val verificationField by viewModel.verificationField.collectAsState()
        val isUserVerified by viewModel.isUserVerified.collectAsState()
        val userRole = viewModel.userRole


        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    title = {
                        Title(
                            text = "User Verification",
                            size = 14.ssp,
                            textColor = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    navigationIcon = {
                        BackButton(
                            iconColor = MaterialTheme.colorScheme.onPrimary
                        ) {
                            if (!navigator.canPop) {
                                exitProcess() // This will close the app
                            } else {
                                navigator.pop()
                            }
                        }
                    }
                )
            }
        ) { ip ->
            Box(modifier = Modifier.padding(ip)) {

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(30.sdp)
                        .clip(RoundedCornerShape(bottomEnd = 12.sdp, bottomStart = 12.sdp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 30.sdp)
                        .padding(12.sdp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Title(
                                text = "${
                                    userRole?.name?.lowercase()
                                        ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                                } Verification",
                                size = 16.ssp
                            )
                            SText(
                                text = "only need to verify one time",
                                textColor = MaterialTheme.colorScheme.secondary,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(
                                    if (isUserVerified) Color(0xFF4CAF50) else Color(0xFFF44336),
                                    shape = CircleShape
                                )
                                .padding(horizontal = 12.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = if (isUserVerified) "Verified" else "Not verified",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(Modifier.height(16.sdp))

                    val fieldTitle by remember {
                        mutableStateOf(
                            when (userRole) {
                                UserRole.Alumni -> "Spid no"
                                UserRole.Faculty -> "Faculty id"
                                UserRole.Organization -> "Organization id"
                                UserRole.Admin -> "Admin"
                                null -> ""
                            }
                        )
                    }

                    CustomInputField(
                        fieldTitle = fieldTitle,
                        textFieldValue = verificationField,
                        onValueChange = viewModel::onVerificationFieldValueChange,
                        placeholder = { SText("Enter your $fieldTitle") },
                        keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Number),
                        supportingText = if (isUserVerified) null else ({
                            SText("Required", textColor = Color.Red)
                        }),
                        isEnable = !isUserVerified,
                    )

                    AnimatedVisibility(!isUserVerified && verificationField.isNotEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            PrimaryButton(
                                contentPadding = PaddingValues(
                                    horizontal = 20.sdp,
                                    vertical = 8.sdp
                                ),
                                onClick = {
                                    viewModel.resendOtp()
                                    keyboardManager?.hide()
                                }) {
                                SText(
                                    "send otp",
                                    fontSize = 14.ssp,
                                    textColor = MaterialTheme.colorScheme.surface,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("logged in as: ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(viewModel.userEmail)
                            }
                        },
                        fontFamily = displayFontFamily()
                    )
                    Text(
                        text = "do you want to change account?",
                        fontFamily = displayFontFamily()
                    )
                    OutlinedButton(
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        contentPadding = PaddingValues(horizontal = 20.sdp, vertical = 8.sdp),
                        onClick = {
                            viewModel.logout {
                                navigator.replaceAll(SplashScreen())
                            }
                        }) {
                        Text(
                            "Logout",
                            fontSize = 14.sp,
                            fontFamily = displayFontFamily()
                        )
                    }
                }

                UiStateHandler(
                    uiState = uiState,
                    onErrorShowed = {},
                    content = {

                    }
                )

                if (showBottomSheet) {
                    ModalBottomSheet(
                        modifier = Modifier.navigationBarsPadding(),
                        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = false),
                        sheetState = sheetState,
                        dragHandle = null,
                        onDismissRequest = {
                            viewModel.onOtpBottomSheetStateChange(false)
                        },
                    ) {
                        OtpBottomSheet(
                            otp = otpField,
                            email = otpEmailField,
                            onOtpTextChange = { ns, _ ->
                                viewModel.onOtpFieldValueChange(ns)
                            },
                            onSubmit = viewModel::verifyOtp,
                            onResendClick = viewModel::resendOtp,
                        )
                    }
                }

                PrimaryButton(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(20.sdp)
                        .fillMaxWidth(),
                    enabled = isUserVerified,
                    contentPadding = PaddingValues(horizontal = 20.sdp, vertical = 8.sdp),
                    onClick = {
                        navigator.replaceAll(SetUpScreen(false))
                    }) {
                    SText(
                        "Next",
                        fontSize = 14.ssp,
                        textColor = MaterialTheme.colorScheme.surface,
                    )
                }
            }
        }
    }
}