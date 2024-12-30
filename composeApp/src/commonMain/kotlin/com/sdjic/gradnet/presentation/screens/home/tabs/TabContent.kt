package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.transitions.SlideTransition
import co.touchlab.kermit.Logger
import com.sdjic.gradnet.presentation.screens.home.BasicNavigationScreen
import com.sdjic.gradnet.presentation.composables.Title

@Composable
fun Tab.TabContent() {
    val tabTitle = options.title

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle){

        val observer = LifecycleEventObserver { _, event ->
            when(event){
                Lifecycle.Event.ON_CREATE -> {}
                Lifecycle.Event.ON_START -> {
                    Logger.d(tag = "Navigator", null, message = { "Start tab $tabTitle" })
                }
                Lifecycle.Event.ON_RESUME -> {}
                Lifecycle.Event.ON_PAUSE -> {}
                Lifecycle.Event.ON_STOP -> {}
                Lifecycle.Event.ON_DESTROY -> {}
                Lifecycle.Event.ON_ANY -> {}
            }
        }
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
            Logger.d("Navigator", null) { "Dispose tab $tabTitle" }
        }
    }

    Navigator(BasicNavigationScreen(index = 0)) { navigator ->
        SlideTransition(navigator) { screen ->
            Column {
//                InnerTabNavigation()
                screen.Content()
                Logger.d("Navigator", null ){ "Current Screen: ${navigator.lastItem}" }
            }
        }
    }
}

@Composable
private fun InnerTabNavigation() {
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
//        TabNavigationButton(HomeTab(""))

        Spacer(modifier = Modifier.weight(.05f))

//        TabNavigationButton(ProfileTab)
    }
}

@Composable
private fun RowScope.TabNavigationButton(
    tab: Tab
) {
    val tabNavigator = LocalTabNavigator.current

    Button(
        enabled = tabNavigator.current.key != tab.key,
        onClick = { tabNavigator.current = tab },
        modifier = Modifier.weight(1f)
    ) {
        Title(text = tab.options.title)
    }
}
