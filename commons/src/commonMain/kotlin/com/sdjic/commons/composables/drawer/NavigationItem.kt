package com.sdjic.commons.composables.drawer

import com.sdjic.shared.Resource as Res
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
        icon = Res.drawable.infoOutline,
        title = "About us"
    ),
    Logout(
        icon = Res.drawable.logout,
        title = "Logout"
    )
}