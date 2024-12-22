package com.sdjic.gradnet.data.repo

import com.sdjic.gradnet.data.local.entity.Test
import com.sdjic.gradnet.data.local.room.TestDao
import com.sdjic.gradnet.domain.repo.TestRepository
import kotlinx.coroutines.flow.Flow

class TestRepositoryImpl(val testDao: TestDao) : TestRepository {
    override suspend fun insertAll(tests: List<Test>) {
        testDao.insertAll(tests)
    }

    override suspend fun fetchFList(): Flow<List<Test>> = testDao.getAll()
}