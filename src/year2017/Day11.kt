package year2017

import println
import readInput
import kotlin.math.abs
import kotlin.math.max

private operator fun Pair<Int, Int>.plus(p: Pair<Int, Int>) =
    first + p.first to second + p.second

fun main() {
    fun dist(coords: Pair<Int, Int>): Int {
        var (i, j) = coords
        if (i < 0) {
            i = -i
            j = -j
        }

        return i + if (j < 0) {
            max(abs(j) - i, 0)
        } else {
            j
        }
    }

    fun part1And2(input: String): Pair<Int, Int> {
        var maxDist = 0
        val coords = input
            .split(',')
            .fold(0 to 0) { acc, dir ->
                (acc + when (dir) {
                    "nw" -> -1 to 1
                    "n" -> 0 to 1
                    "ne" -> 1 to 0
                    "se" -> 1 to -1
                    "s" -> 0 to -1
                    "sw" -> -1 to 0
                    else -> 0 to 0
                }).also { maxDist = max(maxDist, dist(it)) }
            }
        return dist(coords) to maxDist
    }

    val input = readInput(2017, 11).single()
    part1And2(input).println()
}