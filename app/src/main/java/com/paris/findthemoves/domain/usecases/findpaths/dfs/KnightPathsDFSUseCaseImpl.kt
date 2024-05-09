package com.paris.findthemoves.domain.usecases.findpaths.dfs

import com.paris.findthemoves.domain.usecases.findpaths.KnightPathsUseCase
import javax.inject.Inject

class KnightPathsDFSUseCaseImpl (private val dfs: KnightPathsDFS) :
    KnightPathsUseCase {
    override suspend fun findPathsAsync(
        size: Int,
        start: Pair<Int, Int>,
        end: Pair<Int, Int>,
        maxDepth: Int
    ): List<List<Pair<Int, Int>>> {
        return dfs.findPathsAsync(size, start, end, maxDepth)
    }
}