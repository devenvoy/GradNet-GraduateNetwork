package com.sdjic.gradnet.presentation.screens.jobs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.sdjic.gradnet.presentation.core.model.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class JobScreenModel : ScreenModel {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _jobs = MutableStateFlow<List<Job>>(emptyList())
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
                "Work", "Hobby", "Personal", "Office", "Workout"
            )
        }
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


}