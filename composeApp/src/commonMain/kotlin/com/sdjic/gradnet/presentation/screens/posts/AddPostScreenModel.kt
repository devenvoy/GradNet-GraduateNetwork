package com.sdjic.gradnet.presentation.screens.posts

import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddPostScreenModel : ScreenModel {

    private val _selectedImages = MutableStateFlow<List<ImageBitmap>>(emptyList())
    val selectedImages: StateFlow<List<ImageBitmap>> = _selectedImages.asStateFlow()

    val message = MutableSharedFlow<Pair<String,Boolean>?>()

    fun onImageSelected(image: ImageBitmap) {
        _selectedImages.update { it + image }
    }

    fun onImageDeselected(image: ImageBitmap) {
        _selectedImages.update { it - image }
    }

    fun showMessage(message: String, isSuccessful: Boolean) {
        this.message.tryEmit(message to isSuccessful)
    }

}