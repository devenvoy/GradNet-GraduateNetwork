package com.sdjic.gradnet.domain

interface AppCacheSetting {

    var accessToken: String

    val isLoggedIn: Boolean

    var userId : String

//    val observableFromLanguage: Flow<Language>

    fun logout(callBack:()->Unit)
}