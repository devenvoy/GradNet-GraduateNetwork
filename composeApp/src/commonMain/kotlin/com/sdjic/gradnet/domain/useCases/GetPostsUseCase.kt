package com.sdjic.gradnet.domain.useCases


import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.sdjic.gradnet.data.paging.PostPagingSource
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.PostRepository
import com.sdjic.gradnet.presentation.core.model.Filter
import com.sdjic.gradnet.presentation.core.model.Post
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(
    private val postRepository: PostRepository,
    private val pref: AppCacheSetting
) {
    operator fun invoke(limit: Int, filters: List<Filter>): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = limit, initialLoadSize = limit),
            pagingSourceFactory = {
                val postPagingSource = PostPagingSource(
                    postRepository = postRepository,
                    pref = pref
                )
                postPagingSource.filterPost(filters)
                postPagingSource
            }
        ).flow
    }
}


/*

class GetPostsUseCase(
    private val postRepository: PostRepository,
    private val postDao: PostDao,
    private val postRemoteKeysDao: PostRemoteKeysDao,
    private val pref: AppCacheSetting
) {
    @OptIn(ExperimentalPagingApi::class)
    operator fun invoke(limit: Int, filters: List<Filter>): Flow<PagingData<PostTable>> {
        return Pager(
            config = PagingConfig(pageSize = limit, initialLoadSize = limit),
            remoteMediator = PostRemoteMediator(
                postRepository = postRepository,
                postDao = postDao,
                postRemoteKeysDao = postRemoteKeysDao,
                pref = pref
            ),
            pagingSourceFactory = {
                postDao.getPosts()
            }
        ).flow
    }
}
*/
