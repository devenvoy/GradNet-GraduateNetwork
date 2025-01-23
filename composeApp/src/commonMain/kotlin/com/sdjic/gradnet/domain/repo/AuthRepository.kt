package com.sdjic.gradnet.domain.repo

import com.sdjic.gradnet.data.network.entity.LoginResponse
import com.sdjic.gradnet.data.network.entity.ServerError
import com.sdjic.gradnet.data.network.entity.ServerResponse
import com.sdjic.gradnet.data.network.entity.SignUpRequest
import com.sdjic.gradnet.data.network.entity.SignUpResponse
import com.sdjic.gradnet.data.network.entity.UserProfileResponse
import com.sdjic.gradnet.data.network.entity.dto.VerifyUserResponse
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.presentation.core.model.UserProfile

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<ServerResponse<LoginResponse>, ServerError>
    suspend fun signUp(signUpRequest: SignUpRequest): Result<ServerResponse<SignUpResponse>, ServerError>
    suspend fun verifyUser(verificationId:String) : Result<ServerResponse<VerifyUserResponse>,ServerError>
    suspend fun verifiedOtp(verificationId:String,otp:String) : Result<ServerResponse<UserProfileResponse>,ServerError>
}