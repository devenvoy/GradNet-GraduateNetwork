package com.sdjic.domain.repo

import com.sdjic.commons.model.ServerError
import com.sdjic.commons.model.ServerResponse
import com.sdjic.commons.utils.Result
import com.sdjic.domain.model.response.UserProfileResponse
import com.sdjic.domain.model.response.VerifyUserResponse

interface UserRepository {
    suspend fun sendOtp(verificationId: String): Result<ServerResponse<VerifyUserResponse>, ServerError>

    suspend fun verifyOtp(
        verificationId: String,
        otp: String,
        token: String
    ): Result<ServerResponse<UserProfileResponse>, ServerError>

    suspend fun fetchUser(token: String): Result<ServerResponse<UserProfileResponse>, ServerError>

    suspend fun updateUser()

    suspend fun updateUserImages(
        token: String,
        byteArray: ByteArray,
        type: String
    ): Result<ServerResponse<UserProfileResponse>, ServerError>
}