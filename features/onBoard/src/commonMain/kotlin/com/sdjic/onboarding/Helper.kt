package com.sdjic.onboarding

import com.sdjic.shared.Resource

fun getOnBoardingList() = listOf(
    Onboard(
        title = "Connect and Grow",
        description = "Join a vibrant community of students, alumni, and faculty. 🌱",
        lottieFile = 1
    ),
    Onboard(
        title = "Find Opportunities",
        description = "Access jobs, internships, and exclusive university events. 🎓",
        lottieFile = 2
    ),
    Onboard(
        title = "Inspire Others",
        description = "Share achievements and inspire your university network. 💬",
        lottieFile = 3
    )
)


suspend fun getOnboardingLottie(id: Int): ByteArray {
    return when(id){
        2 -> Resource.files.getOnboarding2Lottie()
        3 -> Resource.files.getOnboarding3Lottie()
        else -> Resource.files.getOnboarding1Lottie()
    }
}
