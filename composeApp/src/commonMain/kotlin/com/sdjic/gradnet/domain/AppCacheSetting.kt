package com.sdjic.gradnet.domain

import com.sdjic.gradnet.presentation.core.model.UserProfile

interface AppCacheSetting {

    var accessToken: String

    val isLoggedIn: Boolean

    var userId: String

    var isVerified: Boolean

    var firstInitialized: Boolean

    var userRole: String

    fun saveUserProfile(userProfile: UserProfile)

    fun getUserProfile(): UserProfile

    fun logout(callBack: () -> Unit)
}