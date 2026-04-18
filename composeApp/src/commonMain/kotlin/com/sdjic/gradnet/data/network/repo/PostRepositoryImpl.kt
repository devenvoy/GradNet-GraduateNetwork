package com.sdjic.gradnet.data.network.repo

import GradNet_GraduateNetwork.composeApp.BuildConfig
import com.sdjic.gradnet.data.network.entity.dto.PostDto
import com.sdjic.gradnet.data.network.entity.response.PostResponse
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.utils.BaseGateway
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.domain.repo.PostRepository
import com.sdjic.gradnet.presentation.core.model.Filter
import io.ktor.client.HttpClient
import io.ktor.client.content.ProgressListener
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray

class PostRepositoryImpl(httpClient: HttpClient) : PostRepository, BaseGateway(httpClient) {
    private val baseUrl = BuildConfig.BASE_URL

    override suspend fun getPosts(
        accessToken: String,
        page: Int,
        perPage: Int,
        selectedFilters: List<Filter>
    ): Result<ServerResponse<PostResponse>, ServerError> {
        return tryToExecute<ServerResponse<PostResponse>> {
            get("$baseUrl/posts") {
                header("Authorization", "Bearer $accessToken")
                parameter("page", "$page")
                parameter("perPage", "$perPage")

                selectedFilters.filter { it.value }.forEach { filter ->
                    parameter("role", filter.key)
                }
            }
        }
    }

    override suspend fun getLikedPosts(
        accessToken: String,
        page: Int,
        perPage: Int,
    ): Result<ServerResponse<PostResponse>, ServerError> {
        return tryToExecute<ServerResponse<PostResponse>> {
            get("$baseUrl/posts/liked_post") {
                header("Authorization", "Bearer $accessToken")
                parameter("page", "$page")
                parameter("perPage", "$perPage")
            }
        }
    }

    override suspend fun getPostByUserId(userId: String): Result<ServerResponse<List<PostDto>>, ServerError> {
        return tryToExecute<ServerResponse<List<PostDto>>> {
            get("$baseUrl/posts/user"){
                parameter("user_id", userId)
            }
        }
    }

    override suspend fun getMyPosts(accessToken: String): Result<ServerResponse<List<PostDto>>, ServerError> {
        return tryToExecute<ServerResponse<List<PostDto>>> {
            get("$baseUrl/posts/my") {
                header("Authorization", "Bearer $accessToken")
            }
        }
    }

    override suspend fun createNewPost(
        accessToken: String,
        postContent: String,
        location: String,
        files: List<ByteArray>,
        listener: ProgressListener?
    ): Result<ServerResponse<JsonElement>, ServerError> {
        val imageUrls = mutableListOf<String>()

        // 1. Upload all images
        files.forEach { byteArray ->
            val uploadResult = uploadImage(byteArray, accessToken, type = "POST")
            when (uploadResult) {
                is Result.Success -> {
                    uploadResult.data.value?.fileUrl?.let { url ->
                        imageUrls.add(url)
                    }
                }
                is Result.Error -> return Result.Error(uploadResult.error)
                Result.Loading -> {}
            }
        }

        // 2. Submit JSON payload
        return tryToExecute<ServerResponse<JsonElement>> {
            post("$baseUrl/posts/create") {
                header("Authorization", "Bearer $accessToken")
                contentType(ContentType.Application.Json)
                setBody(
                    buildJsonObject {
                        put("description", postContent)
                        put("location", location)
                        putJsonArray("images") {
                            imageUrls.forEach { add(JsonPrimitive(it)) }
                        }
                    }
                )
                onUpload(listener)
            }
        }
    }

    override suspend fun sendLikePostCall(
        accessToken: String,
        postId: String
    ): Result<ServerResponse<JsonElement>, ServerError> {
        return tryToExecute<ServerResponse<JsonElement>> {
            post("$baseUrl/posts/like") {
                header("Authorization", "Bearer $accessToken")
                contentType(ContentType.Application.Json)
                setBody("""
                    {
                      "postId": "$postId"
                    }
                """.trimIndent())
            }
        }
    }
}