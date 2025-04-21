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
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonElement

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
                parameter("per_page", "$perPage")

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
                parameter("per_page", "$perPage")
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
        return tryToExecute<ServerResponse<JsonElement>> {
            post("$baseUrl/posts/create") {
                header("Authorization", "Bearer $accessToken")
                contentType(ContentType.MultiPart.FormData)

                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("description", postContent)
                            append("location", location)
                            files.forEachIndexed { index, byteArray ->
                                append(
                                    "files",
                                    byteArray,
                                    Headers.build {
                                        append(HttpHeaders.ContentDisposition, "form-data; name=\"files\"; filename=\"image_$index.jpg\"")
                                        append(HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
                                    }
                                )
                            }
                        }
                    )
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
                      "post_id": "$postId"
                    }
                """.trimIndent())
            }
        }
    }
}