package com.sdjic.gradnet.data.network.entity.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobDto(
    @SerialName("id") val id: String,
    @SerialName("jobTitle") val jobTitle: String? = null,
    @SerialName("companyName") val companyName: String? = null,
    @SerialName("workMode") val workMode: String? = null,
    @SerialName("jobLocation") val jobLocation: String? = null,
    @SerialName("jobOverview") val jobOverview: String? = null,
    @SerialName("salary") val salary: String? = null,
    @SerialName("skills") val skills: List<String>? = null,
    @SerialName("requirements") val requirements: List<String>? = null,
    @SerialName("benefits") val benefits: List<String>? = null,
    @SerialName("applyLink") val applyLink: String? = null,
    @SerialName("companyLogo") val companyLogo: String? = null,
    @SerialName("userId") val userId: String,
    @SerialName("isSaved") val isSaved: Boolean = false,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
)
