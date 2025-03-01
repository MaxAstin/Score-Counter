package com.github.maxastin.scorecounter.features.camera

import androidx.lifecycle.viewModelScope
import com.github.maxastin.scorecounter.common.analytics.AnalyticsManager
import com.github.maxastin.scorecounter.common.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val analyticsManager: AnalyticsManager
) : BaseViewModel<Camera.State, Camera.Action, Camera.Event>(
    initState = { Camera.State.Camera }
) {

    override fun onAction(action: Camera.Action) {
        when (action) {
            Camera.Action.ProcessImage -> {
                viewModelScope.launch {
                    setState { Camera.State.Picture(failed = false) }
                    delay(Random.nextLong(1_000, 4_000))
                    setState { Camera.State.Picture(failed = true) }
                }
            }
            Camera.Action.RetryClick -> {
                setState { Camera.State.Camera }
            }
            Camera.Action.CameraError -> {
                analyticsManager.trackCameraError()
            }
        }
    }

}