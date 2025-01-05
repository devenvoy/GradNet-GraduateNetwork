package com.sdjic.gradnet.data.network.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    @SerialName("address")
    val address: String? ,
    @SerialName("email")
    val email: String? ,
    @SerialName("name")
    val name: String? ,
    @SerialName("phone_no")
    val phoneNo: String? ,
    @SerialName("user_id")
    val userId: String?
)
