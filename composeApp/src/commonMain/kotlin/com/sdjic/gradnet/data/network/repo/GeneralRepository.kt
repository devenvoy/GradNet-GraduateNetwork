package com.sdjic.gradnet.data.network.repo

import GradNet_GraduateNetwork.composeApp.BuildConfig
import androidx.compose.ui.graphics.ImageBitmap
import com.sdjic.gradnet.data.network.entity.dto.LostItemResponse
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.utils.BaseGateway
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.di.platform_di.toByteArray
import io.ktor.client.HttpClient
import io.ktor.client.content.ProgressListener
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray

class GeneralRepository(httpClient: HttpClient) : BaseGateway(httpClient) {

    suspend fun submitLostItemReport(
        image: ImageBitmap?,
        description: String,
        accessToken: String,
        listener: ProgressListener?
    ): Result<ServerResponse<JsonElement>, ServerError> {
        val imageUrls = mutableListOf<String>()
        
        // 1. Upload image if exists
        image?.let {
            val byteArray = it.toByteArray()
            val uploadResult = uploadImage(byteArray, accessToken)
            when (uploadResult) {
                is Result.Success -> {
                    uploadResult.data.value?.fileUrl?.let { url ->
                        imageUrls.add(url)
                    }
                }
                is Result.Error -> return Result.Error(uploadResult.error)
                Result.Loading -> {}
            }
        }

        // 2. Submit JSON as @RequestBody
        return tryToExecute<ServerResponse<JsonElement>> {
            post("${BuildConfig.BASE_URL}/lostfound/create") {
                header("Authorization", "Bearer $accessToken")
                contentType(ContentType.Application.Json)
                setBody(
                    buildJsonObject {
                        put("description", description)
                        putJsonArray("images") {
                            imageUrls.forEach { add(JsonPrimitive(it)) }
                        }
                    }
                )
                onUpload(listener)
            }
        }
    }


    suspend fun getLostItems(
        page: Int,
        perPage: Int
    ): Result<ServerResponse<LostItemResponse>,ServerError>{
        return tryToExecute {
            get(BuildConfig.BASE_URL+"/lostfound?page=$page&perPage=$perPage"){
                contentType(ContentType.Application.Json)
            }
        }
    }
}