package com.github.maxastin.scorecounter.features.comingsoon.presentation

import androidx.annotation.DrawableRes
import com.github.maxastin.scorecounter.common.presentation.Base
import com.github.maxastin.scorecounter.shared.domain.model.GameLabel

interface ComingSoon {

    data class State(
        @DrawableRes val image: Int?,
        val subscribed: Boolean
    ): Base.State

    sealed interface Action: Base.Action {
        data class Init(val label: GameLabel) : Action
        data object BackClick : Action
        data class SubscribeClick(val label: GameLabel) : Action
    }

    sealed interface Event: Base.Event {
        data object NavigateBack : Event
    }
}