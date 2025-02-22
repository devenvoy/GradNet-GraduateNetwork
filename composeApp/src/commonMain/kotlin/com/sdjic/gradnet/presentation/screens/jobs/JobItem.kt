package com.sdjic.gradnet.presentation.screens.jobs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sdjic.gradnet.presentation.core.model.Job
import com.sdjic.gradnet.presentation.helper.DateTimeUtils
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.ic_company_placeholder
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun JobItem(job: Job, onClick: () -> Unit) {

    val coroutineScope = rememberCoroutineScope()
    var timeAgo by remember { mutableStateOf("Loading...") }

    LaunchedEffect(job.postedDate) {
        coroutineScope.launch {
            val localDateTime = DateTimeUtils.parseDateAsync(job.postedDate)
            timeAgo = DateTimeUtils.getTimeAgoAsync(DateTimeUtils.toEpochMillis(localDateTime))
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Company Logo
            AsyncImage(
                model = job.companyLogo,
                contentDescription = "Company Logo",
                placeholder = painterResource(Res.drawable.ic_company_placeholder),
                error = painterResource(Res.drawable.ic_company_placeholder),
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = job.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = job.company,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "${job.location} • ${job.jobType.type}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    job.salary?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF4CAF50), // Green Color for Salary
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }


                    Text(
                        text = timeAgo,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
//        HorizontalDivider(thickness = 1.dp)
    }
}

