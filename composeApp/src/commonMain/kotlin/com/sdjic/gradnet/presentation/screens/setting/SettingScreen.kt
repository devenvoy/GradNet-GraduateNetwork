package com.sdjic.gradnet.presentation.screens.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.sdjic.gradnet.di.platform_di.getStorageInfo
import com.sdjic.gradnet.di.platform_di.roundTwoDecimals

class SettingScreen : Screen {
    @Composable
    override fun Content() {
        SettingScreenContent()
    }

    @Composable
    fun SettingScreenContent() {
        val storageInfo = remember { getStorageInfo() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Device Storage Usage", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text(text = "📦 Total Storage: ${roundTwoDecimals(storageInfo.totalStorage)} GB")
                Text(text = "🆓 Free Storage: ${roundTwoDecimals(storageInfo.freeStorage)} GB")
                Text(text = "📱 Used by Other Apps: ${roundTwoDecimals(storageInfo.usedByOtherApps)} GB")
                Text(text = "📂 Used by This App: ${roundTwoDecimals(storageInfo.usedByApp)} MB")
                Text(text = "🗑 Used by Cache: ${roundTwoDecimals(storageInfo.usedByCache)} MB")
                Text(text = "⬇️ Used by Download Dir: ${roundTwoDecimals(storageInfo.usedByDownloadDir)} MB")
            }
        }
    }
}