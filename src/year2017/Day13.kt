package year2017

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .asSequence()
            .map { line -> line.split(": ").map { it.toInt() } }
            .filter { it[0] % ((it[1] - 1) * 2) == 0 }
            .sumOf { it[0] * it[1] }
    }

    fun part2(input: List<String>): Int {
        val scanners = input
            .map { line -> line.split(": ").map { it.toInt() } }
        return generateSequence(0) { it + 1 }
            .first { delay ->
                scanners.none { (it[0] + delay) % ((it[1] - 1) * 2) == 0 }
            }
    }

    val input = readInput(2017, 13)
    part1(input).println()
    part2(input).println()
}