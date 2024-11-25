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

    val input = readInput(2017, 7)
    part1(input).println()
}