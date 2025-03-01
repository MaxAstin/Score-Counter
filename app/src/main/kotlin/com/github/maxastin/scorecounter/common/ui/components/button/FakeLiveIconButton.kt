package com.github.maxastin.scorecounter.common.ui.components.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.maxastin.scorecounter.R
import com.github.maxastin.scorecounter.common.ui.theme.ScoreCounterTheme
import com.github.maxastin.scorecounter.common.ui.util.rememberMultipleEventsCutter

@Composable
fun ScoreCounterIconButton(
    @DrawableRes iconId: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconTint: Color = ScoreCounterTheme.colors.primary,
    hasMarker: Boolean = false,
    withBorder: Boolean = true,
) {
    val multipleEventsCutter = rememberMultipleEventsCutter()

    IconButton(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .run {
                if (withBorder) {
                    border(
                        width = 1.dp,
                        color = ScoreCounterTheme.colors.primary,
                        shape = RoundedCornerShape(6.dp)
                    )
                } else {
                    this
                }
            }
            .size(48.dp),
        onClick = {
            multipleEventsCutter.processEvent(onClick)
        }
    ) {
        Box {
            Icon(
                modifier = Modifier
                    .padding(2.dp)
                    .size(24.dp),
                painter = painterResource(iconId),
                tint = iconTint,
                contentDescription = contentDescription
            )
            if (hasMarker) {
                Badge(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .border(
                            width = 1.dp,
                            color = ScoreCounterTheme.colors.background,
                            shape = CircleShape
                        )
                        .padding(1.dp),
                    containerColor = ScoreCounterTheme.colors.negative,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ScoreCounterIconButtonPreview() {
    ScoreCounterIconButton(
        iconId = R.drawable.ic_share,
        contentDescription = "",
        onClick = {},
        hasMarker = true,
    )
}