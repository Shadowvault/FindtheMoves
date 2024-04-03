package com.paris.findthemoves.domain.usecases.findpaths.dfs

class KnightPathsDFS {
    operator fun invoke(
        size: Int,
        start: Pair<Int, Int>,
        end: Pair<Int, Int>,
        maxDepth: Int
    ): List<List<Pair<Int, Int>>> {
        val moves = arrayOf(
            intArrayOf(1, 2),
            intArrayOf(2, 1),
            intArrayOf(2, -1),
            intArrayOf(1, -2),
            intArrayOf(-1, -2),
            intArrayOf(-2, -1),
            intArrayOf(-2, 1),
            intArrayOf(-1, 2)
        )

        val paths: MutableList<List<Pair<Int, Int>>> = mutableListOf()
        val visited = Array(size) { BooleanArray(size) }

        val startPath = mutableListOf(start)
        visited[start.first][start.second] = true
        dfs(start, 0, maxDepth, startPath, paths, moves, visited, end, size)

        return paths
    }

    private fun dfs(
        current: Pair<Int, Int>,
        depth: Int,
        maxDepth: Int,
        path: MutableList<Pair<Int, Int>>,
        paths: MutableList<List<Pair<Int, Int>>>,
        moves: Array<IntArray>,
        visited: Array<BooleanArray>,
        end: Pair<Int, Int>,
        size: Int
    ) {
        if (current == end && depth <= maxDepth) {
            paths.add(path.toList())
            return
        }

        if (depth > maxDepth) return

        for (move in moves) {
            val newX = current.first + move[0]
            val newY = current.second + move[1]

            if (isValidMove(newX, newY, size) && !visited[newX][newY]) {
                visited[newX][newY] = true
                path.add(Pair(newX, newY))
                dfs(Pair(newX, newY), depth + 1, maxDepth, path, paths, moves, visited, end, size)
                path.removeAt(path.size - 1)
                visited[newX][newY] = false
            }
        }
    }

    private fun isValidMove(x: Int, y: Int, size: Int): Boolean {
        return x in 0 until size && y in 0 until size
    }
}