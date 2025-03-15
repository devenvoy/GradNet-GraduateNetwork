package com.sdjic.gradnet.data.network.entity.dto


import com.maxkeppeker.sheets.core.utils.JvmSerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    @SerialName("contact_us") val contactUs: String? = null,
    @SerialName("date") val date: String? = null,
    @SerialName("description") val description: String?,
    @SerialName("event_name") val eventName: String,
    @SerialName("event_pic") val eventPic: String? = null,
    @SerialName("event_title") val eventTitle: String,
    @SerialName("for_whom") val forWhom: String? = null,
    @SerialName("guest_names") val guestNames: String? = null,
    @SerialName("register_link") val registerLink: String? = null,
    @SerialName("remarks") val remarks: String? = null,
    @SerialName("time") val time: String? = null,
    @SerialName("event_type") val type: String,
    @SerialName("venue") val venue: String? = null
) : JvmSerializable