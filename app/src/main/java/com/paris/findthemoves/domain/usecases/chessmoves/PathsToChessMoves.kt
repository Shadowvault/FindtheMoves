package com.paris.findthemoves.domain.usecases.chessmoves

import com.paris.findthemoves.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PathsToChessMoves @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun convertPathsToChessMovesAsync(paths: List<List<Pair<Int, Int>>>, size: Int): String =
        withContext(dispatcher) {
            paths.joinToString("\n") { moveList ->
                moveList.joinToString(" to ") { (x, y) ->
                    ('A' + y).toString() + (size - x)
                }
            }
        }
}