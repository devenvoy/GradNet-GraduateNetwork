package com.sdjic.gradnet.presentation.screens.setting.lost_found

import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.entity.dto.LostItemDto
import com.sdjic.gradnet.domain.useCases.GetLostItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LostItemListScreenModel(
    private val getLostItemsUseCase: GetLostItemsUseCase
) : ScreenModel {

    private val _lostItems = MutableStateFlow<PagingData<LostItemDto>>(PagingData.empty())
    val lostItems = _lostItems.asStateFlow()

    init {
        screenModelScope.launch {
            launch { refreshItems() }
        }
    }

    suspend fun refreshItems() {
        getLostItemsUseCase.invoke(5)
            .cachedIn(screenModelScope)
            .collect { data -> _lostItems.update { data } }
    }
}