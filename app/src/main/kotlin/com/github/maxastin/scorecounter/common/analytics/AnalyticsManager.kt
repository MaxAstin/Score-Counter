package com.github.maxastin.scorecounter.common.analytics

import android.util.Log
import com.github.maxastin.scorecounter.shared.domain.model.GameLabel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import javax.inject.Inject
import javax.inject.Singleton

private const val GAME_CLICK_EVENT_PREFIX = "game_click_"
private const val SHARE_CLICK_EVENT = "share_click"
private const val CAMERA_ERROR_EVENT = "camera_error"
private const val GO_BACK_FROM_SOON_EVENT_PREFIX = "go_back_from_soon_"
private const val SUBSCRIBE_CLICK_EVENT_PREFIX = "subscribe_click_"

private const val ANALYTICS_TAG = "analytics"

@Singleton
class AnalyticsManager @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) {

    fun trackGameClick(gameLabel: GameLabel) {
        trackEvent(event = "$GAME_CLICK_EVENT_PREFIX${gameLabel.name}")
    }

    fun trackShareClick() {
        trackEvent(event = SHARE_CLICK_EVENT)
    }

    fun trackCameraError() {
        trackEvent(event = CAMERA_ERROR_EVENT)
    }

    fun trackGoBackFromSoon(gameLabel: GameLabel) {
        trackEvent(event = "$GO_BACK_FROM_SOON_EVENT_PREFIX${gameLabel.name}")
    }

    fun trackSubscribeClick(gameLabel: GameLabel) {
        trackEvent(event = "$SUBSCRIBE_CLICK_EVENT_PREFIX${gameLabel.name}")
    }

    private fun trackEvent(event: String, params: Map<String, Any> = emptyMap()) {
        firebaseAnalytics.logEvent(event) {
            params.forEach { (key, value) ->
                when (value) {
                    is String -> param(key, value)
                    is Long -> param(key, value)
                    is Int -> param(key, value.toLong())
                    else -> {
                        // Not supported
                    }
                }
            }
        }
        Log.d(ANALYTICS_TAG, event)
    }

}