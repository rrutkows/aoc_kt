package year2017

import combinations
import println
import readInput

fun main() {
    fun List<String>.parse() = asSequence()
        .map { x -> x.split('\t').map { it.toInt() } }

    fun part1(input: List<String>): Int {
        return input.parse()
            .sumOf { it.max() - it.min() }
    }

    fun part2(input: List<String>): Int {
        return input.parse()
            .sumOf { x ->
                val pair = x.combinations()
                    .map { it.max() to it.min() }
                    .first { it.first % it.second == 0 }
                pair.first / pair.second
            }
    }

    val input = readInput(2017, 2)
    part1(input).println()
    part2(input).println()
}