package com.sdjic.gradnet.presentation.screens.posts

import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.di.platform_di.toByteArray
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddPostScreenModel(
    private val postRepository: PostRepository,
    private val pref: AppCacheSetting
) : ScreenModel {

    private val _selectedImages = MutableStateFlow<List<ImageBitmap>>(emptyList())
    val selectedImages: StateFlow<List<ImageBitmap>> = _selectedImages.asStateFlow()

    val message = MutableSharedFlow<Pair<String, Boolean>?>()

    fun onImageSelected(image: ImageBitmap) {
        _selectedImages.update { it + image }
    }

    fun onImageDeselected(image: ImageBitmap) {
        _selectedImages.update { it - image }
    }

    fun showMessage(message: String, isSuccessful: Boolean) {
        this.message.tryEmit(message to isSuccessful)
    }

    fun uploadNewPost(content: String) {
        screenModelScope.launch {
            if (content.isEmpty() or content.isBlank()) {
                showMessage("Message cannot be empty", false)
                return@launch
            }
            val imageBytes = withContext(Dispatchers.IO){
                _selectedImages.value.map { it.toByteArray() }
            }
            postRepository.createNewPost(
                accessToken = pref.accessToken,
                postContent = content,
                location = "",
                files = imageBytes
            )
        }
    }
}