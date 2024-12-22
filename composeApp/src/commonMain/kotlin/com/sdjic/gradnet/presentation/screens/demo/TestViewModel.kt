package com.sdjic.gradnet.presentation.screens.demo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.local.entity.Test
import com.sdjic.gradnet.domain.repo.TestRepository
import kotlinx.coroutines.launch

class TestViewModel(private val testRepository: TestRepository) : ScreenModel {

    private val _testLists = mutableStateOf(emptyList<Test>())
    val testLists: State<List<Test>> = _testLists

    val counter = mutableStateOf(1)

    init {
        screenModelScope.launch {
            testRepository.fetchFList().collect {
                _testLists.value = it
            }
        }
    }

    fun increment() {
        counter.value++
    }

    fun addTest() {
        screenModelScope.launch {
            testRepository.insertAll(listOf(Test(0, "Test${counter.value}")))
        }
    }
}