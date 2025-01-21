package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.internal.BackHandler
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.gradnet.presentation.composables.CustomDrawer
import com.sdjic.gradnet.presentation.core.model.NavigationItem
import com.sdjic.gradnet.presentation.helper.CustomDrawerState
import com.sdjic.gradnet.presentation.helper.MyTab
import com.sdjic.gradnet.presentation.helper.MyTabOptions
import com.sdjic.gradnet.presentation.helper.coloredShadow
import com.sdjic.gradnet.presentation.helper.isOpened
import com.sdjic.gradnet.presentation.screens.profile.ProfileScreen
import com.sdjic.gradnet.presentation.theme.AppTheme
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.person
import gradnet_graduatenetwork.composeapp.generated.resources.person_outline
import network.chaintech.sdpcomposemultiplatform.getScreenWidth
import kotlin.math.roundToInt

object ProfileTab : MyTab {

    override val options: TabOptions
        @Composable get() = TabOptions(4u, "Me")

    override val tabOption: MyTabOptions
        @Composable get() = remember {
            MyTabOptions(
                index = 4u,
                title = "Me",
                selectedIcon = Res.drawable.person,
                unselectedIcon = Res.drawable.person_outline
            )
        }

    @OptIn(InternalVoyagerApi::class)
    @Composable
    override fun Content() {

        var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }
        var selectedNavigationItem by remember { mutableStateOf(NavigationItem.Home) }
        val scrnWidth = getScreenWidth()
        val density = LocalDensity.current.density
        val screenWidth = remember {
            derivedStateOf { (scrnWidth * density).roundToInt() }
        }

        val offsetValue by remember { derivedStateOf { (screenWidth.value / 4.5).dp } }
        val animatedOffset by animateDpAsState(
            targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
            label = "Animated Offset"
        )
        val animatedScale by animateFloatAsState(
            targetValue = if (drawerState.isOpened()) 0.9f else 1f,
            label = "Animated Scale"
        )

        BackHandler(enabled = drawerState.isOpened()) {
            drawerState = CustomDrawerState.Closed
        }

        AppTheme {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                    .navigationBarsPadding()
                    .fillMaxSize()
            ) {
                CustomDrawer(
                    selectedNavigationItem = selectedNavigationItem,
                    onNavigationItemClick = {
                        selectedNavigationItem = it
                    },
                    onCloseClick = { drawerState = CustomDrawerState.Closed }
                )
                ProfileScreen(
                    modifier = Modifier
                        .offset(x = animatedOffset)
                        .scale(scale = animatedScale)
                        .coloredShadow(
                            color = Color.Black,
                            alpha = 0.1f,
                            shadowRadius = 50.dp
                        ),
                    drawerState = drawerState,
                    onDrawerClick = { drawerState = it },
                )
            }
        }
    }
}
