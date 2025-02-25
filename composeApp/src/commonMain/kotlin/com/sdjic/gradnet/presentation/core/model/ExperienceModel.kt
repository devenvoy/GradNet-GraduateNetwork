package com.sdjic.gradnet.presentation.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ExperienceModel(
    val id : Long = 0,
    val title : String = "",
    val type:String? = null,
    val company:String? = null,
    val location: String? = null,
    val description: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
)