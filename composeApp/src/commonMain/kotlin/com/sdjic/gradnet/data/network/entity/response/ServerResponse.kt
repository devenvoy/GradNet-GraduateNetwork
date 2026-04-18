package com.sdjic.gradnet.data.network.entity.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class  ServerResponse<T>(
    @SerialName("code")
    val code: Int? = null,
    @SerialName("data")
    val value: T? = null,
    @SerialName("message")
    val detail: String = "",
    @SerialName("success")
    val status: Boolean
)

@Serializable
data class  ServerError(
    @SerialName("code")
    val code: Int? = null,
    @SerialName("data")
    val value: Map<String, String>? = null,
    @SerialName("message")
    val detail: String = "",
    @SerialName("status")
    val status: Boolean = false
): com.sdjic.gradnet.data.network.utils.Error
//
//@Serializable
//data class ServerResponse<T>(
//    @SerialName("success") val success: Boolean,
//    @SerialName("message") val message: String = "",
//    @SerialName("data") val data: T? = null
//)
//
//@Serializable
//data class ServerError(
//    @SerialName("success") val success: Boolean = false,
//    @SerialName("message") val message: String = "",
//    @SerialName("data") val data: Map<String, String>? = null
//): com.sdjic.gradnet.data.network.utils.Error