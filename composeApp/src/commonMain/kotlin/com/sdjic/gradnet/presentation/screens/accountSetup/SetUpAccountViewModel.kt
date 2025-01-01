package com.sdjic.gradnet.presentation.screens.accountSetup

import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SetUpAccountViewModel : ScreenModel{

    private val _basicState = MutableStateFlow(BasicState())
    val basicState = _basicState.asStateFlow()



}
