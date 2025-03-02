package com.sdjic.gradnet.presentation.screens.posts

import androidx.paging.PagingData
import app.cash.paging.cachedIn
import app.cash.paging.map
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.useCases.GetPostsUseCase
import com.sdjic.gradnet.domain.useCases.LikePostUseCase
import com.sdjic.gradnet.presentation.core.model.Filter
import com.sdjic.gradnet.presentation.core.model.Post
import com.sdjic.gradnet.presentation.screens.auth.register.model.getUserRoles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostScreenModel(
    private val getPostsUseCase: GetPostsUseCase,
    private val likePostUseCase: LikePostUseCase,
    private val prefs: AppCacheSetting
) : ScreenModel {

    private val _userTypeFilters = MutableStateFlow(
        getUserRoles().map { Filter(it.name, false) }
    )
    val userTypeFilters = _userTypeFilters.asStateFlow()

    private val _selectAllUserTypes = MutableStateFlow(false)
    val selectAllUserTypes = _selectAllUserTypes.asStateFlow()

    private val _showFilterSheet = MutableStateFlow(false)
    val showFilterSheet = _showFilterSheet.asStateFlow()

    private val _posts = MutableStateFlow<PagingData<Post>>(PagingData.empty())
    val posts = _posts.stateIn(
        scope = screenModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = PagingData.empty()
    )

    init {
        toggleSelectAll()
        screenModelScope.launch { refreshPosts() }
    }

    suspend fun refreshPosts() {
        getPostsUseCase(5)
            .cachedIn(screenModelScope)
            .collect { pagingData -> _posts.update { pagingData } }
    }

    fun toggleLike(post: Post) {
        val newPost =  post.copy(
            liked = !post.liked,
            likesCount = if (post.liked) post.likesCount - 1 else post.likesCount + 1
        )

        screenModelScope.launch {
            _posts.value = _posts.value.map { currentPost ->
                if (currentPost.postId == post.postId) newPost else currentPost
            }

            likePostUseCase(post.postId, prefs.accessToken).onError {
                _posts.value = _posts.value.map { currentPost ->
                    if (currentPost.postId == post.postId) post else currentPost
                }
            }
        }
    }

    fun onAction(action: PostScreenAction) = screenModelScope.launch {
        when (action) {
            is PostScreenAction.OnFilterChange -> updateFilter(action.filter)
            is PostScreenAction.OnFilterSheetStateChange -> _showFilterSheet.value = action.show
            PostScreenAction.OnToggleSelectAll -> toggleSelectAll()
            PostScreenAction.OnUpdateFilter -> refreshPosts()
        }
    }

    private fun updateFilter(filter: Filter) {
        _userTypeFilters.update { list ->
            list.map {
                if (it.key == filter.key) {
                    it.copy(value = filter.value)
                } else it
            }
        }
        _selectAllUserTypes.update {
            userTypeFilters.value.all { it.value }
        }
    }

    fun toggleSelectAll() {
        val allSelected = _userTypeFilters.value.all { it.value }
        _selectAllUserTypes.value = allSelected
        _userTypeFilters.update { filters -> filters.map { it.copy(value = allSelected) } }

    }
}