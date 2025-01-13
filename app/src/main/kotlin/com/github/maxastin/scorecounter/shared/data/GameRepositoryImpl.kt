package com.github.maxastin.scorecounter.shared.data

import com.github.maxastin.scorecounter.common.domain.KeyValueStorage
import com.github.maxastin.scorecounter.shared.domain.model.Game
import com.github.maxastin.scorecounter.shared.domain.model.GameLabel
import com.github.maxastin.scorecounter.shared.domain.repo.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val keyValueStorage: KeyValueStorage
) : GameRepository {

    private val gameList = MutableStateFlow(
        GameLabel.entries.map { label ->
            Game(
                label = label,
                subscribed = false
            )
        }
    )

    override suspend fun getGames(): Flow<List<Game>> {
        val subscriptions = getSubscriptions()
        gameList.update { list ->
            list.map {  game ->
                game.copy(subscribed = game.label in subscriptions)
            }
        }
        return gameList
    }

    override fun getGameByLabel(label: GameLabel): Game {
        return gameList.value.find {
            it.label == label
        } ?: throw IllegalStateException("Game not found")
    }

    override suspend fun subscribeGame(label: GameLabel) {
        val subscriptions = getSubscriptions()
        val updatedSubscriptions = (subscriptions + label).joinToString(separator = ",") { subscription ->
            subscription.name
        }
        keyValueStorage.saveSubscriptions(subscriptions = updatedSubscriptions)
        gameList.update { list ->
            list.map {  game ->
                game.copy(subscribed = game.subscribed || game.label == label)
            }
        }
    }

    private suspend fun getSubscriptions(): List<GameLabel> {
        return keyValueStorage.getSubscriptions()
            ?.split(',')
            .orEmpty()
            .mapNotNull { label ->
                GameLabel.entries.find { it.name == label }
            }
    }
}