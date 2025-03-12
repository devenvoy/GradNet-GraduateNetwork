package com.sdjic.gradnet.domain.useCases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sdjic.gradnet.data.network.entity.dto.JobDto
import com.sdjic.gradnet.data.network.utils.map
import com.sdjic.gradnet.domain.ResultPagingSource
import com.sdjic.gradnet.domain.repo.JobsRepository
import com.sdjic.gradnet.presentation.core.model.Job
import kotlinx.coroutines.flow.Flow

class GetJobsUseCase(private val jobsRepository: JobsRepository) {
    operator fun invoke(limit: Int): Flow<PagingData<Job>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            pagingSourceFactory = {
                ResultPagingSource { page, pageSize ->
                    jobsRepository.getJobs(
                        page = page,
                        pageSize = pageSize,
                        query = ""
                    ).map { result ->
                        result.value?.jobDtos?.mapNotNull(::jobDtoToJob) ?: emptyList()
                    }
                }
            }
        ).flow
    }

    private fun jobDtoToJob(jobDto: JobDto?) = jobDto?.let {
        Job(
            id = it.jobId.toString(),
            title = it.jobTitle.orEmpty(),
            company = it.companyName.orEmpty(),
            jobType = it.workMode?.lowercase()?.replaceFirstChar { char -> char.uppercase() },
            location = it.jobLocation.orEmpty(),
            description = it.jobOverview.orEmpty(),
            salary = it.salary,
            requirements = it.requirements.orEmpty(),
            benefits = it.benefits.orEmpty(),
            postedDate = it.createdAt.toString(),
            applyLink = it.applylink.orEmpty(),
            companyLogo = it.companyLogo,
            category = it.industry.orEmpty(),
            skills = it.skills.orEmpty()
        )
    }
}
