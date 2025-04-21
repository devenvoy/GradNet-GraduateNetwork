package com.sdjic.gradnet.presentation.screens.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.sdjic.gradnet.data.network.entity.dto.EventDto
import com.sdjic.gradnet.di.platform_di.getContactsUtil
import com.sdjic.gradnet.presentation.composables.SectionTitle
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.filter.CustomImageChip
import com.sdjic.gradnet.presentation.composables.images.BackButton
import com.sdjic.gradnet.presentation.composables.images.BannerWidget
import com.sdjic.gradnet.presentation.composables.text.Label
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.core.DummyBgImage
import com.sdjic.gradnet.presentation.helper.FadeTransition
import com.sdjic.gradnet.presentation.helper.parallaxLayoutModifier
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import com.sdjic.gradnet.presentation.theme.errorColor
import network.chaintech.kmp_date_time_picker.utils.noRippleEffect

@OptIn(ExperimentalVoyagerApi::class)
class EventDetailScreen(
    private val eventDto: EventDto
) : Screen, ScreenTransition by FadeTransition() {
    @Composable
    override fun Content() {
        EventDetailScreenContent()
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @Composable
    fun EventDetailScreenContent() {

        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            rememberTopAppBarState()
        )
        val navigator = LocalNavigator.currentOrThrow

        val scrollState = rememberScrollState()

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    scrollBehavior = scrollBehavior,
                    title = {
                        Label(
                            textColor = Color.White,
                            text = eventDto.eventTitle ?: "",
                            fontWeight = FontWeight(400)
                        )
                    },
                    navigationIcon = {
                        BackButton(iconColor = Color.White) { navigator.pop() }
                    }
                )
            }
        ) {


            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {


                BannerWidget(
                    modifier = Modifier.height(325.dp)
                        .parallaxLayoutModifier(scrollState, 2),
                    imageUrl = eventDto.eventPic ?: DummyBgImage,
                    contentDescription = null
                )


                Column(
                    modifier = Modifier.fillMaxWidth()
                        .offset(y=(-25).dp)
                        .clip(RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Label(text = eventDto.eventName, modifier = Modifier.padding(12.dp))

                    eventDto.venue?.let {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    contentColor = Color.White
                                ),
                                onClick = {}
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            SText(
                                text = eventDto.venue,
                                textColor = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }

                    // date & time
                    DateTimeRow()

                    SectionTitle(icon = null, title = "Description")

                    SText(
                        text = eventDto.description ?: "No description provided",
                        textColor = MaterialTheme.colorScheme.onBackground.copy(.7f)
                    )

                    eventDto.guestNames?.let {
                        Column {
                            SectionTitle(null, "Guest:")

                            Text(
                                fontFamily = displayFontFamily(),
                                modifier = Modifier.padding(horizontal = 8.dp),
                                text = eventDto.guestNames
                            )
                        }
                    }

                    eventDto.forWhom?.let {
                        Column {
                            SectionTitle(null, "Who can participate:")

                            Text(
                                fontFamily = displayFontFamily(),
                                modifier = Modifier.padding(horizontal = 8.dp),
                                text = eventDto.forWhom
                            )
                        }
                    }

                    Column {
                        SectionTitle(null, "Contact Details")
                        FlowRow(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            eventDto.contactUs?.split(",")?.forEach { number ->
                                CustomImageChip(
                                    text = number,
                                    selected = true,
                                    showEndIcon = false,
                                    onCloseClick = {},
                                    modifier = Modifier.padding(2.dp)
                                        .noRippleEffect { getContactsUtil().dialPhoneNumber(number) }
                                )
                            }
                        }
                    }
                    eventDto.remarks?.let {
                        Column(
                            modifier = Modifier
//                                .padding(8.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(errorColor.copy(.1f))
                                .padding(8.dp, 12.dp)

                        ) {
                            Title(
                                text = "Remarks: ",
                                textColor = errorColor
                            )
                            SText(
                                text = eventDto.remarks,
                                textColor = MaterialTheme.colorScheme.onBackground.copy(.7f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    PrimaryButton(
                        shape = ButtonDefaults.shape,
                        contentPadding = ButtonDefaults.ContentPadding,
                        modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(.6f),
                        onClick = {
                            eventDto.registerLink?.let { link ->
                                getContactsUtil().openLink(link)
                            }
                        }
                    ) {
                        Text(
                            fontFamily = displayFontFamily(),
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "Register here"
                        )
                    }

                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
    }


    @Composable
    fun DateTimeRow() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            DateTimeCard(label = "Start Time", value = eventDto.time ?: " - : - ")
            DateTimeCard(label = "Date", value = eventDto.date ?: " - / - / -")
        }
    }
}


@Composable
fun DateTimeCard(label: String, value: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F3F1)),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .padding(8.dp)
            .size(140.dp, 100.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                fontFamily = displayFontFamily(),
                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF907D75)),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontFamily = displayFontFamily(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF907D75)
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
