package com.sdjic.gradnet.presentation.core.model

import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole

data class Post(
    val postId: String,
    val userId: String,
    val userName: String,
    val userImage: String,
    val content: String,
    val location : String,
    val userRole: UserRole,
    val likesCount: Int,
    val liked: Boolean = false,
    val images: List<String>,
    val createdAt: String
)