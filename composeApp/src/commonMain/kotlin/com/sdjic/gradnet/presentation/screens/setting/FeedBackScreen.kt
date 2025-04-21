@file:OptIn(ExperimentalMaterial3Api::class)

package com.sdjic.gradnet.presentation.screens.setting

import GradNet_GraduateNetwork.composeApp.BuildConfig
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dokar.sonner.ToastType
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.di.platform_di.getContactsUtil
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.images.LongBackButton
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputArea
import com.sdjic.gradnet.presentation.core.model.ToastMessage
import com.sdjic.gradnet.presentation.helper.ToastManager
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.koin.compose.koinInject

class FeedBackScreen : Screen {
    @Composable
    override fun Content() {
        FeedBackScreenContent()
    }

    @Composable
    fun FeedBackScreenContent() {
        val navigator = LocalNavigator.currentOrThrow
        val httpClient = koinInject<HttpClient>()
        val prefs = koinInject<AppCacheSetting>()
        var feedbackField by remember { mutableStateOf("") }
        val scope = rememberCoroutineScope()
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ), title = {
                        Title(
                            textColor = Color.White, text = "FeedBack", size = 14.ssp
                        )
                    }, navigationIcon = {
                        LongBackButton(
                            iconColor = Color.White, onBackPressed = navigator::pop
                        )
                    })
            }) { innerPad ->
            Column(
                modifier = Modifier.padding(innerPad).fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier.padding(20.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.sdp)
                    ) {
                        Title("Please Share your feedback here", size = 20.sp)
                        CustomInputArea(
                            fieldTitle = "",
                            textFieldValue = feedbackField,
                            onValueChange = { s -> feedbackField = s },
                            imeAction = ImeAction.Done,
                            placeholder = { SText("give feedback here") },
                        )

                        PrimaryButton(
                            modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth(),
                            onClick = {
                                scope.launch {
                                    launch(Dispatchers.IO) {
                                        makeFeedbackApiCall(
                                            httpClient = httpClient,
                                            token = prefs.accessToken ?: "",
                                            feedback = feedbackField,
                                            { response ->
                                                launch {
                                                    ToastManager.showMessage(
                                                        ToastMessage(
                                                            message = response.detail,
                                                            type = if (response.status) ToastType.Success else ToastType.Error
                                                        )
                                                    )
                                                }
                                                navigator.popUntilRoot()
                                            },
                                        )
                                    }
                                }
                            },
                        ) {
                            SText(
                                text = "submit",
                                fontWeight = FontWeight.SemiBold,
                                textColor = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 12.ssp
                            )
                        }

                        Spacer(modifier = Modifier.padding(1.dp))

                        TextButton(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = {
                                getContactsUtil().sendEmail(
                                    email = "demo30@gmail.com",
                                    subject = "Feedback",
                                    body = "describe your complaint here"
                                )
                                navigator.popUntilRoot()
                            }) {
                            Text(
                                text = "have a complaint? Click here",
                                fontFamily = displayFontFamily()
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun makeFeedbackApiCall(
        httpClient: HttpClient,
        token: String,
        feedback: String,
        onComplete: (ServerResponse<JsonElement>) -> Unit
    ) {
        val response = httpClient.post(BuildConfig.BASE_URL + "/feedback") {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(
                """
                {
                  "feedback": "$feedback"
                }
            """.trimIndent()
            )
        }
        if (response.status.isSuccess()) {
            val result: ServerResponse<JsonElement> = response.body()
            onComplete(result)
        } else {
            val result: ServerResponse<JsonElement> = response.body()
            onComplete(result)
        }
    }
}

