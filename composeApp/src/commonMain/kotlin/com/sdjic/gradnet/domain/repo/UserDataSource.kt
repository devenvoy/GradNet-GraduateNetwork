package com.sdjic.gradnet.domain.repo

import com.sdjic.gradnet.data.local.entity.EducationTable
import com.sdjic.gradnet.data.local.entity.ExperienceTable
import com.sdjic.gradnet.data.local.entity.UrlTable

interface UserDataSource {
    suspend fun deleteAllEducations()
    suspend fun deleteAllExperiences()
    suspend fun deleteAllUrls()

    suspend fun upsertAllEducations(educations: List<EducationTable>)
    suspend fun upsertAllExperiences(experiences: List<ExperienceTable>)
    suspend fun upsertAllUrls(urls: List<UrlTable>)

    suspend fun getAllEducations(): List<EducationTable>
    suspend fun getAllExperiences(): List<ExperienceTable>
    suspend fun getAllUrls(): List<UrlTable>

    suspend fun getEducationById(id: Long): EducationTable?
    suspend fun getExperienceById(id: Long): ExperienceTable?
    suspend fun getUrlById(id: Long): UrlTable?

}