package year2017

import println
import readInput

fun main() {
    fun part1And2(input: String): Pair<Int, Int> {
        var score = 0
        var garbageChars = 0
        var depth = 0
        var state = "Group" // "Garbage", "Ignore"
        input.forEach { c ->
            when (state) {
                "Group" -> when (c) {
                    '{' -> depth += 1
                    '}' -> score += depth.also { depth -= 1 }
                    '<' -> state = "Garbage"
                }

                "Garbage" -> when (c) {
                    '>' -> state = "Group"
                    '!' -> state = "Ignore"
                    else -> garbageChars += 1
                }

                "Ignore" -> state = "Garbage"
            }
        }
        return score to garbageChars
    }

    val input = readInput(2017, 9).single()
    part1And2(input).println()
}
