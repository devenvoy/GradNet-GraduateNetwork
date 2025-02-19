package com.sdjic.jobs

import androidx.paging.PagingData
import app.cash.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.data.network.CryptoRepository
import com.sdjic.domain.model.response.CryptoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JobScreenModel(
    private val cryptoRepository: CryptoRepository
) : ScreenModel {
    private val _coinList = MutableStateFlow<PagingData<CryptoResponse.Coin>>(PagingData.empty())
    val coinList = _coinList.stateIn(
        scope = screenModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = PagingData.empty()
    )

    init {
        screenModelScope.launch {
            cryptoRepository.getCryptos("INR")
                .cachedIn(screenModelScope)
                .collect { pagingData ->
                    _coinList.value = pagingData
                }
        }
    }
}