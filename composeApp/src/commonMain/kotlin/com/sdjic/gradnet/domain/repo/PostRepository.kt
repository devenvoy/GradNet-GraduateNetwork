package com.sdjic.gradnet.domain.repo

import com.sdjic.gradnet.data.network.entity.dto.PostDto
import com.sdjic.gradnet.data.network.entity.response.PostResponse
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.utils.Result
import io.ktor.client.content.ProgressListener

interface PostRepository {
    suspend fun getPosts(accessToken: String,page: Int, perPage: Int): Result<ServerResponse<PostResponse>, ServerError>

    suspend fun getPostByUserId(userId: String): Result<ServerResponse<List<PostDto>>, ServerError>

    suspend fun getMyPosts(accessToken: String): Result<ServerResponse<List<PostDto>>, ServerError>

    suspend fun createNewPost(
        accessToken: String,
        postContent: String,
        location: String,
        files: List<ByteArray>,
        listener: ProgressListener?
    ): Result<ServerResponse<PostDto>, ServerError>

    suspend fun sendLikePostCall(accessToken: String, postId: String): Result<ServerResponse<Any?>, ServerError>
}