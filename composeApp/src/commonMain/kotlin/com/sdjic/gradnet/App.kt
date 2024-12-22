package com.sdjic.gradnet

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.sdjic.gradnet.di.platform_di.getDatabaseBuilder
import com.sdjic.gradnet.presentation.screens.TestScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    // local database for app
    val gradNetDB = getDatabaseBuilder().build().setQueryCoroutineContext(Dispatchers.IO).build()
    MaterialTheme {
       Navigator(TestScreen())
    }
}