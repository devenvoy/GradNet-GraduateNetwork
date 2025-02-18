package com.sdjic.commons.helper

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.DrawableResource

public interface MyTab : Tab {
    public override val options: TabOptions
        @Composable get

    public val tabOption : MyTabOptions
        @Composable get
}


public data class MyTabOptions(
    val index: UShort,
    val title: String,
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource,
    var badgeCount : Int? = null,
    var badgeIcon : Any?= null
)
