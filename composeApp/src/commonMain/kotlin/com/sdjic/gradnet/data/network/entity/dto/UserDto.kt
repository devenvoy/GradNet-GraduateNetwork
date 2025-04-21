package com.sdjic.gradnet.data.network.entity.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id") val userId: String,
    @SerialName("name") val username: String = "",
    @SerialName("email") val email: String = "",
    @SerialName("role") val userType: String = "",
    @SerialName("profile_pic") val profilePic: String? = "",
    @SerialName("created_at") val createdAt: String = "",
    @SerialName("updated_at") val updatedAt: String = "",
)

@Serializable
data class UserAuthDto(
    @SerialName("id") val userId: String?,
    @SerialName("name") val username: String = "",
    @SerialName("email") val email: String = "",
    @SerialName("role") val userType: String = "",
    @SerialName("verified") val isVerified: Boolean = false,
    @SerialName("createdAt") val createdAt: String = "",
    @SerialName("updatedAt") val updatedAt: String = "",
)