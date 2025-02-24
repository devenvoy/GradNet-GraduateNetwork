package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.sdjic.gradnet.presentation.helper.UploadDialogState

@Composable
fun UploadDialog(
    state: UploadDialogState,
    onDismiss: () -> Unit
) {
    if (state is UploadDialogState.Idle) return

    var titleText by remember { mutableStateOf("Uploading Post") }

    AlertDialog(
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        onDismissRequest = onDismiss,
        title = { Text(titleText) },
        text = {
            when (state) {
                is UploadDialogState.Starting -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Starting upload...")
                    }
                }

                is UploadDialogState.Progress -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth(),
                            progress = { state.progress },
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("${(state.progress * 100).toInt()}% uploaded")
                    }
                }

                is UploadDialogState.Success -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = Color.Green
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        titleText = "Uploading Finished"
                        Text("Upload successful!")
                    }
                }

                is UploadDialogState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Error, contentDescription = "Error", tint = Color.Red)
                        Spacer(modifier = Modifier.height(8.dp))
                        titleText = "Uploading Finished"
                        Text(state.error)
                    }
                }

                UploadDialogState.Idle -> {}
            }
        },
        confirmButton = {
            Button(
                enabled = (state is UploadDialogState.Success || state is UploadDialogState.Error),
                onClick = onDismiss
            ) { Text("OK") }
        }
    )
}
