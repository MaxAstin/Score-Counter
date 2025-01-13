package com.github.maxastin.scorecounter.features.comingsoon.domain


import com.github.maxastin.scorecounter.shared.domain.model.GameLabel
import com.github.maxastin.scorecounter.shared.domain.repo.GameRepository
import javax.inject.Inject

class SubscribeOnGameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend operator fun invoke(label: GameLabel) {
        gameRepository.subscribeGame(label = label)
    }
}