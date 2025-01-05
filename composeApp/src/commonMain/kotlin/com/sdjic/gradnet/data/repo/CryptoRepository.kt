package com.sdjic.gradnet.data.repo

import com.sdjic.gradnet.data.network.entity.CryptoResponse
import com.sdjic.gradnet.data.network.utils.BaseGateway
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.utils.PaginationItems
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

class CryptoRepository(httpClient: HttpClient) : BaseGateway(httpClient) {
    private val BASE_URL = "https://openapiv1.coinstats.app"
    private val API_KEY = "Ip9gbIGRjP23xr4B7+VCeXUcBcqQnr+oDZFa0wEBtw8="

    suspend fun getCryptos(
        page: Int = 1,
        limit: Int = 10,
        cur: String = "EUR"
    ): PaginationItems<CryptoResponse.Coin> {
        val result = executeOrThrow<CryptoResponse> {
            get("$BASE_URL/coins") {
                parameter("page", page)
                parameter("limit", limit)
                parameter("currency", cur)
                header("accept", "application/json")
                header("X-API-KEY",API_KEY)
            }
        }
        return paginateData(
            result = result.result,
            page = result.meta.page,
            total = result.meta.pageCount.toLong()
        )
    }
}