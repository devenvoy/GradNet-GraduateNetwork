package com.sdjic.gradnet.presentation.screens.jobs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.home.HomeScreenViewModel
import network.chaintech.sdpcomposemultiplatform.sdp

class JobScreen : Screen {
    @Composable
    override fun Content() {
        JobScreenContent()
    }

    @Composable
    private fun JobScreenContent() {
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