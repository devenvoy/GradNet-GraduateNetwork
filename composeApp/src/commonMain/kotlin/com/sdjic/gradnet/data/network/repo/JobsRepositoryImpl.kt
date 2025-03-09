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
import io.ktor.client.request.parameter

class JobsRepositoryImpl(httpClient: HttpClient): JobsRepository , BaseGateway(httpClient) {

    override suspend fun getJobs(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<ServerResponse<JobsResponse>, ServerError> {
        return tryToExecute<ServerResponse<JobsResponse>> {
            get("${BuildConfig.BASE_URL}/jobs") {
                parameter("page", "$page")
                parameter("per_page", "$pageSize")
            }
        }
    }

}