package com.github.maxastin.scorecounter.features.welcome.presentation

import com.github.maxastin.scorecounter.common.presentation.Base

interface Welcome {

    data class State(
        val isChecked: Boolean
    ) : Base.State

    sealed interface Action : Base.Action {
        data object NextClick : Action
    }

    sealed interface Event : Base.Event {
        data object OpenNext : Event
    }
}