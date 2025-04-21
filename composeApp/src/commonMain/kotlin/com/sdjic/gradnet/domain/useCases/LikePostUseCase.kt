package com.sdjic.gradnet.domain.useCases

import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.domain.repo.PostRepository
import kotlinx.serialization.json.JsonElement

class LikePostUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: String, accessToken: String) : Result<ServerResponse<JsonElement>,ServerError>{
        return postRepository.sendLikePostCall(accessToken, postId)
    }
}