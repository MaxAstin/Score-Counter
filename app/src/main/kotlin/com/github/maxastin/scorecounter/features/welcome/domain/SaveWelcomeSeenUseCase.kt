package com.github.maxastin.scorecounter.features.welcome.domain

import com.github.maxastin.scorecounter.common.domain.KeyValueStorage
import javax.inject.Inject

class SaveWelcomeSeenUseCase @Inject constructor(
    private val keyValueStorage: KeyValueStorage
) {

    suspend operator fun invoke(welcomeSeen: Boolean) {
        return keyValueStorage.saveWelcomeSeen(welcomeSeen = welcomeSeen)
    }

}