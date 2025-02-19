package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.commons.helper.MyTab
import com.sdjic.commons.helper.MyTabOptions
import com.sdjic.search.SearchScreen
import com.sdjic.shared.Resource as Res

object SearchTab : MyTab {

    override val options: TabOptions
        @Composable get() = remember {
            TabOptions(2u, "Search")
        }
    override val tabOption: MyTabOptions
        @Composable get() = remember {
            MyTabOptions(
                index = 2u,
                title = "Search",
                selectedIcon = Res.drawable.search,
                unselectedIcon = Res.drawable.search
            )
        }

    @Composable
    override fun Content() {
       Navigator(SearchScreen())
    }
}