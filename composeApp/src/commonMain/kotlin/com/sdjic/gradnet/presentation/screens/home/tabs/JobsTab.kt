package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.gradnet.presentation.helper.MyTab
import com.sdjic.gradnet.presentation.helper.MyTabOptions
import com.sdjic.gradnet.presentation.screens.jobs.JobScreen
import com.sdjic.gradnet.presentation.theme.AppTheme
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.work
import gradnet_graduatenetwork.composeapp.generated.resources.work_outline

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
                unselectedIcon = Res.drawable.work_outline
            )
        }

    @Composable
    override fun Content() {
        AppTheme {
            Navigator(JobScreen())
        }
    }
}


