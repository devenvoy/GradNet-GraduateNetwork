package com.sdjic.gradnet.presentation.screens.aboutUs

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring.DampingRatioMediumBouncy
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.composables.images.BackButton
import com.sdjic.gradnet.presentation.composables.images.RoundedCornerImage
import com.sdjic.gradnet.presentation.composables.text.Label
import com.sdjic.gradnet.presentation.helper.colorFromHex
import com.sdjic.gradnet.presentation.helper.horizontalGradientBackground
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import network.chaintech.kmp_date_time_picker.utils.noRippleEffect
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

class AboutUsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        AboutUsScreenContent(
            onNavigateBack = { navigator.pop() }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AboutUsScreenContent(onNavigateBack: () -> Unit) {
        val scrollBehavior =
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

        var selectedItem by remember { mutableStateOf(0) }

        val animatedColor by animateColorAsState(
            targetValue = colorFromHex(developerList[selectedItem].bgColor).copy(.6f),
            label = "animatedColor",
            animationSpec = tween(500)
        )

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    scrollBehavior = scrollBehavior,
                    title = {
                        Label("About Developers", fontWeight = W500, size = 18.sp)
                    },
                    navigationIcon = {
                        BackButton(onBackPressed = onNavigateBack)
                    }
                )
            }
        ) {
            Row(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxWidth()
            ) {
                NavigationRail(
                    modifier = Modifier
                ) {
                    developerList.fastForEachIndexed { index, item ->
                        DeveloperCard(
                            item = item,
                            isSelected = index == selectedItem,
                            onClick = { selectedItem = index }
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(1f)
                        .fillMaxSize()
                        .background(animatedColor)
                ) {

                }
            }
        }
    }

    @Composable
    fun DeveloperCard(
        item: Developer,
        isSelected: Boolean,
        onClick: () -> Unit
    ) {

        val transition = updateTransition(targetState = isSelected, label = "BoxTransition")

        val backgroundColor by transition.animateColor(
            transitionSpec = { tween(durationMillis = 500) },
            label = "BoxColorAnimation"
        ) { visible ->
            if (visible) colorFromHex(item.bgColor).copy(.6f) else Color.Transparent
        }

        val translationY by animateFloatAsState(
            targetValue = if (isSelected) 0f else 2000f,
            animationSpec = spring(DampingRatioMediumBouncy),
            label = "BoxTranslation"
        )


        Box(
            modifier = Modifier
                .heightIn(min = 80.sdp, max = 120.sdp)
                .noRippleEffect(onClick)
        ) {

            Box(
                modifier = Modifier.matchParentSize()
                    .graphicsLayer(translationY = translationY)
                    .horizontalGradientBackground(
                        listOf(Color.Transparent, backgroundColor)
                    )
            )

            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedCornerImage(
                    data = item.image
                )
                Text(
                    modifier = Modifier.widthIn(max = 100.dp),
                    text = item.name,
                    fontFamily = displayFontFamily(),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 12.ssp,
                    ),
                )
            }
        }
    }

    data class Developer(
        val name: String,
        val role: String,
        val description: String,
        val image: String,
        val github: String,
        val linkedin: String,
        val bgColor: String
    )

    val developerList = listOf(
        Developer(
            name = "Amdavadwala Devansh ",
            role = "Android Developer | Compose | Kotlin | Multiplatform",
            description = "",
            image = "https://instagram.fstv5-1.fna.fbcdn.net/v/t51.2885-19/376030627_1744217199339437_5293057648068263787_n.jpg?stp=dst-jpg_s150x150_tt6&_nc_ht=instagram.fstv5-1.fna.fbcdn.net&_nc_cat=105&_nc_oc=Q6cZ2AEnYylH4YsTSaM6JBbkuZsS8EMHox00AxeZ9eQdjrDxtlhSOUnUGoXF3-6LntTEjHg&_nc_ohc=Y4juZBkieGUQ7kNvgGjDRKv&_nc_gid=f7e59a965c5e4109bff1d503ace62cd8&edm=AP4sbd4BAAAA&ccb=7-5&oh=00_AYC6ii5w00LB3bOWx797_h_mK2H08amo0G2RJVJB22gM_g&oe=67BEB49B&_nc_sid=7a9f4b",
            github = "",
            linkedin = "",
            bgColor = "#FFD89C"
        ),
        Developer(
            name = " Malaviya Smit ",
            role = "Backend Developer | Python | Machine Learning",
            description = "",
            image = "https://instagram.fstv5-1.fna.fbcdn.net/v/t51.2885-19/299378448_466040581771917_7101039651663906813_n.jpg?stp=dst-jpg_s150x150_tt6&_nc_ht=instagram.fstv5-1.fna.fbcdn.net&_nc_cat=109&_nc_oc=Q6cZ2AHXaU9741mdeR44X_j99E08x2zKaM1m2PAbwiEXumarLz4B3OxQmsCWRce0Wb9gFSk&_nc_ohc=W2GhUKK14rUQ7kNvgHz3aCB&_nc_gid=1514d78034e74613a7c9a1787c69554d&edm=AP4sbd4BAAAA&ccb=7-5&oh=00_AYCwcdyLbXLDDCtF5xyjKmaUklUTLzZ6hjpFsfXMPMFerA&oe=67BE9792&_nc_sid=7a9f4b",
            github = "",
            linkedin = "",
            bgColor = "#A8E6CF"
        ),
        Developer(
            name = "  Korat Aryan   ",
            role = "Backend Developer | React Developer | Node ",
            description = "",
            image = "https://instagram.fstv5-1.fna.fbcdn.net/v/t51.2885-19/413250164_3465693833682632_8177938303943813843_n.jpg?stp=dst-jpg_s150x150_tt6&_nc_ht=instagram.fstv5-1.fna.fbcdn.net&_nc_cat=108&_nc_oc=Q6cZ2AHYDpxxD78NmvXkTz5wIwLev1R-sopWwI4653XC17ZKyjM-ePVcHHy07rce1nHh0Pk&_nc_ohc=rKUjKztZYO4Q7kNvgGrtBy6&_nc_gid=5ba5e3d76e714df68e6c45c473c9d38c&edm=AP4sbd4BAAAA&ccb=7-5&oh=00_AYBNsrA_cwYOMgLCRtBvgvEQwHQ7wKOJpJ2zx8z1IMkTSw&oe=67BE8FC2&_nc_sid=7a9f4b",
            github = "",
            linkedin = "",
            bgColor = "#A7C7E7"
        ),
        Developer(
            name = "Kyada shubhangi",
            role = "React Developer | Node ",
            description = "",
            image = "https://instagram.fstv5-1.fna.fbcdn.net/v/t51.2885-19/460682937_1175643466880573_8384927044933965467_n.jpg?stp=dst-jpg_s150x150_tt6&_nc_ht=instagram.fstv5-1.fna.fbcdn.net&_nc_cat=105&_nc_oc=Q6cZ2AFmIgHOQm5BsbeJJPkwdUN2dJ_uD9WJmmr8BBWwerrIaXqG0ngmMzeDyTANFckbn2E&_nc_ohc=8XcUkih8w5wQ7kNvgFg32Y2&_nc_gid=b85c482e2a3d4118a614b64d515d159d&edm=AP4sbd4BAAAA&ccb=7-5&oh=00_AYC60rUNIIprk6M-QDsSv5ER1tyN1K8CEkEJf6pmYvGmzQ&oe=67BEA054&_nc_sid=7a9f4b",
            github = "",
            linkedin = "",
            bgColor = "#FFB6C1"
        )
    )

}