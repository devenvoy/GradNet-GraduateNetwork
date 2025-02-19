package com.sdjic.domain.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reactions(
    @SerialName("dislikes") val dislikes: Int?,
    @SerialName("likes") val likes: Int?
)