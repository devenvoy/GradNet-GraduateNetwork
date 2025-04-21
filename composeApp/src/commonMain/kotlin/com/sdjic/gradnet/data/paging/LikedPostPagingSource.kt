package com.sdjic.gradnet.data.paging

import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.data.network.utils.postDtoToPost
import com.sdjic.gradnet.domain.BasePagingSource
import com.sdjic.gradnet.domain.repo.PostRepository
import com.sdjic.gradnet.presentation.core.model.Post
import kotlin.properties.Delegates

class LikedPostPagingSource(
    private val postRepository: PostRepository
) : BasePagingSource<Post>() {

    private var accessToken by Delegates.notNull<String>()

    fun setToken(value: String) {
        accessToken = value
    }

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<Post> {
        var result = PaginationItems<Post>(
            items = emptyList(),
            page = 0,
            total = 0
        )
        postRepository.getLikedPosts(
            accessToken = accessToken,
            page = page,
            perPage = limit
        ).onSuccess { r ->
            if (r.value != null) {
                result = PaginationItems(
                    items = r.value.postDtos.mapNotNull { pd -> postDtoToPost(pd) },
                    page = r.value.page,
                    total = r.value.perPage.toLong()
                )
            }
        }.onError {
            result = PaginationItems(
                items = emptyList(),
                page = 0,
                total = 0
            )
        }
        return result
    }
}
