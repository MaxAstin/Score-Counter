package com.github.maxastin.scorecounter.features.comingsoon.domain

import com.github.maxastin.scorecounter.shared.domain.model.Game
import com.github.maxastin.scorecounter.shared.domain.model.GameLabel
import com.github.maxastin.scorecounter.shared.domain.repo.GameRepository
import javax.inject.Inject

class GetGameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    operator fun invoke(label: GameLabel): Game {
        return gameRepository.getGameByLabel(label = label)
    }

}