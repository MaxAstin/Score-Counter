package com.github.maxastin.scorecounter.features.games.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.github.maxastin.scorecounter.common.presentation.Base
import com.github.maxastin.scorecounter.shared.domain.model.GameLabel
import kotlinx.collections.immutable.ImmutableList

interface Games {

    data class State(
        val items: ImmutableList<GameItem>
    ) : Base.State

    sealed interface Action : Base.Action {
        data class GameClick(
            val label: GameLabel
        ) : Action
        data object ShareClick : Action
    }

    sealed interface Event : Base.Event {
        data object CheckCamera : Event
        data class OpenComingSoon(val label: GameLabel) : Event
        data object ShowShareDialog : Event
    }
}

data class GameItem(
    val label: GameLabel,
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    val subscribed: Boolean
)