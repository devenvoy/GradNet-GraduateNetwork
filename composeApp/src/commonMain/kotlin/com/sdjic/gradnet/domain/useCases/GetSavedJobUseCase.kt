package com.sdjic.gradnet.domain.useCases

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.sdjic.gradnet.data.paging.SavedJobPagingSource
import com.sdjic.gradnet.domain.repo.JobsRepository
import com.sdjic.gradnet.presentation.core.model.Job
import kotlinx.coroutines.flow.Flow

class GetSavedJobUseCase(private val jobsRepository: JobsRepository) {
    operator fun invoke(limit: Int, accessToken: String): Flow<PagingData<Job>> {
        return Pager(
            config = PagingConfig(pageSize = limit, initialLoadSize = limit),
            pagingSourceFactory = {
                val savedJobPagingSource = SavedJobPagingSource(jobsRepository)
                savedJobPagingSource.setToken(accessToken)
                savedJobPagingSource
            }
        ).flow
    }
}
