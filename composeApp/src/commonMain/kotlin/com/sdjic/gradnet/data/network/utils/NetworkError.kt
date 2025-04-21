package com.sdjic.gradnet.data.network.utils

import network.chaintech.kmp_date_time_picker.utils.capitalize

enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    SERVER_TIMEOUT,
    UNKNOWN;

    val message: String
        get() = this.name.lowercase().capitalize()
}