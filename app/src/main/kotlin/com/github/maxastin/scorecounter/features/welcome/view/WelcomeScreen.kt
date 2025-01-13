package com.github.maxastin.scorecounter.features.welcome.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.github.maxastin.scorecounter.R
import com.github.maxastin.scorecounter.common.navigation.NavigationRote
import com.github.maxastin.scorecounter.common.ui.preview.LocalePreview
import com.github.maxastin.scorecounter.common.ui.components.button.ScoreCounterPrimaryButton
import com.github.maxastin.scorecounter.common.ui.theme.ScoreCounterTheme
import com.github.maxastin.scorecounter.common.ui.theme.bold
import com.github.maxastin.scorecounter.features.welcome.presentation.Welcome
import com.github.maxastin.scorecounter.features.welcome.presentation.WelcomeViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun WelcomeScreen(navController: NavHostController) {
    val viewModel: WelcomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onAction = remember {
        { action: Welcome.Action ->
            viewModel.onAction(action)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.event.onEach { event ->
            when (event) {
                is Welcome.Event.OpenNext -> {
                    navController.navigate(route = NavigationRote.Games) {
                        popUpTo(NavigationRote.Welcome) {
                            inclusive = true
                        }
                    }
                }
            }
        }.launchIn(this)
    }

    IntroContent(
        isChecked = state.isChecked,
        onAction = onAction
    )
}

@Composable
private fun IntroContent(
    isChecked: Boolean,
    onAction: (Welcome.Action) -> Unit
) {
    if (isChecked) {
        Column(
            modifier = Modifier
                .background(ScoreCounterTheme.colors.background)
                .padding(32.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = spacedBy(32.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.intro_welcome),
                    style = ScoreCounterTheme.typography.titleLarge.bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(R.drawable.ic_picture),
                    contentDescription = "Logo"
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.intro_description),
                    style = ScoreCounterTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify
                )
            }
            ScoreCounterPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                text = stringResource(id = R.string.common_got_it),
                onClick = {
                    onAction(Welcome.Action.NextClick)
                },
            )
        }
    } else {
        Box(modifier = Modifier.fillMaxSize())
    }
}

@LocalePreview
@Composable
fun IntroScreenPreview() {
    IntroContent(
        isChecked = true,
        onAction = {}
    )
}