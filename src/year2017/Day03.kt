package year2017

import println
import readInput
import kotlin.math.absoluteValue

private operator fun Pair<Int, Int>.plus(p: Pair<Int, Int>): Pair<Int, Int> =
    first + p.first to second + p.second

private fun Pair<Int, Int>.abs(): Pair<Int, Int> = first.absoluteValue to second.absoluteValue

fun main() {
    fun part1(input: Int): Int {
        // r0 1
        // r1 2 * 3 + 2 * 1 = 8
        // r2 2 * 5 + 2 * 3 = 16
        // r3 2 * 7 + 2 * 5 = 24

        var r = 0
        var rEnd = 1 // 1-based index of the last element in a ring

        // Find ring the element belongs to.
        while (rEnd < input) {
            r += 1
            rEnd += r * 8
        }

        // Move backwards within the ring. The distance from the center changes by either 1 or -1.
        var distance = 2 * r
        var m = -1
        while (rEnd > input) {
            rEnd -= 1
            distance += m
            if (distance == r) {
                m = 1
            } else if (distance == 2 * r) {
                m = -1
            }
        }

        return distance
    }

    fun part2(input: Int): Int {
        fun Map<Pair<Int, Int>, Int>.neighborsOf(coords: Pair<Int, Int>): Sequence<Int> {
            val (x, y) = coords
            return sequenceOf(
                x + 1 to y,
                x + 1 to y + 1,
                x to y + 1,
                x - 1 to y + 1,
                x - 1 to y,
                x - 1 to y - 1,
                x to y - 1,
                x + 1 to y - 1
            )
                .map { this[it] ?: 0 }
        }

        val values = mutableMapOf((0 to 0) to 1)
        val directions = listOf(
            0 to -1, // up
            -1 to 0, // left
            0 to 1, // down
            1 to 0, // right
        )

        var r = 0
        var i = 0
        var rEnd = 0 // 0-based index of the last element in a ring
        var coords = 0 to 0
        var value = 1
        var directionsIter = directions.iterator()
        var d = directionsIter.next()
        while (value < input) {
            if (i == rEnd) { //next "ring"
                r += 1
                coords = r to (r - 1)
                rEnd += r * 8
                directionsIter = directions.iterator()
                d = directionsIter.next()
            } else {
                if (coords.abs() == r to r) { //corner
                    d = directionsIter.next()
                }

                coords += d
            }

            i += 1
            value = values.neighborsOf(coords).sum()
            values[coords] = value
        }

        return value
    }

    val input = readInput(2017, 3).first().toInt()
    check(part1(1) == 0)
    check(part1(12) == 3)
    check(part1(23) == 2)
    check(part1(1024) == 31)
    check(part1(1) == 0)
    part1(input).println()

    part2(input).println()
}
