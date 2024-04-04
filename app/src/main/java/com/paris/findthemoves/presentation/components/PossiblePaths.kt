package com.paris.findthemoves.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paris.findthemoves.presentation.utils.UIText

@Composable
fun PossiblePaths(
    paths: UIText,
) {
    val state = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(state = state)) {
        Text(text = paths.asString())
    }
}