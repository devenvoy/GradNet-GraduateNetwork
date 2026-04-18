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
    @SerialName("id") val id: String,
    @SerialName("description") val description: String? = null,
    @SerialName("location") val location: String? = null,
    @SerialName("images") val images: List<String>? = null,
    @SerialName("userId") val userId: String,
    @SerialName("userName") val userName: String? = null,
    @SerialName("userRole") val userRole: String? = null,
    @SerialName("userAvatar") val userAvatar: String? = null,
    @SerialName("likeCount") val likeCount: Int = 0,
    @SerialName("isLiked") val isLiked: Boolean = false,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
)

@Entity(tableName = "post_table")
data class PostTable(
    @PrimaryKey(autoGenerate = false)
    val id: String,
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
        return list?.let { Json.encodeToString(it) } ?: "[]"
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }
}

object PostMapper {

    fun mapDtoToTable(dto: PostDto): PostTable {
        return PostTable(
            id = dto.id,
            userId = dto.userId,
            userName = dto.userName,
            userProfilePic = dto.userAvatar,
            description = dto.description ?: "",
            location = dto.location,
            photos = dto.images,
            createdAt = dto.createdAt,
            likes = dto.likeCount,
            isLiked = dto.isLiked,
            userRole = dto.userRole
        )
    }

    fun mapTableToDto(table: PostTable): PostDto {
        return PostDto(
            id = table.id,
            userId = table.userId.orEmpty(),
            userName = table.userName,
            userRole = table.userRole,
            userAvatar = table.userProfilePic,
            description = table.description,
            location = table.location,
            images = table.photos,
            likeCount = table.likes,
            isLiked = table.isLiked,
            createdAt = table.createdAt,
            updatedAt = table.createdAt
        )
    }

    fun mapTableToPost(table: PostTable): Post {
        return Post(
            postId = table.id,
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
