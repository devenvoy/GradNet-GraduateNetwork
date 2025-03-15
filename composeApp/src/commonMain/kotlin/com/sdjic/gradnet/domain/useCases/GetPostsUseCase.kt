package com.sdjic.gradnet.domain.useCases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sdjic.gradnet.data.network.entity.dto.PostDto
import com.sdjic.gradnet.data.network.utils.map
import com.sdjic.gradnet.data.network.utils.postDtoToPost
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.ResultPagingSource
import com.sdjic.gradnet.domain.repo.PostRepository
import com.sdjic.gradnet.presentation.core.model.Post
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(
    private val postRepository: PostRepository,
    private val pref: AppCacheSetting
) {
    operator fun invoke(limit: Int): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            pagingSourceFactory = {
                ResultPagingSource { page, pageSize ->
                    postRepository.getPosts(
                        accessToken = pref.accessToken,
                        page = page,
                        perPage = pageSize
                    ).map { result ->
                        result.value?.postDtos?.mapNotNull(::postDtoToPost) ?: emptyList()
                    }
                }
            }
        ).flow
    }
}
