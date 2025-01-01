package com.sdjic.gradnet.presentation.screens.accountSetup.basic

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.sdjic.gradnet.presentation.composables.CustomInputField
import com.sdjic.gradnet.presentation.composables.OtpTextField
import com.sdjic.gradnet.presentation.composables.PrimaryButton
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.composables.Title
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicSetUpScreen(
    basicState: BasicState,
    onAction: (BasicScreenAction) -> Unit
) {

    val sheetState = rememberModalBottomSheetState(true)

    if (basicState.showOtpBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.navigationBarsPadding(),
            properties = ModalBottomSheetProperties(shouldDismissOnBackPress = false),
            sheetState = sheetState,
            dragHandle = null,
            onDismissRequest = {
                onAction(
                    BasicScreenAction.OnOtpBottomSheetStateChange(false)
                )
            },
        ) {
            OtpBottomSheet(
                otp = basicState.otpField,
                email = basicState.otpEmailField,
                onOtpTextChange = { ns, fill ->
                    onAction(BasicScreenAction.OnOtpFieldValueChange(ns))
                },
                onSubmit = { onAction(BasicScreenAction.VerifyOtp) },
                onResendClick = { onAction(BasicScreenAction.ResendOtp) },
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(8.sdp),
        verticalArrangement = Arrangement.spacedBy(8.sdp)
    ) {
        Spacer(Modifier.height(8.sdp))
        Column {
            Title(text = "${basicState.userRole.name} Verification", size = 16.ssp)
            SText(text = "one time only", textColor = MaterialTheme.colorScheme.secondary)
        }
        Spacer(Modifier.height(1.sdp))
        val fieldTitle by remember {
            mutableStateOf(
                when (basicState.userRole) {
                    UserRole.Alumni -> "Spid no"
                    UserRole.Faculty -> "Faculty id"
                    UserRole.Organization -> "Oragnization id"
                }
            )
        }
        CustomInputField(
            fieldTitle = fieldTitle,
            textFieldValue = basicState.verificationField,
            onValueChange = { s ->
                onAction(
                    BasicScreenAction.OnVerificationFieldValueChange(s)
                )
            },
            placeholder = {
                SText("Enter your $fieldTitle")
            },
            supportingText = {
                SText("Required", textColor = Color.Red)
            },
            isEnable = !basicState.isVerified,
        )

        if (!basicState.isVerified && basicState.verificationField.isNotEmpty()) {
            val keyboardManager = LocalSoftwareKeyboardController.current
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                PrimaryButton(
                    contentPadding = PaddingValues(horizontal = 20.sdp, vertical = 8.sdp),
                    onClick = {
                        onAction(
                            BasicScreenAction.OnOtpBottomSheetStateChange(true)
                        )
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
    }


}

@Composable
fun OtpBottomSheet(
    email: String,
    otp: String,
    onOtpTextChange: (String, Boolean) -> Unit,
    onResendClick: () -> Unit,
    onSubmit: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(16.sdp)
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 24.sdp)
                .width(50.sdp)
                .height(3.sdp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFBBC0C4))
                .align(Alignment.CenterHorizontally)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.sdp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SText(
                text = "Verify to Proceed",
                fontWeight = Bold,
                fontSize = 16.ssp,
                textColor = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)
                    ) {
                        append("Enter the OTP sent to")
                    }
                    withStyle(
                        style = SpanStyle(fontWeight = W500)
                    ) {
                        append("\n$email")
                    }
                },
                textAlign = TextAlign.Center,
                fontSize = 11.ssp
            )

            OtpTextField(
                otpCount = 6,
                otpText = otp,
                onOtpTextChange = onOtpTextChange
            )
            TextButton(onClick = onResendClick) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = W400
                            )
                        ) {
                            append("Didn’t receive code? ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = W700,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            append("Re-send")
                        }
                    },
                    fontSize = 11.ssp
                )
            }

            PrimaryButton(
                contentPadding = PaddingValues(horizontal = 20.sdp, vertical = 8.sdp),
                onClick = onSubmit,
                shape = RoundedCornerShape(6.sdp),
                enabled = otp.length == 6,
            ) {
                Text(
                    text = "Verify",
                    style = TextStyle(
                        fontWeight = W700,
                        fontSize = 16.ssp
                    )
                )
            }
        }
    }
}