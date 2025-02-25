package com.sdjic.gradnet.domain.repo

import androidx.compose.ui.graphics.ImageBitmap
import com.sdjic.gradnet.data.network.entity.dto.VerifyUserResponse
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.entity.response.UserProfileResponse
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicState
import com.sdjic.gradnet.presentation.screens.accountSetup.education.EducationState
import com.sdjic.gradnet.presentation.screens.accountSetup.profession.ProfessionState

interface UserRepository {
    suspend fun sendOtp(verificationId: String): Result<ServerResponse<VerifyUserResponse>, ServerError>

    suspend fun verifyOtp(
        verificationId: String,
        otp: String,
        token:String
    ): Result<ServerResponse<UserProfileResponse>, ServerError>

    suspend fun fetchUser(token: String): Result<ServerResponse<UserProfileResponse>, ServerError>

    suspend fun updateUser(
        userRole: String,
        accessToken: String,
        basicState: BasicState,
        educationState: EducationState,
        professionState: ProfessionState
    ): Result<ServerResponse<UserProfileResponse>,ServerError>

    suspend fun updateUserImages(
        token: String,
        imageBitmap: ImageBitmap,
        type: String
    ): Result<ServerResponse<UserProfileResponse>, ServerError>
}