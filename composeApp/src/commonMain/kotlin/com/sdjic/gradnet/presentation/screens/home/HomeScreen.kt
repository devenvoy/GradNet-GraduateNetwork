package com.sdjic.gradnet.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.helper.MyTab
import com.sdjic.gradnet.presentation.screens.home.tabs.HomeTab
import com.sdjic.gradnet.presentation.screens.home.tabs.ProfileTab
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.ui.tooling.preview.Preview

class HomeScreen : Screen {
    @Preview
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        HomeScreenContent()
    }

    @Composable
    fun HomeScreenContent() {
        TabNavigator(ProfileTab, tabDisposable = {
            TabDisposable(
                navigator = it, tabs = listOf(HomeTab, ProfileTab)
            )
        }) { tabNavigator ->
            Scaffold(bottomBar = {
                NavigationBar {
                    TabNavigationItem(HomeTab)
                    TabNavigationItem(ProfileTab)
                }
            }) { pVal ->
                Box(
                    modifier = Modifier.padding(bottom = pVal.calculateBottomPadding())
                        .fillMaxSize()
                ) {
                    CurrentTab()
                }
            }
        }
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: MyTab) {
        val tabNavigator = LocalTabNavigator.current
        val isSelected = tabNavigator.current.key == tab.key

        NavigationBarItem(
            selected = isSelected,
            onClick = { tabNavigator.current = tab },
            label = {
                SText(
                    tab.options.title,
                    fontSize = if (isSelected) 12.ssp else 10.ssp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight(500)
                )
            },
            alwaysShowLabel = true,
            icon = {
                BadgedBox(badge = {
                    if (tab.tabOption.badgeCount != null && tab.tabOption.badgeCount!! > 0) {
                        Badge { SText(text = tab.tabOption.badgeCount.toString()) }
                    }
                    if (tab.tabOption.badgeIcon != null) {
                        Badge(containerColor = Color.Red)
                    }
                }) {
                    Icon(
                        imageVector = if (isSelected) tab.tabOption.selectedIcon!! else tab.tabOption.unselectedIcon!!,
                        contentDescription = tab.options.title
                    )
                }
            }
        )
    }
}