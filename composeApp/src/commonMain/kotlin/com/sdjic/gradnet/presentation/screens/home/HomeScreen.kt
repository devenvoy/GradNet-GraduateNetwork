package com.sdjic.gradnet.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import network.chaintech.sdpcomposemultiplatform.sdp

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        HomeScreenContent()
    }

    @Composable
    fun HomeScreenContent() {
        val viewModel = koinScreenModel<HomeScreenViewModel>()
        Scaffold {
            LazyColumn(modifier = Modifier.padding(it).padding(10.sdp)) {
                items(viewModel.coinList) {
                    ListItem(
                        headlineContent = {
                            SText(it.name)
                        },
                        supportingContent = {
                            SText(it.toString())
                        }
                    )
                }
            }
        }
    }
}