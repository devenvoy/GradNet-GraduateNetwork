package com.sdjic.gradnet.presentation.screens.setting.lost_found

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.repo.GeneralRepository
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.presentation.helper.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

typealias LostAndFoundNewsState = UiState<String>

class LostItemReportScreenModel(
    private val generalRepository: GeneralRepository,
    private val pref: AppCacheSetting
) : ScreenModel {

    var descriptionText = mutableStateOf(TextFieldValue(""))
    var selectedImage = mutableStateOf<ImageBitmap?>(null)

    private val _lostItemNewsState = MutableStateFlow<LostAndFoundNewsState>(UiState.Idle)
    val lostItemNewsState = _lostItemNewsState.asStateFlow()


    fun submitFeedback() {
        screenModelScope.launch {
            _lostItemNewsState.value = UiState.Loading
            if (descriptionText.value.text.isBlank()) {
                _lostItemNewsState.value = UiState.Error("Please Fill Form before submitting")
                return@launch
            }
            val description = descriptionText.value.text
            val response = generalRepository.submitLostItemReport(
                image = selectedImage.value,
                description = description,
                accessToken = pref.accessToken.toString(),
                listener = null
            )
            response.onSuccess {
                _lostItemNewsState.value = UiState.Success(it.detail)
            }.onError {
                _lostItemNewsState.value = UiState.Error(it.detail)
            }
        }
    }

    fun onImageSelected(imageBitmap: ImageBitmap) {
        selectedImage.value = imageBitmap
    }
}