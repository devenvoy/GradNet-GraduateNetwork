package com.sdjic.shared

import gradnet_graduatenetwork.shared.generated.resources.Res
import gradnet_graduatenetwork.shared.generated.resources.about_me
import gradnet_graduatenetwork.shared.generated.resources.about_project
import gradnet_graduatenetwork.shared.generated.resources.alternate_email
import gradnet_graduatenetwork.shared.generated.resources.app_name
import gradnet_graduatenetwork.shared.generated.resources.btn_google_sing_in
import gradnet_graduatenetwork.shared.generated.resources.create_account
import gradnet_graduatenetwork.shared.generated.resources.cross
import gradnet_graduatenetwork.shared.generated.resources.empty_trash
import gradnet_graduatenetwork.shared.generated.resources.event
import gradnet_graduatenetwork.shared.generated.resources.event_note
import gradnet_graduatenetwork.shared.generated.resources.github
import gradnet_graduatenetwork.shared.generated.resources.heart
import gradnet_graduatenetwork.shared.generated.resources.heart_outlined
import gradnet_graduatenetwork.shared.generated.resources.home
import gradnet_graduatenetwork.shared.generated.resources.home_outline
import gradnet_graduatenetwork.shared.generated.resources.ic_alumni
import gradnet_graduatenetwork.shared.generated.resources.ic_certificate
import gradnet_graduatenetwork.shared.generated.resources.ic_degree
import gradnet_graduatenetwork.shared.generated.resources.ic_faculty
import gradnet_graduatenetwork.shared.generated.resources.ic_organization
import gradnet_graduatenetwork.shared.generated.resources.ic_share
import gradnet_graduatenetwork.shared.generated.resources.info_outline
import gradnet_graduatenetwork.shared.generated.resources.inter_24pt_regular
import gradnet_graduatenetwork.shared.generated.resources.linkedin
import gradnet_graduatenetwork.shared.generated.resources.logout
import gradnet_graduatenetwork.shared.generated.resources.mail_outline
import gradnet_graduatenetwork.shared.generated.resources.person
import gradnet_graduatenetwork.shared.generated.resources.person_outline
import gradnet_graduatenetwork.shared.generated.resources.phone
import gradnet_graduatenetwork.shared.generated.resources.search
import gradnet_graduatenetwork.shared.generated.resources.settings
import gradnet_graduatenetwork.shared.generated.resources.twitter_bird
import gradnet_graduatenetwork.shared.generated.resources.work
import gradnet_graduatenetwork.shared.generated.resources.work_outline
import org.jetbrains.compose.resources.ExperimentalResourceApi

object Resource {
    object drawable {
        val alternateEmail = Res.drawable.alternate_email
        val btnGoogleSingIn = Res.drawable.btn_google_sing_in
        val cross = Res.drawable.cross
        val emptyTrash = Res.drawable.empty_trash
        val event = Res.drawable.event
        val eventNote = Res.drawable.event_note
        val github = Res.drawable.github
        val heart = Res.drawable.heart
        val heartOutlined = Res.drawable.heart_outlined
        val home = Res.drawable.home
        val homeOutline = Res.drawable.home_outline
        val icAlumni = Res.drawable.ic_alumni
        val icCertificate = Res.drawable.ic_certificate
        val icDegree = Res.drawable.ic_degree
        val icFaculty = Res.drawable.ic_faculty
        val icOrganization = Res.drawable.ic_organization
        val icShare = Res.drawable.ic_share
        val infoOutline = Res.drawable.info_outline
        val linkedin = Res.drawable.linkedin
        val logout = Res.drawable.logout
        val mailOutline = Res.drawable.mail_outline
        val person = Res.drawable.person
        val personOutline = Res.drawable.person_outline
        val phone = Res.drawable.phone
        val search = Res.drawable.search
        val settings = Res.drawable.settings
        val twitterBird = Res.drawable.twitter_bird
        val work = Res.drawable.work
        val workOutline = Res.drawable.work_outline
    }

    object font {
        val interRegular = Res.font.inter_24pt_regular
    }

    object string {
        val app_name = Res.string.app_name
        val create_account = Res.string.create_account
        val about_me = Res.string.about_me
        val about_project = Res.string.about_project
    }

    @OptIn(ExperimentalResourceApi::class)
    object files {
        suspend fun getLoadingLottie() = Res.readBytes("files/loading.lottie")
        suspend fun getOnboarding1Lottie() = Res.readBytes("files/onboarding1.lottie")
        suspend fun getOnboarding2Lottie() = Res.readBytes("files/onboarding2.lottie")
        suspend fun getOnboarding3Lottie() = Res.readBytes("files/onboarding3.lottie")
        suspend fun getWorkingLottie() = Res.readBytes("files/working.lottie")
    }
}