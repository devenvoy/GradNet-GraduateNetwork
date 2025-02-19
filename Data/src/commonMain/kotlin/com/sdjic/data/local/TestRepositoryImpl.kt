package com.sdjic.data.local

import com.sdjic.data.local.entity.Test
import com.sdjic.data.local.room.TestDao
import kotlinx.coroutines.flow.Flow

class TestRepositoryImpl(val testDao: TestDao)  {
    suspend fun insertAll(tests: List<Test>) {
        testDao.insertAll(tests)
    }

    suspend fun fetchFList(): Flow<List<Test>> = testDao.getAll()
}