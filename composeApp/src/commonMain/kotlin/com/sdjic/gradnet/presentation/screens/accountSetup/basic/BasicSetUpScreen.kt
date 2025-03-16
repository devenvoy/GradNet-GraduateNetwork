package com.sdjic.gradnet.presentation.screens.accountSetup.basic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import com.alorma.compose.settings.ui.SettingsSwitch
import com.sdjic.gradnet.di.platform_di.getContactsUtil
import com.sdjic.gradnet.presentation.composables.SectionTitle
import com.sdjic.gradnet.presentation.composables.filter.CustomImageChip
import com.sdjic.gradnet.presentation.composables.images.BackgroundImage
import com.sdjic.gradnet.presentation.composables.images.CircularProfileImage
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputArea
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputField
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.ic_contacts
import network.chaintech.cmpimagepickncrop.CMPImagePickNCropDialog
import network.chaintech.cmpimagepickncrop.imagecropper.ImageAspectRatio
import network.chaintech.cmpimagepickncrop.imagecropper.rememberImageCropper
import network.chaintech.kmp_date_time_picker.utils.noRippleEffect
import network.chaintech.sdpcomposemultiplatform.sdp
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BasicSetUpScreen(
    isVerified: Boolean,
    basicState: BasicState,
    userRole: UserRole,
    onAction: (BasicScreenAction) -> Unit,
) {
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



    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.sdp)
            .padding(bottom = 60.sdp),
        verticalArrangement = Arrangement.spacedBy(8.sdp)
    ) {
        ProfileBackgroundImages(onAction, basicState, true)


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

                SectionTitle(icon = painterResource(Res.drawable.ic_contacts), title = "Contacts")

                FlowRow(modifier = Modifier, verticalArrangement = Arrangement.Top) {
                    FlowRow(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                    ) {

                        if (basicState.contactField.isNotEmpty())
                            CustomImageChip(
                                text = basicState.contactField,
                                selected = true,
                                showEndIcon = false,
                                onCloseClick = {},
                                modifier = Modifier.padding(2.dp)
                            )

                        if (basicState.emailField.isNotEmpty())
                            CustomImageChip(
                                text = basicState.emailField,
                                selected = true,
                                showEndIcon = false,
                                onCloseClick = {},
                                modifier = Modifier.padding(2.dp)
                            )

                    }
                }

                SettingsSwitch(
                    state = basicState.showContactsToOthers,
                    title = {
                        Text("Let Others Reach You", fontFamily = displayFontFamily())
                    },
                    subtitle = {
                        SText("Turn this on if you’d like others to see your contact details and connect with you.")
                    },
                    onCheckedChange = {
                        onAction(BasicScreenAction.OnShowContactsToOthersChange(it))
                    },
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
