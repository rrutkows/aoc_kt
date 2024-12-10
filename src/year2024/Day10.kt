package year2024

import readInput
import show

fun main() {
    val dx = listOf(-1, 0, 1, 0)
    val dy = listOf(0, -1, 0, 1)

    fun solve(input: List<String>, ignoreVisited: Boolean): Int {
        val grid = input
            .map { line -> line.map { it.digitToInt() } }
        val w = grid[0].size
        val h = grid.size

        fun getScore(xStart: Int, yStart: Int): Int {
            val visited = mutableSetOf(xStart to yStart)
            val q = mutableListOf(xStart to yStart)
            var score = 0
            while (q.isNotEmpty()) {
                val (x, y) = q.removeLast()
                val height = grid[y][x]
                if (height == 9) {
                    score += 1
                } else {
                    for (i in dx.indices) {
                        val xNext = x + dx[i]
                        val yNext = y + dy[i]
                        if (xNext in 0..<w && yNext in 0..<h &&
                            grid[yNext][xNext] == height + 1 &&
                            (ignoreVisited || xNext to yNext !in visited)
                        ) {
                            visited.add(xNext to yNext)
                            q.add(xNext to yNext)
                        }
                    }
                }
            }

            return score
        }

        return grid
            .flatMapIndexed { y, row ->
                row.mapIndexed { x, height -> if (height == 0) getScore(x, y) else 0 }
            }
            .sum()
    }

    fun part1(input: List<String>) = solve(input, false)
    fun part2(input: List<String>) = solve(input, true)

    val input = readInput(2024, 10)
    show { part1(input) }
    show { part2(input) }
}