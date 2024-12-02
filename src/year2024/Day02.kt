package year2024

import readInput
import show

fun main() {
    fun isSafe(report: List<Int>): Boolean {
        val validDiff = when {
            report[1] > report[0] -> 1..3
            report[1] < report[0] -> -3..-1
            else -> return false
        }
        return report.windowed(2).all { (it[1] - it[0]) in validDiff }
    }

    fun List<Int>.removeOne(index: Int): List<Int> {
        return filterIndexed { i, _ -> i != index }
    }

    fun solve(input: List<String>, tolerate: (List<Int>) -> Boolean): Int {
        return input
            .asSequence()
            .map { line -> line.split(' ').map { it.toInt() } }
            .count { tolerate(it) }
    }

    fun part1(input: List<String>): Int = solve(input) { isSafe(it) }

    fun part2(input: List<String>): Int = solve(input) { report ->
        isSafe(report) || report.indices.any { isSafe(report.removeOne(it)) }
    }

    val input = readInput(2024, 2)
    show { part1(input) }
    show { part2(input) }
}