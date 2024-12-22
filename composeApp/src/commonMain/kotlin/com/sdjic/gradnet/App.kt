package com.sdjic.gradnet

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.sdjic.gradnet.di.initKoin
import com.sdjic.gradnet.presentation.screens.demo.TestScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    // local database for app
//    val gradNetDB = getDatabaseBuilder().build().setQueryCoroutineContext(Dispatchers.IO).build()
    initKoin({})
    MaterialTheme {
       Navigator(TestScreen())
    }
}