package year2017

import combinations
import readInput
import show

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
                x.combinations()
                    .map { it.max() to it.min() }
                    .first { it.first % it.second == 0 }
                    .run { first / second }
            }
    }

    val input = readInput(2017, 2)
    show { part1(input) }
    show { part2(input) }
}