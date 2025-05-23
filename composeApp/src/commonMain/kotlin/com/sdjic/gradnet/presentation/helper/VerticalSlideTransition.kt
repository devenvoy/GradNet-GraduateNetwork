package com.sdjic.gradnet.presentation.helper

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.ui.unit.IntOffset
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.transitions.ScreenTransition
import com.maxkeppeker.sheets.core.utils.JvmSerializable

@OptIn(ExperimentalVoyagerApi::class)
class VerticalSlideTransition : ScreenTransition, JvmSerializable {

    override fun enter(lastEvent: StackEvent): EnterTransition {
        return slideIn { size ->
            val y = if (lastEvent == StackEvent.Pop) -size.height else size.height
            IntOffset(x = 0, y = y)
        }
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        return slideOut { size ->
            val y = if (lastEvent == StackEvent.Pop) size.height else -size.height
            IntOffset(x = 0, y = y)
        }
    }
}
