package com.sdjic.gradnet.presentation.core.model

import com.sdjic.gradnet.data.network.entity.UserDto

data class Post(
    val postId: Long,
    val user: UserDto,
    val content: String,
    val images: List<String>,
    val createdAt: String
)