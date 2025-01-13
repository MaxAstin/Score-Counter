package com.github.maxastin.scorecounter.features.main.presentation

import com.github.maxastin.scorecounter.common.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<Main.State, Main.Action, Main.Event>(
    initState = {
        Main.State(showNoCameraPermission = false)
    }
) {

    override fun onAction(action: Main.Action) {
        when (action) {
            Main.Action.CameraPermissionDeny -> {
                setState {
                    copy(showNoCameraPermission = true)
                }
            }

            Main.Action.CameraPermissionAccept -> {
                sendEvent(Main.Event.OpenCamera)
            }

            Main.Action.CloseCameraRequiredDialogClick -> {
                setState {
                    copy(showNoCameraPermission = false)
                }
            }
        }
    }

}