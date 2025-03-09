package com.sdjic.gradnet.presentation.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.button.SecondaryOutlinedButton
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.helper.AutoSwipePagerEffect
import com.sdjic.gradnet.presentation.screens.auth.login.LoginScreen
import com.sdjic.gradnet.presentation.screens.auth.register.SignUpScreen
import network.chaintech.sdpcomposemultiplatform.sdp


class OnBoardingScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        OnBoardingScreenContent(
            onLogin = { navigator.replace(LoginScreen()) },
            onRegistration = { navigator.replace(SignUpScreen()) }
        )
    }
}


@Composable
fun OnBoardingScreenContent(
    onLogin: () -> Unit,
    onRegistration: () -> Unit,
) {

    val pagerState: PagerState = rememberPagerState(
        pageCount = { onboardingList.size }
    )

    AutoSwipePagerEffect(pagerState = pagerState, durationMillis = 3000L)

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page: Int ->
                OnboardingPagerItem(onboardingList[page])
            }
            Column(
                modifier = Modifier.padding(vertical = 30.sdp, horizontal = 20.sdp),
                verticalArrangement = Arrangement.spacedBy(10.sdp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    onboardingList.forEachIndexed { index, _ ->
                        OnboardingPagerSlide(
                            isSelected = index == pagerState.currentPage,
                            selectedColor = MaterialTheme.colorScheme.primary,
                            unselectedColor = Color.Gray,
                            size = 8,
                            spacer = 8,
                            selectedLength = 14
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.sdp))
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onRegistration,
                ) {
                    SText(
                        text = "Create Account",
                        fontWeight = W600,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        textColor = Color.White
                    )
                }
                SecondaryOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onLogin,
                ) {
                    SText(
                        text = "Login",
                        fontWeight = W600,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        textColor = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

data class Onboard(val title: String, val description: String, val lottieFile: String)

val onboardingList = listOf(
    Onboard(
        title = "Connect and Grow",
        description = "Join a vibrant community of students, alumni, and faculty. 🌱",
        lottieFile = "onboarding1.lottie"
    ),
    Onboard(
        title = "Find Opportunities",
        description = "Access jobs, internships, and exclusive university events. 🎓",
        lottieFile = "onboarding2.lottie"
    ),
    Onboard(
        title = "Inspire Others",
        description = "Share achievements and inspire your university network. 💬",
        lottieFile = "onboarding3.lottie"
    )
)

