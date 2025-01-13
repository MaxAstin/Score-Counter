package com.github.maxastin.scorecounter.features.main.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.maxastin.scorecounter.R
import com.github.maxastin.scorecounter.common.ui.preview.LocalePreview
import com.github.maxastin.scorecounter.common.ui.clickableWithoutIndication
import com.github.maxastin.scorecounter.common.ui.components.button.ScoreCounterDialogButton
import com.github.maxastin.scorecounter.common.ui.theme.ScoreCounterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackDialog() {
    BasicAlertDialog(
        onDismissRequest = {
            // TODO dismiss
        }
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(ScoreCounterTheme.colors.background)
                .padding(24.dp)
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(
                        R.string.do_you_like,
                        stringResource(R.string.app_name)
                    ),
                    color = ScoreCounterTheme.colors.onBackground,
                    style = ScoreCounterTheme.typography.titleMedium,
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp)
                        .clickableWithoutIndication {
                            // TODO close
                        },
                    painter = painterResource(R.drawable.ic_close),
                    tint = ScoreCounterTheme.colors.onSurfaceVariant,
                    contentDescription = "close"
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                text = stringResource(
                    R.string.help_us_improve,
                    stringResource(R.string.app_name)
                ),
                color = ScoreCounterTheme.colors.onBackground,
                style = ScoreCounterTheme.typography.bodyMedium,
            )
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .width(160.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.shy_emoji),
                contentDescription = "feedback emoji"
            )
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.End),
                horizontalArrangement = spacedBy(16.dp)
            ) {
                ScoreCounterDialogButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.feedback_yes),
                    backgroundColor = Color(0xFF5BC589),
                    textColor = ScoreCounterTheme.colors.onSurface,
                    iconId = R.drawable.thumbs_up,
                    onClick = {
                        // TODO feedback click
                    },
                )
                ScoreCounterDialogButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.feedback_no),
                    backgroundColor = Color(0xFFDD6962),
                    textColor = ScoreCounterTheme.colors.onSurface,
                    iconId = R.drawable.thumbs_down,
                    onClick = {
                        // TODO feedback click
                    },
                )
            }
        }
    }
}

@LocalePreview
@Composable
private fun FeedbackDialogPreview() {
    FeedbackDialog()
}