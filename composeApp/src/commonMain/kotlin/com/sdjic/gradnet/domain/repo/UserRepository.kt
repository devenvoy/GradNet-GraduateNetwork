package com.sdjic.gradnet.domain.repo

import com.sdjic.gradnet.data.network.entity.ServerError
import com.sdjic.gradnet.data.network.entity.ServerResponse
import com.sdjic.gradnet.data.network.entity.UserProfileResponse
import com.sdjic.gradnet.data.network.entity.dto.VerifyUserResponse
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.presentation.core.model.UserProfile

interface UserRepository {

    suspend fun sendOtp(verificationId:String) : Result<ServerResponse<VerifyUserResponse>,ServerError>
    suspend fun verifyOtp(verificationId:String,otp:String) : Result<ServerResponse<UserProfileResponse>,ServerError>
    suspend fun fetchUser(token:String): Result<UserProfile, ServerError>
    suspend fun updateUser()
    suspend fun updateUserProfileImage()
    suspend fun updateUserBackgroundImage()
}