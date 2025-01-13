package com.github.maxastin.scorecounter.common.ui.components.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.maxastin.scorecounter.R
import com.github.maxastin.scorecounter.common.ui.theme.ScoreCounterTheme
import com.github.maxastin.scorecounter.common.ui.util.rememberMultipleEventsCutter

@Composable
fun ScoreCounterDialogButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = ScoreCounterTheme.colors.background,
    textColor: Color = ScoreCounterTheme.colors.border,
    shape: Shape = RoundedCornerShape(6.dp),
    @DrawableRes iconId: Int? = null,
) {
    val multipleEventsCutter = rememberMultipleEventsCutter()

    Button(
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        onClick = {
            multipleEventsCutter.processEvent(onClick)
        },
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                color = textColor,
                style = ScoreCounterTheme.typography.titleSmall,
            )
            if (iconId != null) {
                Image(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(16.dp),
                    painter = painterResource(iconId),
                    contentDescription = "icon"
                )
            }
        }
    }
}

@Preview
@Composable
private fun ScoreCounterDialogButtonPreview() {
    ScoreCounterTheme {
        ScoreCounterDialogButton(
            text = "Button",
            iconId = R.drawable.ic_robot,
            onClick = {}
        )
    }
}