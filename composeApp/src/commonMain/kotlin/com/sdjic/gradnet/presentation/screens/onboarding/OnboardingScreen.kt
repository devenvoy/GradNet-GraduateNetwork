package com.sdjic.gradnet.presentation.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.composables.PrimaryButton
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.composables.SecondaryOutlinedButton
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
        pageCount = { onboardingList.size },
        initialPageOffsetFraction = 0f
    )

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page: Int ->
                OnboardingPagerItem(onboardingList[page])
            }
            Column(
                modifier = Modifier.align(Alignment.BottomCenter).padding(20.sdp),
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
                            size = 6,
                            spacer = 6,
                            selectedLength = 12
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
        title = "Connect and Grow❕🤝",
        description = "Join a vibrant community of students, alumni, and faculty. 🌱",
        lottieFile = "onboarding1.json"
    ),
    Onboard(
        title = "Find Opportunities❕💼",
        description = "Access jobs, internships, and exclusive university events. 🎓",
        lottieFile = "onboarding2.json"
    ),
    Onboard(
        title = "Inspire Others❕🌟",
        description = "Share achievements and inspire your university network. 💬",
        lottieFile = "onboarding3.json"
    )
)

