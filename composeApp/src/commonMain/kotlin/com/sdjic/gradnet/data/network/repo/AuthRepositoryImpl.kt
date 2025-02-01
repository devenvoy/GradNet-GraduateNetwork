package com.sdjic.gradnet.data.network.repo

import GradNet_GraduateNetwork.composeApp.BuildConfig
import com.sdjic.gradnet.data.network.entity.response.LoginResponse
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.entity.response.SignUpRequest
import com.sdjic.gradnet.data.network.entity.response.SignUpResponse
import com.sdjic.gradnet.data.network.utils.BaseGateway
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.domain.repo.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthRepositoryImpl(httpClient: HttpClient) : AuthRepository, BaseGateway(httpClient) {


    override suspend fun login(
        email: String,
        password: String
    ): Result<ServerResponse<LoginResponse>, ServerError> {
        val result = tryToExecute<ServerResponse<LoginResponse>> {
            post(BuildConfig.BASE_URL + "/login") {
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
            post(BuildConfig.BASE_URL + "/signup") {
                contentType(ContentType.Application.Json)
                setBody(signUpRequest)
            }
        }
        return result
    }
}