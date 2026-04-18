package com.sdjic.gradnet.data.network.entity.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaUploadDto(
    @SerialName("id") val id: String,
    @SerialName("fileName") val fileName: String? = null,
    @SerialName("fileUrl") val fileUrl: String,
    @SerialName("fileType") val fileType: String? = null,
    @SerialName("imgType") val imgType: String,
    @SerialName("createdAt") val createdAt: String? = null
)
