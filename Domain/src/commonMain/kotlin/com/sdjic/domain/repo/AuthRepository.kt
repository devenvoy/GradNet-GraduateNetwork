package com.sdjic.domain.repo

import com.sdjic.commons.model.ServerError
import com.sdjic.commons.model.ServerResponse
import com.sdjic.commons.utils.Result
import com.sdjic.domain.model.SignUpRequest
import com.sdjic.domain.model.response.LoginResponse

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<ServerResponse<LoginResponse>, ServerError>
    suspend fun signUp(signUpRequest: SignUpRequest): Result<ServerResponse<LoginResponse>, ServerError>
}