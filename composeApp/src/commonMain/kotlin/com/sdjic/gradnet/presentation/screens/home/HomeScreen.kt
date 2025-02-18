package com.sdjic.gradnet.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.internal.BackHandler
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.sdjic.commons.composables.drawer.CustomDrawer
import com.sdjic.commons.composables.drawer.NavigationItem
import com.sdjic.commons.composables.text.SText
import com.sdjic.commons.helper.LocalDrawerController
import com.sdjic.commons.helper.LocalRootNavigator
import com.sdjic.commons.helper.LocalScrollBehavior
import com.sdjic.commons.helper.MyTab
import com.sdjic.gradnet.presentation.screens.home.tabs.EventsTab
import com.sdjic.gradnet.presentation.screens.home.tabs.JobsTab
import com.sdjic.gradnet.presentation.screens.home.tabs.PostTab
import com.sdjic.gradnet.presentation.screens.home.tabs.ProfileTab
import com.sdjic.gradnet.presentation.screens.home.tabs.SearchTab
import kotlinx.coroutines.launch
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

    @OptIn(ExperimentalMaterial3Api::class, InternalVoyagerApi::class)
    @Composable
    fun HomeScreenContent() {
        val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()

        val bottomTabList:List<MyTab> = listOf(
            PostTab,
            JobsTab,
            SearchTab,
            EventsTab,
            ProfileTab
        )

        BackHandler(enabled = drawerState.isOpen) {
            if (drawerState.isOpen) {
                scope.launch {
                    drawerState.close()
                }
            }
        }

        TabNavigator(
            bottomTabList.last(),
            tabDisposable = {
                TabDisposable(
                    navigator = it,
                    tabs = bottomTabList
                )
            }
        ) {
            BackHandler(it.current != PostTab && drawerState.isClosed) {
                it.current = PostTab
            }
            CompositionLocalProvider(
                LocalScrollBehavior provides scrollBehavior,
                LocalRootNavigator provides navigator,
                LocalDrawerController provides drawerState,
                LocalLayoutDirection provides LayoutDirection.Rtl
            ) {
                ModalNavigationDrawer(
                    gesturesEnabled = it.current == ProfileTab,
                    drawerContent = {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            DrawerContent(navigator, drawerState)
                        }
                    },
                    drawerState = drawerState,
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Scaffold(
                            bottomBar = {
                                BottomAppBar(
                                    modifier = Modifier.shadow(16.dp),
                                    scrollBehavior = scrollBehavior,
                                    containerColor = MaterialTheme.colorScheme.background
                                ) {
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
    }

    @Composable
    private fun DrawerContent(navigator: Navigator, drawerState: DrawerState) {
        val selectedNavigationItem by remember { mutableStateOf(NavigationItem.Profile) }
        val scope = rememberCoroutineScope()
        ModalDrawerSheet(
            modifier = Modifier.fillMaxWidth(.8f),
            drawerShape = MaterialTheme.shapes.large
        ) {
            CustomDrawer(
                selectedNavigationItem = selectedNavigationItem,
                onNavigationItemClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isOpen) close() else open()
                        }
                    }
                }
            )
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
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f),
                indicatorColor = MaterialTheme.colorScheme.primary.copy(.1f)
            ),
            label = {
                SText(
                    tab.options.title,
                    fontSize = 10.ssp,
                    fontWeight = FontWeight.W600,
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
                        modifier = Modifier.size(20.sdp),
                        painter = painterResource(if (isSelected) tab.tabOption.selectedIcon else tab.tabOption.unselectedIcon),
                        contentDescription = tab.options.title
                    )
                }
            }
        )
    }
}