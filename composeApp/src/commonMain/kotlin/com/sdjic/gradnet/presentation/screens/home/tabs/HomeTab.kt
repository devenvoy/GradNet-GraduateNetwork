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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.composables.Title
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.home.HomeScreenViewModel
import network.chaintech.sdpcomposemultiplatform.sdp

object HomeTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val defaultIcon = rememberVectorPainter(Icons.Outlined.Home)
            val selectedIcon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Home",
                    icon = selectedIcon
                )
            }
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
