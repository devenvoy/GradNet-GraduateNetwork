package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.commons.helper.MyTab
import com.sdjic.commons.helper.MyTabOptions
import com.sdjic.posts.PostScreen
import com.sdjic.shared.Resource as Res

object PostTab : MyTab {

    override val options: TabOptions
        @Composable
        get() {
            return remember {
                TabOptions(0u, "Home")
            }
        }
    override val tabOption: MyTabOptions
        @Composable get() = remember {
            MyTabOptions(
                index = 0u,
                title = "Home",
                selectedIcon = Res.drawable.home,
                unselectedIcon = Res.drawable.homeOutline
            )
        }

    @Composable
    override fun Content() {
        Navigator(PostScreen())
    }
}











