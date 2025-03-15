package com.sdjic.gradnet.data.network.entity.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.sdjic.gradnet.presentation.core.model.Post
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class PostDto(
    @SerialName("post_id") val postId: String,
    @SerialName("user_id") var userId: String? = null,
    @SerialName("user_name") var userName: String? = null,
    @SerialName("user_profile_pic") var userProfilePic: String? = null,
    @SerialName("description") val description: String,
    @SerialName("location") val location: String?,
    @SerialName("photos") val photos: List<String?>?,
    @SerialName("created_at") val createdAt: String,
    @SerialName("likes") val likes: Int = 0,
    @SerialName("liked_by") val isLiked: Boolean,
    @SerialName("user_role") var userRole: String? = null
)


@Entity(tableName = "post_table")
data class PostTable(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val postId: String,
    var userId: String? = null,
    var userName: String? = null,
    var userProfilePic: String? = null,
    val description: String,
    val location: String?,
    val photos: List<String>?,
    val createdAt: String,
    val likes: Int = 0,
    val isLiked: Boolean,
    var userRole: String? = null
)


object Converters {

    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return list?.let { Json.encodeToString(it) } ?: "[]" // Convert List<String> to JSON String
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return try {
            Json.decodeFromString(value) // Convert JSON String back to List<String>
        } catch (e: Exception) {
            emptyList()
        }
    }
}


object PostMapper {

    fun mapDtoToTable(dto: PostDto): PostTable {
        return PostTable(
            id = dto.postId, // Assuming postId is unique
            postId = dto.postId,
            userId = dto.userId,
            userName = dto.userName,
            userProfilePic = dto.userProfilePic,
            description = dto.description,
            location = dto.location,
            photos = dto.photos?.filterNotNull() ?: emptyList(), // Ensuring null safety
            createdAt = dto.createdAt,
            likes = dto.likes,
            isLiked = dto.isLiked,
            userRole = dto.userRole
        )
    }

    fun mapTableToDto(table: PostTable): PostDto {
        return PostDto(
            postId = table.postId,
            userId = table.userId,
            userName = table.userName,
            userProfilePic = table.userProfilePic,
            description = table.description,
            location = table.location,
            photos = table.photos ?: emptyList(), // Ensuring null safety
            createdAt = table.createdAt,
            likes = table.likes,
            isLiked = table.isLiked,
            userRole = table.userRole
        )
    }

    fun mapTableToPost(table: PostTable): Post {
        return Post(
            postId = table.postId,
            userId = table.userId.orEmpty(),
            userName = table.userName.orEmpty(),
            userImage = table.userProfilePic.orEmpty(),
            userRole = UserRole.getUserRole(table.userRole ?: "") ?: UserRole.Alumni,
            content = table.description,
            likesCount = table.likes,
            liked = table.isLiked,
            images = table.photos ?: emptyList(),
            location = table.location.orEmpty(),
            createdAt = table.createdAt
        )
    }
}
