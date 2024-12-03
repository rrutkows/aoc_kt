package year2024

import readInput
import show

fun main() {
    fun solve(input: List<String>, pre: (String) -> String): Int {
        return input.joinToString(separator = "").run {
            Regex("""mul\((\d+),(\d+)\)""")
                .findAll(pre(this))
                .sumOf {
                    it.groupValues.run { this[1].toInt() * this[2].toInt() }
                }
        }
    }

    fun part1(input: List<String>) = solve(input) { it }
    fun part2(input: List<String>) = solve(input) {
        Regex("""don't\(\).*?do\(\)""").replace(it, "")
    }

    val input = readInput(2024, 3)
    show { part1(input) }
    show { part2(input) }
}