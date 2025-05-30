package com.sdjic.gradnet.data.network.repo

import GradNet_GraduateNetwork.composeApp.BuildConfig
import com.sdjic.gradnet.data.network.entity.response.JobsResponse
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.utils.BaseGateway
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.domain.repo.JobsRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.json.JsonElement

class JobsRepositoryImpl(httpClient: HttpClient) : JobsRepository, BaseGateway(httpClient) {

    override suspend fun getJobs(
        accessToken: String,
        query: String,
        page: Int,
        pageSize: Int,
        filter: List<String>
    ): Result<ServerResponse<JobsResponse>, ServerError> {
        return tryToExecute<ServerResponse<JobsResponse>> {
            get("${BuildConfig.BASE_URL}/jobs") {
                header("Authorization", "Bearer $accessToken")
                parameter("page", "$page")
                parameter("per_page", "$pageSize")
                filter.forEach {
                    parameter("work_mode", it.uppercase())
                }
                if (query.isNotEmpty()) {
                    parameter("search", query)
                }
            }
        }
    }

    override suspend fun getSavedJobs(
        page: Int,
        pageSize: Int,
        accessToken: String
    ): Result<ServerResponse<JobsResponse>, ServerError> {
        return tryToExecute<ServerResponse<JobsResponse>> {
            get("${BuildConfig.BASE_URL}/job/saved") {
                header("Authorization", "Bearer $accessToken")
                parameter("page", "$page")
                parameter("per_page", "$pageSize")
            }
        }
    }

    override suspend fun toggleSavedJob(
        jobId: String,
        accessToken: String
    ): Result<ServerResponse<JsonElement>, ServerError> {
        return tryToExecute {
            post("${BuildConfig.BASE_URL}/job/save") {
                headers {
                    append("Authorization", "Bearer $accessToken")
                    append("accept", "application/json")
                    append("Content-Type", "application/json")
                }
                setBody(mapOf("job_id" to jobId))
            }
        }
    }
}