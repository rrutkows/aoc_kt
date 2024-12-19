package year2024

import readInput
import show

fun main() {
    fun part1(input: List<String>): Int {
        val patterns = input.first().split(", ")

        fun build(remaining: String): Boolean {
            if (remaining.isEmpty()) {
                return true
            }

            return patterns
                .filter { remaining.startsWith(it) }
                .any { build(remaining.substring(it.length)) }
        }

        return input
            .drop(2)
            .count(::build)
    }

    fun part2(input: List<String>): Long {
        val patterns = input.first().split(", ")

        val cache = mutableMapOf<String, Long>()
        fun build(remaining: String): Long {
            return cache.getOrPut(remaining) {
                if (remaining.isEmpty()) {
                    1L
                } else {
                    patterns
                        .filter { remaining.startsWith(it) }
                        .sumOf { build(remaining.substring(it.length)) }
                }
            }
        }

        return input
            .drop(2)
            .sumOf(::build)
    }

    val input = readInput(2024, 19)
    show { part1(input) }
    show { part2(input) }
}