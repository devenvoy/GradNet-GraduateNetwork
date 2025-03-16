package com.sdjic.gradnet.domain.repo

import com.sdjic.gradnet.data.network.entity.response.JobsResponse
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.utils.Result

interface JobsRepository {
    suspend fun getJobs(
        query: String,
        page: Int,
        pageSize: Int,
        filter: List<String>
    ): Result<ServerResponse<JobsResponse>, ServerError>
}