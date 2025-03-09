package com.sdjic.gradnet.presentation.screens.jobs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.composables.filter.FilterChipDropdown
import com.sdjic.gradnet.presentation.core.model.Job
import com.sdjic.gradnet.presentation.core.model.JobType
import com.sdjic.gradnet.presentation.helper.LocalScrollBehavior
import com.sdjic.gradnet.presentation.helper.PagingListUI
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.theme.AppTheme
import network.chaintech.kmp_date_time_picker.utils.noRippleEffect
import network.chaintech.sdpcomposemultiplatform.sdp

class JobScreen : Screen {
    @Composable
    override fun Content() {
        AppTheme {
            JobScreenContent()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun JobScreenContent() {
        val navigator = LocalNavigator.currentOrThrow

        val navigateToDetail: (Job) -> Unit = { navigator.push(JobDetailScreen(it)) }
        val viewModel = koinScreenModel<JobScreenModel>()
        val scrollBehavior = LocalScrollBehavior.current

        val query by viewModel.query.collectAsState()
        val isActive by viewModel.searchActive.collectAsState()

        val selectedTopics by viewModel.selectedTopics.collectAsState()
        val savedTopics by viewModel.savedTopics.collectAsState()

        val data = viewModel.jobs.collectAsLazyPagingItems()

        Scaffold {

            LaunchedEffect(isActive) {
                scrollBehavior.state.heightOffset = if (isActive) -1000f else 0f
            }

            Column {
                SearchBar(
                    inputField = {
                        SearchBarField(query, viewModel, isActive)
                    },
                    expanded = isActive,
                    onExpandedChange = { viewModel.onSearchActiveChange(it) },
                    modifier = Modifier
                        .padding(horizontal = if (isActive) 0.dp else 10.dp)
                        .fillMaxWidth(),
                    content = {
                        FilterChipDropdown(
                            modifier = Modifier.padding(8.dp),
                            selectedTopics = selectedTopics,
                            savedTopics = savedTopics,
                            onSelectedTopicChange = viewModel::onTopicSelected,
                            onSavedTopicsChange = viewModel::onSavedTopicsChange
                        )
                    }
                )

                PagingListUI(
                    modifier = Modifier.padding(10.sdp),
                    data = data
                ) { item ->
                    JobItem(job = item) { navigateToDetail(item) }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun SearchBarField(
        query: String,
        viewModel: JobScreenModel,
        isActive: Boolean
    ) {
        SearchBarDefaults.InputField(
            query = query,
            onQueryChange = viewModel::onQueryChange,
            onSearch = {
                viewModel.onSearchActiveChange(false)
            },
            expanded = isActive,
            onExpandedChange = viewModel::onSearchActiveChange,
            placeholder = {
                Text("Search jobs")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                if (isActive) {
                    Icon(
                        modifier = Modifier.noRippleEffect {
                            if (query.isEmpty()) {
                                viewModel.onSearchActiveChange(false)
                            } else {
                                viewModel.onQueryChange("")
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
            }
        )
    }
}
