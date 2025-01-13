package com.github.maxastin.scorecounter.features.welcome.presentation

import androidx.lifecycle.viewModelScope
import com.github.maxastin.scorecounter.common.presentation.BaseViewModel
import com.github.maxastin.scorecounter.features.welcome.domain.CheckWelcomeSeenUseCase
import com.github.maxastin.scorecounter.features.welcome.domain.SaveWelcomeSeenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val checkWelcomeSeenUseCase: CheckWelcomeSeenUseCase,
    private val saveWelcomeSeenUseCase: SaveWelcomeSeenUseCase,
) : BaseViewModel<Welcome.State, Welcome.Action, Welcome.Event>(
    initState = {
        Welcome.State(isChecked = false)
    }
) {

    init {
        viewModelScope.launch {
            if (checkWelcomeSeenUseCase()) {
                sendEvent(Welcome.Event.OpenNext)
            } else {
                setState {
                    copy(isChecked = true)
                }
            }
        }
    }

    override fun onAction(action: Welcome.Action) {
        when (action) {
            Welcome.Action.NextClick -> {
                viewModelScope.launch {
                    saveWelcomeSeenUseCase(welcomeSeen = true)
                    sendEvent(Welcome.Event.OpenNext)
                }
            }
        }
    }

}