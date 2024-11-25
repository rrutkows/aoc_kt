package year2017

import println
import readInput

fun main() {
    fun solve(input: List<String>, update: (Int) -> Int): Int {
        val jumps = input.map { it.toInt() }.toMutableList()
        var pos = 0
        var i = 0
        while (pos < jumps.size) {
            val next = pos + jumps[pos]
            jumps[pos] = update(jumps[pos])
            pos = next
            i++
        }

        return i
    }

    fun part1(input: List<String>) = solve(input) { it + 1 }
    fun part2(input: List<String>) = solve(input) { if (it >= 3) it - 1 else it + 1 }

    val input = readInput(2017, 5)
    part1(input).println()
    part2(input).println()
}