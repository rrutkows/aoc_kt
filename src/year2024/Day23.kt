package year2024

import combinations
import readInput
import show

fun main() {
    fun part1(input: List<String>): Int {
        val connections = mutableMapOf<String, MutableList<String>>()
        input.forEach { line ->
            val cs = line
                .split("-")
            connections.getOrPut(cs[0]) { mutableListOf() }.add(cs[1])
            connections.getOrPut(cs[1]) { mutableListOf() }.add(cs[0])
        }

        val seen = mutableSetOf<List<String>>()
        for ((k, v) in connections) {
            if (k.startsWith("t")) {
                for (pair in v.combinations()) {
                    if (pair[0] in connections[pair[1]]!!) {
                        seen.add((pair + k).sorted())
                    }
                }
            }
        }

        return seen.size
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