package com.github.maxastin.scorecounter

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.crashlytics.crashlytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ScoreCounterApp: Application() {

    override fun onCreate() {
        super.onCreate()

        Firebase.analytics.setAnalyticsCollectionEnabled(!BuildConfig.DEBUG)
        Firebase.crashlytics.isCrashlyticsCollectionEnabled = !BuildConfig.DEBUG
    }
}