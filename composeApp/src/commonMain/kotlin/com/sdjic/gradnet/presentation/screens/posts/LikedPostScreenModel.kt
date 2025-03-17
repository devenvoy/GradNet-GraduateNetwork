package com.sdjic.gradnet.presentation.screens.posts

import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import app.cash.paging.map
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.useCases.GetLikedPostsUseCase
import com.sdjic.gradnet.domain.useCases.LikePostUseCase
import com.sdjic.gradnet.presentation.core.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class LikedPostScreenModel(
    private val getLikedPostUseCase: GetLikedPostsUseCase,
    private val likedPostScreen: LikePostUseCase,
    private val prefs: AppCacheSetting
) : ScreenModel {

    private val _posts = MutableStateFlow<PagingData<Post>>(PagingData.empty())
    val posts = _posts.asStateFlow()

    init {
        screenModelScope.launch { refreshPosts() }
    }

    suspend fun refreshPosts() {
        getLikedPostUseCase(5, prefs.accessToken)
            .cachedIn(screenModelScope)
            .distinctUntilChanged()
            .collectLatest { pagingData ->
                println("TAG111 New paging data received")
                _posts.value = pagingData
            }
    }

    fun toggleLike(post: Post) {
        val newPost = post.copy(
            liked = !post.liked,
            likesCount = if (post.liked) post.likesCount - 1 else post.likesCount + 1
        )

        screenModelScope.launch {
            _posts.value = _posts.value.map { currentPost ->
                if (currentPost.postId == post.postId) newPost else currentPost
            }

            likedPostScreen(post.postId, prefs.accessToken).onError {
                _posts.value = _posts.value.map { currentPost ->
                    if (currentPost.postId == post.postId) post else currentPost
                }
            }
        }
    }


}