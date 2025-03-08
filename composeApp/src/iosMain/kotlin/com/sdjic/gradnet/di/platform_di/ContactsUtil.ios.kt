package com.sdjic.gradnet.di.platform_di

import com.sdjic.gradnet.presentation.core.model.Post
import com.sdjic.ioshelper.IosHelper
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIViewController


@OptIn(ExperimentalForeignApi::class)
actual fun getContactsUtil(): ContactsUtil {

    return object : ContactsUtil {
        val helper = IosHelper()
        override fun sendSms(phoneNumber: String, message: String) {
            helper.sendSmsWithPhoneNumber(
                phoneNumber = phoneNumber,
                message = message
            )
        }

        override fun openLink(link: String) {
            helper.openLinkWithLink(link)
        }

        override fun dialPhoneNumber(phoneNumber: String) {
            helper.dialPhoneNumberWithPhoneNumber(phoneNumber)
        }

        override fun sendEmail(email: String, subject: String, body: String) {
            helper.sendEmailWithEmail(email,subject, body)
        }

        override fun shareText(text: String) {
            val uiViewController : UIViewController
            helper.shareTextWithText(text = text)
        }

        override fun sharePost(post: Post) {
            helper.sharePostWithImageUrls(imageUrls = post.images, description = post.content)
        }
    }
}
