package com.github.maxastin.scorecounter.common.data

import android.content.Context
import androidx.core.content.edit
import com.github.maxastin.scorecounter.common.domain.KeyValueStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val WELCOME_SEEN = "welcome seen"
private const val SUBSCRIPTIONS = "subscriptions"

class SharedPreferencesStorage @Inject constructor(
    @ApplicationContext private val context: Context
) : KeyValueStorage {

    private val sharedPreferences = context.getSharedPreferences("Main", Context.MODE_PRIVATE)

    override suspend fun saveWelcomeSeen(welcomeSeen: Boolean) {
        sharedPreferences.edit {
            putBoolean(WELCOME_SEEN, welcomeSeen)
        }
    }

    override suspend fun saveSubscriptions(subscriptions: String) {
        sharedPreferences.edit {
            putString(SUBSCRIPTIONS, subscriptions)
        }
    }

    override suspend fun getWelcomeSeen(defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(WELCOME_SEEN, defaultValue)
    }

    override suspend fun getSubscriptions(): String? {
        return sharedPreferences.getString(SUBSCRIPTIONS, null)
    }

}