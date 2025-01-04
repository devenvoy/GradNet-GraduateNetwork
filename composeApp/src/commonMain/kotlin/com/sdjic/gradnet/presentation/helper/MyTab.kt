package com.sdjic.gradnet.presentation.helper

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

public interface MyTab : Tab {
    public override val options: TabOptions
        @Composable get

    public val tabOption : MyTabOptions
        @Composable get
}


public data class MyTabOptions(
    val index: UShort,
    val title: String,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    var badgeCount : Int? = null,
    var badgeIcon : Any?= null
)
