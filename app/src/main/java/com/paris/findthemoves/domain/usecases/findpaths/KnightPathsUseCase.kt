package com.paris.findthemoves.domain.usecases.findpaths

interface KnightPathsUseCase {
    suspend fun findPathsAsync(size: Int, start: Pair<Int, Int>, end: Pair<Int, Int>, maxDepth: Int): List<List<Pair<Int, Int>>>
}