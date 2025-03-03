package com.github.maxastin.scorecounter.features.games.view

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.github.maxastin.scorecounter.R
import com.github.maxastin.scorecounter.common.navigation.NavigationRote
import com.github.maxastin.scorecounter.common.ui.clickableWithoutIndication
import com.github.maxastin.scorecounter.common.ui.components.button.ScoreCounterIconButton
import com.github.maxastin.scorecounter.common.ui.preview.LocalePreview
import com.github.maxastin.scorecounter.common.ui.theme.ScoreCounterTheme
import com.github.maxastin.scorecounter.common.ui.theme.bold
import com.github.maxastin.scorecounter.common.ui.util.Subscribe
import com.github.maxastin.scorecounter.features.games.presentation.GameItem
import com.github.maxastin.scorecounter.features.games.presentation.Games
import com.github.maxastin.scorecounter.features.games.presentation.GamesViewModel
import com.github.maxastin.scorecounter.shared.domain.model.GameLabel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

private const val GOOGLE_PLAY_LINK = "https://play.google.com/store/apps/details?id=com.github.maxastin.scorecounter"

@Composable
fun GamesScreen(
    navController: NavHostController,
    onCameraRequired: () -> Unit
) {

    val viewModel: GamesViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onAction = remember {
        { action: Games.Action ->
            viewModel.onAction(action)
        }
    }

    val context = LocalContext.current
    viewModel.event.Subscribe { event ->
        when (event) {
            Games.Event.CheckCamera -> {
                onCameraRequired()
            }

            is Games.Event.OpenComingSoon -> {
                navController.navigate(
                    NavigationRote.ComingSoon(label = event.label)
                )
            }

            Games.Event.ShowShareDialog -> {
                context.showShareDialog(
                    text = context.resources.getString(
                        R.string.sharing_text,
                        context.resources.getString(R.string.app_name),
                        GOOGLE_PLAY_LINK
                    )
                )
            }
        }
    }

    GamesContent(
        items = state.items,
        onAction = onAction
    )
}

@Composable
private fun GamesContent(
    items: ImmutableList<GameItem>,
    onAction: (Games.Action) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .padding(
                    horizontal = 32.dp,
                    vertical = 16.dp
                )
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(id = R.string.games_title),
                style = ScoreCounterTheme.typography.titleLarge.bold
            )
            ScoreCounterIconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                iconId = R.drawable.ic_share,
                contentDescription = "share",
                onClick = {
                    onAction(Games.Action.ShareClick)
                }
            )
        }

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(32.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = spacedBy(16.dp),
            verticalArrangement = spacedBy(16.dp),
        ) {
            items(items) { item ->
                GameItem(
                    item = item,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun GameItem(
    item: GameItem,
    onAction: (Games.Action) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickableWithoutIndication {
                onAction(Games.Action.GameClick(label = item.label))
            }
    ) {
        Box(modifier = Modifier.clip(RoundedCornerShape(16.dp))) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = item.image),
                contentDescription = null
            )
            if (item.subscribed) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(ScoreCounterTheme.colors.background)
                        .padding(4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.ic_check_in_circle),
                        tint = ScoreCounterTheme.colors.positive,
                        contentDescription = null,
                    )
                }
            }
        }
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            text = stringResource(item.title),
            style = ScoreCounterTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

private fun Context.showShareDialog(text: String) {
    try {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        val chooser = Intent.createChooser(intent, null)
        startActivity(chooser)
    } catch (exception: Exception) {
        // TODO: handle exception
    }
}

@LocalePreview
@Composable
fun IntroScreenPreview() {
    GamesContent(
        items = listOf(
            GameItem(
                label = GameLabel.CATAN,
                image = R.drawable.img_catan,
                title = R.string.games_catan,
                subscribed = false
            ),
            GameItem(
                label = GameLabel.CARCASSONNE,
                image = R.drawable.img_carcassonne,
                title = R.string.games_carcassonne,
                subscribed = false
            ),
            GameItem(
                label = GameLabel.WINGSPAN,
                image = R.drawable.img_wingspan,
                title = R.string.games_wingspan,
                subscribed = true
            ),
            GameItem(
                label = GameLabel.CITADELS,
                image = R.drawable.img_citadels,
                title = R.string.games_citadels,
                subscribed = false
            )
        ).toPersistentList(),
        onAction = {}
    )
}