package com.github.maxastin.scorecounter.features.comingsoon.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.github.maxastin.scorecounter.R
import com.github.maxastin.scorecounter.common.ui.clickableWithoutIndication
import com.github.maxastin.scorecounter.common.ui.components.button.ScoreCounterPrimaryButton
import com.github.maxastin.scorecounter.common.ui.preview.BooleanProvider
import com.github.maxastin.scorecounter.common.ui.preview.LocalePreview
import com.github.maxastin.scorecounter.common.ui.theme.ScoreCounterTheme
import com.github.maxastin.scorecounter.common.ui.theme.bold
import com.github.maxastin.scorecounter.common.ui.util.Subscribe
import com.github.maxastin.scorecounter.features.comingsoon.presentation.ComingSoon
import com.github.maxastin.scorecounter.features.comingsoon.presentation.ComingSoonViewModel
import com.github.maxastin.scorecounter.shared.domain.model.GameLabel

@Composable
fun ComingSoonScreen(
    label: GameLabel,
    navController: NavHostController
) {
    val viewModel: ComingSoonViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onAction = remember {
        { action: ComingSoon.Action ->
            viewModel.onAction(action)
        }
    }

    BackHandler {
        onAction(ComingSoon.Action.BackClick)
    }

    LaunchedEffect(Unit) {
        viewModel.onAction(ComingSoon.Action.Init(label = label))
    }
    viewModel.event.Subscribe { event ->
        when (event) {
            is ComingSoon.Event.NavigateBack -> {
                navController.popBackStack()
            }
        }
    }

    ComingSoonContent(
        state = state,
        onAction = onAction
    )
}

@Composable
private fun ComingSoonContent(
    state: ComingSoon.State,
    onAction: (ComingSoon.Action) -> Unit
) {
    Column(modifier = Modifier.padding(32.dp)) {
        Box {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(24.dp)
                    .clickableWithoutIndication {
                        onAction(ComingSoon.Action.BackClick)
                    },
                imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                contentDescription = "Back",
                tint = ScoreCounterTheme.colors.onBackground,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.coming_soon_title),
                style = ScoreCounterTheme.typography.titleLarge.bold,
                textAlign = TextAlign.Center
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            state.image?.let { image ->
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp)),
                    painter = painterResource(image),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = "Robot"
                )
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            text = stringResource(id = R.string.coming_soon_description),
            style = ScoreCounterTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify
        )
        ScoreCounterPrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            text = if (state.subscribed) {
                R.string.coming_soon_subscribed
            } else {
                R.string.coming_soon_subscribe
            }.let { id ->
                stringResource(id = id)
            },
            onClick = { onAction(ComingSoon.Action.SubscribeClick) },
            enabled = !state.subscribed,
            startIcon = {
                if (state.subscribed) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_check_in_circle),
                        tint = ScoreCounterTheme.colors.onSurface,
                        contentDescription = null
                    )
                }
            }
        )
    }
}

@LocalePreview
@Composable
fun ComingSoonScreenPreview(@PreviewParameter(BooleanProvider::class) subscribed: Boolean) {
    ScoreCounterTheme {
        ComingSoonContent(
            state = ComingSoon.State(
                label = GameLabel.CATAN,
                image = R.drawable.img_catan,
                subscribed = subscribed
            ),
            onAction = {}
        )
    }
}