package com.sdjic.gradnet.data.paging

import com.sdjic.gradnet.data.network.utils.jobDtoToJob
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.BasePagingSource
import com.sdjic.gradnet.domain.repo.JobsRepository
import com.sdjic.gradnet.presentation.core.model.Job

class JobsPagingSource(
    private val jobsRepository: JobsRepository,
    private val selectedFilter: List<String> = emptyList() // Default empty list
) : BasePagingSource<Job>() {

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<Job> {
        var result = PaginationItems<Job>(
            items = emptyList(),
            page = 0,
            total = 0
        )
        jobsRepository.getJobs(
            page = page,
            query = "",
            pageSize = limit,
            filter = selectedFilter
        ).onSuccess { r ->
            r.value?.let { response ->
                result = PaginationItems(
                    items = response.jobDtos.mapNotNull { jd -> jobDtoToJob(jd) },
                    page = response.page,
                    total = response.perPage.toLong()
                )
            }
        }.onError {
            result = PaginationItems(
                items = emptyList(),
                page = 0,
                total = 0
            )
        }
        return result
    }
}
