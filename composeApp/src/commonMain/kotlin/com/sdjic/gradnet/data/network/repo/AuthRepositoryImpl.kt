package com.sdjic.gradnet.data.network.repo

import GradNet_GraduateNetwork.composeApp.BuildConfig
import com.sdjic.gradnet.data.network.entity.response.ForgotPasswordResponse
import com.sdjic.gradnet.data.network.entity.response.LoginResponse
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.entity.response.SignUpRequest
import com.sdjic.gradnet.data.network.entity.response.SignUpResponse
import com.sdjic.gradnet.data.network.utils.BaseGateway
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.domain.repo.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonElement

class AuthRepositoryImpl(httpClient: HttpClient) : AuthRepository, BaseGateway(httpClient) {

    override suspend fun login(
        email: String,
        password: String
    ): Result<ServerResponse<LoginResponse>, ServerError> {
        return tryToExecute<ServerResponse<LoginResponse>> {
            post("${BuildConfig.BASE_URL}/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(
                    mapOf(
                        "email" to email,
                        "password" to password
                    )
                )
            }
        }
    }

    override suspend fun signUp(
        signUpRequest: SignUpRequest
    ): Result<ServerResponse<SignUpResponse>, ServerError> {
        return tryToExecute<ServerResponse<SignUpResponse>> {
            post("${BuildConfig.BASE_URL}/auth/signup") {
                contentType(ContentType.Application.Json)
                setBody(signUpRequest)
            }
        }
    }

    override suspend fun updatePassword(
        accessToken: String,
        oldPassword: String,
        newPassword: String
    ): Result<ServerResponse<JsonElement>, ServerError> {
        return tryToExecute {
            post("${BuildConfig.BASE_URL}/auth/change-password") {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $accessToken")
                setBody(
                    mapOf(
                        "currentPassword" to oldPassword,
                        "newPassword" to newPassword
                    )
                )
            }
        }
    }

    override suspend fun forgotPassword(email: String): Result<ForgotPasswordResponse, ServerError> {
        return tryToExecute {
            post("${BuildConfig.BASE_URL}/auth/forgot-password") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("email" to email))
            }
        }
    }
}
