package com.github.maxastin.scorecounter.features.comingsoon.presentation

import androidx.lifecycle.viewModelScope
import com.github.maxastin.scorecounter.common.analytics.AnalyticsManager
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
    private val subscribeOnGameUseCase: SubscribeOnGameUseCase,
    private val analyticsManager: AnalyticsManager,
) : BaseViewModel<ComingSoon.State, ComingSoon.Action, ComingSoon.Event>(
    initState = {
        ComingSoon.State(
            label = null,
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
                        label = action.label,
                        image = game.toGameItem().image,
                        subscribed = game.subscribed
                    )
                }
            }

            ComingSoon.Action.BackClick -> {
                val label = state.value.label ?: return
                analyticsManager.trackGoBackFromSoon(label)
                sendEvent(ComingSoon.Event.NavigateBack)
            }

            is ComingSoon.Action.SubscribeClick -> {
                val label = state.value.label ?: return
                analyticsManager.trackSubscribeClick(label)
                setState {
                    copy(subscribed = true)
                }
                viewModelScope.launch {
                    subscribeOnGameUseCase(label = label)
                }
            }
        }
    }

}