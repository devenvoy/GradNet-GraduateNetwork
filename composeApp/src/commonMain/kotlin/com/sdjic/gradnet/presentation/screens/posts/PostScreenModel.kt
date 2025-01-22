package com.sdjic.gradnet.presentation.screens.posts

import cafe.adriel.voyager.core.model.ScreenModel
import com.sdjic.gradnet.presentation.core.model.Filter
import kotlinx.coroutines.flow.MutableStateFlow

class PostScreenModel : ScreenModel {

    var userTypeFilters = MutableStateFlow(emptyList<Filter>())

    init {
        userTypeFilters.value = listOf(
            Filter("all", true),
            Filter("faculty", true),
            Filter("alumni", true),
            Filter("organization", true)
        )
    }
}