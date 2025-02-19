package com.sdjic.posts

import com.sdjic.posts.model.Filter

sealed interface PostScreenAction {
    data class OnFilterSheetStateChange(val show: Boolean) : PostScreenAction
    data class OnFilterChange(val filter: Filter) : PostScreenAction
    data object OnToggleSelectAll : PostScreenAction
    data object OnUpdateFilter : PostScreenAction
}