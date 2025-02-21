package com.sdjic.gradnet.presentation.screens.posts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import coil3.compose.LocalPlatformContext
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.images.CircularProfileImage
import com.sdjic.gradnet.presentation.composables.images.RoundedCornerImage
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.core.DummyDpImage
import com.sdjic.gradnet.presentation.helper.VerticalSlideTransition
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.theme.errorColor
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Camera
import network.chaintech.cmpimagepickncrop.CMPImagePickNCropDialog
import network.chaintech.cmpimagepickncrop.imagecropper.rememberImageCropper

@OptIn(ExperimentalVoyagerApi::class)
class AddPost : Screen, ScreenTransition by VerticalSlideTransition() {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        AddPostContent { navigator.pop() }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddPostContent(
        addPostScreenModel: AddPostScreenModel = koinScreenModel(), onDismiss: () -> Unit
    ) {

        var showExitDialog by remember { mutableStateOf(false) }
        var isPanelVisible by remember { mutableStateOf(false) }
        val richTextState = rememberRichTextState()
        val openLinkDialog = remember { mutableStateOf(false) }
        val imageCropper = rememberImageCropper()
        var openImagePicker by remember { mutableStateOf(false) }
        val selectedImages by addPostScreenModel.selectedImages.collectAsState()

        Scaffold(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectVerticalDragGestures { _, dragAmount ->
                        if (dragAmount > 80) { // Detect downward drag
                            if (richTextState.toText()
                                    .isNotEmpty() or selectedImages.isNotEmpty()
                            ) {
                                showExitDialog = true
                            } else {
                                onDismiss()
                            }
                        }
                    }
                },
            topBar = {
                TopAppBar(navigationIcon = {
                    IconButton(
                        onClick = {
                            if (richTextState.toText()
                                    .isNotEmpty() or selectedImages.isNotEmpty()
                            ) {
                                showExitDialog = true
                            } else {
                                onDismiss()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }, actions = {
                    PrimaryButton(
                        modifier = Modifier.padding(end = 5.dp),
                        shape = CircleShape,
                        enabled = true,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FloatingActionButtonDefaults.containerColor,
                        ),
                        onClick = {},
                        contentPadding = PaddingValues(horizontal = 25.dp)
                    ) {
                        Text("Post", color = Color.White)
                    }
                }, title = {})
            }) { ip ->

            if (openLinkDialog.value) {
                Dialog(
                    onDismissRequest = {
                        openLinkDialog.value = false
                    },
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    SlackLinkDialog(
                        state = richTextState,
                        openLinkDialog = openLinkDialog
                    )
                }
            }

            if (showExitDialog) {
                AlertDialog(
                    onDismissRequest = { showExitDialog = false },
                    title = { Text(text = "Discard changes", color = errorColor) },
                    text = { Text(text = "Are you sure want to discard changes?") },
                    confirmButton = {  // Buttons
                        TextButton(onClick = {
                            showExitDialog = false
                            onDismiss()
                        }) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showExitDialog = false }) {
                            Text("Cancel", color = Color.Gray)
                        }
                    }
                )
            }

            CMPImagePickNCropDialog(
                imageCropper = imageCropper,
                openImagePicker = openImagePicker,
                autoZoom = true,
                imagePickerDialogHandler = { openImagePicker = false },
                selectedImageCallback = addPostScreenModel::onImageSelected
            )

            Column(
                modifier = Modifier.padding(ip)
            ) {
                UserDetailRow()

                RichTextEditor(
                    state = richTextState,
                    placeholder = {
                        Text(text = "What do you want to talk about")
                    },
                    colors = RichTextEditorDefaults.richTextEditorColors(
                        textColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        placeholderColor = Color.Gray.copy(alpha = .6f),
                    ),
                    modifier = Modifier.weight(1f)
                )


                Surface(
                    modifier = Modifier.fillMaxWidth().navigationBarsPadding().imePadding(),
                    shadowElevation = 16.dp,
                    tonalElevation = 0.dp,
                    shape = RoundedCornerShape(
                        topStart = 28.0.dp,
                        topEnd = 28.0.dp,
                        bottomEnd = 0.0.dp,
                        bottomStart = 0.0.dp
                    )
                ) {
                    Column {
                        if (selectedImages.isNotEmpty()) {
                            LazyRow(
                                modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
                            ) {
                                items(selectedImages) {
                                    SelectedImageItem(it, addPostScreenModel)
                                }
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AnimatedVisibility(
                                isPanelVisible,
                                enter = fadeIn(tween(500)) + slideInHorizontally { 1 },
                                exit = slideOutHorizontally { 1 },
                            ) {
                                SlackPanel(
                                    modifier = Modifier.height(40.dp),
                                    state = richTextState,
                                    openLinkDialog = openLinkDialog,
                                )
                            }
                            if (isPanelVisible) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                            SlackPanelButton(
                                onClick = { isPanelVisible = !isPanelVisible },
                                isSelected = false,
                                tint = MaterialTheme.colorScheme.primaryContainer,
                                icon = if (isPanelVisible) Icons.AutoMirrored.Filled.ArrowBackIos else Icons.AutoMirrored.Filled.ArrowForwardIos,
                            )
                            if (!isPanelVisible) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                            if (!isPanelVisible) {
                                PrimaryButton(
                                    modifier = Modifier.padding(end = 5.dp),
                                    shape = CircleShape,
                                    enabled = selectedImages.size < 5,
                                    onClick = {
                                        if (selectedImages.size < 5) {
                                            openImagePicker = true
                                        }
                                    },
                                    contentPadding = PaddingValues(
                                        horizontal = 20.dp,
                                        vertical = 5.dp
                                    )
                                ) {
                                    Icon(
                                        modifier = Modifier.size(22.dp),
                                        imageVector = FontAwesomeIcons.Solid.Camera,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Images", color = Color.White)
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    @Composable
    private fun SelectedImageItem(
        it: ImageBitmap,
        addPostScreenModel: AddPostScreenModel
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            RoundedCornerImage(
                modifier = Modifier,
                imageBitmap = it,
                imageSize = 80.dp
            )
            IconButton(
                onClick = { addPostScreenModel.onImageDeselected(it) },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.onSurface.copy(.6f)
                ),
                modifier = Modifier.align(Alignment.TopEnd)
                    .size(20.dp)
                    .offset(4.dp, (-4).dp)
            ) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}

@Composable
fun UserDetailRow() {
    val platformContext = LocalPlatformContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProfileImage(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            context = platformContext,
            data = DummyDpImage,
            borderWidth = 0.dp,
            imageSize = 50.dp
        )
        Column {
            Title("Devansh Amdavadwala")
            SText("Android Developer")
        }
    }
}
