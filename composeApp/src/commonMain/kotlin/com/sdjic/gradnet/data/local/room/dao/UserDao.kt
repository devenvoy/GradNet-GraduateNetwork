package com.sdjic.gradnet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.sdjic.gradnet.data.local.entity.EducationTable
import com.sdjic.gradnet.data.local.entity.ExperienceTable
import com.sdjic.gradnet.data.local.entity.UrlTable

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertAllEducationTable(educationTable: List<EducationTable>)

    @Query("SELECT * FROM education_table")
    suspend fun getAllEducationTable(): List<EducationTable>

    @Query("DELETE FROM education_table")
    suspend fun deleteAllEducationTable()

    @Query("SELECT * FROM education_table WHERE id = :id")
    suspend fun getEducationTableById(id: Long): EducationTable?

    @Upsert
    suspend fun upsertAllExperienceTable(experienceTable: List<ExperienceTable>)

    @Query("SELECT * FROM experience_table")
    suspend fun getAllExperienceTable(): List<ExperienceTable>

    @Query("DELETE FROM experience_table")
    suspend fun deleteAllExperienceTable()

    @Query("SELECT * FROM experience_table WHERE id = :id")
    suspend fun getExperienceTableById(id: Long): ExperienceTable?

    @Upsert
    suspend fun upsertAllUrlTable(urlTable: List<UrlTable>)

    @Query("SELECT * FROM url_table")
    suspend fun getAllUrlTable(): List<UrlTable>

    @Query("DELETE FROM url_table")
    suspend fun deleteAllUrlTable()

    @Query("SELECT * FROM url_table WHERE id = :id")
    suspend fun getUrlTableById(id: Long): UrlTable?

    @Transaction
    suspend fun deleteAndUpsertAllEducation(educationTable: List<EducationTable>) {
        deleteAllEducation()
        upsertAllEducationTable(educationTable)
    }

    @Transaction
    suspend fun deleteAndUpsertAllExperience(experienceTable: List<ExperienceTable>) {
        deleteAllExperience()
        upsertAllExperienceTable(experienceTable)
    }

    @Transaction
    suspend fun deleteAndUpsertAllUrl(urlTable: List<UrlTable>) {
        deleteAllUrl()
        upsertAllUrlTable(urlTable)
    }

    @Query("DELETE FROM education_table")
    suspend fun deleteAllEducation()

    @Query("DELETE FROM experience_table")
    suspend fun deleteAllExperience()

    @Query("DELETE FROM url_table")
    suspend fun deleteAllUrl()

    @Transaction
    suspend fun clearDatabase(): Boolean {
        return try {
            deleteAllUrl()
            deleteAllExperience()
            deleteAllEducation()
            true
        } catch (e: Exception) {
            false
        }
    }

}