package com.sdjic.jobs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import com.sdjic.commons.composables.text.SText
import com.sdjic.commons.composables.text.Title
import com.sdjic.commons.helper.PagingListUI
import com.sdjic.commons.helper.koinScreenModel
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
        val viewModel = koinScreenModel<JobScreenModel>()
        val data = viewModel.coinList.collectAsLazyPagingItems()
        Scaffold {
            PagingListUI(
                modifier = Modifier.padding(it).padding(10.sdp),
                data = data
            ) { item ->
                ListItem(
                    headlineContent = {
                        Title(item.name)
                    },
                    supportingContent = {
                        SText(item.toString())
                    }
                )
            }
        }
    }
}