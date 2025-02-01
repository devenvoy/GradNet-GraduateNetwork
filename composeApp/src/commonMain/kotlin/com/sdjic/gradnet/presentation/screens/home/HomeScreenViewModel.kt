package com.sdjic.gradnet.presentation.screens.home

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.entity.response.CryptoResponse
import com.sdjic.gradnet.data.network.source.CoinPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val coinPagingSource: CoinPagingSource
) : ScreenModel {

    private val _coinList = MutableStateFlow<PagingData<CryptoResponse.Coin>>(PagingData.empty())
    val coinList = _coinList.stateIn(
        scope = screenModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = PagingData.empty()
    )

    init {
        screenModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 6),
                pagingSourceFactory = { coinPagingSource }
            ).flow
                .cachedIn(screenModelScope)
                .collect { pagingData ->
                    _coinList.value = pagingData
                }
        }
    }
}
