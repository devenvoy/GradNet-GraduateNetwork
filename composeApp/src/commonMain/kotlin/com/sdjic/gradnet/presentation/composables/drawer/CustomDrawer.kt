package com.sdjic.gradnet.presentation.composables.drawer


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sdjic.gradnet.presentation.core.model.NavigationItem
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.ic_alumni
import org.jetbrains.compose.resources.painterResource

@Composable
fun CustomDrawer(
    selectedNavigationItem: NavigationItem,
    onNavigationItemClick: (NavigationItem) -> Unit,
) {
    Box(contentAlignment = Alignment.TopEnd) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(Res.drawable.ic_alumni),
                contentDescription = "Zodiac Image"
            )
            Spacer(modifier = Modifier.height(40.dp))
            NavigationItem.entries.toTypedArray().dropLast(1).forEach { navigationItem ->
                NavigationItemView(
                    navigationItem = navigationItem,
                    selected = navigationItem == selectedNavigationItem,
                    onClick = { onNavigationItemClick(navigationItem) }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            HorizontalDivider()
            NavigationItem.entries.toTypedArray().takeLast(1).forEach { navigationItem ->
                NavigationItemView(
                    navigationItem = navigationItem,
                    selected = false,
                    onClick = { onNavigationItemClick(navigationItem) }
                )
            }
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}