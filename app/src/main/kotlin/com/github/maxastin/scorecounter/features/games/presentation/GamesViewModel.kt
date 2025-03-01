package com.github.maxastin.scorecounter.features.games.presentation

import androidx.lifecycle.viewModelScope
import com.github.maxastin.scorecounter.common.analytics.AnalyticsManager
import com.github.maxastin.scorecounter.common.presentation.BaseViewModel
import com.github.maxastin.scorecounter.features.games.domain.GetGamesUseCase
import com.github.maxastin.scorecounter.shared.domain.model.Game
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
    private val analyticsManager: AnalyticsManager,
) : BaseViewModel<Games.State, Games.Action, Games.Event>(
    initState = {
        Games.State(
            items = persistentListOf()
        )
    }
) {

    init {
        viewModelScope.launch {
            getGamesUseCase().collect { games ->
                setState {
                    copy(
                        items = games.map(Game::toGameItem)
                            .toPersistentList()
                    )
                }
            }
        }
    }

    override fun onAction(action: Games.Action) {
        when (action) {
            is Games.Action.GameClick -> {
                analyticsManager.trackGameClick(action.label)
                if (action.label.isReal) {
                    sendEvent(Games.Event.OpenComingSoon(action.label))
                } else {
                    sendEvent(Games.Event.CheckCamera)
                }
            }
            is Games.Action.ShareClick -> {
                analyticsManager.trackShareClick()
                sendEvent(Games.Event.ShowShareDialog)
            }
        }
    }

}