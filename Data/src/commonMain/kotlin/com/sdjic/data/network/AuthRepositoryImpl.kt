package com.sdjic.data.network

import com.sdjic.commons.model.ServerError
import com.sdjic.commons.model.ServerResponse
import com.sdjic.commons.utils.BaseGateway
import com.sdjic.commons.utils.Result
import com.sdjic.domain.model.SignUpRequest
import com.sdjic.domain.model.response.LoginResponse
import com.sdjic.domain.repo.AuthRepository
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
            post(Config.getBaseUrl() + "/login") {
                contentType(ContentType.Application.Json)
                setBody(
                    """{
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
    ): Result<ServerResponse<LoginResponse>, ServerError> {
        val result = tryToExecute<ServerResponse<LoginResponse>> {
            post(Config.getBaseUrl() + "/signup") {
                contentType(ContentType.Application.Json)
                setBody(signUpRequest)
            }
        }
        return result
    }
}