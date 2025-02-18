package com.sdjic.gradnet.presentation.screens.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.sdjic.commons.helper.koinScreenModel
import network.chaintech.sdpcomposemultiplatform.sdp

class TestScreen : Screen {
    @Composable
    override fun Content() {

        val testViewModel = koinScreenModel<TestViewModel>()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.sdp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        testViewModel.increment()
                        testViewModel.addTest()
                    }) {
                        Text("add test no: ${testViewModel.counter.value}")
                    }
                }
            }
            items(testViewModel.testLists.value) {
                Text(it.name)
            }
        }
    }
}