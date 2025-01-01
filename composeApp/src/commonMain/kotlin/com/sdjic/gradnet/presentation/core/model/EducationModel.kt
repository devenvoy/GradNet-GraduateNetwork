package com.sdjic.gradnet.presentation.core.model

data class EducationModel(
    val id : Int = 0,
    val schoolName : String,
    val degree:String? = null,
    val field:String? = null,
    val location: String? = null,
    val description: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
)
