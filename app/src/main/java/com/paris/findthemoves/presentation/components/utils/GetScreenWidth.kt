package com.paris.findthemoves.presentation.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun getScreenWidth(): Dp {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val screenWidth = remember(configuration, context) {
        context.resources.displayMetrics.widthPixels.dp / context.resources.displayMetrics.density
    }

    return screenWidth
}