package com.paris.findthemoves.domain.usecases.chessmoves

interface PathsToChessMovesUseCase {
    suspend fun convertPathsToChessMoves(paths: List<List<Pair<Int, Int>>>, size: Int): String
}