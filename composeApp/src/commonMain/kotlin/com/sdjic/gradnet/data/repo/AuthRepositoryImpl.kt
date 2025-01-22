package com.sdjic.gradnet.data.repo

import GradNet_GraduateNetwork.composeApp.BuildConfig
import com.sdjic.gradnet.data.network.entity.LoginResponse
import com.sdjic.gradnet.data.network.entity.ServerError
import com.sdjic.gradnet.data.network.entity.ServerResponse
import com.sdjic.gradnet.data.network.entity.SignUpRequest
import com.sdjic.gradnet.data.network.entity.SignUpResponse
import com.sdjic.gradnet.data.network.utils.BaseGateway
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.domain.repo.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthRepositoryImpl(httpClient: HttpClient) : AuthRepository, BaseGateway(httpClient) {

    private val loginPath = "/auth/login"
    private val signUpPath = "/auth/register"

    override suspend fun login(
        email: String,
        password: String
    ): Result<ServerResponse<LoginResponse>, ServerError> {
        val result = tryToExecute<ServerResponse<LoginResponse>> {
            post(BuildConfig.BASE_URL + loginPath) {
                contentType(ContentType.Application.Json)
                setBody("""{
                    "email": "$email",
                    "password": "$password"
                }""".trimIndent()
                )
            }
        }
        return result
    }

    override suspend fun signUp(
        signUpRequest: SignUpRequest
    ): Result<ServerResponse<SignUpResponse>, ServerError> {
        val result = tryToExecute<ServerResponse<SignUpResponse>> {
            post(BuildConfig.BASE_URL + signUpPath) {
                contentType(ContentType.Application.Json)
                setBody(signUpRequest)
            }
        }
        return result
    }
}