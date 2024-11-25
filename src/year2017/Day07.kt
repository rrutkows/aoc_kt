package year2017

import println
import readInput

fun main() {
    fun part1(input: List<String>): String {
        val re = Regex("[a-z]+")
        val all = mutableSetOf<String>()
        val held = mutableSetOf<String>()
        input.forEach { x ->
            val names = re.findAll(x).map { it.value }
            all.add(names.first())
            held.addAll(names.drop(1))
        }

        return all.first { it !in held }
    }

    fun part2(input: List<String>): Int {
        class Node(val weight: Int, val children: List<String>, var totalWeight: Int? = null)

        val nodes = input.associate { x ->
            val parts = Regex("""\w+""").findAll(x).map { it.value }.toList()
            parts[0] to Node(parts[1].toInt(), parts.drop(2))
        }

        fun Node.getTotalWeight(): Int {
            if (totalWeight == null) {
                totalWeight = weight + children.sumOf { nodes[it]!!.getTotalWeight() }
            }

            return totalWeight!!
        }

        for (node in nodes.values) {
            val groups = node.children.groupBy { nodes[it]!!.getTotalWeight() }
            if (groups.size == 2) {
                val expected = groups.filterValues { it.size > 1 }.keys.first()
                val incorrect = groups.filterValues { it.size == 1 }.keys.first()
                return nodes[groups[incorrect]!!.single()]!!.weight + (expected - incorrect)
            }
        }

        return 0
    }

    val input = readInput(2017, 7)
    part1(input).println()
    part2(input).println()
}