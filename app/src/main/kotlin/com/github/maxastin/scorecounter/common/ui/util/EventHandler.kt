package com.github.maxastin.scorecounter.common.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.github.maxastin.scorecounter.common.presentation.Base
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun <T: Base.Event> Flow<T>.Subscribe(block: (T) -> Unit) {
    LaunchedEffect(Unit) {
        this@Subscribe.onEach { event ->
            block(event)
        }.launchIn(this)
    }
}