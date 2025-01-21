package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.gradnet.presentation.helper.MyTab
import com.sdjic.gradnet.presentation.helper.MyTabOptions
import com.sdjic.gradnet.presentation.screens.profile.ProfileScreen
import com.sdjic.gradnet.presentation.theme.AppTheme

object ProfileTab : MyTab {

    override val options: TabOptions
       @Composable get() = TabOptions(4u,"Profile")

    override val tabOption: MyTabOptions
       @Composable get() = remember {
           MyTabOptions(
               index = 4u,
               title = "Profile",
               selectedIcon = Icons.Default.Person,
               unselectedIcon = Icons.Outlined.Person
           )
       }

    @Composable
    override fun Content() {
        AppTheme {
            ProfileScreen()
        }
    }
}
