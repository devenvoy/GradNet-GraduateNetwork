package com.sdjic.gradnet.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.helper.LocalRootNavigator
import com.sdjic.gradnet.presentation.helper.LocalScrollBehavior
import com.sdjic.gradnet.presentation.helper.MyTab
import com.sdjic.gradnet.presentation.screens.home.tabs.EventsTab
import com.sdjic.gradnet.presentation.screens.home.tabs.JobsTab
import com.sdjic.gradnet.presentation.screens.home.tabs.PostTab
import com.sdjic.gradnet.presentation.screens.home.tabs.ProfileTab
import com.sdjic.gradnet.presentation.screens.home.tabs.SearchTab
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

class HomeScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey
    @Preview
    @Composable
    override fun Content() {
        HomeScreenContent()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeScreenContent() {
        val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
        val navigator = LocalNavigator.currentOrThrow

        val bottomTabList = listOf(
            PostTab,
            JobsTab,
            SearchTab,
            EventsTab,
            ProfileTab
        )

        CompositionLocalProvider(LocalScrollBehavior provides scrollBehavior) {
            CompositionLocalProvider(LocalRootNavigator provides navigator) {
                TabNavigator(
                    bottomTabList.last(),
                    tabDisposable = {
                        TabDisposable(
                            navigator = it,
                            tabs = bottomTabList
                        )
                    }
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomAppBar(scrollBehavior = scrollBehavior) {
                                bottomTabList.forEach { TabNavigationItem(it) }
                            }
                        }) { pVal ->
                        Box(
                            modifier = Modifier
                                .padding(bottom = pVal.calculateBottomPadding())
                                .fillMaxSize()
                        ) {
                            CurrentTab()
                        }
                    }
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
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = .8f)
            ),
            label = {
                SText(
                    tab.options.title,
                    fontSize = 10.ssp,
                    fontWeight = FontWeight.W700
                )
            },
            alwaysShowLabel = false,
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
                        modifier = Modifier.size(22.sdp),
                        painter = painterResource(if (isSelected) tab.tabOption.selectedIcon else tab.tabOption.unselectedIcon),
                        contentDescription = tab.options.title
                    )
                }
            }
        )
    }
}