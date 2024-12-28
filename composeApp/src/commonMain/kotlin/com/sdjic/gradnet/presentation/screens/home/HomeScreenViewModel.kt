package com.sdjic.gradnet.presentation.screens.home

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.entity.CryptoResponse
import com.sdjic.gradnet.data.network.source.CoinPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val coinPagingSource: CoinPagingSource
):ScreenModel {

    val coinList = MutableStateFlow<PagingData<CryptoResponse.Coin>>(PagingData.empty())

    init {
        screenModelScope.launch {
            Pager(config = PagingConfig(pageSize = 6),
                pagingSourceFactory = { coinPagingSource }
            ).flow.collect {
                coinList.value = it
            }
        }
    }
}