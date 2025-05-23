package com.sdjic.gradnet.domain.repo

import androidx.compose.ui.graphics.ImageBitmap
import com.sdjic.gradnet.data.network.entity.dto.PostDto
import com.sdjic.gradnet.data.network.entity.dto.VerifyUserResponse
import com.sdjic.gradnet.data.network.entity.response.SearchProfileResponse
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.entity.response.UserProfileResponse
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicState
import com.sdjic.gradnet.presentation.screens.accountSetup.education.EducationState
import com.sdjic.gradnet.presentation.screens.accountSetup.profession.ProfessionState
import kotlinx.serialization.json.JsonElement

interface UserRepository {
    suspend fun sendOtp(
        verificationId: String,
        token: String
    ): Result<ServerResponse<VerifyUserResponse>, ServerError>

    suspend fun verifyOtp(
        verificationId: String,
        otp: String,
        token: String
    ): Result<ServerResponse<UserProfileResponse>, ServerError>

    suspend fun fetchProfile(token: String): Result<ServerResponse<UserProfileResponse>, ServerError>

    suspend fun fetchUser(userId: String): Result<ServerResponse<UserProfileResponse>, ServerError>

    suspend fun updateUser(
        userRole: String,
        accessToken: String,
        basicState: BasicState,
        educationState: EducationState,
        professionState: ProfessionState
    ): Result<ServerResponse<UserProfileResponse>, ServerError>

    suspend fun updateUserImages(
        token: String,
        imageBitmap: ImageBitmap,
        type: String
    ): Result<ServerResponse<UserProfileResponse>, ServerError>

    suspend fun checkUpdateToken(
        oldToken: String
    ): Result<ServerResponse<Map<String, String>>, ServerError>

    suspend fun fetchUserPosts(userId: String): Result<ServerResponse<List<PostDto>>, ServerError>

    suspend fun getUsers(
        page: Int,
        query: String,
        pageSize: Int,
    ): Result<ServerResponse<SearchProfileResponse>, ServerError>

    suspend fun deletePost(postId: String, token: String): Result<ServerResponse<JsonElement>, ServerError>
}