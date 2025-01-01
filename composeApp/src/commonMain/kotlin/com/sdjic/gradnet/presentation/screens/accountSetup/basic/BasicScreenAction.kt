package com.sdjic.gradnet.presentation.screens.accountSetup.basic

sealed interface BasicScreenAction {
    class OnnVrificationFieldValueChange(s:String) : BasicScreenAction
    data object OnVerifyClick : BasicScreenAction
}
