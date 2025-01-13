package com.github.maxastin.scorecounter.features.camera

import com.github.maxastin.scorecounter.common.presentation.Base

interface Camera {

    sealed interface State : Base.State {
        data object Camera : State
        data class Picture(
            val failed: Boolean
        ) : State
    }

    sealed interface Action : Base.Action {
        data object ProcessImage : Action
        data object RetryClick : Action
    }

    object Event : Base.Event
}