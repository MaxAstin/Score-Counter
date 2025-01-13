package com.github.maxastin.scorecounter.common.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class BooleanProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(true, false)
}