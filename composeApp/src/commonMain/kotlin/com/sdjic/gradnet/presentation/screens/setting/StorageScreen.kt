package com.sdjic.gradnet.presentation.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.di.platform_di.clearCache
import com.sdjic.gradnet.di.platform_di.getScreenWidth
import com.sdjic.gradnet.di.platform_di.getStorageInfo
import com.sdjic.gradnet.di.platform_di.roundTwoDecimals
import com.sdjic.gradnet.presentation.composables.images.BackButton

class StorageScreen : Screen {
    @Composable
    override fun Content() {
        StorageScreenContent()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StorageScreenContent() {
        val navigator = LocalNavigator.currentOrThrow
        var storageInfo by remember { mutableStateOf(getStorageInfo()) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Manage Storage") },
                    navigationIcon = { BackButton { navigator.pop() } }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                StorageVisualizer(
                    totalStorageGB = storageInfo.totalStorage,
                    freeStorageGB = storageInfo.freeStorage,
                    usedByOtherAppsGB = storageInfo.usedByOtherApps,
                    usedByThisAppMB = storageInfo.usedByApp,
                    usedByCacheMB = storageInfo.usedByCache
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        onClick = {
                            clearCache()
                            storageInfo = getStorageInfo()
                        }) {
                        Text(text = "Clear Cache", modifier = Modifier.padding(horizontal = 20.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun StorageVisualizer(
    totalStorageGB: Float,
    freeStorageGB: Float,
    usedByOtherAppsGB: Float,
    usedByThisAppMB: Float,
    usedByCacheMB: Float
) {
    val totalStorageMB = totalStorageGB * 1024
    val usedByOtherAppsMB = usedByOtherAppsGB * 1024

    val screenWidth = getScreenWidth()
    val barHeight = 20.dp

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Device Storage Usage", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .background(Color.Gray.copy(alpha = 0.3f), CircleShape)
                .border(1.dp, Color.Black, shape = CircleShape)
        ) {

            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = CircleShape.topStart,
                            topEnd = CornerSize(0),
                            bottomEnd = CornerSize(0),
                            bottomStart = CircleShape.bottomStart
                        )
                    ),
                contentAlignment = Alignment.CenterEnd
            ) {
                StorageBar(
                    screenWidth,
                    usedByOtherAppsMB,
                    totalStorageMB,
                    Color(0xFFB0BEC5)
                )
                StorageBar(
                    screenWidth,
                    usedByThisAppMB * 10,
                    totalStorageMB,
                    Color(0xFF1E88E5)
                ) // Blue - This App
                StorageBar(
                    screenWidth,
                    usedByCacheMB * 10,
                    totalStorageMB,
                    Color(0xFFFFA726)
                ) // Orange - Cache
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        StorageLegendItem(
            Color.Gray.copy(alpha = 0.3f),
            "Total Storage: ${roundTwoDecimals(totalStorageGB)} GB"
        )
        StorageLegendItem(
            Color(0xFFB0BEC5),
            "Used by Other Apps: ${roundTwoDecimals(usedByOtherAppsGB)} GB"
        )
        StorageLegendItem(
            Color(0xFF1E88E5),
            "Used by This App: ${roundTwoDecimals(usedByThisAppMB)} MB"
        )
        StorageLegendItem(
            Color(0xFFFFA726),
            "Used by Cache: ${roundTwoDecimals(usedByCacheMB)} MB"
        )
        StorageLegendItem(
            Color.Gray.copy(alpha = 0.3f),
            "Free Storage: ${roundTwoDecimals(freeStorageGB)} GB"
        )
    }
}

@Composable
fun StorageBar(screenWidth: Dp, sizeMB: Float, totalStorageMB: Float, color: Color) {
    val widthRatio = sizeMB / totalStorageMB
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(screenWidth * widthRatio)
            .background(color)
    )
}

@Composable
fun StorageLegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}