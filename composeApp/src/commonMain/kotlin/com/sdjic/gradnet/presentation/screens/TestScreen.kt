package com.sdjic.gradnet.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.sdjic.gradnet.data.entity.Test
import com.sdjic.gradnet.di.platform_di.getDatabaseBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import network.chaintech.sdpcomposemultiplatform.sdp

class TestScreen  : Screen{
    private val gradNetDB = getDatabaseBuilder().build().setQueryCoroutineContext(Dispatchers.IO).build()
    private val testDao = gradNetDB.testDao
    @Composable
    override fun Content() {

        var tests by remember { mutableStateOf(emptyList<Test>()) }

        LaunchedEffect(Unit){
            testDao.insertAll(
                listOf(
                    Test(0,"Test1"),
                    Test(0,"Test2"),
                    Test(0,"Test3"),
                    Test(0,"Test4"),
                    Test(0,"Test5"),
                    Test(0,"Test6"),
                    Test(0,"Test7"),
                )
            )
            delay(4000)
            tests = testDao.getAll()
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.sdp)
        ) {
            items(tests){
                Text(it.name)
            }
        }
    }
}