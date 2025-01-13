package com.github.maxastin.scorecounter.common.util

import android.app.Activity
import android.content.Intent
import com.google.android.play.core.review.ReviewManagerFactory

fun Activity.launchInAppReview() {
    val reviewManager = ReviewManagerFactory.create(this)
    val request = reviewManager.requestReviewFlow()
    request.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            reviewManager.launchReviewFlow(this, task.result)
        }
    }
}

fun Activity.openSharing(text: String): Boolean {
    return try {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        val chooser = Intent.createChooser(intent, null)
        startActivity(chooser)
        true
    } catch (exception: Exception) {
        false
    }
}