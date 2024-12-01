package year2024

import println
import readInput
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
        val (l1, l2) = input
            .map { line -> line.split("   ").map { it.toInt() }.let { it[0] to it[1] } }
            .unzip()
        val counts = mutableMapOf<Int, Int>()
        l2.forEach { counts[it] = 1 + counts.getOrDefault(it, 0) }

        return l1.sumOf { it * counts.getOrDefault(it, 0) }
    }

    val input = readInput(2024, 1)
    part1(input).println()
    part2(input).println()
}