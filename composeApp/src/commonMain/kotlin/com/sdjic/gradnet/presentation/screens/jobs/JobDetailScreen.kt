package com.sdjic.gradnet.presentation.screens.jobs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.rememberAsyncImagePainter
import com.sdjic.gradnet.di.platform_di.getContactsUtil
import com.sdjic.gradnet.presentation.composables.filter.ChipItem
import com.sdjic.gradnet.presentation.composables.images.BackButton
import com.sdjic.gradnet.presentation.core.model.Job
import com.sdjic.gradnet.presentation.theme.displayFontFamily

class JobDetailScreen(private val job: Job) : Screen {

    @Composable
    override fun Content() {
        JobDetailContent(job)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun JobDetailContent(job: Job) {

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        BackButton(onBackPressed = navigator::pop)
                    },
                    actions = {
                        var mIsOptionMenuExpanded by remember { mutableStateOf(false) }
                        IconButton(onClick = { mIsOptionMenuExpanded = true }) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "menu")
                        }
                        DropdownMenu(
                            expanded = mIsOptionMenuExpanded,
                            offset = DpOffset(x = (-100).dp, y = (-20).dp),
                            onDismissRequest = { mIsOptionMenuExpanded = false },
                            properties = PopupProperties(
                                dismissOnBackPress = true,
                                dismissOnClickOutside = true
                            )
                        ) {
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = "menu"
                                    )
                                },
                                text = { Text("Save job", fontFamily = displayFontFamily()) },
                                onClick = {
                                    mIsOptionMenuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Report,
                                        contentDescription = "menu"
                                    )
                                },
                                text = { Text("Report job", fontFamily = displayFontFamily()) },
                                onClick = {
                                    mIsOptionMenuExpanded = false
                                }
                            )
                        }
                    }
                )
            }
        ) { innPad ->
            Column(
                modifier = Modifier
                    .padding(innPad)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    job.companyLogo?.let {
                        Image(
                            painter = rememberAsyncImagePainter(it),
                            contentDescription = "Company Logo",
                            modifier = Modifier.size(40.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Text(
                        text = "${job.company} ",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = job.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                )

                Text(
                    text = job.location,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(8.dp))


                job.salary?.let {
                    Text(
                        fontFamily = displayFontFamily(),
                        text = "Salary:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.padding(start = 10.dp),
                        fontFamily = displayFontFamily()
                    )
                }

                job.jobType?.let {
                    ChipItem(
                        modifier = Modifier,
                        topic = job.jobType,
                        textColor = MaterialTheme.colorScheme.onSecondary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    fontFamily = displayFontFamily(),
                    text = "Job Description",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = job.description, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    fontFamily = displayFontFamily(),
                    text = "Requirements",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                job.requirements.forEach { requirement ->
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        fontFamily = displayFontFamily(),
                        text = "• $requirement", fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    fontFamily = displayFontFamily(),
                    text = "Benefits",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                job.benefits.forEach { benefit ->
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        fontFamily = displayFontFamily(),
                        text = "• $benefit", fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Skills", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                job.skills.forEach { skill ->
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        fontFamily = displayFontFamily(),
                        text = "• $skill", fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { getContactsUtil().openLink(job.applyLink) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Apply Now")
                }
            }
        }
    }
}