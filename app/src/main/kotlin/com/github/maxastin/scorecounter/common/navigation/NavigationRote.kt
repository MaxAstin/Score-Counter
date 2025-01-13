package com.github.maxastin.scorecounter.common.navigation

import com.github.maxastin.scorecounter.shared.domain.model.GameLabel
import kotlinx.serialization.Serializable

interface NavigationRote {

    @Serializable
    object Welcome

    @Serializable
    object Games

    @Serializable
    data class ComingSoon(val label: GameLabel)

    @Serializable
    object Camera

}