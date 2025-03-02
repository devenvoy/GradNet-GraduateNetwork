package com.sdjic.gradnet.presentation.screens.accountSetup.basic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.images.BackgroundImage
import com.sdjic.gradnet.presentation.composables.images.CircularProfileImage
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputArea
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputField
import com.sdjic.gradnet.presentation.composables.textInput.OtpTextField
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Plus
import network.chaintech.cmpimagepickncrop.CMPImagePickNCropDialog
import network.chaintech.cmpimagepickncrop.imagecropper.ImageAspectRatio
import network.chaintech.cmpimagepickncrop.imagecropper.rememberImageCropper
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicSetUpScreen(
    isVerified: Boolean,
    basicState: BasicState,
    userRole: UserRole,
    onAction: (BasicScreenAction) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(true)
    val scope = rememberCoroutineScope()
    val profileCropper = rememberImageCropper()
    val bgCropper = rememberImageCropper()

    CMPImagePickNCropDialog(
        imageCropper = bgCropper,
        enableRotationOption = false,
        enabledFlipOption = false,
        openImagePicker = basicState.openBackGroundImagePicker,
        shapes = null,
        aspects = listOf(ImageAspectRatio(16, 9)),
        autoZoom = false,
        imagePickerDialogHandler = { onAction(BasicScreenAction.OnBackGroundDialogState(it)) },
        selectedImageCallback = { onAction(BasicScreenAction.OnBackgroundImageChange(it)) }
    )

    CMPImagePickNCropDialog(
        imageCropper = profileCropper,
        openImagePicker = basicState.openProfileImagePicker,
        autoZoom = true,
        imagePickerDialogHandler = { onAction(BasicScreenAction.OnProfileDialogState(it)) },
        selectedImageCallback = { onAction(BasicScreenAction.OnProfileImageChange(it)) }
    )


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
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.sdp)
            .padding(bottom = 60.sdp),
        verticalArrangement = Arrangement.spacedBy(8.sdp)
    ) {
        ProfileBackgroundImages(onAction, basicState, true)

        if (!isVerified) {
            Column {
                Title(
                    text = "${userRole.name.lowercase().capitalize()} Verification",
                    size = 16.ssp
                )
                SText(text = "only once", textColor = MaterialTheme.colorScheme.secondary)
            }
        }

        Spacer(Modifier.height(1.sdp))

        val fieldTitle by remember {
            mutableStateOf(
                when (userRole) {
                    UserRole.Alumni -> "Spid no"
                    UserRole.Faculty -> "Faculty id"
                    UserRole.Organization -> "Organization id"
                    UserRole.Admin -> "Admin"
                }
            )
        }

        CustomInputField(
            fieldTitle = fieldTitle,
            textFieldValue = basicState.verificationField,
            onValueChange = { s ->
                onAction(BasicScreenAction.OnVerificationFieldValueChange(s))
            },
            placeholder = { SText("Enter your $fieldTitle") },
            supportingText = if (isVerified) null else ({
                SText("Required", textColor = Color.Red)
            }),
            isEnable = !isVerified,
        )

        AnimatedVisibility(!isVerified && basicState.verificationField.isNotEmpty()) {
            val keyboardManager = LocalSoftwareKeyboardController.current
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                PrimaryButton(
                    contentPadding = PaddingValues(horizontal = 20.sdp, vertical = 8.sdp),
                    onClick = {
                        onAction(BasicScreenAction.ResendOtp)
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

        AnimatedVisibility(isVerified) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                CustomInputField(
                    fieldTitle = "Name",
                    textFieldValue = basicState.nameField,
                    onValueChange = { s ->
                        onAction(BasicScreenAction.OnNameFieldValueChange(s))
                    },
                    placeholder = { SText("Enter name") },
                    supportingText = {
                        if (basicState.nameField.isEmpty()) {
                            SText("Required", textColor = Color.Red)
                        }
                    },
                )

                CustomInputArea(
                    fieldTitle = "About",
                    textFieldValue = basicState.aboutField,
                    onValueChange = { s ->
                        onAction(BasicScreenAction.OnAboutFieldValueChange(s))
                    },
                    placeholder = { SText("tell about yourself..") },
                )

                CustomInputArea(
                    fieldTitle = "Address",
                    textFieldValue = basicState.addressField,
                    onValueChange = { s ->
                        onAction(BasicScreenAction.OnAddressFieldValueChange(s))
                    },
                    placeholder = { SText("add address") },
                )
            }
        }
    }
}

@Composable
fun ProfileBackgroundImages(
    onAction: (BasicScreenAction) -> Unit,
    basicState: BasicState,
    showEditButton: Boolean = false
) {
    val platformContext = LocalPlatformContext.current
    Box {
        val backgroundEditClick = {
            onAction(BasicScreenAction.OnBackGroundDialogState(true))
        }
        BackgroundImage(
            imageBitmap = basicState.backgroundImage,
            data = basicState.backGroundImageUrl,
            modifier = Modifier.clickable(onClick = backgroundEditClick),
            context = platformContext
        )
        if (showEditButton) {
            IconButton(
                modifier = Modifier.padding(10.sdp).size(22.sdp).align(Alignment.TopEnd),
                onClick = backgroundEditClick,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = Color.White
                )
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        }
        Box(modifier = Modifier.padding(top = 70.sdp, start = 10.sdp)) {
            val onProfileClick = {
                onAction(
                    BasicScreenAction.OnProfileDialogState(true)
                )
            }
            CircularProfileImage(
                modifier = Modifier.clickable(onClick = onProfileClick),
                placeHolderName = basicState.nameField.ifEmpty { "Graduate network" },
                data = basicState.profileImageUrl,
                imageBitmap = basicState.profileImage,
                imageSize = 90.sdp
            )
            if (showEditButton) {
                IconButton(
                    modifier = Modifier.size(20.sdp).align(Alignment.BottomEnd)
                        .offset(y = (-5).sdp, x = (-5).sdp),
                    onClick = onProfileClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
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