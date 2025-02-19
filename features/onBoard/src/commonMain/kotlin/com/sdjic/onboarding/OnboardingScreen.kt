package com.sdjic.onboarding

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import cafe.adriel.voyager.core.screen.Screen
import com.sdjic.commons.composables.DotIndicatorItem
import com.sdjic.commons.composables.button.PrimaryButton
import com.sdjic.commons.composables.button.SecondaryOutlinedButton
import com.sdjic.commons.composables.text.SText
import com.sdjic.commons.helper.AutoSwipePagerEffect
import network.chaintech.sdpcomposemultiplatform.sdp


class OnBoardingScreen(private val authNavigator: AuthNavigatorAction) : Screen {

    @Composable
    override fun Content() {
        OnBoardingScreenContent(
            onLogin = { authNavigator.navigateToLogin() },
            onRegistration = { authNavigator.navigateToSignUp() }
        )
    }
}


@Composable
fun OnBoardingScreenContent(
    onLogin: () -> Unit,
    onRegistration: () -> Unit,
) {

    val onboardingList by remember { mutableStateOf(getOnBoardingList()) }

    val pagerState: PagerState = rememberPagerState(
        pageCount = { onboardingList.size }
    )

    AutoSwipePagerEffect(pagerState = pagerState, durationMillis = 2000L)

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
                        DotIndicatorItem(
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

