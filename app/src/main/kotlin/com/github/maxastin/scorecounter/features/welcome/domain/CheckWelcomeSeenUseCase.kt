package com.github.maxastin.scorecounter.features.welcome.domain

import com.github.maxastin.scorecounter.common.domain.KeyValueStorage
import javax.inject.Inject

class CheckWelcomeSeenUseCase @Inject constructor(
    private val keyValueStorage: KeyValueStorage
) {

    suspend operator fun invoke(): Boolean {
        return keyValueStorage.getWelcomeSeen( defaultValue = false)
    }

}