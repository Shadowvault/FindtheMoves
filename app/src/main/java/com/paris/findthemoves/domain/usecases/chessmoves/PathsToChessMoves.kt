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
            val chessMoves = mutableListOf<String>()
            for (moveList in paths) {
                val moves = StringBuilder()
                for (move in moveList) {
                    val (x, y) = move
                    val col = ('A' + y).toString()
                    val row = (size - x).toString()
                    moves.append("$col$row to ")
                }
                moves.delete(moves.length - 4, moves.length)
                chessMoves.add(moves.toString())
            }
            return@withContext chessMoves.joinToString(separator = "\n")
        }
}