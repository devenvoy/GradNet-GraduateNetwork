package com.sdjic.gradnet.data.network.entity.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobDto(
    @SerialName("applylink") val applylink: String?,
    @SerialName("benefits") val benefits: List<String>?,
    @SerialName("company_logo") val companyLogo: String?,
    @SerialName("company_name") val companyName: String?,
    @SerialName("created_at") val createdAt: String?,
    @SerialName("industry") val industry: String?,
    @SerialName("job_id") val jobId: String?,
    @SerialName("job_location") val jobLocation: String?,
    @SerialName("job_overview") val jobOverview: String?,
    @SerialName("job_title") val jobTitle: String?,
    @SerialName("privacy") val privacy: String?,
    @SerialName("requirements") val requirements: List<String>?,
    @SerialName("salary") val salary: String?,
    @SerialName("skills") val skills: List<String>?,
    @SerialName("updated_at") val updatedAt: String?,
    @SerialName("work_mode") val workMode: String?
)