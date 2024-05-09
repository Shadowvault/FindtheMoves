package com.paris.findthemoves.domain.usecases.chessmoves

import com.paris.findthemoves.domain.usecases.findpaths.dfs.KnightPathsDFS
import javax.inject.Inject

class PathsToChessMovesUseCaseImpl (private val pathsToMoves: PathsToChessMoves): PathsToChessMovesUseCase {
    override suspend fun convertPathsToChessMoves(paths: List<List<Pair<Int, Int>>>, size: Int): String {
        return pathsToMoves.convertPathsToChessMovesAsync(paths, size)
    }
}