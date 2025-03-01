package com.sdjic.gradnet.data.network.entity.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    @SerialName("post_id") val postId: String,
    @SerialName("user_id")    var userId: String? = null,
    @SerialName("user_name")    var userName: String? = null,
    @SerialName("user_profile_pic") var userProfilePic: String? = null,
    @SerialName("description") val description: String,
    @SerialName("location") val location: String?,
    @SerialName("photos") val photos: List<String?>?,
    @SerialName("created_at") val createdAt: String,
    @SerialName("likes") val likes : Int = 0,
    @SerialName("liked_by") val isLiked : Boolean,
    @SerialName("user_role") var userRole: String? = null
)