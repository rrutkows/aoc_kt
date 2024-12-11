package year2024

import readInput
import show
import kotlin.math.log10
import kotlin.math.pow

fun main() {
    fun solve(input: String, steps: Int): Long {
        var stones = input.split(" ").associate { it.toLong() to 1L }
        fun MutableMap<Long, Long>.add(key: Long, value: Long) {
            this[key] = getValue(key) + value
        }
        for (i in 0..<steps) {
            val next = mutableMapOf<Long, Long>().withDefault { 0L }
            for ((s, count) in stones) {
                if (s == 0L) {
                    next.add(1L, count)
                } else {
                    val digits = log10(s.toDouble()).toInt() + 1
                    if (digits % 2 == 0) {
                        val t = 10.0.pow(digits / 2).toLong()
                        next.add(s / t, count)
                        next.add(s - s / t * t, count)
                    } else {
                        next.add(s * 2024, count)
                    }
                }
            }
            stones = next
        }

        return stones.values.sum()
    }

    val input = readInput(2024, 11).single()
    fun part1(input: String) = solve(input, 25)
    fun part2(input: String) = solve(input, 75)
    show { part1(input) }
    show { part2(input) }
}