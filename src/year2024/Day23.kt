package year2024

import combinations
import readInput
import show

fun main() {
    fun part1(input: List<String>): Int {
        val all = mutableSetOf<String>()
        val connections = input
            .map { line ->
                line
                    .split("-")
                    .also {
                        all.add(it[0])
                        all.add(it[1])
                    }
                    .sorted()
                    .let { it[0] to it[1] }
            }
            .toSet()
        return all
            .sorted()
            .combinations(3)
            .count { x ->
                x[0] to x[1] in connections
                        && x[1] to x[2] in connections
                        && x[0] to x[2] in connections
                        && x.any { it.startsWith("t") }
            }
    }

    fun part2(input: List<String>): String {
        var biggest = listOf<String>()
        val connections = mutableMapOf<String, MutableSet<String>>()
        input.forEach { line ->
            val cs = line
                .split("-")
            connections.getOrPut(cs[0]) { mutableSetOf() }.add(cs[1])
            connections.getOrPut(cs[1]) { mutableSetOf() }.add(cs[0])
        }

        for ((k, v) in connections) {
            val groups = mutableListOf<MutableList<String>>()
            for (other in v) {
                val group = groups.firstOrNull { group -> group.all { it in connections[other]!! } }
                if (group == null) {
                    groups.add(mutableListOf(other))
                } else {
                    group.add(other)
                }
            }
            val group = groups.maxBy { it.size }
            if (group.size + 1 > biggest.size) {
                biggest = group + k
            }
        }

        return biggest.sorted().joinToString(separator = ",")
    }

    val input = readInput(2024, 23)

    show { part1(input) }
    show { part2(input) }
}