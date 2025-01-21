package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.gradnet.presentation.helper.MyTab
import com.sdjic.gradnet.presentation.helper.MyTabOptions
import com.sdjic.gradnet.presentation.screens.jobs.JobScreenContent
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.home
import gradnet_graduatenetwork.composeapp.generated.resources.home_outline

object HomeTab : MyTab {

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
        val scrollState = rememberLazyListState()
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {

                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                }
            }
        ) { pVal ->
            LazyColumn(
                state = scrollState,
                modifier = Modifier.padding(pVal)
            ) {
                items(10) {
                    JobScreenContent()
                }
            }
        }
    }
}
