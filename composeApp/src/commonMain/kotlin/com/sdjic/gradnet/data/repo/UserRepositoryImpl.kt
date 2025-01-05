package com.sdjic.gradnet.data.repo

import com.sdjic.gradnet.data.network.entity.ServerError
import com.sdjic.gradnet.data.network.utils.BaseGateway
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.domain.repo.UserRepository
import com.sdjic.gradnet.presentation.core.model.UserProfile
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay

class UserRepositoryImpl(httpClient: HttpClient) : UserRepository ,BaseGateway(httpClient){

    override suspend fun fetchUser(token: String): Result<UserProfile, ServerError> {
        delay(2000L)
        return Result.Success(UserProfile())
    }

    override suspend fun updateUser() {}

    override suspend fun updateUserProfileImage() {}

    override suspend fun updateUserBackgroundImage() {}
}