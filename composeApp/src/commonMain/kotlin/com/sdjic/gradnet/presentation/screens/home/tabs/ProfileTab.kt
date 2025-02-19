package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.commons.helper.LocalRootNavigator
import com.sdjic.commons.helper.MyTab
import com.sdjic.commons.helper.MyTabOptions
import com.sdjic.domain.AppCacheSetting
import com.sdjic.gradnet.presentation.screens.accountSetup.SetUpScreen
import com.sdjic.profile.ProfileScreen
import org.koin.compose.koinInject
import com.sdjic.shared.Resource as Res

object ProfileTab : MyTab {

    override val options: TabOptions
        @Composable get() = TabOptions(4u, "Me")

    override val tabOption: MyTabOptions
        @Composable get() = remember {
            MyTabOptions(
                index = 4u,
                title = "Me",
                selectedIcon = Res.drawable.person,
                unselectedIcon = Res.drawable.personOutline
            )
        }

    @Composable
    override fun Content() {
        val appCacheSetting = koinInject<AppCacheSetting>()
        val parentNavigator = LocalRootNavigator.current

        ProfileScreen(
            onEditClick = { parentNavigator.push(SetUpScreen(true)) },
        )
    }
}