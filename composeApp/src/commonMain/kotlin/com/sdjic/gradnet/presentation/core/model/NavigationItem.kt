package com.sdjic.gradnet.presentation.core.model

import com.sdjic.shared.resources.Res
import com.sdjic.shared.resources.info_outline
import com.sdjic.shared.resources.logout
import com.sdjic.shared.resources.person
import com.sdjic.shared.resources.settings
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