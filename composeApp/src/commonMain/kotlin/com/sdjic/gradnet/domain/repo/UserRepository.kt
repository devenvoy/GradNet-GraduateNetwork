package com.sdjic.gradnet.domain.repo

import com.sdjic.gradnet.data.network.entity.ServerError
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.presentation.core.model.BaseUser

interface UserRepository {
    suspend fun fetchUser(token:String): Result<BaseUser, ServerError>
    suspend fun updateUser()
    suspend fun updateUserProfileImage()
    suspend fun updateUserBackgroundImage()
}