package com.sdjic.gradnet.data.network.entity.dto

import com.maxkeppeker.sheets.core.utils.JvmSerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    @SerialName("id") val id: String,
    @SerialName("eventTitle") val eventTitle: String,
    @SerialName("eventName") val eventName: String,
    @SerialName("date") val date: String,
    @SerialName("time") val time: String,
    @SerialName("description") val description: String,
    @SerialName("venue") val venue: String,
    @SerialName("registerLink") val registerLink: String? = null,
    @SerialName("guestNames") val guestNames: String? = null,
    @SerialName("forWhom") val forWhom: String? = null,
    @SerialName("remarks") val remarks: String? = null,
    @SerialName("contactUs") val contactUs: String,
    @SerialName("eventPic") val eventPic: String? = null,
    @SerialName("eventType") val eventType: String,
    @SerialName("userId") val userId: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
) : JvmSerializable
