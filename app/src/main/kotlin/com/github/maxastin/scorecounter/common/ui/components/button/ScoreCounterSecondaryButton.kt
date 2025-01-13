package com.github.maxastin.scorecounter.common.ui.components.button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.maxastin.scorecounter.common.ui.theme.ScoreCounterTheme
import com.github.maxastin.scorecounter.common.ui.util.rememberMultipleEventsCutter

@Composable
fun ScoreCounterSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val multipleEventsCutter = rememberMultipleEventsCutter()

    Button(
        modifier = modifier,
        shape = RoundedCornerShape(32.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = {
            multipleEventsCutter.processEvent(onClick)
        },
    ) {
        Text(
            text = text,
            color = ScoreCounterTheme.colors.onSurfaceVariant,
            style = ScoreCounterTheme.typography.titleSmall,
        )
    }
}

@Preview
@Composable
private fun ScoreCounterSecondaryButtonPreview() {
    ScoreCounterTheme {
        ScoreCounterSecondaryButton(
            text = "Button",
            onClick = {},
        )
    }
}