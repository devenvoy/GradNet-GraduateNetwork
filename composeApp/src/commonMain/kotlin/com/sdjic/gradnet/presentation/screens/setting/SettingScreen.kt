package com.sdjic.gradnet.presentation.screens.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.di.platform_di.getStorageInfo
import com.sdjic.gradnet.presentation.composables.images.BackButton
import network.chaintech.kmp_date_time_picker.utils.noRippleEffect

class SettingScreen : Screen {
    @Composable
    override fun Content() {
        SettingScreenContent()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SettingScreenContent() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Settings") },
                    navigationIcon = { BackButton { navigator.pop() } }
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(it)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {

                SettingItem(
                    modifier = Modifier.fillMaxWidth(),
                    icon = Icons.Outlined.Storage,
                    title = "Manage Storage",
                    subtitle = "${getStorageInfo().usedByCache} MB",
                    onClick = {
                        navigator.push(StorageScreen())
                    }
                )
            }
        }
    }
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.noRippleEffect(onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(end = 16.dp),
            imageVector = icon,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = null
        )
        Column {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}