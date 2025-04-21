package com.sdjic.gradnet.domain.repo

import com.sdjic.gradnet.data.local.entity.Test
import kotlinx.coroutines.flow.Flow

interface TestRepository {
    suspend fun insertAll(tests: List<Test>)
    suspend fun fetchFList(): Flow<List<Test>>
}