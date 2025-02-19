package com.sdjic.data.network

import com.sdjic.commons.model.ServerError
import com.sdjic.commons.model.ServerResponse
import com.sdjic.commons.utils.BaseGateway
import com.sdjic.commons.utils.Result
import com.sdjic.domain.model.response.UserProfileResponse
import com.sdjic.domain.model.response.VerifyUserResponse
import com.sdjic.domain.repo.UserRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class UserRepositoryImpl(httpClient: HttpClient) : UserRepository, BaseGateway(httpClient) {

    override suspend fun fetchUser(token: String): Result<ServerResponse<UserProfileResponse>, ServerError> {
        return tryToExecute<ServerResponse<UserProfileResponse>> {
            get(Config.getBaseUrl() + "/fetch_profile") {
                header(HttpHeaders.Authorization, "Bearer $token")
                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun updateUser() {}

    override suspend fun updateUserImages(
        token: String,
        byteArray: ByteArray,
        type: String
    ): Result<ServerResponse<UserProfileResponse>, ServerError> {
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.submitFormWithBinaryData(
                    url = "${Config.getBaseUrl()}/image_upload?img_type=$type",
                    formData = formData {
                        append("file", byteArray, Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(HttpHeaders.ContentDisposition, "filename=image.jpg")
                        })
                    }
                ) {
                    header(HttpHeaders.Authorization, "Bearer $token")
                    onUpload { bytesSentTotal, contentLength ->
                    }
                }

                if (response.status.isSuccess()) {
                    val serverResponse = response.body<ServerResponse<UserProfileResponse>>()
                    Result.Success(serverResponse)
                } else {
                    val serverError = response.body<ServerError>()
                    Result.Error(serverError)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(ServerError(500, null, e.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun sendOtp(verificationId: String): Result<ServerResponse<VerifyUserResponse>, ServerError> {
        return tryToExecute<ServerResponse<VerifyUserResponse>> {
            post(Config.getBaseUrl() + "/verify-user?verify_id=${verificationId}") {
                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun verifyOtp(
        verificationId: String,
        otp: String,
        token: String
    ): Result<ServerResponse<UserProfileResponse>, ServerError> {
        return tryToExecute<ServerResponse<UserProfileResponse>> {
            post(Config.getBaseUrl() + "/verify-otp?verify_id=${verificationId}&otp=${otp}") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }
}