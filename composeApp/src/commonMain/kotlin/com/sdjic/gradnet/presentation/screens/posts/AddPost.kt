package com.sdjic.gradnet.presentation.screens.posts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.screen.Screen
import coil3.compose.LocalPlatformContext
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.images.CircularProfileImage
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.core.DummyDpImage
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Camera

class AddPost : Screen {
    @Composable
    override fun Content() {
        AddPostContent { }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddPostContent(
        addPostScreenModel: AddPostScreenModel = koinScreenModel(), onDismiss: () -> Unit
    ) {

        var isPanelVisible by remember { mutableStateOf(false) }
        val richTextState = rememberRichTextState()
        val openLinkDialog = remember { mutableStateOf(false) }


        Scaffold(topBar = {
            TopAppBar(navigationIcon = {
                IconButton(
                    onClick = onDismiss
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

            if (openLinkDialog.value){
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

                Row(
                    modifier = Modifier.fillMaxWidth().navigationBarsPadding()
                        .padding(10.dp)
                        .imePadding(),
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
                            enabled = true,
                            onClick = {},
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 5.dp)
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
