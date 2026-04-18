package com.sdjic.gradnet.data.network.entity.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("userId") val userId: String,
    @SerialName("email") val email: String = "",
    @SerialName("firstName") val firstName: String? = null,
    @SerialName("lastName") val lastName: String? = null,
    @SerialName("displayName") val displayName: String? = null,
    @SerialName("avatarUrl") val avatarUrl: String? = null,
    @SerialName("accountId") val accountId: String? = null,
    @SerialName("accountType") val accountType: String? = null,
    @SerialName("roles") val roles: List<String> = emptyList(),
    @SerialName("permissions") val permissions: List<String> = emptyList(),
    @SerialName("isActive") val isActive: Boolean = true,
    @SerialName("isLocked") val isLocked: Boolean = false,
    @SerialName("createdAt") val createdAt: String? = null,
    @SerialName("lastLoginAt") val lastLoginAt: String? = null,
    @SerialName("emailVerified") val emailVerified: Boolean = false
)

@Serializable
data class UserAuthDto(
    @SerialName("userId") val userId: String?,
    @SerialName("email") val email: String = "",
    @SerialName("firstName") val firstName: String? = null,
    @SerialName("lastName") val lastName: String? = null,
    @SerialName("displayName") val displayName: String? = null,
    @SerialName("avatarUrl") val avatarUrl: String? = null,
    @SerialName("accountId") val accountId: String? = null,
    @SerialName("accountType") val accountType: String? = null,
    @SerialName("roles") val roles: List<String> = emptyList(),
    @SerialName("permissions") val permissions: List<String> = emptyList(),
    @SerialName("isActive") val isActive: Boolean = true,
    @SerialName("isLocked") val isLocked: Boolean = false,
    @SerialName("createdAt") val createdAt: String? = null,
    @SerialName("lastLoginAt") val lastLoginAt: String? = null,
    @SerialName("emailVerified") val emailVerified: Boolean = false
)
