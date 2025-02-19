package com.sdjic.domain

interface AppCacheSetting {

    var accessToken: String

    val isLoggedIn: Boolean

    var userId : String

    var isVerified : Boolean

//    val observableFromLanguage: Flow<Language>

    fun logout(callBack:()->Unit)
}