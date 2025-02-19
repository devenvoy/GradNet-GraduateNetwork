package com.sdjic.domain.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoResponse(
    @SerialName("meta") val meta: Meta = Meta(),
    @SerialName("result") val result: List<Coin> = listOf()
) {
    @Serializable
    data class Meta(
        @SerialName("hasNextPage") val hasNextPage: Boolean = false,
        @SerialName("hasPreviousPage") val hasPreviousPage: Boolean = false,
        @SerialName("itemCount") val itemCount: Int = 0,
        @SerialName("limit") val limit: Int = 0,
        @SerialName("page") val page: Int = 0,
        @SerialName("pageCount") val pageCount: Int = 0
    )

    @Serializable
    data class Coin(
        @SerialName("availableSupply") val availableSupply: Long = 0,
        @SerialName("contractAddress") val contractAddress: String? = null,
        @SerialName("decimals") val decimals: Int? = null,
        @SerialName("explorers") val explorers: List<String> = listOf(),
        @SerialName("fullyDilutedValuation") val fullyDilutedValuation: Double = 0.0,
        @SerialName("icon") val icon: String = "",
        @SerialName("id") val id: String = "",
        @SerialName("marketCap") val marketCap: Double = 0.0,
        @SerialName("name") val name: String = "",
        @SerialName("price") val price: Double = 0.0,
        @SerialName("priceBtc") val priceBtc: Double = 0.0,
        @SerialName("priceChange1d") val priceChange1d: Double = 0.0,
        @SerialName("priceChange1h") val priceChange1h: Double = 0.0,
        @SerialName("priceChange1w") val priceChange1w: Double = 0.0,
        @SerialName("rank") val rank: Int = 0,
        @SerialName("redditUrl") val redditUrl: String = "",
        @SerialName("symbol") val symbol: String = "",
        @SerialName("totalSupply") val totalSupply: Long = 0,
        @SerialName("twitterUrl") val twitterUrl: String = "",
        @SerialName("volume") val volume: Double = 0.0,
        @SerialName("websiteUrl") val websiteUrl: String? = null
    )
}