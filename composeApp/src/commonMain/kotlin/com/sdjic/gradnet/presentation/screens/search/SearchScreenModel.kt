package com.sdjic.gradnet.presentation.screens.search

import androidx.paging.PagingData
import app.cash.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.entity.dto.UserDto
import com.sdjic.gradnet.domain.useCases.GetUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchScreenModel(
    private val getUsersUseCase: GetUsersUseCase
) : ScreenModel {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _users = MutableStateFlow<PagingData<UserDto>>(PagingData.empty())
    val users = _users.asStateFlow()

    private val _searchActive = MutableStateFlow(false)
    val searchActive = _searchActive.asStateFlow()

    init {
        screenModelScope.launch { retrieveUsers() }
    }

    fun onQueryChange(q: String) {
        _query.update { q }
    }

    fun onSearchActiveChange(active: Boolean) {
        if (!active) {
            screenModelScope.launch { retrieveUsers() }
        }
        _searchActive.update { active }
    }

    suspend fun retrieveUsers() {
        getUsersUseCase.invoke(20, _query.value)
            .cachedIn(screenModelScope)
            .collect { pagingData -> _users.update { pagingData } }
    }
}