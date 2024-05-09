package com.paris.findthemoves.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.paris.findthemoves.presentation.components.utils.getScreenWidth

@Composable
fun Chessboard(
    size: Int,
    tileColor: (Int, Int) -> Long,
    tilePress: (Int, Int) -> Unit,
) {

    val tileSize = getScreenWidth() / size
    val boardSize = getScreenWidth()
    Column(
        modifier = Modifier.size(boardSize)
    ) {
        for (i in 0 until size) {
            Row {
                for (j in 0 until size) {
                    Box(
                        modifier = Modifier
                            .background(Color(tileColor(i, j)))
                            .size(tileSize)
                            .clickable { tilePress(i, j) }
                    )
                }
            }
        }
    }
}