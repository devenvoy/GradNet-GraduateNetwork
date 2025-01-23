package com.sdjic.gradnet.presentation.screens.posts

import com.sdjic.gradnet.presentation.core.model.Filter

sealed interface PostScreenAction {
    data class OnFilterSheetStateChange(val show: Boolean) : PostScreenAction
    data class OnFilterChange(val filter: Filter) : PostScreenAction
    data object OnToggleSelectAll : PostScreenAction
    data object OnUpdateFilter : PostScreenAction
}