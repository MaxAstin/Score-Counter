package com.github.maxastin.scorecounter.common.ui.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.github.maxastin.scorecounter.R
import com.github.maxastin.scorecounter.common.ui.preview.BooleanProvider
import com.github.maxastin.scorecounter.common.ui.theme.ScoreCounterTheme
import com.github.maxastin.scorecounter.common.ui.util.rememberMultipleEventsCutter

@Composable
fun ScoreCounterPrimaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    startIcon: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val multipleEventsCutter = rememberMultipleEventsCutter()

    Button(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = ScoreCounterTheme.colors.onSurface,
            containerColor = ScoreCounterTheme.colors.primary,
            disabledContentColor = ScoreCounterTheme.colors.onSurface.copy(alpha = 0.5f),
            disabledContainerColor = ScoreCounterTheme.colors.primary.copy(alpha = 0.5f),
        ),
        onClick = {
            multipleEventsCutter.processEvent(onClick)
        },
        enabled = enabled
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            startIcon()
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                color = ScoreCounterTheme.colors.onSurface,
                style = ScoreCounterTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun ScoreCounterPrimaryButtonPreview(@PreviewParameter(BooleanProvider::class) enabled: Boolean) {
    ScoreCounterTheme {
        ScoreCounterPrimaryButton(
            text = "Button",
            onClick = {},
            enabled = enabled
        )
    }
}

@Preview
@Composable
private fun ScoreCounterPrimaryButtonWithIconPreview(@PreviewParameter(BooleanProvider::class) enabled: Boolean) {
    ScoreCounterTheme {
        ScoreCounterPrimaryButton(
            text = "Button",
            onClick = {},
            enabled = enabled,
            startIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_robot),
                    tint = ScoreCounterTheme.colors.onSurface,
                    contentDescription = null
                )
            }
        )
    }
}