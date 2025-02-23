package com.sdjic.gradnet.data.network.entity.dto


import com.maxkeppeker.sheets.core.utils.JvmSerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    @SerialName("contact_us") val contactUs: String?,
    @SerialName("date") val date: String?,
    @SerialName("description") val description: String?,
    @SerialName("event_name") val eventName: String?,
    @SerialName("event_pic") val eventPic: String?,
    @SerialName("event_title") val eventTitle: String?,
    @SerialName("for_whom") val forWhom: String?,
    @SerialName("guest_names") val guestNames: String?,
    @SerialName("register_link") val registerLink: String?,
    @SerialName("remarks") val remarks: String?,
    @SerialName("time") val time: String?,
    @SerialName("venue") val venue: String?
) : JvmSerializable