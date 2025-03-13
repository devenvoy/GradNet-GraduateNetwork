package com.sdjic.gradnet.domain.repo

import com.sdjic.gradnet.data.network.entity.response.ForgotPasswordResponse
import com.sdjic.gradnet.data.network.entity.response.LoginResponse
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.entity.response.SignUpRequest
import com.sdjic.gradnet.data.network.entity.response.SignUpResponse
import com.sdjic.gradnet.data.network.utils.Result
import kotlinx.serialization.json.JsonElement

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<ServerResponse<LoginResponse>, ServerError>
    suspend fun signUp(signUpRequest: SignUpRequest): Result<ServerResponse<SignUpResponse>, ServerError>
    suspend fun updatePassword(accessToken : String,oldPassword: String, newPassword: String): Result<ServerResponse<JsonElement>, ServerError>
    suspend fun forgotPassword(email: String): Result<ForgotPasswordResponse, ServerError>
}