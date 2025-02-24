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
) {
    @SerialName("user_id")
    var userId: String? = null

    @SerialName("user_name")
    var userName: String? = null

    @SerialName("user_profile_pic") var userProfilePic: String? = null

    @SerialName("user_role") var userRole: String? = null


    constructor(
        postId: String,
        description: String,
        location: String?,
        photos: List<String?>?,
        createdAt: String,
        userId: String?,
        userName: String?,
        userProfilePic: String?,
        userRole: String?
    ) : this(postId, description, location, photos, createdAt){
        this.userId = userId
        this.userName = userName
        this.userProfilePic = userProfilePic
        this.userRole = userRole
    }
}