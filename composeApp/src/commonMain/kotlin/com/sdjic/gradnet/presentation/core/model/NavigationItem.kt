package com.sdjic.gradnet.presentation.core.model

import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.home
import gradnet_graduatenetwork.composeapp.generated.resources.person
import gradnet_graduatenetwork.composeapp.generated.resources.search
import org.jetbrains.compose.resources.DrawableResource

enum class NavigationItem(
    val title: String,
    val icon: DrawableResource
) {
    Home(
        icon = Res.drawable.home,
        title = "Home"
    ),
    Profile(
        icon = Res.drawable.person,
        title = "Profile"
    ),
    Settings(
        icon = Res.drawable.search,
        title = "Settings"
    )
}