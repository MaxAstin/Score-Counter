package com.github.maxastin.scorecounter.shared.domain.model

data class Game(
    val label: GameLabel,
    val subscribed: Boolean
)

enum class GameLabel(val isReal: Boolean) {
    CATAN(isReal = true),
    CARCASSONNE(isReal = true),
    WINGSPAN(isReal = true),
    SCYTHE(isReal = true),
    AGRICOLA(isReal = true),
    CONCORDIA(isReal = true),
    EVERDELL(isReal = true),
    CITADELS(isReal = true),
    BLUEBERRY_FOREST(isReal = false),
    HEDGEHOG_CEMETERY(isReal = false)
}
