package com.sdjic.gradnet.data.network.source

import com.sdjic.gradnet.data.network.entity.response.CryptoResponse
import com.sdjic.gradnet.data.network.repo.CryptoRepository
import com.sdjic.gradnet.domain.utils.BasePagingSource
import com.sdjic.gradnet.domain.utils.PaginationItems

class CoinPagingSource(
    private val cryptoRepository: CryptoRepository
) : BasePagingSource<CryptoResponse.Coin>() {

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<CryptoResponse.Coin> {
        return cryptoRepository.getCryptos(page,limit)
    }
}