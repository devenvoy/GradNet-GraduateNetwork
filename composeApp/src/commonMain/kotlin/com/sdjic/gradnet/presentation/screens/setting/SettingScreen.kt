package com.sdjic.gradnet.presentation.screens.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.screens.auth.password.ChangePasswordScreen
import com.sdjic.gradnet.presentation.screens.setting.lost_found.LostItemListScreen
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
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
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

                SettingItem(
                    modifier = Modifier.fillMaxWidth(),
                    icon = Icons.Outlined.Password,
                    title = "Change Password",
                    onClick = {
                        navigator.push(ChangePasswordScreen())
                    }
                )

                SettingItem(
                    modifier = Modifier.fillMaxWidth(),
                    icon = Icons.Outlined.Shield,
                    title = "Privacy Policy",
                    onClick = {
                        navigator.push(PrivatePolicyScreen())
                    }
                )

                SettingItem(
                    modifier = Modifier.fillMaxWidth(),
                    icon = Icons.Outlined.Feedback,
                    title = "Give Feedback",
                    onClick = {
                        navigator.push(FeedBackScreen())
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
    subtitle: String? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.noRippleEffect(onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(end = 16.dp, start = 8.dp),
            imageVector = icon,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = null
        )
        Column {
            Title(
                text = title,
                textColor = MaterialTheme.colorScheme.onSurface
            )
            subtitle?.let {
                Text(
                    text = subtitle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
    HorizontalDivider()
}