package com.sdjic.gradnet.presentation.screens.home

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.CryptoRepository
import com.sdjic.gradnet.data.network.entity.CryptoResponse
import com.sdjic.gradnet.data.network.utils.onSuccess
import kotlinx.coroutines.launch

class HomeScreenViewModel(
private val cryptoRepository: CryptoRepository
):ScreenModel {

    val coinList = mutableStateListOf<CryptoResponse.Coin>()

    init {
        screenModelScope.launch {
            val list = cryptoRepository.getCryptos()
            list.onSuccess {
                coinList.addAll(it.result)
            }
        }
    }
}