package com.sdjic.gradnet.presentation.core.model

data class Post(
    val postId: String,
    val userId: String,
    val userName: String,
    val userImage: String,
    val content: String,
    val likesCount: Int,
    val images: List<String>,
    val createdAt: String
)