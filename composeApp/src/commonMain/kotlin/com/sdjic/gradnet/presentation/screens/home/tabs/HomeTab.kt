package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
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
import network.chaintech.sdpcomposemultiplatform.sdp

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
                selectedIcon = Icons.Default.Home,
                unselectedIcon = Icons.Outlined.Home
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
