package com.sdjic.gradnet.presentation.screens.accountSetup.basic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.sdjic.gradnet.presentation.composables.CustomInputField
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.composables.Title
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun BasicSetUpScreen(
    basicState: BasicState,
    onAction: (BasicScreenAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Column {
                Title(text = "${basicState.userRole.name} Verification")
                SText(text = "only time only")
                Spacer(Modifier.height(8.sdp))
                val fieldTitle by remember {
                    mutableStateOf(
                        when (basicState.userRole) {
                            UserRole.Alumni -> "Spid no."
                            UserRole.Faculty -> "Faculty id"
                            UserRole.Organization -> "Oragnization id"
                        }
                    )
                }
                CustomInputField(
                    fieldTitle = fieldTitle,
                    textFieldValue = basicState.verificationField,
                    onValueChange = { s -> onAction(BasicScreenAction.OnnVrificationFieldValueChange(s)) },
                    placeholder = {
                        SText("Enter your $fieldTitle")
                    },
                    isEnable = basicState.verificationFieldEnable,
                )
            }
        }
    }
}