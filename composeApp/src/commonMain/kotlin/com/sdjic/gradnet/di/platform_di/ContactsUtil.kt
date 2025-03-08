package com.sdjic.gradnet.di.platform_di

import com.sdjic.gradnet.presentation.core.model.Post

interface ContactsUtil {
    fun sendSms(phoneNumber: String, message: String)
    fun openLink(link:String)
    fun dialPhoneNumber(phoneNumber: String)
    fun sendEmail(email: String, subject: String, body: String)
    fun shareText(text: String)

    fun sharePost(post: Post)
}

expect fun getContactsUtil(): ContactsUtil