package com.sdjic.gradnet.domain.useCases


import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.sdjic.gradnet.data.paging.LikedPostPagingSource
import com.sdjic.gradnet.domain.repo.PostRepository
import com.sdjic.gradnet.presentation.core.model.Post
import kotlinx.coroutines.flow.Flow

class GetLikedPostsUseCase(private val postRepository: PostRepository) {
    operator fun invoke(limit: Int, accessToken: String): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = limit, initialLoadSize = limit),
            pagingSourceFactory = {
                val postPagingSource = LikedPostPagingSource(postRepository)
                postPagingSource.setToken(accessToken)
                postPagingSource
            }
        ).flow
    }
}

