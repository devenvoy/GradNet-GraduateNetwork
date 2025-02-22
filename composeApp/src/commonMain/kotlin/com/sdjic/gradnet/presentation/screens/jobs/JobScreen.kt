package com.sdjic.gradnet.presentation.screens.jobs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.sdjic.gradnet.presentation.core.model.Job
import com.sdjic.gradnet.presentation.core.model.JobType
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.home.HomeScreenViewModel

class JobScreen : Screen {
    @Composable
    override fun Content() {
        JobScreenContent()
    }

    @Composable
    private fun JobScreenContent() {
        CryptoListContent()
    }

    @Composable
    fun CryptoListContent() {
        val viewModel = koinScreenModel<JobScreenModel>()
//        val data = viewModel.coinList.collectAsLazyPagingItems()
        Scaffold {
            /*  PagingListUI(
                  modifier = Modifier.padding(it).padding(10.sdp),
                  data = data
              ) { item ->
                  ListItem(
                      headlineContent = {
                          Title(item.name)
                      },
                      supportingContent = {
                          SText(item.toString())
                      }
                  )
              }*/

            LazyColumn(
                modifier = Modifier.padding(it),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(10) {
                    JobItem(
                        job = sampleJobs.first()
                    ) {

                    }
                }
            }
        }
    }
}

val sampleJobs = listOf(
    Job(
        id = "1",
        title = "Android Developer",
        company = "Google",
        location = "Mountain View, CA",
        salary = "$120K - $150K",
        jobType = JobType.FullTime,
        description = "Looking for an experienced Android Developer...",
        requirements = listOf("3+ years of experience", "Kotlin, Jetpack Compose"),
        benefits = listOf("Health Insurance", "401(k) Matching"),
        postedDate = "2025-02-22T12:30:00Z",
        applyLink = "https://careers.google.com",
        companyLogo = "https://logo.clearbit.com/google.com",
        experienceRequired = "3+ years",
        skills = listOf("Kotlin", "Jetpack Compose", "MVVM")
    )
)