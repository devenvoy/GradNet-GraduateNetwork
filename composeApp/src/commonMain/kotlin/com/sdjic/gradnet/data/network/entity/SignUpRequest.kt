package com.sdjic.gradnet.data.network.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("address")
    val address: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("password")
    val password: String?,
    @SerialName("phone_no")
    val phoneNo: String?
)