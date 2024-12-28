package com.sdjic.gradnet.data.network

import com.sdjic.gradnet.data.network.entity.CryptoResponse
import com.sdjic.gradnet.data.network.utils.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import com.sdjic.gradnet.data.network.utils.Result
import io.ktor.client.call.body
import io.ktor.client.request.header

class CryptoRepository(
    private val httpClient: HttpClient
)  {
    private val BASE_URL = "https://openapiv1.coinstats.app"
    private val API_KEY = "Ip9gbIGRjP23xr4B7+VCeXUcBcqQnr+oDZFa0wEBtw8="

   suspend fun getCryptos(limit:Int = 10,cur:String = "EUR"): Result<CryptoResponse,NetworkError>  {
        val response = try{
            httpClient.get("$BASE_URL/coins"){
                header("X-API-KEY",API_KEY)
                parameter("limit", limit)
                parameter("currency", cur)
            }
        }catch(e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch(e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

       return when(response.status.value) {
           in 200..299 -> {
               val censoredText = response.body<CryptoResponse>()
               Result.Success(censoredText)
           }
           401 -> Result.Error(NetworkError.UNAUTHORIZED)
           409 -> Result.Error(NetworkError.CONFLICT)
           408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
           413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
           in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
           else -> Result.Error(NetworkError.UNKNOWN)
       }
    }
}