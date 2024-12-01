package year2024

import readInput
import show
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { line -> line.split("   ").map { it.toInt() }.let { it[0] to it[1] } }
            .unzip()
            .run { first.sorted().zip(second.sorted()) }
            .sumOf { abs(it.first - it.second) }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { line -> line.split("   ").map { it.toInt() }.let { it[0] to it[1] } }
            .unzip()
            .run { first to second.groupingBy { it }.eachCount() }
            .run { first.sumOf { it * second.getOrDefault(it, 0) } }
    }

    val input = readInput(2024, 1)
    show { part1(input) }
    show { part2(input) }
}