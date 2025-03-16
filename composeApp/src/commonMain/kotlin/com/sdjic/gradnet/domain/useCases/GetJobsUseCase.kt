package com.sdjic.gradnet.domain.useCases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sdjic.gradnet.data.network.entity.dto.JobDto
import com.sdjic.gradnet.data.network.utils.jobDtoToJob
import com.sdjic.gradnet.data.network.utils.map
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.data.paging.JobsPagingSource
import com.sdjic.gradnet.data.paging.PaginationItems
import com.sdjic.gradnet.data.paging.PostPagingSource
import com.sdjic.gradnet.domain.BasePagingSource
import com.sdjic.gradnet.domain.ResultPagingSource
import com.sdjic.gradnet.domain.repo.JobsRepository
import com.sdjic.gradnet.presentation.core.model.Job
import kotlinx.coroutines.flow.Flow

class GetJobsUseCase(private val jobsRepository: JobsRepository) {
    operator fun invoke(limit: Int, filters: List<String>): Flow<PagingData<Job>> {
        return Pager(
            config = PagingConfig(pageSize = limit, initialLoadSize = limit),
            pagingSourceFactory = {
                JobsPagingSource(jobsRepository, filters)
            }
        ).flow
    }
}
