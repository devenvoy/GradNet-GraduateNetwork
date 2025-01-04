package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.gradnet.presentation.helper.MyTab
import com.sdjic.gradnet.presentation.helper.MyTabOptions
import com.sdjic.gradnet.presentation.screens.profile.ProfileScreen

object ProfileTab : MyTab {

    override val options: TabOptions
       @Composable get() = TabOptions(1u,"Profile")

    override val tabOption: MyTabOptions
       @Composable get() = remember {
           MyTabOptions(
               index = 1u,
               title = "Profile",
               selectedIcon = Icons.Default.Person,
               unselectedIcon = Icons.Outlined.Person
           )
       }

    @Composable
    override fun Content() {
        ProfileScreen()
    }
}
