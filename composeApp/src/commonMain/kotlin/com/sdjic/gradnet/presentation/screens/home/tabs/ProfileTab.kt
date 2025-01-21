package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.gradnet.presentation.helper.MyTab
import com.sdjic.gradnet.presentation.helper.MyTabOptions
import com.sdjic.gradnet.presentation.screens.profile.ProfileScreen
import com.sdjic.gradnet.presentation.theme.AppTheme
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.person
import gradnet_graduatenetwork.composeapp.generated.resources.person_outline

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

    @Composable
    override fun Content() {
        AppTheme {
            ProfileScreen()
        }
    }
}
