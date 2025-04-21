package com.sdjic.gradnet.presentation.screens.setting.lost_found

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dokar.sonner.ToastType
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.button.SecondaryOutlinedButton
import com.sdjic.gradnet.presentation.composables.images.LongBackButton
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.core.model.ToastMessage
import com.sdjic.gradnet.presentation.helper.ToastManager
import com.sdjic.gradnet.presentation.helper.UiStateHandler
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import gradnet_graduatenetwork.composeapp.generated.resources.ic_feedback_outlined
import gradnet_graduatenetwork.composeapp.generated.resources.ic_upload_cloud
import kotlinx.coroutines.delay
import network.chaintech.cmpimagepickncrop.CMPImagePickNCropDialog
import network.chaintech.cmpimagepickncrop.imagecropper.rememberImageCropper
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource
import gradnet_graduatenetwork.composeapp.generated.resources.Res as R

class LostItemReportScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<LostItemReportScreenModel>()
        LostItemScreenContent(
            viewModel = viewModel,
            navigateUp = { navigator.popUntilRoot() }
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LostItemScreenContent(
    viewModel: LostItemReportScreenModel,
    navigateUp: () -> Unit
) {

    val imageCropper = rememberImageCropper()
    var openImagePicker by remember { mutableStateOf(false) }
    CMPImagePickNCropDialog(
        imageCropper = imageCropper,
        openImagePicker = openImagePicker,
        autoZoom = true,
        imagePickerDialogHandler = { openImagePicker = false },
        selectedImageCallback = viewModel::onImageSelected
    )


    Scaffold(
        topBar =
            {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ), title = {
                        Title(
                            textColor = Color.White, text = "Add Post", size = 14.ssp
                        )
                    }, navigationIcon = {
                        LongBackButton(
                            iconColor = Color.White, onBackPressed = navigateUp
                        )
                    })
            }
    )
    {

        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(10.sdp)
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.3f)
                        .clip(RoundedCornerShape(16.dp))
                        .drawBehind {
                            drawRoundRect(
                                color = Color.Gray,
                                cornerRadius = CornerRadius(16.dp.toPx()),
                                style = Stroke(
                                    width = 4.dp.toPx(),
                                    pathEffect = PathEffect.dashPathEffect(
                                        floatArrayOf(
                                            5.dp.toPx(),
                                            5.dp.toPx()
                                        )
                                    )
                                )
                            )
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.sdp),
                        contentAlignment = Center
                    ) {
                        if (viewModel.selectedImage.value == null) {

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Image(
                                    modifier = Modifier
                                        .width(100.sdp)
                                        .height(50.sdp),
                                    painter = painterResource(R.drawable.ic_upload_cloud),
                                    contentDescription = null
                                )
                                Spacer(Modifier.height(8.sdp))
                                SecondaryOutlinedButton(
                                    shape = CircleShape,
                                    contentPadding = PaddingValues(16.sdp, 4.sdp),
                                    onClick = { openImagePicker = true }
                                ) {
                                    SText(text = "Choose Image to Upload")
                                }
                            }
                        } else {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(4.sdp),
                                bitmap = viewModel.selectedImage.value!!,
                                contentDescription = null
                            )
                            IconButton(
                                modifier = Modifier
                                    .padding(10.sdp)
                                    .size(20.sdp)
                                    .align(Alignment.TopEnd),
                                onClick = {
                                    viewModel.selectedImage.value = null
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Cancel,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.sdp))

                SText(
                    text = "Describe Item here",
                    fontWeight = W600,
                    fontSize = 14.ssp,
                )
                Spacer(modifier = Modifier.height(10.sdp))
                OutlinedTextField(
                    value = viewModel.descriptionText.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFE9E9EA)
                    ),
                    onValueChange = { value -> viewModel.descriptionText.value = value },
                    placeholder = {
                        Text(
                            text = "Brief Description of item and your contact information",
                            style = TextStyle(fontSize = 14.sp, color = Color.LightGray)
                        )
                    }
                )
            }

            UiStateHandler(
                uiState = viewModel.lostItemNewsState.collectAsState().value,
                onErrorShowed = {},
                content = { msg ->
                    LaunchedEffect(Unit) {
                        navigateUp()
                        delay(200)
                        ToastManager.showMessage(
                            ToastMessage(
                                message = msg,
                                type = ToastType.Success
                            )
                        )
                    }
                }
            )

            PrimaryButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 20.sdp, vertical = 10.sdp)
                    .fillMaxWidth(),
                onClick = {
                    viewModel.submitFeedback()
                },
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = 10.sdp)
                        .size(18.sdp),
                    painter = painterResource(R.drawable.ic_feedback_outlined),
                    contentDescription = null
                )
                SText(
                    text = "Post",
                    fontWeight = FontWeight.Bold,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 14.ssp
                )
            }
        }
    }
}

