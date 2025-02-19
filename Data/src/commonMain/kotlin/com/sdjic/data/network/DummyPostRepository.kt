package com.sdjic.data.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sdjic.commons.utils.BaseGateway
import com.sdjic.commons.utils.ResultPagingSource
import com.sdjic.commons.utils.map
import com.sdjic.domain.model.response.DummyPostResponse
import com.sdjic.domain.model.response.Post
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow

class DummyPostRepository(httpClient: HttpClient) : BaseGateway(httpClient) {

    fun getPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                ResultPagingSource { page, limit ->
                    tryToExecute<DummyPostResponse> {
                        get("https://dummyjson.com/posts") {
                            parameter("limit", limit)
                            header("accept", "application/json")
                        }
                    }.map { it.posts?.filterNotNull().orEmpty() }
                }
            }
        ).flow
    }
}