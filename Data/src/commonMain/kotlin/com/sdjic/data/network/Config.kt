package com.sdjic.data.network

object Config {
    private var BASE_URL = ""

    fun setBaseUrl(url: String) {
        BASE_URL = url
    }

    fun getBaseUrl(): String {
        return BASE_URL
    }
}