package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.commons.helper.MyTab
import com.sdjic.commons.helper.MyTabOptions
import com.sdjic.gradnet.presentation.screens.jobs.JobScreen
import com.sdjic.shared.Resource as Res

// to see latest job req post and search job based on requirements
object JobsTab : MyTab {

    override val options: TabOptions
        @Composable get() = remember {
            TabOptions(
                index = 1u,
                title = "Jobs"
            )
        }

    override val tabOption: MyTabOptions
        @Composable get() = remember {
            MyTabOptions(
                index = 1u,
                title = "Jobs",
                selectedIcon = Res.drawable.work,
                unselectedIcon = Res.drawable.workOutline
            )
        }

    @Composable
    override fun Content() {
        Navigator(JobScreen())
    }
}


