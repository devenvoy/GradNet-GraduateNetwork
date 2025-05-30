package com.sdjic.gradnet.presentation.screens.posts

import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import co.touchlab.kermit.Logger
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.di.platform_di.toByteArray
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.location.LocationRepository
import com.sdjic.gradnet.domain.repo.PostRepository
import com.sdjic.gradnet.presentation.core.model.UserProfile
import com.sdjic.gradnet.presentation.helper.UiState
import com.sdjic.gradnet.presentation.helper.UploadDialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddPostScreenModel(
    private val postRepository: PostRepository,
    private val locationRepository: LocationRepository,
    private val pref: AppCacheSetting
) : ScreenModel {

    private val _selectedImages = MutableStateFlow<List<ImageBitmap>>(emptyList())
    val selectedImages: StateFlow<List<ImageBitmap>> = _selectedImages.asStateFlow()

    private val _uiState = MutableStateFlow<AddPostState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _uploadDialogState = MutableStateFlow<UploadDialogState>(UploadDialogState.Idle)
    val uploadDialogState: StateFlow<UploadDialogState> = _uploadDialogState.asStateFlow()

    private val _user = MutableStateFlow(pref.getUserProfile())
    val user: StateFlow<UserProfile> = _user.asStateFlow()

    fun onImageSelected(image: ImageBitmap) {
        _selectedImages.update { it + image }
    }

    fun onImageDeselected(image: ImageBitmap) {
        _selectedImages.update { it - image }
    }

    fun uploadNewPost(content: String) {
        screenModelScope.launch {
            _uiState.update { UiState.Loading }

            if (content.isBlank()) {
                _uiState.update { UiState.Error("Message can't be empty") }
                return@launch
            }

            _uploadDialogState.update { UploadDialogState.Starting }

            val imageBytes = withContext(Dispatchers.IO) {
                _selectedImages.value.map { it.toByteArray() }
            }

            val result = postRepository.createNewPost(
                accessToken = pref.accessToken.toString(),
                postContent = content,
                location = locationRepository.getCurrentLocation().toString(),
                files = imageBytes
            ) { bytesSentTotal, contentLength ->
                if (contentLength != null) {
                    val progress = bytesSentTotal.toFloat() / contentLength.toFloat()
                    _uploadDialogState.value = UploadDialogState.Progress(progress)
                }
                Logger.i("Progress: $bytesSentTotal/$contentLength")
            }

            result.onSuccess { r ->
                _uiState.update { UiState.Success(r.detail) }
                _uploadDialogState.update { UploadDialogState.Success(r.detail) }
            }.onError { e ->
                _uiState.update { UiState.Error(e.detail) }
                _uploadDialogState.update { UploadDialogState.Error(e.detail) }
            }
        }
    }
}