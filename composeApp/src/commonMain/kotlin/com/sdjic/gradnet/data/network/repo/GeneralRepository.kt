package com.sdjic.gradnet.data.network.repo

import GradNet_GraduateNetwork.composeApp.BuildConfig
import androidx.compose.ui.graphics.ImageBitmap
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.utils.BaseGateway
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.di.platform_di.toByteArray
import io.ktor.client.HttpClient
import io.ktor.client.content.ProgressListener
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonElement

class GeneralRepository(httpClient: HttpClient) : BaseGateway(httpClient) {

    suspend fun submitLostItemReport(
        image: ImageBitmap?,
        description: String,
        accessToken : String,
        listener: ProgressListener?
    ): Result<ServerResponse<JsonElement>, ServerError> {
        return tryToExecute<ServerResponse<JsonElement>> {
            post("${BuildConfig.BASE_URL}/lostfound/create") {
                header("Authorization", "Bearer $accessToken")
                contentType(ContentType.MultiPart.FormData)
                val byteArray = image?.toByteArray()
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("description", description)
                            byteArray?.let {
                                append(
                                    "files",
                                    byteArray,
                                    Headers.build {
                                        append(HttpHeaders.ContentDisposition, "form-data; name=\"files\"; filename=\"image.jpg\"")
                                        append(HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
                                    }
                                )
                            }
                        }
                    )
                )
                onUpload(listener)
            }
        }
    }
}