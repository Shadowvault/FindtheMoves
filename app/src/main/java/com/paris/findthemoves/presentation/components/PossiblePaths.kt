package com.paris.findthemoves.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PossiblePaths(
    paths: List<List<Pair<Int, Int>>>,
) {
    if (paths.isNotEmpty()) {
        val state = rememberScrollState()
        Column(modifier = Modifier.verticalScroll(state = state)) {
            paths.forEach { path ->
                Text(text = path.toString())
            }
        }
    } else {
        Text(text = "No paths found")
    }
}