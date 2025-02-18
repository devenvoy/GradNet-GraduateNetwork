package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.gradnet.presentation.helper.MyTab
import com.sdjic.gradnet.presentation.helper.MyTabOptions
import com.sdjic.gradnet.presentation.screens.posts.PostScreen
import com.sdjic.shared.resources.Res
import com.sdjic.shared.resources.home
import com.sdjic.shared.resources.home_outline

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
                unselectedIcon = Res.drawable.home_outline
            )
        }

    @Composable
    override fun Content() {
        Navigator(PostScreen())
    }
}











