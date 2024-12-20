package year2024

import readInput
import show
import kotlin.math.abs

fun main() {
    data class Data(val walls: Set<Coords>, val start: Coords, val end: Coords)

    val dx = listOf(1, 0, -1, 0)
    val dy = listOf(0, 1, 0, -1)

    fun parse(input: List<String>): Data {
        val walls = mutableSetOf<Coords>()
        var start: Coords? = null
        var end: Coords? = null
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                when (c) {
                    '#' -> walls.add(x to y)
                    'S' -> start = x to y
                    'E' -> end = x to y
                }
            }
        }
        return Data(walls, start!!, end!!)
    }

    fun solve(input: List<String>, cheatAllowed: Int): Int {
        val (walls, s, e) = parse(input)
        var c = s
        var prev = -1 to -1
        val track = mutableMapOf(s to 0)
        while (c != e) {
            val (x, y) = c
            for (i in dx.indices) {
                val nextX = x + dx[i]
                val nextY = y + dy[i]
                if (nextX to nextY !in walls && nextX to nextY != prev) {
                    prev = c
                    c = nextX to nextY
                    track[c] = track[prev]!! + 1
                    break
                }
            }
        }

        var result = 0
        for (cheatStart in track.keys) {
            val (cx, cy) = cheatStart
            for (x in cx - cheatAllowed..cx + cheatAllowed) {
                for (y in cy - cheatAllowed..cy + cheatAllowed) {
                    val taxi = abs(x - cx) + abs(y - cy)
                    if (taxi in 2..cheatAllowed
                        && x to y in track
                        && track[cx to cy]!! + taxi <= track[x to y]!! - 100
                    ) {
                        result += 1
                    }
                }
            }
        }

        return result
    }

    fun part1(input: List<String>) = solve(input, 2)
    fun part2(input: List<String>) = solve(input, 20)

    val input = readInput(2024, 20)

    show { part1(input) }
    show { part2(input) }
}