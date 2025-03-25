package com.sdjic.gradnet.presentation.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import com.sdjic.gradnet.data.network.entity.dto.UserDto
import com.sdjic.gradnet.presentation.composables.filter.UserRoleChip
import com.sdjic.gradnet.presentation.composables.images.CircularProfileImage
import com.sdjic.gradnet.presentation.helper.CashPagingGridUi
import com.sdjic.gradnet.presentation.helper.LocalRootNavigator
import com.sdjic.gradnet.presentation.helper.LocalScrollBehavior
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import com.sdjic.gradnet.presentation.screens.profile.ProfileScreen
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import network.chaintech.kmp_date_time_picker.utils.noRippleEffect

class SearchScreen : Screen {
    @Composable
    override fun Content() {
        SearchScreenContent()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchScreenContent() {
        val scope = rememberCoroutineScope()
        val pullToRefreshState = rememberPullToRefreshState()
        val navigator = LocalRootNavigator.current
        val scrollBehavior = LocalScrollBehavior.current
        val viewModel = koinScreenModel<SearchScreenModel>()

        val navigateToDetail: (String) -> Unit = { navigator.push(ProfileScreen(it)) }

        val query by viewModel.query.collectAsState()
        val isActive by viewModel.searchActive.collectAsState()
        val data = viewModel.users.collectAsLazyPagingItems()

        var hasLoadedOnce by remember { mutableStateOf(false) }

        val isRefreshing by remember {
            derivedStateOf {
                if (!hasLoadedOnce) false
                else data.loadState.refresh is LoadState.Loading
            }
        }

        LaunchedEffect(data.loadState) {
            if (data.loadState.refresh is LoadState.NotLoading) {
                hasLoadedOnce = true
            }
        }

        Scaffold {

            LaunchedEffect(isActive) {
                scrollBehavior.state.heightOffset = if (isActive) -1000f else 0f
            }

            Column {
                SearchBar(
                    inputField = {
                        SearchBarField(query = query, viewModel = viewModel, isActive = isActive)
                    },
                    expanded = isActive,
                    onExpandedChange = {
                        viewModel.onSearchActiveChange(it)
                    },
                    modifier = Modifier
                        .padding(horizontal = if (isActive) 0.dp else 10.dp)
                        .fillMaxWidth(),
                    content = {
                    }
                )

                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    state = pullToRefreshState,
                    onRefresh = { data.refresh() }
                ) {
                    CashPagingGridUi(
                        paddingValues = PaddingValues(10.dp),
                        data = data,
                        state = rememberLazyGridState()
                    ) { item, modifier ->
                        item?.let {
                            UserItem(item) {
                                navigateToDetail(item.userId)
                            }
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun UserItem(user: UserDto, onClick: () -> Unit) {
        Card(
            onClick = onClick,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                CircularProfileImage(
                    placeHolderName = user.username,
                    data = user.profilePic
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = displayFontFamily(),
                    text = user.username, style = MaterialTheme.typography.bodyLarge
                )
                UserRoleChip(
                    userRole = UserRole.getUserRole(user.userType) ?: UserRole.Alumni
                )
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun SearchBarField(
        viewModel: SearchScreenModel,
        query: String,
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
                Text("Search users")
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