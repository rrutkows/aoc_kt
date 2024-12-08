package year2024

import readInput
import show

fun main() {

    val directions = listOf(
        0 to -1,
        1 to 0,
        0 to 1,
        -1 to 0
    )

    fun walk(input: List<String>, start: Coords, obstacle: Coords? = null): Pair<Boolean, Set<Coords>> {
        fun isObstacle(x: Int, y: Int): Boolean {
            return input[y][x] == '#' || x to y == obstacle
        }

        val w = input[0].length
        val h = input.size
        var dIdx = 0
        var (dx, dy) = directions[dIdx]
        var (x, y) = start
        val visited = mutableSetOf<Coords>()
        val visitedWithDir = mutableSetOf<Pair<Coords, Int>>()
        while (x in 0..<w && y in 0..<h) {
            visited.add(x to y)

            if (!visitedWithDir.add((x to y) to dIdx)) {
                // cycle!
                return true to visited
            }

            while (x + dx in 0..<w && y + dy in 0..<h && isObstacle(x + dx, y + dy)) {
                dIdx = (dIdx + 1) % directions.size
                dx = directions[dIdx].first
                dy = directions[dIdx].second
            }
            x += dx
            y += dy
        }

        return false to visited
    }

    fun part1(input: List<String>): Int {
        val y = input.indexOfFirst { '^' in it }
        val x = input[y].indexOf('^')
        return walk(input, x to y).second.size
    }

    fun part2(input: List<String>): Int {
        val y = input.indexOfFirst { '^' in it }
        val x = input[y].indexOf('^')
        val visited = walk(input, x to y).second
        return visited
            .filter { it != x to y }
            .count { walk(input, x to y, it).first }
    }

    val input = readInput(2024, 6)
    show { part1(input) }
    show { part2(input) }
}