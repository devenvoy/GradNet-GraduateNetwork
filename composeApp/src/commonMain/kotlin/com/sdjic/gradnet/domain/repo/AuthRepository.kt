package com.sdjic.gradnet.domain.repo

import com.sdjic.gradnet.data.network.entity.response.LoginResponse
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.entity.response.SignUpRequest
import com.sdjic.gradnet.data.network.entity.response.SignUpResponse
import com.sdjic.gradnet.data.network.utils.Result

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<ServerResponse<LoginResponse>, ServerError>
    suspend fun signUp(signUpRequest: SignUpRequest): Result<ServerResponse<SignUpResponse>, ServerError>
}