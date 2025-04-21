package com.sdjic.gradnet.domain.repo

import com.sdjic.gradnet.data.network.entity.response.JobsResponse
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.utils.Result
import kotlinx.serialization.json.JsonElement

interface JobsRepository {
    suspend fun getJobs(
        accessToken: String,
        query: String,
        page: Int,
        pageSize: Int,
        filter: List<String>
    ): Result<ServerResponse<JobsResponse>, ServerError>

    suspend fun getSavedJobs(
        page: Int,
        pageSize: Int,
        accessToken: String
    ): Result<ServerResponse<JobsResponse>, ServerError>

    suspend fun toggleSavedJob(
        jobId: String,
        accessToken: String
    ): Result<ServerResponse<JsonElement>, ServerError>

}