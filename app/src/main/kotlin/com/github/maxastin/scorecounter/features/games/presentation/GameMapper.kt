package com.github.maxastin.scorecounter.features.games.presentation

import com.github.maxastin.scorecounter.R
import com.github.maxastin.scorecounter.shared.domain.model.Game
import com.github.maxastin.scorecounter.shared.domain.model.GameLabel

private val gameImageMap = mapOf(
    GameLabel.CATAN to R.drawable.img_catan,
    GameLabel.CARCASSONNE to R.drawable.img_carcassonne,
    GameLabel.WINGSPAN to R.drawable.img_wingspan,
    GameLabel.SCYTHE to R.drawable.img_scythe,
    GameLabel.AGRICOLA to R.drawable.img_agricola,
    GameLabel.CONCORDIA to R.drawable.img_concordia,
    GameLabel.EVERDELL to R.drawable.img_everdell,
    GameLabel.CITADELS to R.drawable.img_citadels,
    GameLabel.BLUEBERRY_FOREST to R.drawable.img_blueberry_forest,
    GameLabel.HEDGEHOG_CEMETERY to R.drawable.img_hedgehog_cemetery
)

private val gameTitleMap = mapOf(
    GameLabel.CATAN to R.string.games_catan,
    GameLabel.CARCASSONNE to R.string.games_carcassonne,
    GameLabel.WINGSPAN to R.string.games_wingspan,
    GameLabel.SCYTHE to R.string.games_scythe,
    GameLabel.AGRICOLA to R.string.games_agricola,
    GameLabel.CONCORDIA to R.string.games_concordia,
    GameLabel.EVERDELL to R.string.games_everdell,
    GameLabel.CITADELS to R.string.games_citadels,
    GameLabel.BLUEBERRY_FOREST to R.string.games_blueberry_forest,
    GameLabel.HEDGEHOG_CEMETERY to R.string.games_hedgehog_cemetery
)

fun Game.toGameItem(): GameItem {
    return GameItem(
        label = label,
        image = gameImageMap[label] ?: R.drawable.img_catan,
        title = gameTitleMap[label] ?: R.string.games_catan,
        subscribed = subscribed
    )
}