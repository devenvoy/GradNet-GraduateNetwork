package com.sdjic.gradnet.presentation.screens.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.data.network.entity.dto.EventDto
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.filter.CustomImageChip
import com.sdjic.gradnet.presentation.composables.images.BackButton
import com.sdjic.gradnet.presentation.composables.images.BannerWidget
import com.sdjic.gradnet.presentation.composables.text.Label
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.core.DummyBgImage

class EventDetailScreen(
    private val eventDto: EventDto
) : Screen {
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

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MediumTopAppBar(
                    scrollBehavior = scrollBehavior,
                    title = {
                        Label(
                            text = eventDto.eventTitle ?: "",
                            fontWeight = FontWeight(400)
                        )
                    },
                    navigationIcon = {
                        BackButton { navigator.pop() }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(it)
                    .verticalScroll(rememberScrollState())
            ) {
//                eventDto.eventPic?.let {
                    BannerWidget(
                        modifier = Modifier.height(250.dp),
                        imageUrl = eventDto.eventPic ?: DummyBgImage,
                        contentDescription = null
                    )
//                }
                Column(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Label(text = eventDto.eventName ?: "")
                    eventDto.venue?.let {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Title(text = "Venue: ")
                            SText(text = eventDto.venue)
                        }
                    }
                    eventDto.date?.let {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Title(text = "Date: ")
                            SText(text = eventDto.date)
                        }
                    }
                    eventDto.time?.let {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Title(text = "Time: ")
                            SText(text = eventDto.time)
                        }
                    }
                    eventDto.guestNames?.let {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Title(text = "Guest: ")
                            SText(text = eventDto.guestNames)
                        }
                    }
                    SText(
                        text = eventDto.description ?: "",
                        textColor = MaterialTheme.colorScheme.onBackground.copy(.7f)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Title(text = "Who can participate: ")
                        SText(text = eventDto.forWhom ?: "")
                    }
                    Column {
                        Title("Contact Details")
                        FlowRow(
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            eventDto.contactUs?.split(",")?.forEach { number ->
                                CustomImageChip(
                                    text = number,
                                    selected = true,
                                    showEndIcon = false,
                                    onClick = {},
                                    modifier = Modifier.padding(2.dp)
                                )
                            }
                        }
                    }
                    eventDto.remarks?.let {
                        Column {
                            Title(
                                text = "Remarks: ",
                                textColor = Color(0xFFD64933)
                            )
                            SText(
                                text = eventDto.remarks,
                                textColor = MaterialTheme.colorScheme.onBackground.copy(.7f)
                            )
                        }
                    }
                    PrimaryButton(
                        shape = ButtonDefaults.shape,
                        contentPadding = ButtonDefaults.ContentPadding,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            eventDto.registerLink?.let {

                            }
                        }
                    ) {
                        Text(text = "Register here")
                    }
                }
            }
        }
    }
}