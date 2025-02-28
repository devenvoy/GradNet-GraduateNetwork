package com.sdjic.gradnet.domain.useCases

import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.data.network.utils.map
import com.sdjic.gradnet.domain.repo.PostRepository

class LikePostUseCase(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: String, accessToken: String) : Result<ServerResponse<Map<String,String>?>,ServerError>{
        return postRepository.sendLikePostCall(accessToken, postId).map { it: ServerResponse<Any?> ->
            ServerResponse(code = it.code,value = null, status = it.status, detail = it.detail)
        }
    }
}