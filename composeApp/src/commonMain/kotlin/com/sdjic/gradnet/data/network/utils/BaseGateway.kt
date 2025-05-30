package com.sdjic.gradnet.data.network.utils

import co.touchlab.kermit.Logger
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.domain.utils.InternetException
import com.sdjic.gradnet.domain.utils.UnknownErrorException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.serialization.SerializationException

abstract class BaseGateway(val client: HttpClient) {

    suspend inline fun <reified T> executeOrThrow(method: HttpClient.() -> HttpResponse): T {
        try {
            return client.method().body()
        } catch (e: ClientRequestException) {
            val errorMessages = e.response.body<T>()
            throw UnknownErrorException(e.message)
        } catch (e: InternetException.NoInternetException) {
            throw InternetException.NoInternetException()
        } catch (e: Exception) {
            throw UnknownErrorException(e.message.toString())
        }
    }

    suspend inline fun <reified T> tryToExecute(method: HttpClient.() -> HttpResponse): Result<T, ServerError> {
        return try {
            val response: HttpResponse = client.method()
            Logger.d("Response: $response \n")
            if (!response.status.isSuccess()) {
                val networkError = response.body<ServerError>()
                Logger.e("Response Error: $networkError")
                return Result.Error(networkError)
            }
            val responseBody: T = response.body()
            Logger.d("Response Body: $responseBody")
            Result.Success(responseBody)
        } catch (e: ClientRequestException) {
            val networkError = e.response.body<ServerError>()
            Logger.e("Response Error: $networkError")
            Result.Error(networkError)
        } catch (e: InternetException.NoInternetException) {
            Logger.e("Response Error: ${e.message}")
            Result.Error(
                ServerError(
                    code = 400,
                    value = null,
                    detail = NetworkError.NO_INTERNET.message,
                )
            )
        } catch (e: SocketTimeoutException) {
            Logger.e("Response Error: ${e.message}")
            Result.Error(
                ServerError(
                    code = 400,
                    value = null,
                    detail = NetworkError.SERVER_TIMEOUT.message,
                )
            )
        } catch (e: SerializationException) {
            Logger.e("Response Error: ${e.message}")
            Result.Error(
                ServerError(
                    code = 400,
                    value = null,
                    detail = NetworkError.SERIALIZATION.message,
                )
            )
        } catch (e: Exception) {
            Logger.e("Response Error: ${e.message}")
            Result.Error(
                ServerError(
                    code = 500,
                    value = null,
                    detail = "Unknown Error",
                )
            )
        }
    }


    private fun Map<String, String>.containsErrors(vararg errorCodes: String): Boolean =
        keys.containsAll(errorCodes.toList())

    private fun Map<String, String>.getOrEmpty(key: String): String = get(key) ?: ""
}