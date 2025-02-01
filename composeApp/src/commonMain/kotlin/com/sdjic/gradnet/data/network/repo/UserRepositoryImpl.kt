package com.sdjic.gradnet.data.network.repo

import GradNet_GraduateNetwork.composeApp.BuildConfig
import com.sdjic.gradnet.data.network.entity.ServerError
import com.sdjic.gradnet.data.network.entity.ServerResponse
import com.sdjic.gradnet.data.network.entity.UserProfileResponse
import com.sdjic.gradnet.data.network.entity.dto.VerifyUserResponse
import com.sdjic.gradnet.data.network.utils.BaseGateway
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.domain.repo.UserRepository
import com.sdjic.gradnet.presentation.core.model.UserProfile
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.delay

class UserRepositoryImpl(httpClient: HttpClient) : UserRepository ,BaseGateway(httpClient){

    override suspend fun fetchUser(token: String): Result<UserProfile, ServerError> {
        delay(2000L)
        return Result.Success(UserProfile())
    }

    override suspend fun updateUser() {}

    override suspend fun updateUserProfileImage() {}

    override suspend fun updateUserBackgroundImage() {}

    override suspend fun sendOtp(verificationId: String): Result<ServerResponse<VerifyUserResponse>, ServerError> {
        return tryToExecute<ServerResponse<VerifyUserResponse>>{
            post(BuildConfig.BASE_URL+"/verify-user?verify_id=${verificationId}"){
                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun verifyOtp(
        verificationId: String,
        otp: String
    ): Result<ServerResponse<UserProfileResponse>, ServerError> {
        return tryToExecute<ServerResponse<UserProfileResponse>>{
            post(BuildConfig.BASE_URL+"/verify-otp?verify_id=${verificationId}&otp=${otp}"){
                contentType(ContentType.Application.Json)
            }
        }
    }
}