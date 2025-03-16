package com.sdjic.gradnet.presentation.screens.jobs

import androidx.paging.PagingData
import app.cash.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.domain.useCases.GetJobsUseCase
import com.sdjic.gradnet.presentation.core.model.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JobScreenModel(
    private val getJobsUseCase: GetJobsUseCase
) : ScreenModel {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _jobs = MutableStateFlow<PagingData<Job>>(PagingData.empty())
    val jobs = _jobs.asStateFlow()

    private val _searchActive = MutableStateFlow(false)
    val searchActive = _searchActive.asStateFlow()

    private val _selectedTopics = MutableStateFlow(setOf<String>())
    val selectedTopics = _selectedTopics.asStateFlow()

    private val _savedTopics = MutableStateFlow(setOf<String>())
    val savedTopics = _savedTopics.asStateFlow()

    init {
        _savedTopics.update {
            setOf(
                "Work", "Hobby", "Personal", "Office", "Workout","Fulltime","Parttime","Remote","Internship","Contract","Hybrid"
            )
        }
        screenModelScope.launch { fetchJobs() }
    }

    fun onQueryChange(q: String) {
        _query.update { q }
    }

    fun onSearchActiveChange(active: Boolean) {
        _searchActive.update { active }
    }

    fun onTopicSelected(strings: Set<String>) {
        _selectedTopics.update { strings }
    }

    fun onSavedTopicsChange(strings: Set<String>) {
        _savedTopics.update { strings }
    }

   suspend fun fetchJobs(){
       getJobsUseCase.invoke(20,_selectedTopics.value.toList())
           .cachedIn(screenModelScope)
           .collect { pagingData -> _jobs.update { pagingData } }
    }
}