package com.sdjic.gradnet.presentation.screens.jobs

import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.JobsRepository
import com.sdjic.gradnet.domain.useCases.GetSavedJobUseCase
import com.sdjic.gradnet.presentation.core.model.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class SavedJobScreenModel(
    private val getSavedJobUseCase: GetSavedJobUseCase,
    private val jobRepository: JobsRepository,
    private val prefs: AppCacheSetting
) : ScreenModel {

    private val _jobs = MutableStateFlow<PagingData<Job>>(PagingData.empty())
    val jobs = _jobs.asStateFlow()

    init {
        screenModelScope.launch { refreshPosts() }
    }

    suspend fun refreshPosts() {
        getSavedJobUseCase(5, prefs.accessToken.orEmpty())
            .cachedIn(screenModelScope)
            .distinctUntilChanged()
            .collectLatest { pagingData ->
                println("TAG111 New paging data received")
                _jobs.value = pagingData
            }
    }
/*
    fun toggleSaveJob(job: Job) {
        val newPost = job.copy(
            liked = !post.liked,
            likesCount = if (post.liked) post.likesCount - 1 else post.likesCount + 1
        )

        screenModelScope.launch {
            _jobs.value = _jobs.value.map { currentPost ->
                if (currentPost.postId == post.postId) newPost else currentPost
            }

            saveJobUseCase(post.postId, prefs.accessToken).onError {
                _jobs.value = _jobs.value.map { currentPost ->
                    if (currentPost.postId == post.postId) post else currentPost
                }
            }
        }
    }*/


}