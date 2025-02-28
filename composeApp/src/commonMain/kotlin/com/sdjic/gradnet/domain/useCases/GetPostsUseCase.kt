package com.sdjic.gradnet.domain.useCases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sdjic.gradnet.data.network.entity.dto.PostDto
import com.sdjic.gradnet.data.network.utils.map
import com.sdjic.gradnet.domain.ResultPagingSource
import com.sdjic.gradnet.domain.repo.PostRepository
import com.sdjic.gradnet.presentation.core.model.Post
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(
    private val postRepository: PostRepository
) {
    operator fun invoke(limit: Int): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            pagingSourceFactory = {
                ResultPagingSource { page, pageSize ->
                    postRepository.getPosts(page = page, perPage = pageSize).map { result ->
                        result.value?.postDtos?.mapNotNull { postDtoToPost(it) } ?: emptyList()
                    }
                }
            }
        ).flow
    }

    private fun postDtoToPost(postDto: PostDto?): Post? {
        return if (postDto != null)
            Post(
                postId = postDto.postId,
                userId = postDto.userId.orEmpty(),
                userName = postDto.userName.orEmpty(),
                userImage = postDto.userProfilePic.orEmpty(),
                userRole = UserRole.getUserRole(postDto.userRole ?: "") ?: UserRole.Alumni,
                content = postDto.description,
                likesCount = postDto.likes,
                images = postDto.photos?.filterNotNull() ?: emptyList(),
                location = postDto.location.orEmpty(),
                createdAt = postDto.createdAt
            )
        else null
    }
}
