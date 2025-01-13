package com.github.maxastin.scorecounter.common.domain

interface KeyValueStorage {

    suspend fun saveWelcomeSeen(welcomeSeen: Boolean)
    suspend fun saveSubscriptions(subscriptions: String)

    suspend fun getWelcomeSeen(defaultValue: Boolean): Boolean
    suspend fun getSubscriptions(): String?

}