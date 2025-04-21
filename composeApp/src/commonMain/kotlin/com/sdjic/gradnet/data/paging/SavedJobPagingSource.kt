package com.sdjic.gradnet.data.paging

import com.sdjic.gradnet.data.network.utils.jobDtoToJob
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.BasePagingSource
import com.sdjic.gradnet.domain.repo.JobsRepository
import com.sdjic.gradnet.presentation.core.model.Job
import kotlin.properties.Delegates

class SavedJobPagingSource(private val jobsRepository: JobsRepository) : BasePagingSource<Job>() {

    private var accessToken by Delegates.notNull<String>()

    fun setToken(value: String) {
        accessToken = value
    }

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<Job> {
        var result = PaginationItems<Job>(
            items = emptyList(),
            page = 0,
            total = 0
        )
        jobsRepository.getSavedJobs(
            accessToken = accessToken,
            page = page,
            pageSize = limit
        ).onSuccess { r ->
            if (r.value != null) {
                result = PaginationItems(
                    items = r.value.jobDtos.mapNotNull { pd -> jobDtoToJob(pd) },
                    page = r.value.page,
                    total = r.value.perPage.toLong()
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
