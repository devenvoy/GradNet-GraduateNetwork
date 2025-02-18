package com.sdjic.gradnet.presentation.core.model

import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.info_outline
import gradnet_graduatenetwork.composeapp.generated.resources.logout
import gradnet_graduatenetwork.composeapp.generated.resources.person
import gradnet_graduatenetwork.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.DrawableResource

enum class NavigationItem(
    val title: String,
    val icon: DrawableResource
) {
    Profile(
        icon = Res.drawable.person,
        title = "Profile"
    ),
    Settings(
        icon = Res.drawable.settings,
        title = "Settings"
    ),
    AboutUs(
        icon = Res.drawable.info_outline,
        title = "About us"
    ),
    Logout(
        icon = Res.drawable.logout,
        title = "Logout"
    )
}