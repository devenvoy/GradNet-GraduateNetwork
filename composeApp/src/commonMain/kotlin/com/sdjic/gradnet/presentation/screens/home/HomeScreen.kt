package com.sdjic.gradnet.presentation.screens.home

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.sdjic.gradnet.presentation.screens.home.tabs.HomeTab
import com.sdjic.gradnet.presentation.screens.home.tabs.ProfileTab

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        HomeScreenContent()
    }

    @Composable
    fun HomeScreenContent() {
        TabNavigator(
            ProfileTab,
            tabDisposable = {
                TabDisposable(
                    navigator = it,
                    tabs = listOf(HomeTab(it.current.key), ProfileTab)
                )
            }
        ) { tabNavigator ->
            Scaffold(
                content = { CurrentTab() },
                bottomBar = {
                    val currentKey by remember { mutableStateOf(tabNavigator.current.key) }
                    NavigationBar {
                        TabNavigationItem(HomeTab(currentKey))
                        TabNavigationItem(ProfileTab)
                    }
                }
            )
        }
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current
        NavigationBarItem(
            selected = tabNavigator.current.key == tab.key,
            onClick = { tabNavigator.current = tab },
            icon = {
                BadgedBox(badge = {}) {
                    Icon(
                        painter = tab.options.icon!!,
                        contentDescription = tab.options.title
                    )
                }
            }
        )
    }
}