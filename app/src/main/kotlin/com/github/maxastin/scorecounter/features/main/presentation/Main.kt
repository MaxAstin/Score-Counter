package com.github.maxastin.scorecounter.features.main.presentation

import com.github.maxastin.scorecounter.common.presentation.Base

interface Main {

    data class State(
        val showNoCameraPermission: Boolean
    ): Base.State

    sealed interface Action: Base.Action {
        data object CameraPermissionDeny: Action
        data object CameraPermissionAccept: Action
        data object CloseCameraRequiredDialogClick: Action
    }

    sealed interface Event: Base.Event {
        data object OpenCamera: Event
    }

}