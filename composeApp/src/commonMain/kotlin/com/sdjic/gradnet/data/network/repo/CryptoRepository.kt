package com.sdjic.gradnet.data.network.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sdjic.gradnet.data.network.entity.response.CryptoResponse
import com.sdjic.gradnet.data.network.utils.BaseGateway
import com.sdjic.gradnet.data.network.utils.map
import com.sdjic.gradnet.domain.ResultPagingSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow

class CryptoRepository(httpClient: HttpClient) : BaseGateway(httpClient) {
    private val BASE_URL = "https://openapiv1.coinstats.app"
    private val API_KEY = "Ip9gbIGRjP23xr4B7+VCeXUcBcqQnr+oDZFa0wEBtw8="

    fun getCryptos(cur: String = "EUR"): Flow<PagingData<CryptoResponse.Coin>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                ResultPagingSource { _page, _limit ->
                    tryToExecute<CryptoResponse> {
                        get("$BASE_URL/coins") {
                            parameter("page", _page)
                            parameter("limit", _limit)
                            parameter("currency", cur)
                            header("accept", "application/json")
                            header("X-API-KEY", API_KEY)
                        }
                    }.map { it.result }
                }
            }
        ).flow
    }
}