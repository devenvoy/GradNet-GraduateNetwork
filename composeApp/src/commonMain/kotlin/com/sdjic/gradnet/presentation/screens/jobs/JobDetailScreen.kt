package com.sdjic.gradnet.presentation.screens.jobs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.rememberAsyncImagePainter
import com.sdjic.gradnet.di.platform_di.getContactsUtil
import com.sdjic.gradnet.presentation.composables.filter.ChipItem
import com.sdjic.gradnet.presentation.composables.images.LongBackButton
import com.sdjic.gradnet.presentation.core.model.Job
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.theme.AppTheme
import com.sdjic.gradnet.presentation.theme.displayFontFamily

class JobDetailScreen(private val jobDetail: Job) : Screen {

    @Composable
    override fun Content() {
        val jobDetailScreenModel = koinScreenModel<JobDetailScreenModel>()
        LaunchedEffect(jobDetail) {
            jobDetailScreenModel.updateJobDetail(jobDetail)
        }
        AppTheme {
            JobDetailContent(viewModel = jobDetailScreenModel)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun JobDetailContent(viewModel: JobDetailScreenModel) {

        val navigator = LocalNavigator.currentOrThrow
        val job by viewModel.jobDetail.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(title = { }, navigationIcon = {
                    LongBackButton(
                        onBackPressed = navigator::pop,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }, actions = {
                    /* var mIsOptionMenuExpanded by remember { mutableStateOf(false) }
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
                     }*/
                    IconButton(onClick = { viewModel.toggleSaveJob() }) {
                        Icon(
                            imageVector = if (job.isSaved) Icons.Outlined.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                })
            }) { innPad ->
            Column(
                modifier = Modifier.padding(innPad).fillMaxSize()
                    .verticalScroll(rememberScrollState()).padding(16.dp),
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${job.company} ",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(8.dp).fillMaxWidth(),
                        )
                        Text(
                            text = job.title,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    job.companyLogo?.let {
                        Image(
                            painter = rememberAsyncImagePainter(it),
                            contentDescription = "Company Logo",
                            modifier = Modifier.padding(12.dp).size(80.dp)
                                .clip(MaterialTheme.shapes.small),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                job.jobType?.let {
                    ChipItem(
                        modifier = Modifier,
                        topic = job.jobType!!,
                        textColor = MaterialTheme.colorScheme.primaryContainer
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))

                SalaryAndLocationCard(job.salary, job.location)

                HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    fontFamily = displayFontFamily(),
                    text = "Job Description",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = job.description, fontSize = 14.sp)


                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    fontFamily = displayFontFamily(),
                    text = "Requirements",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                job.requirements.forEach { requirement ->
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        fontFamily = displayFontFamily(),
                        text = "• $requirement",
                        fontSize = 14.sp
                    )
                }


                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    fontFamily = displayFontFamily(),
                    text = "Benefits",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                job.benefits.forEach { benefit ->
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        fontFamily = displayFontFamily(),
                        text = "• $benefit",
                        fontSize = 14.sp
                    )
                }


                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Skills", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                job.skills.forEach { skill ->
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        fontFamily = displayFontFamily(),
                        text = "• $skill",
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { getContactsUtil().openLink(job.applyLink) },
                    modifier = Modifier.fillMaxWidth(.8f).align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Apply Now")
                }
            }
        }
    }
}

@Composable
fun SalaryAndLocationCard(
    salary: String?, location: String, modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.padding(vertical = 12.dp).fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
        ) {
            salary?.let {
                InfoItem(
                    icon = Icons.Default.AccountBalanceWallet,
                    iconColor = Color(0xFF1E88E5),
                    textLabel = "Salary",
                    textValue = salary
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            InfoItem(
                icon = Icons.Default.LocationOn,
                iconColor = Color(0xFF1E88E5),
                textLabel = "Location",
                textValueColor = Color.Unspecified,
                textValue = location
            )
        }
    }
}


@Composable
fun InfoItem(
    icon: ImageVector,
    iconColor: Color,
    textLabel: String,
    textValue: String,
    textValueColor: Color = Color(0xFF4CAF50)
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.size(40.dp).background(Color(0xFFE0E7EF), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = textLabel,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = textLabel, fontSize = 12.sp,
                fontFamily = displayFontFamily(),
            )
            Text(
                text = textValue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = displayFontFamily(),
                color = textValueColor
            )
        }
    }
}