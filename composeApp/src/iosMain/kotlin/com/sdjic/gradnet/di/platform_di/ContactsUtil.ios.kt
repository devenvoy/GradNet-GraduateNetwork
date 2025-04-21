package com.sdjic.gradnet.di.platform_di

import com.sdjic.gradnet.presentation.core.model.Post
//import com.sdjic.ioshelper.IosHelper
//import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun getContactsUtil(): ContactsUtil {

    return object : ContactsUtil {
        //        val helper = IosHelper()
        override fun sendSms(phoneNumber: String, message: String) {
//            helper.sendSmsWithPhoneNumber(
//                phoneNumber = phoneNumber,
//                message = message
//            )
        }

        override fun openLink(link: String) {
//            helper.openLinkWithLink(link)
            val nsUrl = NSURL(string = link)
            if (nsUrl != null) {
                UIApplication.sharedApplication.openURL(nsUrl)
            }
        }

        override fun dialPhoneNumber(phoneNumber: String) {
//            helper.dialPhoneNumberWithPhoneNumber(phoneNumber)
            val phoneUrl = "tel:$phoneNumber"
            openLink(phoneUrl)
        }

        override fun sendEmail(email: String, subject: String, body: String) {
//            helper.sendEmailWithEmail(email,subject, body)
            val encodedSubject = subject.replace(" ", "%20") // URL encoding
            val encodedBody = body.replace(" ", "%20")
            val emailUrl = "mailto:$email?subject=$encodedSubject&body=$encodedBody"

            openLink(emailUrl)
        }

        override fun shareText(text: String) {
//            helper.shareTextWithText(text = text)
            share(context = PlatformContext(), text)
        }

        override fun sharePost(post: Post) {
//            helper.sharePostWithImageUrls(imageUrls = post.images, description = post.content)
        }
    }
}
