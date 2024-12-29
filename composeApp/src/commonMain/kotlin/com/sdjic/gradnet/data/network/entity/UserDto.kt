package com.sdjic.gradnet.data.network.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("address")
    val address: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("phone_no")
    val phoneNo: Long?,
    @SerialName("updated_at")
    val updatedAt: String?
)