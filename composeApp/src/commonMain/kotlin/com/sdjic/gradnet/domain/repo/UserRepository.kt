package com.sdjic.gradnet.domain.repo

import com.sdjic.gradnet.data.network.entity.ServerError
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.presentation.core.model.UserProfile

interface UserRepository {
    suspend fun fetchUser(token:String): Result<UserProfile, ServerError>
    suspend fun updateUser()
    suspend fun updateUserProfileImage()
    suspend fun updateUserBackgroundImage()
}