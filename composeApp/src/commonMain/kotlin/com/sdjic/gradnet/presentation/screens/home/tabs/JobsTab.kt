package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.composables.Title
import com.sdjic.gradnet.presentation.helper.MyTab
import com.sdjic.gradnet.presentation.helper.MyTabOptions
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.home.HomeScreenViewModel
import com.sdjic.gradnet.presentation.screens.jobs.JobScreenContent
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.work
import gradnet_graduatenetwork.composeapp.generated.resources.work_outline
import network.chaintech.sdpcomposemultiplatform.sdp

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
        CryptoListContent()
    }


    @Composable
    fun CryptoListContent() {
        val viewModel = koinScreenModel<HomeScreenViewModel>()
        val data = viewModel.coinList.collectAsLazyPagingItems()
        Scaffold {
            LazyColumn(modifier = Modifier.padding(it).padding(10.sdp)) {
                items(data.itemCount) {
                    ListItem(
                        headlineContent = {
                            Title(data[it]?.name ?: "")
                        },
                        supportingContent = {
                            data[it]?.let {
                                SText(it.toString())
                            }
                        }
                    )
                }
            }
        }
    }
}


