package com.sdjic.gradnet.domain.useCases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sdjic.gradnet.data.paging.JobsPagingSource
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.JobsRepository
import com.sdjic.gradnet.presentation.core.model.Job
import kotlinx.coroutines.flow.Flow

class GetJobsUseCase(
    private val jobsRepository: JobsRepository,
    private val prefs: AppCacheSetting
) {
    operator fun invoke(limit: Int, filters: List<String>, value: String): Flow<PagingData<Job>> {
        return Pager(
            config = PagingConfig(pageSize = limit, initialLoadSize = limit),
            pagingSourceFactory = {
                val source = JobsPagingSource(jobsRepository, prefs.accessToken.toString(), filters)
                source.intiQuery(value)
                source
            }
        ).flow
    }
}
