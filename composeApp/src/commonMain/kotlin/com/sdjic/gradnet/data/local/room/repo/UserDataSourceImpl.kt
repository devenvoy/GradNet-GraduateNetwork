package com.sdjic.gradnet.data.local.room.repo

import com.sdjic.gradnet.data.local.entity.EducationTable
import com.sdjic.gradnet.data.local.entity.ExperienceTable
import com.sdjic.gradnet.data.local.entity.UrlTable
import com.sdjic.gradnet.data.local.room.dao.UserDao
import com.sdjic.gradnet.domain.repo.UserDataSource

class UserDataSourceImpl(private val userDao: UserDao) : UserDataSource {

    override suspend fun deleteAllEducations() {
        userDao.deleteAllEducationTable()
    }

    override suspend fun deleteAllExperiences() {
        userDao.deleteAllExperienceTable()
    }

    override suspend fun deleteAllUrls() {
        userDao.deleteAllUrlTable()
    }

    override suspend fun upsertAllEducations(educations: List<EducationTable>) {
        userDao.deleteAndUpsertAllEducation(educations)
    }

    override suspend fun upsertAllExperiences(experiences: List<ExperienceTable>) {
        userDao.deleteAndUpsertAllExperience(experiences)
    }

    override suspend fun upsertAllUrls(urls: List<UrlTable>) {
        userDao.deleteAndUpsertAllUrl(urls)
    }

    override suspend fun getAllEducations(): List<EducationTable> {
        return userDao.getAllEducationTable()
    }

    override suspend fun getAllExperiences(): List<ExperienceTable> {
        return userDao.getAllExperienceTable()
    }

    override suspend fun getAllUrls(): List<UrlTable> {
        return userDao.getAllUrlTable()
    }

    override suspend fun getEducationById(id: Long): EducationTable? {
        return userDao.getEducationTableById(id)
    }

    override suspend fun getExperienceById(id: Long): ExperienceTable? {
        return userDao.getExperienceTableById(id)
    }

    override suspend fun getUrlById(id: Long): UrlTable? {
        return userDao.getUrlTableById(id)
    }

    override suspend fun clearDatabase(): Boolean {
        return userDao.clearDatabase()
    }
}