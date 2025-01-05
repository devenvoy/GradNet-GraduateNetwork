package com.sdjic.gradnet.presentation.core.model

data class ExperienceModel(
    val id : Int = 0,
    val title : String = "",
    val type:String? = null,
    val company:String? = null,
    val location: String? = null,
    val description: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
)