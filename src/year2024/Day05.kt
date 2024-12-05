package year2024

import combinations
import readInput
import show

fun main() {
    fun getOrderingRules(input: List<String>): Map<Int, List<Int>> {
        return input
            .takeWhile { it.isNotBlank() }
            .map { line -> line.split('|').map { it.toInt() } }
            .groupBy({ it[0] }, { it[1] })
    }

    fun part1(input: List<String>): Int {
        val rules = getOrderingRules(input)
        return input
            .takeLastWhile { it.isNotBlank() }
            .map { line -> line.split(',').map { it.toInt() } }
            .filter { update ->
                update.combinations().none {
                    it[0] in rules.getOrDefault(it[1], emptyList())
                }
            }
            .sumOf { it[it.size / 2] }
    }

    fun part2(input: List<String>): Int {
        val rules = getOrderingRules(input)
        return input
            .takeLastWhile { it.isNotBlank() }
            .map { line -> line.split(',').map { it.toInt() } }
            .filter { update ->
                update.combinations().any {
                    it[0] in rules.getOrDefault(it[1], emptyList())
                }
            }
            .map {
                it.sortedWith { a, b ->
                    if (b in rules.getOrDefault(a, emptyList())) -1 else 1
                }
            }
            .sumOf { it[it.size / 2] }
    }

    val input = readInput(2024, 5)
    show { part1(input) }
    show { part2(input) }
}