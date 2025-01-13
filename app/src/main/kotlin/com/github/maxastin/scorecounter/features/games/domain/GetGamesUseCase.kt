package com.github.maxastin.scorecounter.features.games.domain

import com.github.maxastin.scorecounter.shared.domain.model.Game
import com.github.maxastin.scorecounter.shared.domain.repo.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend operator fun invoke(): Flow<List<Game>> {
       return gameRepository.getGames()
    }
}