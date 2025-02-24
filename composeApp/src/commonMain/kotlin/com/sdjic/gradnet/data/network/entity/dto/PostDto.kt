package com.sdjic.gradnet.data.network.entity.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    @SerialName("post_id") val postId: String,
    @SerialName("description") val description: String,
    @SerialName("location") val location: String?,
    @SerialName("photos") val photos: List<String?>?,
    @SerialName("created_at") val createdAt: String,
    @SerialName("user_id") val userId: String? = null,
    @SerialName("user_name") val userName: String,
    @SerialName("user_profile_pic") val userProfilePic: String?,
    @SerialName("user_role") val userRole: String?
)