package com.sdjic.gradnet.presentation.core.model

import com.sdjic.domain.model.response.UserDto

data class Post(
    val postId: Long,
    val user: UserDto,
    val content: String,
    val likesCount:Int,
    val images: List<String>,
    val createdAt: String
)