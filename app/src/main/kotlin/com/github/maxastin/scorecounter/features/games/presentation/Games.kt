package com.github.maxastin.scorecounter.features.games.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.github.maxastin.scorecounter.common.presentation.Base
import com.github.maxastin.scorecounter.shared.domain.model.GameLabel
import kotlinx.collections.immutable.ImmutableList

interface Games {

    data class State(
        val items: ImmutableList<GameItem>
    ) : Base.State

    sealed interface Action : Base.Action {
        data class GameClick(
            val label: GameLabel
        ) : Action
    }

    sealed interface Event : Base.Event {
        data object CheckCamera : Event
        data class OpenComingSoon(val label: GameLabel) : Event
    }
}

data class GameItem(
    val label: GameLabel,
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    val subscribed: Boolean
)

//enum class GameLabel(
//    @DrawableRes val image: Int,
//    @StringRes val title: Int,
//    val isReal: Boolean,
//) {
//    CATAN(
//        image = R.drawable.img_catan,
//        title = R.string.games_catan,
//        isReal = true
//    ),
//    CARCASSONNE(
//        image = R.drawable.img_carcassonne,
//        title = R.string.games_carcassonne,
//        isReal = true
//    ),
//    WINGSPAN(
//        image = R.drawable.img_wingspan,
//        title = R.string.games_wingspan,
//        isReal = true
//    ),
//    SCYTHE(
//        image = R.drawable.img_scythe,
//        title = R.string.games_scythe,
//        isReal = true
//    ),
//    AGRICOLA(
//        image = R.drawable.img_agricola,
//        title = R.string.games_agricola,
//        isReal = true
//    ),
//    CONCORDIA(
//        image = R.drawable.img_concordia,
//        title = R.string.games_concordia,
//        isReal = true
//    ),
//    EVERDELL(
//        image = R.drawable.img_everdell,
//        title = R.string.games_everdell,
//        isReal = true
//    ),
//    CITADELS(
//        image = R.drawable.img_citadels,
//        title = R.string.games_citadels,
//        isReal = true
//    ),
//    BLUEBERRY_FOREST(
//        image = R.drawable.img_blueberry_forest,
//        title = R.string.games_blueberry_forest,
//        isReal = false
//    ),
//    HEDGEHOG_CEMETERY(
//        image = R.drawable.img_hedgehog_cemetery,
//        title = R.string.games_hedgehog_cemetery,
//        isReal = false
//    )
//}