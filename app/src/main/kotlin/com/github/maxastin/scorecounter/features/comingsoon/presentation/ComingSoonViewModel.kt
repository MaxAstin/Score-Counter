package com.github.maxastin.scorecounter.features.comingsoon.presentation

import androidx.lifecycle.viewModelScope
import com.github.maxastin.scorecounter.common.presentation.BaseViewModel
import com.github.maxastin.scorecounter.features.comingsoon.domain.GetGameUseCase
import com.github.maxastin.scorecounter.features.comingsoon.domain.SubscribeOnGameUseCase
import com.github.maxastin.scorecounter.features.games.presentation.toGameItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComingSoonViewModel @Inject constructor(
    private val getGameUseCase: GetGameUseCase,
    private val subscribeOnGameUseCase: SubscribeOnGameUseCase
) : BaseViewModel<ComingSoon.State, ComingSoon.Action, ComingSoon.Event>(
    initState = {
        ComingSoon.State(
            image = null,
            subscribed = false,
        )
    }
) {

    override fun onAction(action: ComingSoon.Action) {
        when (action) {
            is ComingSoon.Action.Init -> {
                val game = getGameUseCase(label = action.label)
                setState {
                    copy(
                        image = game.toGameItem().image,
                        subscribed = game.subscribed
                    )
                }
            }

            ComingSoon.Action.BackClick -> {
                // TODO send event
                sendEvent(ComingSoon.Event.NavigateBack)
            }

            is ComingSoon.Action.SubscribeClick -> {
                setState {
                    copy(subscribed = true)
                }
                viewModelScope.launch {
                    subscribeOnGameUseCase(label = action.label)
                }
            }
        }
    }

}