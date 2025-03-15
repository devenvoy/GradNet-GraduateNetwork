package com.sdjic.gradnet.domain.useCases


import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.sdjic.gradnet.data.network.utils.map
import com.sdjic.gradnet.data.network.utils.postDtoToPost
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.ResultPagingSource
import com.sdjic.gradnet.domain.repo.PostRepository
import com.sdjic.gradnet.presentation.core.model.Filter
import com.sdjic.gradnet.presentation.core.model.Post
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(
    private val postRepository: PostRepository,
    private val pref: AppCacheSetting
) {
    operator fun invoke(limit: Int,filters: List<Filter>): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = limit, initialLoadSize = limit),
            pagingSourceFactory = {
                ResultPagingSource { page, pageSize ->
                    postRepository.getPosts(
                        accessToken = pref.accessToken,
                        page = page,
                        perPage = pageSize,
                        selectedFilters = filters
                    ).map { result ->
                        result.value?.postDtos?.mapNotNull(::postDtoToPost) ?: emptyList()
                    }
                }
            }
        ).flow
    }
}
