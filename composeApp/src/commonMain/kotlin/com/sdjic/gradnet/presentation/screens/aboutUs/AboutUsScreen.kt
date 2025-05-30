package com.sdjic.gradnet.presentation.screens.aboutUs

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.maxkeppeker.sheets.core.utils.JvmSerializable
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.sdjic.gradnet.di.platform_di.getContactsUtil
import com.sdjic.gradnet.presentation.composables.images.LongBackButton
import com.sdjic.gradnet.presentation.composables.images.RoundedCornerImage
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.core.aryanPic
import com.sdjic.gradnet.presentation.core.devanshPic
import com.sdjic.gradnet.presentation.core.shubhangiPic
import com.sdjic.gradnet.presentation.core.smitPic
import com.sdjic.gradnet.presentation.helper.colorFromHex
import com.sdjic.gradnet.presentation.helper.horizontalGradientBackground
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Github
import compose.icons.fontawesomeicons.brands.InstagramSquare
import compose.icons.fontawesomeicons.brands.Linkedin
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

        var selectedItem by remember { mutableStateOf(developerList.first()) }

        val animatedColor by animateColorAsState(
            targetValue = colorFromHex(selectedItem.bgColor).copy(.6f),
            label = "animatedColor",
            animationSpec = tween(500)
        )
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ), title = {
                        Title(
                            textColor = Color.White, text = "About Developers", size = 14.ssp
                        )
                    }, navigationIcon = {
                        LongBackButton(
                            iconColor = Color.White, onBackPressed = onNavigateBack
                        )
                    })
            }
        ) { innerPad ->

            Row(
                modifier = Modifier
                    .padding(innerPad)
                    .fillMaxWidth()
                    .background(animatedColor)
            ) {
                NavigationRail(
                    containerColor = Color.Transparent,
                    modifier = Modifier
                ) {
                    developerList.fastForEachIndexed { index, item ->
                        DeveloperCard(
                            item = item,
                            isSelected = item == selectedItem,
                            onClick = { selectedItem = item }
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(1f)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    RichText(
                        state = RichTextState().setMarkdown(selectedItem.description)
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    DeveloperContactInfo(selectedItem)

                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }

    @Composable
    private fun DeveloperContactInfo(selectedItem: Developer) {
        Row {
            Icon(
                modifier = Modifier.padding(4.dp).size(36.dp)
                    .noRippleEffect { getContactsUtil().openLink(selectedItem.github) },
                imageVector = FontAwesomeIcons.Brands.Github,
                contentDescription = "github"
            )

            Icon(
                modifier = Modifier.padding(4.dp).size(36.dp)
                    .noRippleEffect { getContactsUtil().openLink(selectedItem.instagram) },
                imageVector = FontAwesomeIcons.Brands.InstagramSquare,
                contentDescription = "InstagramSquare"
            )

            Icon(
                modifier = Modifier.padding(4.dp).size(36.dp)
                    .noRippleEffect { getContactsUtil().openLink(selectedItem.linkedin) },
                imageVector = FontAwesomeIcons.Brands.Linkedin,
                contentDescription = "Linkedin"
            )
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


        Box(
            modifier = Modifier
                .heightIn(min = 80.sdp, max = 120.sdp)
                .noRippleEffect(onClick)
        ) {

            Box(
                modifier = Modifier.matchParentSize()
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
                    data = item.profileImageUrl
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
        val profileImageUrl: String,
        val github: String,
        val instagram: String,
        val linkedin: String,
        val bgColor: String
    ) : JvmSerializable

    val developerList = listOf(
        Developer(
            name = "Amdavadwala Devansh ",
            role = "Android Developer | Compose | Kotlin | Multiplatform",
            description = """
                Android Application Developer with developing, and maintaining high-quality mobile applications that meet client requirements and enhance user experiences. Proficient in Java, Kotlin, and Android SDK, with strong problem-solving skills and a keen eye for detail. Skilled in deliver projects within deadlines and budget constraints. Excited to leverage my expertise in Android development to contribute to innovative and impactful projects.

                reach out to me :-
                Phone no :+91 9429509806 
                Email : devanshamadavadwala@gmail.com
            """.trimIndent(),
            profileImageUrl = devanshPic,
            github = "https://github.com/devenvoy",
            linkedin = "https://www.linkedin.com/in/devansh-a-bb104524a/",
            instagram = "https://www.instagram.com/aj_devansh/",
            bgColor = "#FFD89C"
        ),
        Developer(
            name = " Malaviya Smit ",
            role = "Backend Developer | Python | Machine Learning",
            description = """
                Project Leader & Backend Developer
                As the leader of GradNet, I oversee the project's vision and technical direction while handling backend development in Python. With expertise in AI and Machine Learning, I focus on building robust and scalable solutions that enhance alumni connectivity and engagement.
            """.trimIndent(),
            profileImageUrl = smitPic,
            github = "https://github.com/smit014",
            linkedin = "https://www.linkedin.com/in/smit-malaviya/",
            instagram = "https://www.instagram.com/smit_malaviya_14/",
            bgColor = "#A8E6CF"
        ),
        Developer(
            name = "  Korat Aryan   ",
            role = "Backend Developer | React Developer | Node ",
            description = """
                Designer, Researcher & Content Manager
                Aryan is the creative mind behind GradNet’s user experience. He ensures the platform is visually appealing and user-friendly while also conducting in-depth research to refine and enhance our features. Additionally, he manages content, ensuring that alumni have access to valuable and engaging information.
             """.trimIndent(),
            profileImageUrl = aryanPic,
            github = "https://github.com/korat08",
            linkedin = "https://www.linkedin.com/in/korat-aryan-3b4688242/",
            instagram = "https://www.instagram.com/_aryan0808/",
            bgColor = "#A7C7E7"
        ),
        Developer(
            name = "Kyada shubhangi",
            role = "React Developer | Node ",
            description = """
                Backend Developer & Content Manager
                Shubhangi plays a crucial role in backend development and ensures that our platform has well-structured, informative content. She is responsible for curating engaging resources that keep alumni informed and involved.
            """.trimIndent(),
            profileImageUrl = shubhangiPic,
            github = "https://github.com/ShubhangiKyada",
            linkedin = "https://www.linkedin.com/in/shubhangi-kyada-a81347289/",
            instagram = "https://www.instagram.com/shubhangi_kyada_/",
            bgColor = "#FFB6C1"
        )
    )
}