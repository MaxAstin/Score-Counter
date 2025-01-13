package com.github.maxastin.scorecounter.features.main.view

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.maxastin.scorecounter.R
import com.github.maxastin.scorecounter.common.ui.preview.LocalePreview
import com.github.maxastin.scorecounter.common.ui.components.button.ScoreCounterSecondaryButton
import com.github.maxastin.scorecounter.common.ui.theme.ScoreCounterTheme
import com.github.maxastin.scorecounter.features.main.presentation.Main

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraIsRequiredDialog(onAction: (Main.Action) -> Unit) {
    BasicAlertDialog(
        onDismissRequest = {
            onAction(Main.Action.CloseCameraRequiredDialogClick)
        }
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(ScoreCounterTheme.colors.background)
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.required_camera_permisseon_title,
                    stringResource(R.string.app_name)
                ),
                color = ScoreCounterTheme.colors.onBackground,
                style = ScoreCounterTheme.typography.titleMedium,
            )
            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.required_camera_permisseon_body),
                color = ScoreCounterTheme.colors.onBackground,
                style = ScoreCounterTheme.typography.bodyMedium,
            )

            val context = LocalContext.current
            ScoreCounterSecondaryButton(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.End),
                text = stringResource(R.string.settings),
                onClick = {
                    context.startActivity(
                        Intent().apply {
                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                    )
                },
            )
        }
    }
}

@LocalePreview
@Composable
private fun CameraIsRequiredDialogPreview() {
    ScoreCounterTheme {
        CameraIsRequiredDialog(onAction = {})
    }
}