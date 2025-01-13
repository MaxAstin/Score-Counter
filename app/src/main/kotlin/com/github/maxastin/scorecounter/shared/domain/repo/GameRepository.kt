package com.github.maxastin.scorecounter.shared.domain.repo

import com.github.maxastin.scorecounter.shared.domain.model.Game
import com.github.maxastin.scorecounter.shared.domain.model.GameLabel
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    fun getGameByLabel(label: GameLabel): Game

    suspend fun getGames(): Flow<List<Game>>

    suspend fun subscribeGame(label: GameLabel)

}