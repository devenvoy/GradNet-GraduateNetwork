package com.sdjic.gradnet.data.network.repo

import GradNet_GraduateNetwork.composeApp.BuildConfig
import androidx.compose.ui.graphics.ImageBitmap
import co.touchlab.kermit.Logger
import com.sdjic.gradnet.data.network.entity.UserProfileRequest
import com.sdjic.gradnet.data.network.entity.dto.URLDto
import com.sdjic.gradnet.data.network.entity.dto.VerifyUserResponse
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.entity.response.UserProfileResponse
import com.sdjic.gradnet.data.network.utils.BaseGateway
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.di.platform_di.toByteArray
import com.sdjic.gradnet.domain.repo.UserRepository
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicState
import com.sdjic.gradnet.presentation.screens.accountSetup.education.EducationState
import com.sdjic.gradnet.presentation.screens.accountSetup.profession.ProfessionState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class UserRepositoryImpl(httpClient: HttpClient) : UserRepository, BaseGateway(httpClient) {

    override suspend fun fetchProfile(token: String): Result<ServerResponse<UserProfileResponse>, ServerError> {
        return tryToExecute<ServerResponse<UserProfileResponse>> {
            get(BuildConfig.BASE_URL + "/fetch_profile") {
                header(HttpHeaders.Authorization, "Bearer $token")
                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun fetchUser(userId: String): Result<ServerResponse<UserProfileResponse>, ServerError> {
        return tryToExecute<ServerResponse<UserProfileResponse>> {
            get(BuildConfig.BASE_URL + "/fetch_profile/$userId") {
                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun updateUser(
        userRole: String,
        accessToken: String,
        basicState: BasicState,
        educationState: EducationState,
        professionState: ProfessionState
    ): Result<ServerResponse<UserProfileResponse>, ServerError> {
        return tryToExecute<ServerResponse<UserProfileResponse>>{
            post(BuildConfig.BASE_URL+"/profile"){
                header(HttpHeaders.Authorization, "Bearer $accessToken")
                contentType(ContentType.Application.Json)
                setBody(
                    createUserUpdateRequestBody(userRole,basicState,educationState,professionState)
                )
            }
        }
    }

    private fun createUserUpdateRequestBody(
        userRole: String,
        basicState: BasicState,
        educationState: EducationState,
        professionState: ProfessionState
    ): UserProfileRequest {
        return UserProfileRequest(
            role = userRole,
            name = basicState.nameField,
            about_self = basicState.aboutField,
            address = basicState.addressField,
            languages = educationState.languages,
            skills = educationState.skills,
            industry_type = "",
            employee = 0,
            website = "",
            department = "",
            designation = "",
            education = educationState.eductionList,
            experience = professionState.experienceList,
            urls = listOf(
                URLDto("linkedinUrl",professionState.linkedinUrl),
                URLDto("twitterUrl",professionState.twitterUrl),
                URLDto("githubUrl",professionState.githubUrl),
                *professionState.otherUrls.filter { it.isNotEmpty() }
                    .map { URLDto(it, it) }
                    .toTypedArray()
            ),
        )
    }

    override suspend fun updateUserImages(
        token: String,
        imageBitmap: ImageBitmap,
        type: String
    ): Result<ServerResponse<UserProfileResponse>, ServerError> {
        return withContext(Dispatchers.IO) {
            try {
                val byteArray = imageBitmap.toByteArray()
                Logger.e("Response Body: $token")
                val response: HttpResponse = client.submitFormWithBinaryData(
                    url = "${BuildConfig.BASE_URL}/image_upload?img_type=$type",
                    formData = formData {
                        append("file", byteArray, Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(HttpHeaders.ContentDisposition, "filename=image.jpg")
                        })
                    }
                ) {
                    header(HttpHeaders.Authorization, "Bearer $token")
                    onUpload { bytesSentTotal, contentLength ->
                        Logger.i("Response Body: Sent $bytesSentTotal bytes from $contentLength")
                    }
                }

                if (response.status.isSuccess()) {
                    val serverResponse = response.body<ServerResponse<UserProfileResponse>>()
                    Result.Success(serverResponse)
                } else {
                    Logger.e("Response Body: ${response.body<String>()}")
                    val serverError = response.body<ServerError>()
                    Result.Error(serverError)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(ServerError(500, null, e.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun checkUpdateToken(oldToken: String): Result<ServerResponse<Map<String,String>>, ServerError> {
        return tryToExecute {
            post(BuildConfig.BASE_URL + "/refresh-token/"){
                header(HttpHeaders.Authorization, "Bearer $oldToken")
                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun sendOtp(verificationId: String): Result<ServerResponse<VerifyUserResponse>, ServerError> {
        return tryToExecute<ServerResponse<VerifyUserResponse>> {
            post(BuildConfig.BASE_URL + "/verify-user?verify_id=${verificationId}") {
                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun verifyOtp(
        verificationId: String,
        otp: String,
        token:String
    ): Result<ServerResponse<UserProfileResponse>, ServerError> {
        return tryToExecute<ServerResponse<UserProfileResponse>> {
            post(BuildConfig.BASE_URL + "/verify-otp?verify_id=${verificationId}&otp=${otp}") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }
}